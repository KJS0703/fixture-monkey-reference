package option.instantiate;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.instantiator.Instantiator;
import com.navercorp.fixturemonkey.api.type.TypeReference;
import constructor_properties.AllArgsBeforeNoArgs;
import constructor_properties.NoArgsBeforeAllArgs;
import constructor_properties.TwoCustomConstructors;
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
}
