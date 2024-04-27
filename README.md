# KT-MARKET 포트폴리오
## 목차
- 프로젝트 상세
- 요구사항 분석
- 테이블 ERD
- 엔티티 ERD
- 핵심 기능과 코드
- 요구사항과 테스트 코드

### 프로젝트 상세
- Java : 17
- SpringBoot : 3.2.4
- MySql : 8.0.32
- QueryDSL : 5.0.0

### 요구사항 분석
- 기능 목록
  - 회원 기능
    - 회원 등록
    - 회원 조회
  - 상품 기능
    - 상품 등록
    - 상품 수정
    - 상품 조회
    - 상품 목록 조회
  - 영수증 기능
    - 영수증 발급
    - 영수증 조회
    - 영수증 목록 조회

- 요구사항 상세
  - 회원
    - 회원 등록
      - 이미 존재하는 아이디는 등록할 수 없다.
      - 회원마다 각자의 회원 포인트를 보유한다.
  - 상품
    - 상품 등록
      - 이미 존재하는 상품명은 등록할 수 없다.
    - 상품 수정
      - 상품 이름, 가격, 재고만 수정이 가능하다.
    - 상품 목록 조회
      - 페이징 처리가 가능하다.
      - 상품명을 통한 검색이 가능하다.
      - 상품 가격의 최솟값, 최댓값을 통한 검색이 가능하다.
      - 상품 타입으로 검색이 가능하다.
  - 영수증
    - 

### 테이블 ERD
<img width="800" alt="kt_market_ERD1" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/3d0ff157-eb06-46eb-bf3f-7866d9a3cf0f">

### 엔티티 ERD
<img width="800" alt="kt_market_ERD2" src="https://github.com/Leekyeongtaek/kt-market/assets/56353425/603fbe23-3eb7-4575-870e-f67010dea1da">
