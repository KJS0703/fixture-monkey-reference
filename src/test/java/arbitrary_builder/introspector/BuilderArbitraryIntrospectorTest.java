package arbitrary_builder.introspector;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import introspector.ByBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BuilderArbitraryIntrospectorTest {
    FixtureMonkey fm = FixtureMonkey.builder()
                                    .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
                                    .build();

    @Test
    @DisplayName("BuilderArbitraryIntrospector를 사용해서 픽스처 생성.")
    void test100() {
    	// given
        ByBuilder sample = fm.giveMeBuilder(ByBuilder.class)
                             .setNotNull("name")
                             .set("age", 10)
                             .sample();

        // when
        String nameAge = sample.printNameAge();

        // then
        Assertions.assertThat(nameAge)
                  .isNotBlank()
                  .endsWith("10");
    }

    @Test
    @DisplayName("@Builder가 AllArgsConstructor를 만들어주기때문에 ConstructorPropertiesArbitraryIntrospector.INSTANCE를 사용해도 무방하다.")
    void test200() {
        FixtureMonkey otherIntrospector = FixtureMonkey.builder()
                                        .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                                        .plugin(new JakartaValidationPlugin())
                                        .build();

        ByBuilder sample = otherIntrospector.giveMeBuilder(ByBuilder.class)
                                            .setNotNull("name")
                                            .set("age", 10)
                                            .sample();

        String nameAge = sample.printNameAge();

        Assertions.assertThat(nameAge)
                  .isNotBlank()
                  .endsWith("10");
    }
}
