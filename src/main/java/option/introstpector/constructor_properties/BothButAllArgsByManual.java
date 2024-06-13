package option.introstpector.constructor_properties;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.beans.ConstructorProperties;

@NoArgsConstructor
public class BothButAllArgsByManual {
    @NotBlank
    public String name;
    @NotBlank
    public String job;

    @ConstructorProperties({"name", "job"})
    public BothButAllArgsByManual(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
