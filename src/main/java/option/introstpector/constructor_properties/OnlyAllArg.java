package option.introstpector.constructor_properties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

/**
 * lombok.config에 lombok.anyConstructor.addConstructorProperties=true 이므로
 * ConstructorPropertiesArbitraryIntrospector로 인스턴스를 생성할 수 있음.
 */
@AllArgsConstructor
public class OnlyAllArg {
    @NotBlank
    public String name;
    @NotBlank
    public String job;
}
