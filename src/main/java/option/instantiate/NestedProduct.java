package option.instantiate;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public class NestedProduct {
    long id;

    String productName;

    long price;

    List<String> options;

    Instant createdAt;

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
