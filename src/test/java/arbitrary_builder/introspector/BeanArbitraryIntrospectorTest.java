package arbitrary_builder.introspector;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import introspector.AllArgWithSetter;
import introspector.NoArgWithSetter;
import introspector.NoSetter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 *  Introspector 는 옵션을 통해 전역적으로도 객체 생성 방법을 지정할 수 있게 한다.
 *  다양한 Introspector 로 객체를 생성하는 방법을 제공한다.
 *  각 Introspector 는 클래스의 인스턴스 생성할 수 있는 몇 가지 제약 조건이 있다.
 */

@DisplayName("BeanArbitraryIntrospector 는 NoArgsConstructor, Setter가 필요함.")
class BeanArbitraryIntrospectorTest {

    @Test
    @DisplayName("NoArgConstructor가 없으면 null 반환.")
    void test_100() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        AllArgWithSetter actual = fixtureMonkey.giveMeBuilder(AllArgWithSetter.class)
                                               .sample();

        // then
        Assertions.assertThat(actual).isNull();
    }

    @Test
    @DisplayName("Setter가 없으면 예외 발생")
    void test_101() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        ArbitraryBuilder<NoSetter> arbitraryBuilder = fixtureMonkey.giveMeBuilder(NoSetter.class);

        Assertions.assertThatThrownBy(arbitraryBuilder::sample)
                  .isInstanceOf(NullPointerException.class)
                  .hasMessageContaining("Cannot invoke \"java.beans.PropertyDescriptor.getWriteMethod()\" because \"propertyDescriptor\" is null");
    }

    @Test
    @DisplayName("기본 생성자와 setter가 있으면 생성 성공.")
    void test_102() {
        FixtureMonkey fixtureMonkey = FixtureMonkey.create();

        NoArgWithSetter actual = fixtureMonkey.giveMeBuilder(NoArgWithSetter.class)
                                               .sample();

        Assertions.assertThat(actual).isNotNull();

        /*
         * 참고 : name, job 필드는 null, empty 일 수 있음.
         */
    }
}
