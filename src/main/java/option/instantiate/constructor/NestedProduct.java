package option.instantiate.constructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class NestedProduct {
    public long id;
    public String productName;
    public long price;
    public List<String> options;
    public Instant createdAt;

    public NestedProduct() {
        this.id = 0;
        this.productName = null;
        this.price = 0;
        this.options = null;
        this.createdAt = null;
    }

    public NestedProduct(
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

    public NestedProduct(
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
