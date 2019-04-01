# Fairy Project

## Icon

<img src="http://selab.sogang.ac.kr/img/fairy/logo.png" width="64px" height="64px">

## Abstract

<img src="http://selab.sogang.ac.kr/img/fairy/what.png">  

## REST Application Programming Interface

### GET
| ACTION  |      URI      |  DESCRIPTION | ARGUMENT |
|:------------------:|:-------------:|:------:|------:|
| GET |/transaction/list|현재 생성된 모든 블록들에 포함된 트랜잭션 조회 |-
| GET |/transaction/search| 특정 type의 트랜잭션만 조회 | type:TransactionType
| GET |/block/list| 현재 생성된 모든 블록의 데이터를 조회 | -
| GET |/block/lastest| 최근 생성된 블록의 데이터를 조회 | -
| GET |/block/lastest/height| 최근 생성된 블록의 높이 값 | -


### POST
| ACTION  |      URI      |  DESCRIPTION | ARGUMENT |
|:------------------:|:-------------:|:------:|------:|
| POST |/transaction/token|토큰 트랜잭션을 발행 |-



#### post.transaction.token
 - ftxid: 이전 트랜잭션 아이디
 - ftxaddress: input 값을 가져올 주소
 - address: value(token의 량, double)

made by blockchain and software engineering lab., sogang univ.
