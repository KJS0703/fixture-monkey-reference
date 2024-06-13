package option.instantiate;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.instantiator.Instantiator;
import com.navercorp.fixturemonkey.api.type.TypeReference;
import option.instantiate.constructor.AllArgsBeforeNoArgs;
import option.instantiate.constructor.NoArgsBeforeAllArgs;
import option.instantiate.constructor.TwoCustomConstructors;
import option.instantiate.constructor.GenericObject;
import option.instantiate.constructor.NestedProduct;
import option.instantiate.constructor.ProductList;
import option.instantiate.factory_method.TwoFactoryMethods;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

/*
    https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/generating-objects/instantiate-methods/#%EC%83%9D%EC%84%B1%EC%9E%90
    위 페이지에 있는 docs 내용에 대한 학습 테스트 들.
 */
class InstantiateTest {

    @RepeatedTest(10)
    @DisplayName("constructor() - 기본생성자 있으면 기본 생성자를 사용한다. - 생성자 순서 무시")
    void test_100() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        NoArgsBeforeAllArgs instant = fixtureMonkey.giveMeBuilder(NoArgsBeforeAllArgs.class)
                                                  .instantiate(Instantiator.constructor())
                                                  .sample();

        Assertions.assertThat(instant.name).isNullOrEmpty();
        Assertions.assertThat(instant.job).isNullOrEmpty();
        Assertions.assertThat(instant.onlyInitiatedByNoArgs).isTrue();

        AllArgsBeforeNoArgs instant_02 = fixtureMonkey.giveMeBuilder(AllArgsBeforeNoArgs.class)
                                                      .instantiate(Instantiator.constructor())
                                                      .sample();

        Assertions.assertThat(instant_02.name).isNullOrEmpty();
        Assertions.assertThat(instant_02.job).isNullOrEmpty();
        Assertions.assertThat(instant_02.onlyInitiatedByNoArgs).isTrue();
    }

    @RepeatedTest(10)
    @DisplayName("constructor() - 기본 생성자가 없으면 첫 번째 작성된 생성자를 사용함.")
    void test_200() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        TwoCustomConstructors instant = fixtureMonkey.giveMeBuilder(TwoCustomConstructors.class)
                                                    .instantiate(Instantiator.constructor())
                                                    .sample();

        Assertions.assertThat(instant.options).isEmpty();
    }

    @RepeatedTest(10)
    @DisplayName("constructor() - 특정 생성자를 선택할 수 있다.")
    void test_300() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        TwoCustomConstructors instant = fixtureMonkey.giveMeBuilder(TwoCustomConstructors.class)
                                       .instantiate(
                                           Instantiator.constructor()
                                                       .parameter(String.class)
                                                       .parameter(long.class)
                                                       .parameter(long.class)
                                       )
                                       .sample();

        Assertions.assertThat(instant.options).isEmpty();
        Assertions.assertThat(instant.productName).isNotEqualTo("defaultProductName");


        instant = fixtureMonkey.giveMeBuilder(TwoCustomConstructors.class)
                                       .instantiate(
                                           Instantiator.constructor()
                                                       .parameter(long.class)
                                                       .parameter(long.class)
                                                       .parameter(new TypeReference<List<String>>() {})
                                       )
                                       .sample();

        Assertions.assertThat(instant.productName).isEqualTo("defaultProductName");
    }

    @RepeatedTest(10)
    @DisplayName("constructor() - 매개변수 이름으로 힌트 제공")
    void test_301() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        TwoCustomConstructors instant = fixtureMonkey.giveMeBuilder(TwoCustomConstructors.class)
                                                     .instantiate(
                                                         Instantiator.constructor()
                                                                     .parameter(String.class, "pName")
                                                                     .parameter(long.class)
                                                                     .parameter(long.class)
                                                     )
                                                     .set("pName", "제품 이름이요.")
                                                     .sample();

        Assertions.assertThat(instant.productName).isEqualTo("제품 이름이요.");
    }

    @RepeatedTest(10)
    @DisplayName("constructor() - 매개변수 이름으로 힌트 제공하면 필드명 사용불가")
    void test_302() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        TwoCustomConstructors instant = fixtureMonkey.giveMeBuilder(TwoCustomConstructors.class)
                                                     .instantiate(
                                                         Instantiator.constructor()
                                                                     .parameter(String.class, "pName")
                                                                     .parameter(long.class)
                                                                     .parameter(long.class)
                                                     )
                                                     .set("productName", "제품 이름이요.")
                                                     .sample();

        Assertions.assertThat(instant.productName).isNotEqualTo("제품 이름이요.");
    }

    @RepeatedTest(30)
    @DisplayName("constructor() - 제네릭 객체일 때.")
    void test_400() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        GenericObject<String> instant = fixtureMonkey.giveMeBuilder(new TypeReference<GenericObject<String>>() {})
                                                     .instantiate(
                                                         Instantiator.constructor()
                                                                     .parameter(String.class, "str")
                                                     )
                                                     .set("str", "Hello")
                                                     .sample();
        Assertions.assertThat(instant).isNotNull();
        Assertions.assertThat(instant.value).isEqualTo("Hello");
    }

    @RepeatedTest(30)
    @DisplayName("constructor() - 중첩된 객체가 포함된 생성자.")
    void test_500() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();
        ProductList productList = fixtureMonkey.giveMeBuilder(ProductList.class)
                                               .instantiate(
                                                   ProductList.class,
                                                   Instantiator.constructor()
                                                       .parameter(new TypeReference<List<NestedProduct>>() {}, "list")
                                               )
                                               .instantiate(
                                                   NestedProduct.class,
                                                   Instantiator.constructor()
                                                       .parameter(long.class)
                                                       .parameter(long.class)
                                                       .parameter(new TypeReference<List<String>>(){})
                                               )
                                               .size("list", 1)
                                               .sample();

        Assertions.assertThat(productList.listName).isEqualTo("defaultProductListName");
        Assertions.assertThat(productList.list).hasSize(1);
        Assertions.assertThat(productList.list.get(0).productName).isEqualTo("defaultProductName");
    }

    @RepeatedTest(10)
    @DisplayName("factoryMethod() - 특정 팩토리 메서드를 사용할 수 있다.")
    void test_600() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

            TwoFactoryMethods instant = fixtureMonkey.giveMeBuilder(TwoFactoryMethods.class)
                                                     .instantiate(
                                                         Instantiator.factoryMethod("from")
                                                             .parameter(long.class)
                                                                .parameter(long.class)
                                                     )
                                                     .sample();
        Assertions.assertThat(instant.getProductName()).isEqualTo("product");

        instant = fixtureMonkey.giveMeBuilder(TwoFactoryMethods.class)
                               .instantiate(
                                   Instantiator.factoryMethod("from")
                                               .parameter(String.class)
                                               .parameter(long.class)
                               )
                               .sample();

        Assertions.assertThat(instant.getProductName()).isNotEqualTo("product");
    }
}
