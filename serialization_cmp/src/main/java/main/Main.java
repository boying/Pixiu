package main;

import bean.Item;
import bean.PbBean;
import bean.Person;
import com.google.common.collect.ImmutableMap;
import executor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Person person = new Person(
                "32098119927443759X",
                12,
                false,
                Arrays.asList(new Item(1, "desc1"), new Item(2, "desc2"), new Item(3, "desc3")),
                ImmutableMap.of("a", new Item(1, "desc1"), "b", new Item(2, "desc2"))
        );

        System.out.println("#############################################");
        int times = 1000000;
        test(Person.class, person, times);
        testPb(person, times);

        System.out.println("#############################################");
        test1(Person.class, person, times);
    }

    public static <T> void test(Class<T> clazz, T obj, int times) {
        JacksonExecutor<T> jacksonExecutor = new JacksonExecutor<>(clazz, obj);
        FastjsonExecutor<T> fastjsonExecutor = new FastjsonExecutor<>(clazz, obj);
        GsonExecutor<T> gsonExecutor = new GsonExecutor<>(clazz, obj);
        JavaExecutor<T> javaExecutor = new JavaExecutor<>(clazz, obj);


        SerializeStatistics ss = jacksonExecutor.serialize(times);
        DeserializeStatistics ds = jacksonExecutor.deserialize(times);
        out("Jackson", ss, ds);
        System.out.println();

        ss = fastjsonExecutor.serialize(times);
        ds = fastjsonExecutor.deserialize(times);
        out("Fastjson", ss, ds);
        System.out.println();

        ss = gsonExecutor.serialize(times);
        ds = gsonExecutor.deserialize(times);
        out("Gson", ss, ds);
        System.out.println();

        ss = javaExecutor.serialize(times);
        ds = javaExecutor.deserialize(times);
        out("Java", ss, ds);
        System.out.println();
    }

    public static void testPb(Person person, int times) {
        List<PbBean.Item> items = new ArrayList<>();
        items.add(PbBean.Item.newBuilder().setType(1).setDesc("desc1").build());
        items.add(PbBean.Item.newBuilder().setType(2).setDesc("desc2").build());
        items.add(PbBean.Item.newBuilder().setType(3).setDesc("desc3").build());


        PbBean.Person pbPerson = PbBean.Person.newBuilder()
                .setId(person.getId())
                .setAge(person.getAge())
                .addAllList(items)
                .putAllMap(ImmutableMap.of(
                        "a",
                        PbBean.Item.newBuilder().setType(1).setDesc("desc1").build(),
                        "b",
                        PbBean.Item.newBuilder().setType(2).setDesc("desc2").build()
                ))
                .build();

        PBExecutor<PbBean.Person> personPbExecutor = new PBExecutor<>(PbBean.Person.class, pbPerson);
        SerializeStatistics serialize = personPbExecutor.serialize(times);
        DeserializeStatistics deserialize = personPbExecutor.deserialize(times);
        out("ProtoBuf", serialize, deserialize);
    }

    public static <T> void test1(Class<T> clazz, T obj, int times) {
        JacksonExecutor<T> jacksonExecutor = new JacksonExecutor<>(clazz, obj);
        FastjsonExecutor<T> fastjsonExecutor = new FastjsonExecutor<>(clazz, obj);
        GsonExecutor<T> gsonExecutor = new GsonExecutor<>(clazz, obj);
        JavaExecutor<T> javaExecutor = new JavaExecutor<>(clazz, obj);


        SerializeStatistics ss = jacksonExecutor.serializeToStr(times);
        DeserializeStatistics ds = jacksonExecutor.deserializeFromStr(times);
        out("Jackson", ss, ds);
        System.out.println();

        ss = fastjsonExecutor.serializeToStr(times);
        ds = fastjsonExecutor.deserializeFromStr(times);
        out("Fastjson", ss, ds);
        System.out.println();

        ss = gsonExecutor.serializeToStr(times);
        ds = gsonExecutor.deserializeFromStr(times);
        out("Gson", ss, ds);
        System.out.println();

        /*
        ss = javaExecutor.serializeToStr(times);
        ds = javaExecutor.deserializeFromStr(times);
        out("Java", ss, ds);
        System.out.println();
        */
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
