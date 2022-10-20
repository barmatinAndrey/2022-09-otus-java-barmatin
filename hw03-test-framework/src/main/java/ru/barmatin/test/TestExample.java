package ru.barmatin.test;

import ru.barmatin.annotation.After;
import ru.barmatin.annotation.Before;
import ru.barmatin.annotation.Test;

public class TestExample {

    @Before
    boolean beforeTest() {
        return true;
    }

    @Test
    boolean test1() {
        try {
            return Integer.parseInt("8") == 8;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }


    @Test
    boolean test2() {
        try {
            return Integer.parseInt("aaa") == 8;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Test
    boolean test3() {
        try {
            return Integer.parseInt("5") == 8;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @After
    boolean afterTest() {
        return true;
    }

}
