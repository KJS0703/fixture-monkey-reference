package option.introspector;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import option.introstpector.constructor_properties.AllArgsBeforeNoArgs;
import option.introstpector.constructor_properties.AllArgsBeforeNoArgsByLomBock;
import option.introstpector.constructor_properties.BothButAllArgsByManual;
import option.introstpector.constructor_properties.NoArgsBeforeAllArgs;
import option.introstpector.constructor_properties.NoArgsBeforeAllArgsByLomBock;
import option.introstpector.constructor_properties.OnlyAllArg;
import option.introstpector.constructor_properties.OnlyNoArg;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

@DisplayName("ConstructorPropertiesArbitraryIntrospector.INSTANCE 그리고 lombok.anyConstructor.addConstructorProperties=true")
class ConstructorPropertiesArbitraryIntrospectorTest {

    /*
        ConstructorPropertiesArbitraryIntrospector.INSTANCE
        lombok.anyConstructor.addConstructorProperties=true
    * */

    @RepeatedTest(30)
    @DisplayName("기본 생성자만 사용하면 성공 단, name, job은 null이다.")
    void test_100() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();

        OnlyNoArg onlyNoArg = fm.giveMeBuilder(OnlyNoArg.class).sample();

        Assertions.assertThat(onlyNoArg.name).isNullOrEmpty();
        Assertions.assertThat(onlyNoArg.job).isNullOrEmpty();

    }

    @RepeatedTest(30)
    @DisplayName("AllArg만 사용하면 성공. @NotBlank가 붙어있으므로 빈문자열이 아니다.")
    void test_200() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();

        OnlyAllArg instance = fm.giveMeOne(OnlyAllArg.class);

        Assertions.assertThat(instance.name).isNotNull();
        Assertions.assertThat(instance.job).isNotNull();
    }

    @RepeatedTest(30)
    @DisplayName("NoArg를 AllArgs보다 먼저 작성하면 실패.")
    void test_201() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();

        Assertions.assertThatThrownBy(() -> fm.giveMeOne(NoArgsBeforeAllArgs.class))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessageContaining("could not be generated. Check the ArbitraryIntrospector used or the APIs used in the ArbitraryBuilder.");
    }

    @RepeatedTest(30)
    @DisplayName("lombokc 어노테이션 / NoArg를 AllArgs보다 먼저 작성하면 실패.")
    void test_201_by_lombock() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();

        Assertions.assertThatThrownBy(() -> fm.giveMeOne(NoArgsBeforeAllArgsByLomBock.class))
                  .isInstanceOf(IllegalArgumentException.class)
                  .hasMessageContaining("could not be generated. Check the ArbitraryIntrospector used or the APIs used in the ArbitraryBuilder.");
    }

    @RepeatedTest(30)
    @DisplayName("AllArgs를 NoArg보다 먼저 작성하면 성공.")
    void test_202() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();
        AllArgsBeforeNoArgs instance = fm.giveMeOne(AllArgsBeforeNoArgs.class);

        Assertions.assertThat(instance.name).isNotNull();
        Assertions.assertThat(instance.job).isNotNull();
    }

    @RepeatedTest(30)
    @DisplayName("lombokc 어노테이션 / AllArgs를 NoArg보다 먼저 작성하면 성공.")
    void test_202_by_lombock() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();
        AllArgsBeforeNoArgsByLomBock instance = fm.giveMeOne(AllArgsBeforeNoArgsByLomBock.class);

        Assertions.assertThat(instance.name).isNotNull();
        Assertions.assertThat(instance.job).isNotNull();
    }

    @RepeatedTest(30)
    @DisplayName("기본 생성자는 lombock, AllArgsConstructor는 수동으로 생성하면 인스턴스 생성 성공. @NotBlank가 붙어있으므로 빈문자열이 아니다.")
    void test_203() {
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();

        BothButAllArgsByManual instance = fm.giveMeOne(BothButAllArgsByManual.class);

        Assertions.assertThat(instance.name).isNotNull();
        Assertions.assertThat(instance.job).isNotNull();
    }
}
