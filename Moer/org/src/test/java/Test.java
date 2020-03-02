import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: moer
 * @Date: 2019/6/12 17:04
 * @Description:
 */
public class Test {

  public static void main(String[] args) {
    HashSet set = new HashSet();
    Person a = new Person(1001, "A");
    Person b = new Person(1002, "B");
    set.add(a);
    set.add(b);
    a.setName("C");
    set.remove(a);
    System.out.println(set);
    set.add(new Person(1001,"C"));
    System.out.println(set);
    set.add(new Person(1001,"A"));
    System.out.println(set);
    System.out.println(a.hashCode());
  }


}
