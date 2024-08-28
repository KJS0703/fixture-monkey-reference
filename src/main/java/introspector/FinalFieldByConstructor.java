package introspector;

public class FinalFieldByConstructor {
    public final String name;
    public final String job;
    public String address;

    public FinalFieldByConstructor() {
        this.name = "defaultName";
        this.job = "defaultJob";
    }
}
