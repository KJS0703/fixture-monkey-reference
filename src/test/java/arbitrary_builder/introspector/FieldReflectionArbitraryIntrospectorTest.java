package arbitrary_builder.introspector;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import introspector.FinalField;
import introspector.FinalFieldByConstructor;
import option.introstpector.constructor_properties.OnlyAllArg;
import option.introstpector.constructor_properties.OnlyNoArg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * FieldReflectionArbitraryIntrospector는 리플렉션을 사용하여 새 인스턴스를 생성하고 필드를 설정합니다.
 * 따라서 생성할 클래스는 인자가 없는 생성자(또는 기본 생성자)와 getter 또는 setter 중 하나를 가져야 합니다.
 */
class FieldReflectionArbitraryIntrospectorTest {
    FixtureMonkey fm = FixtureMonkey.builder()
                                    .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
                                    .build();

    @Test
    @DisplayName("기본 생성자가 없으면 sample()은 null을 반환합니다.")
    void test100() {
        // when
        OnlyAllArg sample = fm.giveMeBuilder(OnlyAllArg.class)
                              .sample();

        // then
        Assertions.assertThat(sample).isNull();
    }

    @Test
    @DisplayName("기본 생성자가 있으면 값을 설정할 수 있다.")
    void test101() {
        OnlyNoArg sample = fm.giveMeBuilder(OnlyNoArg.class)
                             .set("name", "kjs")
                             .set("job", "driver")
                             .sample();

        Assertions.assertThat(sample).isNotNull();
        Assertions.assertThat(sample.name).isEqualTo("kjs");
        Assertions.assertThat(sample.job).isEqualTo("driver");
    }

    @Test
    @DisplayName("final 필드 값을 직접 할당하고있다면 fm으로 값 설정 불가능.")
    void test102() {
        // when
        FinalField sample = fm.giveMeBuilder(FinalField.class)
                              .set("name", "kjs")
                              .set("job", "driver")
                              .set("address", "seoul")
                              .sample();

        // then
        Assertions.assertThat(sample).isNotNull();
        Assertions.assertThat(sample.name)
                  .isNotEqualTo("kjs")
                  .isEqualTo("defaultName");
        Assertions.assertThat(sample.job)
                  .isNotEqualTo("driver")
                  .isEqualTo("defaultJob");
        Assertions.assertThat(sample.address)
                  .isEqualTo("seoul");
    }

    @Test
    @DisplayName("final 필드를 생성자로 주입받는다면, fm으로 값 설정 가능함.")
    void test103() {
        // when
        FinalFieldByConstructor sample = fm.giveMeBuilder(FinalFieldByConstructor.class)
                                           .set("name", "kjs")
                                           .set("job", "driver")
                                           .set("address", "seoul")
                                           .sample();

        // then
        Assertions.assertThat(sample).isNotNull();
        Assertions.assertThat(sample.name)
                  .isEqualTo("kjs");
        Assertions.assertThat(sample.job)
                  .isEqualTo("driver");
        Assertions.assertThat(sample.address)
                  .isEqualTo("seoul");
    }
}
