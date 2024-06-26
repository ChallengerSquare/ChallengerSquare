package com.ssafy.challs.domain;

import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import com.ssafy.challs.domain.auth.jwt.dto.SecurityMember;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

	@Override
	public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
		SecurityMember member = new SecurityMember(Long.valueOf(annotation.id()), Boolean.valueOf(annotation.role()));

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member, "password",
			member.getAuthorities());
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(token);
		return context;
	}
}
