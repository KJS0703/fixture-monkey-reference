package arbitrarybuilder;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.instantiator.Instantiator;
import com.navercorp.fixturemonkey.customizer.Values;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

/**
 * @apiNote https://naver.github.io/fixture-monkey/v1-0-0-kor/docs/customizing-objects/apis/#set
 */
@DisplayName("Customizing API - functions used with ArbitraryBuilder.set")
public class ArbitraryBuilderSetTest {

    @RepeatedTest(30)
    @DisplayName("set() 이용 시 필드가 같은 다른 객체가 설정된다 (깊은 복사)")
    void set_100() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .build();

        AllArgsBeforeNoArgs allArgsBeforeNoArgs = fm.giveMeBuilder(AllArgsBeforeNoArgs.class)
                                                    .instantiate(Instantiator.constructor()
                                                                             .parameter(String.class)
                                                                             .parameter(String.class))
                                                    .sample();

        // when
        AllArgsBeforeNoArgsHasChild allArgsBeforeNoArgsHasChild = fm.giveMeBuilder(AllArgsBeforeNoArgsHasChild.class)
                                                                    .instantiate(Instantiator.constructor()
                                                                                             .parameter(String.class, "name")
                                                                                             .parameter(String.class, "job")
                                                                                             .parameter(AllArgsBeforeNoArgs.class, "child"))
                                                                    .instantiate(AllArgsBeforeNoArgs.class,
                                                                                 Instantiator.constructor()
                                                                                             .parameter(String.class, "name")
                                                                                             .parameter(String.class, "job"))
                                                                    .set("name", "name")
                                                                    .set("job", "job")
                                                                    .set("child", allArgsBeforeNoArgs)
                                                                    .sample();

        // then
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.name).isEqualTo("name");
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.job).isEqualTo("job");
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.child)
                  .extracting(a -> a.name,
                              a -> a.job)
                  .containsExactly(allArgsBeforeNoArgs.name,
                                   allArgsBeforeNoArgs.job);
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.child).isNotEqualTo(allArgsBeforeNoArgs);
    }

    @RepeatedTest(30)
    @DisplayName("set() & Values.just() 이용 시 같은 객체가 설정된다 (얕은 복사)")
    void set_101() {
        // given
        FixtureMonkey fm = FixtureMonkey.builder()
                                        .build();

        AllArgsBeforeNoArgs allArgsBeforeNoArgs = fm.giveMeBuilder(AllArgsBeforeNoArgs.class)
                                                    .instantiate(Instantiator.constructor()
                                                                             .parameter(String.class)
                                                                             .parameter(String.class))
                                                    .sample();

        // when
        AllArgsBeforeNoArgsHasChild allArgsBeforeNoArgsHasChild = fm.giveMeBuilder(AllArgsBeforeNoArgsHasChild.class)
                                                                    .instantiate(Instantiator.constructor()
                                                                                             .parameter(String.class, "name")
                                                                                             .parameter(String.class, "job")
                                                                                             .parameter(AllArgsBeforeNoArgs.class, "child"))
                                                                    .instantiate(AllArgsBeforeNoArgs.class,
                                                                                 Instantiator.constructor()
                                                                                             .parameter(String.class, "name")
                                                                                             .parameter(String.class, "job"))
                                                                    .set("name", "name")
                                                                    .set("job", "job")
                                                                    .set("child", Values.just(allArgsBeforeNoArgs))
                                                                    .sample();

        // then
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.name).isEqualTo("name");
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.job).isEqualTo("job");
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.child)
                  .extracting(a -> a.name,
                              a -> a.job)
                  .containsExactly(allArgsBeforeNoArgs.name,
                                   allArgsBeforeNoArgs.job);
        Assertions.assertThat(allArgsBeforeNoArgsHasChild.child).isEqualTo(allArgsBeforeNoArgs);
    }

}
