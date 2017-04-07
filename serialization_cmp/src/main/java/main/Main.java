package main;

import bean.Item;
import bean.Person;
import com.google.common.collect.ImmutableMap;
import executor.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Person person = new Person(
                "32098119927443759X",
                12,
                false,
                Arrays.asList(new Item(1, "desc1"), new Item(2, "desc2"), new Item(3, "desc3")),
                ImmutableMap.of("a", new Item(1, "desc1"), "b", new Item(2, "desc2"))
        );

        test(Person.class, person, 1000000);
    }

    public static <T> void test(Class<T> clazz, T obj, int times) {

        JacksonExecutor<T> jacksonExecutor = new JacksonExecutor<>(clazz, obj);
        FastjsonExecutor<T> fastjsonExecutor = new FastjsonExecutor<>(clazz, obj);
        JavaExecutor<T> javaExecutor = new JavaExecutor<>(clazz, obj);

        SerializeStatistics ss = jacksonExecutor.serialize(times);
        DeserializeStatistics ds = jacksonExecutor.deserialize(times);
        out("Jackson", ss, ds);
        System.out.println();

        ss = fastjsonExecutor.serialize(times);
        ds = fastjsonExecutor.deserialize(times);
        out("Fastjson", ss, ds);
        System.out.println();

        ss = javaExecutor.serialize(times);
        ds = javaExecutor.deserialize(times);
        out("Java", ss, ds);
        System.out.println();
    }

    public static void out(String title, SerializeStatistics serializeStatistics,
                           DeserializeStatistics deserializeStatistics) {
        System.out.println(title);
        System.out.println(serializeStatistics);
        System.out.println(deserializeStatistics);
        long totalCost = serializeStatistics.getTotalCost() + deserializeStatistics.getTotalCost();
        double avgCost = (double) totalCost / serializeStatistics.getTimes();
        System.out.println("total cost " + totalCost + "ms, avg cost " + avgCost
                + "ms, serialize byte is " + serializeStatistics.getByteSize());
    }

}
