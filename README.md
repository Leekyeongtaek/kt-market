# KT-MARKET 포트폴리오
## 프로젝트 주제
- 상품의 관리, 회원의 상품 구매와 영수증 발급 기능을 제공하는 소규모 마트 애플리케이션

## 목차
- 프로젝트 상세
- 요구사항 분석
- 테이블 ERD
- 엔티티 ERD
- 핵심 기능과 코드
- 테스트 코드

### 프로젝트 상세
- Java : 17
- SpringBoot : 3.2.4
- MySql : 8.0.32
- QueryDSL : 5.0.0

### 요구사항 분석
- 기능 목록(Api)
  - 회원 관련 기능
    - 회원 등록
    - 회원 조회
    - 영수증 분석(회원 포인트)
  - 상품 관련 기능
    - 상품 등록
    - 상품 수정
    - 상품 조회
    - 상품 목록 조회
  - 영수증 관련 기능
    - 영수증 발급
    - 영수증 조회
    - 영수증 목록 조회

- 기능 요구사항 상세
  - 회원
    - 회원 등록
      - 이미 존재하는 아이디는 등록할 수 없다.
      - 회원 포인트 엔티티와 일대일 관계를 맺는다.
  - 상품
    - 상품 등록
      - 이미 존재하는 상품명은 등록할 수 없다.
    - 상품 수정
      - 상품 이름, 가격, 재고는 수정이 가능하다.
      - 상품 타입은 수정이 불가능하다.
    - 상품 목록 조회
      - 페이징 기능 적용
      - 상품명을 통한 검색이 가능하다.
      - 상품 가격의 최솟값, 최댓값을 통한 검색이 가능하다.
      - 상품 타입으로 검색이 가능하다.
  - 영수증
    - 영수증 발급
      - 구매 상품 데이터에 대한 유효성 검증 필요.
      - 사용하려는 포인트는 예상 결제 금액보다 클 수 없다.
      - 영수증의 상태가 결제(PAYED)로 변경 된다.
      - 현금 결제 금액과 회원 등급에 따라 회원 포인트를 적립한다.
      - 포인트를 사용한 경우 사용한 포인트만큼 회원 포인트를 차감한다.
      - 포인트만을 사용해서 결제한 경우 회원 포인트를 적립하지 않는다.
    - 영수증 조회
      - 사용한 포인트, 총결제금액, 실제 결제금액, 발행일 정보를 포함해야 한다.
    - 영수증 목록 조회
      - 페이징 기능 적용
  - 기타
    - 회원 등급
      - BRONZE, SILVER, GOLD로 분류한다.
      - 최초 가입시 회원 등급은 BRONZE 상태이다.
      - 회원 등급에 따라 영수증 발급시 현금 결제 금액에서 BRONZE(0.01), SILVER(0.03), GOLD(0.05)만큼 포인트를 적립한다.
      - 누적 포인트가 1000 포인트 이상인 경우 SILVER, 2000 포인트 이상인 경우 GOLDE 등급으로 변경된다.

### 테이블 ERD
<img width="800" alt="kt_market_ERD1" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/3d0ff157-eb06-46eb-bf3f-7866d9a3cf0f">

### 엔티티 ERD
<img width="800" alt="kt_market_ERD2" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/603fbe23-3eb7-4575-870e-f67010dea1da">

### 핵심 기능과 코드
<img width="1151" alt="포인트1" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/ed617042-3128-4df7-8755-20b8efc09912">
<img width="1153" alt="포인트2" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/23ee600f-6036-4286-9b0c-0c36a77ecf88">
<img width="1155" alt="포인트3" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/7260d468-f3af-46d8-be70-e1a8fc252b7d">
<img width="1148" alt="포인트4" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/afb4c44b-ca95-4ed6-aa60-afaf9c3616a6">
<img width="1149" alt="포인트5" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/cf68e679-92d1-4334-b99a-79bea94f6175">
<img width="1165" alt="포인트6" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/be525c36-2c12-40c8-a5eb-4ea0969c7db2">
<img width="1150" alt="포인트7" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/ed86c093-f701-4500-8e5d-b79130d71cbf">

### 테스트 코드
