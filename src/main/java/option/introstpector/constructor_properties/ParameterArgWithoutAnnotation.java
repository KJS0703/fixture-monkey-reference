package option.introstpector.constructor_properties;

import jakarta.validation.constraints.NotBlank;

public class ParameterArgWithoutAnnotation {
    @NotBlank
    public String name;
    @NotBlank
    public String job;

    public ParameterArgWithoutAnnotation(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
