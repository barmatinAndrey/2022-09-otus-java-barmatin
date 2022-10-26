package ru.barmatin.test;

import ru.barmatin.annotation.After;
import ru.barmatin.annotation.Before;
import ru.barmatin.annotation.Test;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {

    public static void runTestClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getDeclaredMethods();

            List<Method> testMethodList = new ArrayList<>();
            List<Method> beforeMethodList = new ArrayList<>();
            List<Method> afterMethodList = new ArrayList<>();

            for (Method method: methods) {
                Annotation[] annotations = method.getDeclaredAnnotations();
                for (Annotation annotation: annotations) {
                    Class<? extends Annotation> annotationType = annotation.annotationType();
                    if (annotationType == Test.class) {
                        testMethodList.add(method);
                    }
                    else if (annotationType == Before.class) {
                        beforeMethodList.add(method);
                    }
                    else if (annotationType == After.class) {
                        afterMethodList.add(method);
                    }
                }
            }

            Constructor<?> constructor = clazz.getConstructor();

            int successCount = 0;
            for (Method method: testMethodList) {
                boolean testSuccess = runTest(constructor, method, beforeMethodList, afterMethodList);
                if (testSuccess) {
                    successCount++;
                }
            }

            System.out.println("Successful tests: " + successCount +
                    "\nFailed tests: " + (testMethodList.size() - successCount) +
                    "\nTotal tests: "+ testMethodList.size());

        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean runTest(Constructor<?> constructor, Method method,
                                List<Method> beforeMethodList, List<Method> afterMethodList) {
        try {
            Object object = constructor.newInstance();

            boolean beforeSuccess;
            boolean testSuccess = false;
            boolean afterSuccess;

            beforeSuccess = runMethodList(object, beforeMethodList);
            if (beforeSuccess) {
                testSuccess = runMethod(method, object);
            }
            afterSuccess = runMethodList(object, afterMethodList);
            return beforeSuccess && testSuccess && afterSuccess;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    private static boolean runMethod(Method method, Object object) {
        try {
            method.invoke(object);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }

    private static boolean runMethodList(Object object, List<Method> methodList) {
        boolean allSuccess = true;
        for (Method method : methodList) {
            if (!runMethod(method, object)) {
                allSuccess = false;
            };
        }
        return allSuccess;
    }

}
