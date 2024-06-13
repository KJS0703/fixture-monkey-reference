package arbitrarybuilder;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @apiNote https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/customizing-objects/apis/#thenapply
 */
@DisplayName("Customizing API - functions used with ArbitraryBuilder.thenApply")
class ArbitraryBuilderThenApplyTest {

    @RepeatedTest(30)
    @DisplayName("thenApply() 사용 시 이미 builder에 설정된 값을 통해 다른 필드를 설정할 수 있다")
    void thenApply_100() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(new FieldReflectionArbitraryIntrospector())
                                        .build();

        Pojo pojo = fm.giveMeBuilder(Pojo.class)
                      .set("id", 1L)
                      .thenApply((it, builder) -> builder.set("name", "entity" + it.id))
                      .sample();

        // then
        Assertions.assertThat(pojo.id).isEqualTo(1L);
        Assertions.assertThat(pojo.name).isEqualTo("entity1");
    }

    @RepeatedTest(30)
    @DisplayName("thenApply()로 setLazy() 설정된 필드를 참조하는 경우 Suplier가 한번 이상 적용된다")
    void thenApply_101() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .objectIntrospector(new FieldReflectionArbitraryIntrospector())
                                        .build();
        AtomicReference<Long> idGenerator = new AtomicReference<>(0L);

        Pojo pojo = fm.giveMeBuilder(Pojo.class)
                      .setLazy("id", () -> idGenerator.getAndSet(idGenerator.get() + 1))
                      .thenApply((it, builder) -> builder.set("name", "entity" + it.id))
                      .thenApply((it, builder) -> builder.set("job", "entity" + it.id))
                      .sample();

        // then
        Assertions.assertThat(pojo.id).isGreaterThan(0L);
        Assertions.assertThat(pojo.name).isEqualTo("entity" + pojo.id);
        Assertions.assertThat(pojo.job).isEqualTo("entity" + pojo.id);
    }

}
