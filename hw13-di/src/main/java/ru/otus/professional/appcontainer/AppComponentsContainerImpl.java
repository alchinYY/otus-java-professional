package ru.otus.professional.appcontainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.professional.appcontainer.api.AppComponent;
import ru.otus.professional.appcontainer.api.AppComponentsContainer;
import ru.otus.professional.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.professional.exception.DiException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private static final Logger log = LoggerFactory.getLogger(AppComponentsContainerImpl.class);

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        try {
            var configObject = createInstance(configClass);
            var orderedConfigMethodsList = findAllConfigurationMethodsWithOrder(configClass);
            for (Method method : orderedConfigMethodsList) {
                var componentName = getComponentNameFromConfigMethod(method);
                log.info("configName:{}", componentName);
                var appComponentInstance = initConfiguration(method, configObject);
                appComponents.add(appComponentInstance);
                appComponentsByName.put(componentName, appComponentInstance);
            }
            log.info("all configClass: {}", appComponentsByName.keySet());

        } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException ex) {
            throw new DiException("Config cannot initialize by reason::" + ex.getMessage(), ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        log.info("get component with name:{}", componentClass.getName());
        var configComponentsList = appComponents.stream()
                .filter(component -> componentClass.isAssignableFrom(component.getClass()))
                .toList();

        if(configComponentsList.isEmpty()) {
            throw new DiException(String.format("Current been not found \"%s\"", componentClass.getName()));
        }
        if(configComponentsList.size() > 1) {
            throw new DiException(String.format("Current been is double \"%s\"", componentClass.getName()));
        }
        return (C) configComponentsList.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        if (appComponentsByName.containsKey(componentName)) {
            return (C) appComponentsByName.get(componentName);
        } else {
            throw new DiException("Current been not found \"" + componentName + "\"");
        }
    }

    private Object initConfiguration(Method currentMethod, Object configClass)
            throws InvocationTargetException, IllegalAccessException {
        var parameters = currentMethod.getParameterTypes();
        var arrayWithArgs = new Object[parameters.length];

        log.info("for invoke method \"{}\" need next parameters: \"{}\"",
                currentMethod.getName(), Stream.of(parameters).map(Class::getName).toList());

        for (int i = 0; i < parameters.length; i++) {
            arrayWithArgs[i] = getAppComponent(parameters[i]);
        }

        return currentMethod.invoke(configClass, arrayWithArgs);
    }

    private List<Method> findAllConfigurationMethodsWithOrder(Class<?> configClass) {
        return Arrays.stream(configClass.getMethods())
                .filter(m -> m.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(o -> o.getAnnotation(AppComponent.class).order()))
                .toList();
    }

    private Object createInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            throw new DiException("Config cannot initialize by reason::" + ex.getMessage(), ex);
        }
    }

    private String getComponentNameFromConfigMethod(Method method) {
        var name = method.getAnnotation(AppComponent.class).name();
        if (appComponentsByName.containsKey(name)) {
            throw new DiException("current key is exists \"" + name + "\"");
        }
        return name;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

}
