package ru.otus.professional.engine;

import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.engine.anotation.After;
import ru.otus.professional.engine.anotation.Before;
import ru.otus.professional.engine.anotation.Test;
import ru.otus.professional.exception.IllegalAnnotationException;
import ru.otus.professional.exception.TestClassConstructorException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@UtilityClass
@Slf4j
class TestEngine {

    public <T> TestStat executeAllTests(Class<T> clazz) {
        var allMethods = getListMethodsFromCLass(clazz);
        var beforeMethods = getMethodsWithAnnotationFilter(allMethods, Before.class);
        var afterMethods = getMethodsWithAnnotationFilter(allMethods, After.class);
        var testStat = TestStat.createNewInstance();
        for (var testMethod : getMethodsWithAnnotationFilter(allMethods, Test.class)) {
            var instance = newInstance(clazz);
            var displayName = getDisplayName(testMethod);
            testStat.addTest();
            log.info("* Запуск теста::{}", displayName);
            runAllRequiredMethods(instance, beforeMethods);
            try {
                runTestMethod(instance, testMethod);
            } catch (Exception e) {
                e.printStackTrace(System.out);
                log.warn("result::ko");
                testStat.addFailedTest(displayName);
            } finally {
                runAllRequiredMethods(instance, afterMethods);
            }
        }
        return testStat;
    }

    private <T> List<Method> getListMethodsFromCLass(Class<T> clazz) {
        return Stream.of(clazz.getMethods()).toList();
    }

    private <T> void runTestMethod(T instance, Method testMethod) throws InvocationTargetException, IllegalAccessException {
        checkIllegalAnnotation(testMethod);
        testMethod.invoke(instance);
        log.info("резултат::ok");
    }

    private <T> T newInstance(Class<T> clazz) {
        try {
            log.debug("Class Name:{}", clazz.getSimpleName());
            Constructor<?>[] constructors = clazz.getConstructors();
            log.debug("--- constructors:\n{}", Arrays.toString(constructors));
            Constructor<T> constructor = clazz.getConstructor();
            return constructor.newInstance();
        } catch (Exception ex) {
            throw new TestClassConstructorException("Не удалось создать конструктор класса " + clazz.getName(), ex);
        }
    }

    private <T> void runAllRequiredMethods(T instance, List<Method> methods) {
        try {
            for (Method method : methods) {
                method.invoke(instance);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <T extends Annotation> List<Method> getMethodsWithAnnotationFilter(List<Method> methods, Class<T> annotationClass) {
        return methods.stream()
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .toList();
    }

    private void checkIllegalAnnotation(Method method) {
        if (method.isAnnotationPresent(Before.class)
                || method.isAnnotationPresent(After.class)) {
            throw new IllegalAnnotationException(
                    "Test-method \"" + method.getName() + "\" contain annotation @Before or @After"
            );
        }
    }

    private String getDisplayName(Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        if (Strings.isNullOrEmpty(testAnnotation.value())) {
            return method.getName();
        } else {
            return testAnnotation.value();
        }
    }


}
