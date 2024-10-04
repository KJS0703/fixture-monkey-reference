package option.introstpector.constructor_properties;

import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

public class ParameterArgWithAnnotation {
    @NotBlank
    public String name;
    @NotBlank
    public String job;

    @ConstructorProperties({"name", "job"})
    public ParameterArgWithAnnotation(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
