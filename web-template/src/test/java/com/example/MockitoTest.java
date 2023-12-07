package com.example;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author WuQinglong
 * @since 2022/12/15 17:21
 */
public class MockitoTest {

    @Test
    public void f2()
    {
        Random mockRandom = Mockito.mock(Random.class);
        Mockito.when(mockRandom.nextInt())
            .thenReturn(3);

        for (int i = 0; i < 5; i++) {
            System.out.println(mockRandom.nextInt());
        }
    }

}
