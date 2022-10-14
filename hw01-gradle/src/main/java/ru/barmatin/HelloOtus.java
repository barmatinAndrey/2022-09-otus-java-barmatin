package ru.barmatin;

import com.google.common.base.Strings;

public class HelloOtus {
    public static void main(String... args) {
        System.out.println(Strings.isNullOrEmpty("test") ? "Empty string" : "Not empty string");
    }
}
