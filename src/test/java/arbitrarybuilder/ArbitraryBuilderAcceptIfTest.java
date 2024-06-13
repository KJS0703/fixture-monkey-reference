package arbitrarybuilder;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.instantiator.Instantiator;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import com.navercorp.fixturemonkey.customizer.Values;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

/**
 * @apiNote https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/customizing-objects/apis/#acceptif
 */
@DisplayName("Customizing API - functions used with ArbitraryBuilder.acceptIf")
public class ArbitraryBuilderAcceptIfTest {

    @RepeatedTest(30)
    @DisplayName("특정 조건에 맞는 객체들에 대해 추가 설정을 적용할 수 있다")
    void acceptIf_100() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(new FieldReflectionArbitraryIntrospector())
                                        .build();

        List<Pojo> pojoList = fm.giveMeBuilder(Pojo.class)
                                .acceptIf(
                                    it -> it.status == StatusEnum.END,
                                    builder -> builder.set("name", "-")
                                )
                                .sampleList(100);

        // then
        Assertions.assertThat(pojoList)
                  .filteredOn(pojo -> pojo.status == StatusEnum.END)
                  .extracting(pojo -> pojo.name)
                  .containsOnly("-");
    }

}
