package jzw;

import org.mockito.Mockito;

import java.util.LinkedList;

/**
 * Created by boying on 2017/9/21.
 */
public class MockitoSample {
    public static void main(String[] args) {
        smaple1();
    }

    static void smaple1() {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = Mockito.mock(LinkedList.class);

        // stubbing appears before the actual execution
        Mockito.when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));
    }
}
