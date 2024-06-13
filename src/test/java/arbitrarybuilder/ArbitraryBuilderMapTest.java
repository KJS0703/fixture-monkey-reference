package arbitrarybuilder;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

/**
 * @apiNote https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/customizing-objects/apis/#map
 */
@DisplayName("Customizing API - functions used with ArbitraryBuilder.map")
public class ArbitraryBuilderMapTest {

    @RepeatedTest(30)
    @DisplayName("생성된 ArbitraryBuilder 타입 변환 - 기존에 정의된 속성은 적용된다")
    void map_100() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(new FieldReflectionArbitraryIntrospector())
                                        .build();

        ArbitraryBuilder<Pojo> pojoBuilder = fm.giveMeBuilder(Pojo.class)
                                               .set("name", "fixed");
        List<String> names = pojoBuilder.map(it -> it.name)
                                           .sampleList(100);

        // then
        Assertions.assertThat(names)
                  .containsOnly("fixed");
    }

}
