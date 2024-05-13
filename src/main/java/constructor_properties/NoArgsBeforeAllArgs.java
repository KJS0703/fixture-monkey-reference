package constructor_properties;

import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

public class NoArgsBeforeAllArgs {
    @NotBlank
    public String name;
    @NotBlank
    public String job;

    public NoArgsBeforeAllArgs() {
    }

    @ConstructorProperties({"name", "job"})
    public NoArgsBeforeAllArgs(String name, String job) {
        this.name = name;
        this.job = job;
    }
}
