package option.instantiate.factory_method;

import lombok.Value;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Value
public class TwoFactoryMethods {
    long id;

    String productName;

    long price;

    List<String> options;

    Instant createdAt;

    public TwoFactoryMethods(
        String productName,
        long id,
        long price
    ) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.options = Collections.emptyList();
        this.createdAt = Instant.now();
    }

    public static TwoFactoryMethods from(String productName, long id) {
        return new TwoFactoryMethods(productName, id, 0);
    }

    public static TwoFactoryMethods from(long id, long price) {
        return new TwoFactoryMethods("product", id, price);
    }
}
