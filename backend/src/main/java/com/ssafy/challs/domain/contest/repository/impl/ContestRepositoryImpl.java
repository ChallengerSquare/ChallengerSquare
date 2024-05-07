package com.ssafy.challs.domain.contest.repository.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.challs.domain.contest.dto.request.ContestSearchRequestDto;
import com.ssafy.challs.domain.contest.entity.Contest;
import com.ssafy.challs.domain.contest.entity.QContest;
import com.ssafy.challs.domain.contest.repository.ContestRepositoryCustom;
import com.ssafy.challs.domain.team.entity.QTeam;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContestRepositoryImpl implements ContestRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Contest> searchContest(ContestSearchRequestDto searchRequestDto, Pageable pageable) {
		QContest contest = QContest.contest;
		QTeam team = QTeam.team;

		BooleanBuilder whereBuilder = new BooleanBuilder();

		// 검색 키워드 조건
		if (searchRequestDto.keyword() != null && !searchRequestDto.keyword().isEmpty()) {
			BooleanExpression keywordInTitle = contest.contestTitle.containsIgnoreCase(searchRequestDto.keyword());
			BooleanExpression keywordInTeamName = team.teamName.containsIgnoreCase(searchRequestDto.keyword());
			BooleanExpression keywordCondition = keywordInTitle.or(keywordInTeamName);
			whereBuilder.and(keywordCondition);
		}

		// 카테고리 조건
		if (searchRequestDto.category() != null) {
			BooleanExpression categoryCondition = contest.contestCategory.eq(searchRequestDto.category());
			whereBuilder.and(categoryCondition);
		}

		// 종료된 대회 조건
		if (Boolean.TRUE.equals(searchRequestDto.isEnd())) {
			BooleanExpression isEndCondition = contest.contestState.eq('E');
			whereBuilder.and(isEndCondition);
		}

		// 결과 쿼리 실행
		List<Contest> results = queryFactory.selectFrom(contest)
			.innerJoin(contest.team, team)
			.where(whereBuilder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 효율적인 전체 개수 계산을 위해 JPAQuery<Long> 객체 생성
		JPAQuery<Long> countQuery = createCountQuery(contest, team, whereBuilder);

		// Page 객체 생성 후 반환
		return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
	}

	private JPAQuery<Long> createCountQuery(QContest contest, QTeam team, BooleanBuilder whereBuilder) {
		// JPAQuery<Long> 객체를 생성하여 반환
		return queryFactory
			.select(contest.count())
			.from(contest)
			.leftJoin(contest.team, team)
			.where(whereBuilder);
	}
}