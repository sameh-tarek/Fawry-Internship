package org.example.store;

import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import static org.mockito.Mockito.*;

import java.util.List;

public class MockTest {

    @Test
    void test1() {

        List list = mock(List.class);
        list.add("Red");
        list.add("Green");
        list.add("Blue");


        when(list.get(anyInt())).thenReturn("Black");
        when(list.get(0)).thenReturn("Yellow");

        System.out.println(list);
        System.out.println(list.size());

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));

        verify(list).add("Red");
        verify(list).add("Blue");
        verify(list).add("Green");
        verify(list).add("Blue");

        verify(list, times(3)).add(Matchers.anyString());

    }

}
