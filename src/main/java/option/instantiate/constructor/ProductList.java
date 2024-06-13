package option.instantiate.constructor;

import java.util.List;

public class ProductList {
    public String listName;
    public List<NestedProduct> list;

    public ProductList(List<NestedProduct> list) {
        this.listName = "defaultProductListName";
        this.list = list;
    }
}
