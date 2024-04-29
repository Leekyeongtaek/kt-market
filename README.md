# KT-MARKET 포트폴리오
## 프로젝트 주제
- 상품의 관리, 회원의 상품 구매와 영수증 발급 기능을 제공하는 소규모 마켓 프로그램

## 검증 기능 코드 참고 링크
- 영수증 유효성 검증 부분은 조영호 님의 우아한 객체지향 강의에서 코드를 참고하여 작성했습니다.
- [우아한객체지향](https://www.youtube.com/watch?v=dJ5C4qRqAgA)

## 목차
- [프로젝트 상세](#프로젝트-상세)
- [요구사항 분석](#요구사항-분석)
- [테이블 ERD](#테이블-ERD)
- [엔티티 ERD](#엔티티-ERD)
- [핵심 기능과 코드](#핵심-기능과-코드)
- [테스트 코드](#테스트-코드)

### 프로젝트 상세
- IDE : IntelliJ
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
#### MemberGrade
```
class MemberGradeTest {

    @ValueSource(ints = 10000)
    @ParameterizedTest(name = "paymentAmount")
    void 회원등급이_BRONZE라면_결제금액의_1퍼센트를_멤버십_포인트로_반환(int paymentAmount) {
        //given
        MemberGrade bronze = MemberGrade.BRONZE;

        //when
        int membershipPoint = bronze.calculateMembershipPoint(paymentAmount);

        //then
        Assertions.assertThat(membershipPoint).isEqualTo(100);
    }

    @ValueSource(ints = 10000)
    @ParameterizedTest(name = "paymentAmount")
    void 회원등급이_SILVER라면_결제금액의_3퍼센트를_멤버십_포인트로_반환(int paymentAmount) {
        //given
        MemberGrade silver = MemberGrade.SILVER;

        //when
        int membershipPoint = silver.calculateMembershipPoint(paymentAmount);

        //then
        Assertions.assertThat(membershipPoint).isEqualTo(300);
    }

    @ValueSource(ints = 10000)
    @ParameterizedTest(name = "paymentAmount")
    void 회원등급이_GOLD라면_결제금액의_5퍼센트를_멤버십_포인트로_반환(int paymentAmount) {
        //given
        MemberGrade gold = MemberGrade.GOLD;

        //when
        int membershipPoint = gold.calculateMembershipPoint(paymentAmount);

        //then
        Assertions.assertThat(membershipPoint).isEqualTo(500);
    }
}
```
#### Member
```
class MemberTest {

    @Test
    void 회원등급이_BRONZE이고_누적포인트가_1000이상이_되면_멤버십_업그레이드시_SILVER_등급이_된다() {
        //given
        MemberPoint memberPoint = MemberPoint.builder()
                .memberPointItems(List.of(MemberPointItem.builder().build()))
                .accumulatedPoint(1000)
                .build();

        Member member = Member.builder()
                .memberPoint(memberPoint)
                .grade(BRONZE)
                .build();

        //when
        member.upgradeMemberGrade();

        //then
        assertThat(member.getGrade()).isEqualTo(SILVER);
    }

    @Test
    void 회원등급이_SILVER이고_누적포인트가_2000이상이_되면_멤버십_업그레이드시_GOLD_등급이_된다() {
        //given
        MemberPoint memberPoint = MemberPoint.builder()
                .memberPointItems(List.of(MemberPointItem.builder().build()))
                .accumulatedPoint(2000)
                .build();

        Member member = Member.builder()
                .memberPoint(memberPoint)
                .grade(SILVER)
                .build();

        //when
        member.upgradeMemberGrade();

        //then
        assertThat(member.getGrade()).isEqualTo(GOLD);
    }

}
```
#### MemberPointItem
```
class MemberPointTest {

    private MemberPoint memberPoint;

    @BeforeEach
    void setUp() {
        memberPoint = MemberPoint.builder()
                .memberPointItems(new ArrayList<>())
                .availablePoint(1000)
                .accumulatedPoint(1000)
                .build();

        Member.builder()
                .grade(MemberGrade.BRONZE)
                .memberPoint(memberPoint)
                .build();
    }

    @Test
    void 영수증_발급시_포인트를_사용하지_않고_현금으로만_결제한_경우_멤버십포인트가_적립된다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .usePoint(0)
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        memberPoint.parseReceipt(receipt);

        //then
        assertThat(memberPoint.getAvailablePoint()).isEqualTo(1010);
        assertThat(memberPoint.getAccumulatedPoint()).isEqualTo(1010);
        assertThat(memberPoint.getMemberPointItems().size()).isEqualTo(1);
        assertThat(memberPoint.getMemberPointItems()).extracting("point").containsExactly(10);
        assertThat(memberPoint.getMemberPointItems()).extracting("type").containsExactly(MEMBERSHIP);
    }

    /*
    회원 등급에 따른 포인트 적립은 포인트만을 사용해서 결제 했다면 포인트 적립은 발생하지 않는다.
    실제 결제 금액이 0보다 큰 경우에만 결제 금액에 따라 포인트가 적립된다.
     */
    @Test
    void 영수증_발급시_포인트를_사용한_경우_사용한_포인트_만큼_보유_포인트에서_차감된다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .usePoint(1000)
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        memberPoint.parseReceipt(receipt);

        //then
        assertThat(memberPoint.getAvailablePoint()).isEqualTo(0);
        assertThat(memberPoint.getAccumulatedPoint()).isEqualTo(1000);
        assertThat(memberPoint.getMemberPointItems().size()).isEqualTo(1);
        assertThat(memberPoint.getMemberPointItems()).extracting("point").containsExactly(-1000);
        assertThat(memberPoint.getMemberPointItems()).extracting("type").containsExactly(PAYMENT);
    }

    @Test
    void 영수증_발급시_현금과_포인트를_함께_사용하면_보유_포인트는_차감되고_실제_결제금액만큼_포인트가_적립된다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .usePoint(500)
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        memberPoint.parseReceipt(receipt);

        //then
        assertThat(memberPoint.getAvailablePoint()).isEqualTo(505);
        assertThat(memberPoint.getAccumulatedPoint()).isEqualTo(1005);
        assertThat(memberPoint.getMemberPointItems().size()).isEqualTo(2);
        assertThat(memberPoint.getMemberPointItems()).extracting("point").containsExactly(-500, 5);
        assertThat(memberPoint.getMemberPointItems()).extracting("type").containsExactly(PAYMENT, MEMBERSHIP);
    }
}
```
#### Product
```
class ProductTest {

    @Test
    void ProductSave_객체로_Product_객체를_생성할_수_있다() {
        //given
        ProductSave productSave = new ProductSave("동원)양반죽", 2000, 2, FOOD);

        //when
        Product product = productSave.toEntity();

        //then
        assertThat(product.getName()).isEqualTo("동원)양반죽");
        assertThat(product.getPrice()).isEqualTo(2000);
        assertThat(product.getStock()).isEqualTo(2);
        assertThat(product.getType()).isEqualTo(FOOD);
    }

    @Test
    void ProductUpdate_객체로_Product_객체를_수정할_수_있다() {
        //given
        ProductUpdate productUpdate = new ProductUpdate("동원)양반죽 수정", 2000, 10);

        Product product = Product.builder()
                .name("동원)양반죽")
                .price(1000)
                .stock(5)
                .build();

        //when
        product.update(productUpdate);

        //then
        assertThat(product.getName()).isEqualTo("동원)양반죽 수정");
        assertThat(product.getPrice()).isEqualTo(2000);
        assertThat(product.getStock()).isEqualTo(15);
    }

}
```
#### Product 검색
```
@Import(TestConfig.class)
@DataJpaTest
class ProductQueryRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductQueryRepository productQueryRepository;

    @BeforeEach
    void setUp() {
        Product product1 = Product.builder()
                .name("삼립)오븐에구운도넛")
                .type(Product.Type.FOOD)
                .price(1200)
                .build();

        Product product2 = Product.builder()
                .name("롯데)도리토스나쵸치즈")
                .type(SNACK)
                .price(1700)
                .build();

        Product product3 = Product.builder()
                .name("농심)양파링")
                .type(SNACK)
                .price(1500)
                .build();

        Product product4 = Product.builder()
                .name("매일)우유속에딸기")
                .type(DRINK)
                .price(1900)
                .build();

        Product product5 = Product.builder()
                .name("매일)우유속에코코아")
                .type(DRINK)
                .price(1900)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3, product4, product5));
    }

    @Test
    void 상품_검색시_상품명으로_상품을_검색할_수_있다() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        ProductSearchCondition searchCondition = new ProductSearchCondition("매일", null, null, null);

        //when
        List<ProductQuery> content = productQueryRepository.searchProduct(pageRequest, searchCondition).getContent();

        //then
        assertThat(content.stream().allMatch(p -> p.getName().contains("우유"))).isTrue();
    }

    @Test
    void 상품_검색시_상품_가격으로_상품을_검색할_수_있다() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        ProductSearchCondition searchCondition = new ProductSearchCondition(null, 1200, 1700, null);

        //when
        List<ProductQuery> content = productQueryRepository.searchProduct(pageRequest, searchCondition).getContent();

        //then
        assertThat(content.stream().allMatch(p -> p.getPrice() >= 1200 && p.getPrice() <= 1700)).isTrue();
    }

    @Test
    void 상품_검색시_상품_타입으로_상품을_검색할_수_있다() {
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);
        ProductSearchCondition searchCondition = new ProductSearchCondition(null, null, null, SNACK);

        //when
        List<ProductQuery> content = productQueryRepository.searchProduct(pageRequest, searchCondition).getContent();

        //then
        assertThat(content.stream().allMatch(p -> p.getType().equals(SNACK))).isTrue();
    }
}
```
#### Receipt
```
class ReceiptTest {

    @Test
    void 결제완료() {
        //given
        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(ReceiptItem.builder().build()))
                .build();

        //when
        receipt.payed();

        //then
        assertThat(receipt.getStatus()).isEqualTo(PAYED);
    }

    @Test
    void 결제취소() {
        //given
        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(ReceiptItem.builder().build()))
                .status(PAYED)
                .build();

        //when
        receipt.cancel();

        //then
        assertThat(receipt.getStatus()).isEqualTo(CANCEL);
    }

    @Test
    void 총결제금액은_구매상품의_상품금액_곱하기_구매수량의_합이다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(2000)
                .quantity(5)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .build();

        //when
        int totalPrice = receipt.calculateTotalPrice();

        //then
        assertThat(totalPrice).isEqualTo(10000);
    }

    @Test
    void 실제_결제금액은_총결제금액에서_사용포인트를_차감한_금액이다() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(2000)
                .quantity(5)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .usePoint(1000)
                .build();

        //when
        int paymentAmount = receipt.calculatePaymentAmount();

        //then
        assertThat(paymentAmount).isEqualTo(9000);
    }

    @Test
    void 현금결제_여부는_예상_결제금액에서_사용포인트를_차감한_금액이_0보다_큰_경우_True_반환() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .usePoint(500)
                .build();

        //when
        boolean isCashPayment = receipt.isCashPayment();

        //then
        assertThat(isCashPayment).isTrue();
    }

    @Test
    void 포인트_사용여부는_사용포인트_값이_0보다_큰_경우_True_반환() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();

        Receipt receipt = Receipt.builder()
                .receiptItems(List.of(receiptItem))
                .usePoint(500)
                .build();

        //when
        boolean isUsePoint = receipt.isUsePoint();

        //then
        assertThat(isUsePoint).isTrue();
    }

    @Test
    void 사용하려는_포인트금액이_예상_결제금액보다_큰_경우_예외발생() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder()
                .productPrice(1000)
                .quantity(1)
                .build();


        Receipt receipt = Receipt.builder()
                .usePoint(1001)
                .receiptItems(List.of(receiptItem))
                .build();

        //when + then
        assertThatThrownBy(receipt::validatePointUsePolicy)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용 포인트는 예상 결제 금액보다 클 수 없습니다.");
    }
}
```
#### ProductCounter
```
class ProductCounterTest {

    @Test
    void ProductCart_객체로_Receipt_객체를_생성할_수_있다() {
        //given
        ProductCart.ProductCartItem productCartItem1 = new ProductCart.ProductCartItem(1L, "매일우유", 1000, 5);
        ProductCart.ProductCartItem productCartItem2 = new ProductCart.ProductCartItem(2L, "서울우유", 1200, 1);
        ProductCart productCart = new ProductCart(1L, List.of(productCartItem1, productCartItem2), 100);

        //when
        Receipt receipt = new ProductCounter().unpackProductCart(productCart);

        //then
        assertThat(receipt.getMemberId()).isEqualTo(1L);
        assertThat(receipt.getUsePoint()).isEqualTo(100);
        assertThat(receipt.getReceiptItems().size()).isEqualTo(2);
        assertThat(receipt.getReceiptItems()).extracting("productId").containsExactly(1L, 2L);
        assertThat(receipt.getReceiptItems()).extracting("productName").containsExactly("매일우유", "서울우유");
        assertThat(receipt.getReceiptItems()).extracting("productPrice").containsExactly(1000, 1200);
        assertThat(receipt.getReceiptItems()).extracting("quantity").containsExactly(5, 1);
    }
}
```
#### ReceiptValidator
```
class ReceiptValidatorTest {

    ReceiptValidator receiptValidator;

    @BeforeEach
    void setUp() {
        receiptValidator = new ReceiptValidator(Mockito.mock(ProductRepository.class));
    }

    @Test
    void 유효성_검증_성공() {
        //given
        ReceiptItem receiptItem = ReceiptItem.builder().productId(1L).productName("매일우유").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1000).build();

        //when + then
        Assertions.assertThatNoException().isThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }}));
    }

    @Test
    void 상품번호가_존재하지_않는_상품_번호인_경우_예외발생() {
        ReceiptItem receiptItem = ReceiptItem.builder().productId(2L).productName("매일우유").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1000).build();

        Assertions.assertThatThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("존재하지 않는 상품 번호입니다.");
    }

    @Test
    void 상품명이_일치하지_않으면_예외발생() {
        ReceiptItem receiptItem = ReceiptItem.builder().productId(1L).productName("매일우유 변경").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1000).build();

        Assertions.assertThatThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("상품명이 변경되었습니다.");
    }

    @Test
    void 상품가격이_일치하지_않으면_예외발생() {
        ReceiptItem receiptItem = ReceiptItem.builder().productId(1L).productName("매일우유").productPrice(1000).quantity(1).build();

        Receipt receipt = Receipt.builder().receiptItems(List.of(receiptItem)).build();

        Product product = Product.builder().name("매일우유").price(1200).build();

        Assertions.assertThatThrownBy(() -> receiptValidator.validate(receipt, new HashMap<>() {{
            put(1L, product);
        }})).isInstanceOf(IllegalArgumentException.class).hasMessage("가격이 변경되었습니다.");
    }

}
```
