package introspector;

import java.beans.ConstructorProperties;

public class StringLengthConstructor {
    public Integer len;

    @ConstructorProperties({"str"})
    public StringLengthConstructor(String str) {
        this.len = str== null ? 0 : str.length();
    }
}
