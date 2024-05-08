from apscheduler.schedulers.background import BackgroundScheduler
from apscheduler.executors.pool import ThreadPoolExecutor
from blockchain import Blockchain
import pytz


blockchain = Blockchain.get_blockchain()


def mine_block_regularly():
    previous_block = blockchain.get_previous_block()
    previous_proof = previous_block['proof']
    proof = blockchain.proof_of_work(previous_proof)
    previous_hash = blockchain.hash(previous_block)
    blockchain.create_block(proof, previous_hash)
    print("Block has been mined")
    pass


def replace_chain_regularly():
    is_chain_replaced = blockchain.replace_chain()
    if is_chain_replaced:
        print("체인이 교체되었습니다.")
    else:
        print("현재 체인이 가장 길어 교체하지 않았습니다.")
    pass


def start_scheduler():
    executors = {
        'default': ThreadPoolExecutor(1)  # 동시에 하나의 작업만 실행
    }
    scheduler = BackgroundScheduler(executors=executors, timezone=pytz.timezone('Asia/Seoul'))
    scheduler.add_job(func=mine_block_regularly, trigger="interval", seconds=10, max_instances=1)
    scheduler.add_job(func=replace_chain_regularly, trigger="interval", minutes=1, max_instances=1)
    scheduler.start()


if __name__ == "__main__":
    start_scheduler()