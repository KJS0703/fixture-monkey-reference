package arbitrarybuilder;

import com.navercorp.fixturemonkey.ArbitraryBuilder;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @apiNote https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/customizing-objects/apis/#setlazy
 */
@DisplayName("Customizing API - functions used with ArbitraryBuilder.setLazy")
class ArbitraryBuilderSetLazyTest {

    @RepeatedTest(30)
    @DisplayName("setLazy()에 전달된 인자 Suplier는 sample() 호출 시 실행된다")
    void setLazy_100() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(new FieldReflectionArbitraryIntrospector())
                                        .build();
        AtomicReference<Long> idGenerator = new AtomicReference<>(0L);

        ArbitraryBuilder<Pojo> builder1 = fm.giveMeBuilder(Pojo.class)
                                            .setLazy("id", () -> idGenerator.getAndSet(idGenerator.get() + 1));

        ArbitraryBuilder<Pojo> builder2 = fm.giveMeBuilder(Pojo.class)
                                            .setLazy("id", () -> idGenerator.getAndSet(idGenerator.get() + 1));

        // then
        Assertions.assertThat(builder2.sample())
                  .extracting(b -> b.id)
                  .isEqualTo(0L);
        Assertions.assertThat(builder1.sample())
                  .extracting(b -> b.id)
                  .isEqualTo(1L);
    }

}
