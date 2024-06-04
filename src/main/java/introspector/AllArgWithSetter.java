package introspector;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class AllArgWithSetter {
    @NotBlank
    public String name;
    @NotBlank
    public String job;
}
