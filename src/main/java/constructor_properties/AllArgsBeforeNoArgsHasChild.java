package constructor_properties;

import arbitrarybuilder.AllArgsBeforeNoArgs;
import jakarta.validation.constraints.NotBlank;

import java.beans.ConstructorProperties;

public class AllArgsBeforeNoArgsHasChild {
    @NotBlank
    public String name;
    @NotBlank
    public String job;

    public AllArgsBeforeNoArgs child;

    @ConstructorProperties({"name", "job", "child"})
    public AllArgsBeforeNoArgsHasChild(String name, String job, AllArgsBeforeNoArgs child) {
        this.name = name;
        this.job = job;
        this.child = child;
    }
    public AllArgsBeforeNoArgsHasChild() {
    }
}
