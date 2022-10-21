package ru.barmatin.test;

import ru.barmatin.annotation.After;
import ru.barmatin.annotation.Before;
import ru.barmatin.annotation.Test;

public class TestExample {

    @Before
    void beforeTest1() {
        System.out.println("beforeTest1");
    }

    @Before
    void beforeTest2() {
        System.out.println("beforeTest2");
    }

    @Test
    void test1() {
        boolean result = Integer.parseInt("aaa") == 8;
        System.out.println("test1");
    }


    @Test
    void test2() {
        boolean result = Integer.parseInt("8") == 8;
        System.out.println("test2");
    }

    @After
    void afterTest1() {
        System.out.println("afterTest1");
    }

    @After
    void afterTest2() {
        System.out.println("afterTest2");
    }

}
