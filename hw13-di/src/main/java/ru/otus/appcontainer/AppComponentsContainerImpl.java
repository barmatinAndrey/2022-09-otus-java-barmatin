package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import ru.otus.AppComponentsException;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        processConfig(initialConfigClasses);
    }

    public AppComponentsContainerImpl(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        Set<Class<?>> initialConfigClasses = reflections.getSubTypesOf(Object.class);
        processConfig(initialConfigClasses.toArray(new Class<?>[0]));
    }

    private void processConfig(Class<?>... initialConfigClasses) {
        try {
            List<Method> methodList = new ArrayList<>();
            Map<Method, Object> configObjMap = new HashMap<>();
            for (Class<?> configClass : initialConfigClasses) {
                checkConfigClass(configClass);
                Object configObj = configClass.getConstructor().newInstance();
                for (Method method : configClass.getDeclaredMethods()) {
                    if (method.isAnnotationPresent(AppComponent.class)) {
                        methodList.add(method);
                        configObjMap.put(method, configObj);
                    }
                }
            }
            methodList.sort(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()));

            Map<Class<?>, Object> objectMap = new HashMap<>();
            for (Method method : methodList) {
                Class<?> returnType = method.getReturnType();
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    Object parameter = objectMap.get(parameterType);
                    if (parameter != null) {
                        parameters[i] = parameter;
                    }
                }
                Object obj = method.invoke(configObjMap.get(method), parameters);
                objectMap.put(returnType, obj);
                if (appComponentsByName.get(method.getAnnotation(AppComponent.class).name()) == null) {
                    appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), obj);
                } else {
                    throw new AppComponentsException("В контексте не должно быть компонентов с одинаковым именем");
                }
            }
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> objectList = appComponentsByName.values().stream().toList();
        List<Object> componentList = new ArrayList<>();
        for (Object obj : objectList) {
            if (componentClass.isAssignableFrom(obj.getClass())) {
                componentList.add(obj);
            }
        }
        if (componentList.size() == 1) {
            return (C)componentList.get(0);
        }
        else {
            throw new AppComponentsException("Найдено " + componentList.size() + " кандидатов вместо одного");
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C)appComponentsByName.get(componentName);
    }
}
