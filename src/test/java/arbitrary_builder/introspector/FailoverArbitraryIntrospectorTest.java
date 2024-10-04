package arbitrary_builder.introspector;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import option.introstpector.constructor_properties.NoArgsBeforeAllArgs;
import option.introstpector.constructor_properties.OnlyAllArg;
import option.introstpector.constructor_properties.OnlyNoArg;
import option.introstpector.constructor_properties.ParameterArgWithoutAnnotation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

// https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/generating-objects/introspector/#failoverarbitraryintrospector
class FailoverArbitraryIntrospectorTest {
    FixtureMonkey sut = FixtureMonkey.builder()
                                     .objectIntrospector(new FailoverIntrospector(
                                        Arrays.asList(
                                            FieldReflectionArbitraryIntrospector.INSTANCE,
                                            ConstructorPropertiesArbitraryIntrospector.INSTANCE
                                        )
                                    ))
                                     .plugin(new JakartaValidationPlugin())
                                     .build();

    @Test
    @DisplayName("ConstructorPropertiesArbitraryIntrospector로 생성할 수 있으나 FieldReflectionArbitraryIntrospector로 생성할 수 없는 클래스")
    void test100() {
        // when
        OnlyAllArg instance = sut.giveMeOne(OnlyAllArg.class);

        // then
        Assertions.assertThat(instance.name).isNotNull();
        Assertions.assertThat(instance.job).isNotNull();
    }

    @Test
    @DisplayName("FieldReflectionArbitraryIntrospector로 생성할 수 있으나 ConstructorPropertiesArbitraryIntrospector로 생성할 수 없는 클래스")
    void test101() {
        // when
        OnlyNoArg instance = sut.giveMeBuilder(OnlyNoArg.class)
                                .set("name", "kjs")
                                .set("job", "driver")
                               .sample();

        // then
        Assertions.assertThat(instance.name).isEqualTo("kjs");
        Assertions.assertThat(instance.job).isEqualTo("driver");
    }

    @Test
    @DisplayName("두 인스펙터로 생성할 수 없는 클래스")
    void test200() {
        // when
        ParameterArgWithoutAnnotation instance = sut.giveMeOne(ParameterArgWithoutAnnotation.class);

        // then
        Assertions.assertThat(instance).isNull();
    }

    /**
     * ConstructorPropertiesArbitraryIntrospector만 쓸때는 생성하지 못하는데,
     * Failover로 감싸면 생성할 수 있다.
     */
    @Test
    @DisplayName("ConstructorPropertiesArbitraryIntrospector로 생성할 수 없으나 Failover로 생성할 수 있는 클래스")
    void test300() {

        FixtureMonkey f = FixtureMonkey.builder()
                                         .objectIntrospector(new FailoverIntrospector(
                                             Arrays.asList(
                                                 ConstructorPropertiesArbitraryIntrospector.INSTANCE
                                             )
                                         ))
                                         .plugin(new JakartaValidationPlugin())
                                         .build();

        // when
        NoArgsBeforeAllArgs instance = f.giveMeOne(NoArgsBeforeAllArgs.class);

        // then
        Assertions.assertThat(instance.name).isNotNull();
        Assertions.assertThat(instance.job).isNotNull();
    }
}
