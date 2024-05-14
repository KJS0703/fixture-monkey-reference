package constructor_properties;

import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

public class AllArgsBeforeNoArgs {
    @NotBlank
    public String name;
    @NotBlank
    public String job;

    public boolean onlyInitiatedByNoArgs;

    @ConstructorProperties({"name", "job"})
    public AllArgsBeforeNoArgs(String name, String job) {
        this.name = name;
        this.job = job;
    }
    public AllArgsBeforeNoArgs() {
        this.onlyInitiatedByNoArgs = true;
    }
}
