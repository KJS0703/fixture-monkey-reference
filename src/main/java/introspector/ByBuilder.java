package introspector;

import lombok.Builder;

@Builder
public class ByBuilder {
    private String name;
    private int age;

    public String printNameAge() {
        return name + " " + age;
    }
}
/*

package introspector;

import java.beans.ConstructorProperties;

public class ByBuilder {
   private String name;
   private int age;

   public String printNameAge() {
      return this.name + " " + this.age;
   }

   @ConstructorProperties({"name", "age"})
   ByBuilder(String name, int age) {
      this.name = name;
      this.age = age;
   }

   public static introspector.ByBuilder.ByBuilderBuilder builder() {
      return new introspector.ByBuilder.ByBuilderBuilder();
   }
}

 */
