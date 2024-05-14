package option.instantiate.constructor;

public class GenericObject<T> {
    public T value;

    public GenericObject(T value) {
        this.value = value;
    }
}
