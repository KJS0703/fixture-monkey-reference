package arbitrarybuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pojo {

    public Long id;

    public String name;

    public String job;

    public StatusEnum status;
}
