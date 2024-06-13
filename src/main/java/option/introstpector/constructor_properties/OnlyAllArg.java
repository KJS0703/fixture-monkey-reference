package option.introstpector.constructor_properties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OnlyAllArg {
    @NotBlank
    public String name;
    @NotBlank
    public String job;
}
