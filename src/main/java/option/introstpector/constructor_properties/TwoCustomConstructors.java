package option.introstpector.constructor_properties;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class TwoCustomConstructors {
    public long id;

    public String productName;

    public long price;

    public List<String> options;

    public Instant createdAt;

    public TwoCustomConstructors (
        String str,
        long id,
        long price
    ) {
        this.id = id;
        this.productName = str;
        this.price = price;
        this.options = Collections.emptyList();
        this.createdAt = Instant.now();
    }

    public TwoCustomConstructors (
        long id,
        long price,
        List<String> options
    ) {
        this.id = id;
        this.productName = "defaultProductName";
        this.price = price;
        this.options = options;
        this.createdAt = Instant.now();
    }
}
