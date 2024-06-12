package arbitrarybuilder;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

/**
 * @apiNote https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/customizing-objects/apis/#zipwith
 */
@DisplayName("Customizing API - functions used with ArbitraryBuilder.zipWith")
public class ArbitraryBuilderZipWithTest {

    @RepeatedTest(30)
    @DisplayName("생성된 ArbitraryBuilder 타입 통합 - 기존에 정의된 속성은 적용된다")
    void zipWith_100() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(new FieldReflectionArbitraryIntrospector())
                                        .build();

        ArbitraryBuilder<Long> idBuilder = fm.giveMeBuilder(Long.class)
                                             .set(1L);
        ArbitraryBuilder<String> nameBuilder = fm.giveMeBuilder(String.class)
                                                 .set("fixed");
        List<Pojo> pojoList = idBuilder.zipWith(nameBuilder, (id, name) -> new Pojo(id, name, null, null))
                                       .sampleList(100);

        // then
        Assertions.assertThat(pojoList)
                  .extracting(it -> it.id, it -> it.name, it -> it.job, it -> it.status)
                  .containsOnly(
                      Assertions.tuple(1L, "fixed", null, null));
    }

}
