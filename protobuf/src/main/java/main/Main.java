package main;

import com.example.tutorial.AddressBookProtos;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;

/**
 * Created by jiangzhiwen on 17/4/7.
 */
public class Main {
    public static void main(String[] args) {
        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder()
                .setEmail("jjj@xxx.ccom")
                .setId(111)
                .setName("jjj")
                .addPhones(AddressBookProtos.Person.PhoneNumber.newBuilder()
                        .setNumber("13842424")
                        .setTypeValue(AddressBookProtos.Person.PhoneType.HOME_VALUE)
                        .build())
                .putAllMm(ImmutableMap.of("kkk", "vvv"))
                .build();

        System.out.println(person.toString());

        // serialize
        byte[] bytes = person.toByteArray();

        try {
            // deserialize
            AddressBookProtos.Person person1 = AddressBookProtos.Person.parseFrom(bytes);
            System.out.println(person1);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }
}
