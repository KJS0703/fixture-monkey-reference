package option.introstpector.constructor_properties;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class NoArgsBeforeAllArgsByLomBock {
    @NotBlank
    public String name;
    @NotBlank
    public String job;
}
