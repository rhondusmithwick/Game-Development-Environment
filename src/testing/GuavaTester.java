package testing;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class GuavaTester implements Tester {

    public static void main (String[] args) {
        (new GuavaTester()).test();
    }

    @Override
    public void test () {
        Collection<Integer> list1 = Arrays.asList(1, 2, 3, 4);
        Collection<Integer> list2 = Arrays.asList(4, 5, 6, 7);
        for (int i : Iterables.concat(list1, list2)) {
            System.out.println(i);
        }
        // System.out.println(list1.retainAll(list2));
        Set<Integer> s1 = new HashSet<Integer>(list1);
        Set<Integer> s2 = new HashSet<Integer>(list2);
        // System.out.println(s1.retainAll(s2));
        System.out.println(Sets.intersection(s1, s2));
    }

}
