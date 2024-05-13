package constructor_properties;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AllArgsBeforeNoArgsByLomBock {
    @NotBlank
    public String name;
    @NotBlank
    public String job;
}
