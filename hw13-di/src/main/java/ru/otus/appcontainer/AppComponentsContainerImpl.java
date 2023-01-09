package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

//    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void processConfig(Class<?> configClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        checkConfigClass(configClass);
        Object obj = configClass.getConstructor().newInstance();
//        configClass.isAssignableFrom()
        List<Method> methodList = new ArrayList<>();
        for (Method method: configClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AppComponent.class)) {
                methodList.add(method);
            }
        }
        methodList.sort(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()));

        Map<Class<?>, Object> objectMap = new HashMap<>();
        methodList.forEach(method -> {
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

            try {
                Object object = method.invoke(obj, parameters);
                objectMap.put(returnType, object);
//                appComponents.add(object);
                appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), object);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> objectList = appComponentsByName.values().stream().toList();
        for (Object obj: objectList) {
            if (componentClass.isAssignableFrom(obj.getClass())) {
                return (C) obj;
            }
        }
        return null;
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C)appComponentsByName.get(componentName);
    }
}
