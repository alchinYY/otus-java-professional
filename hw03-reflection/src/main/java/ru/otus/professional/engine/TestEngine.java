package ru.otus.professional.engine;

import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
class TestEngine<T> {

    private final Class<T> clazz;

    public TestStat executeAllTests() {
        var allMethods = getListMethodsFromCLass();
        var beforeMethods = getMethodsWithAnnotationFilter(allMethods, Before.class);
        var afterMethods = getMethodsWithAnnotationFilter(allMethods, After.class);
        var testStat = new TestStat();
        for (var testMethod : getMethodsWithAnnotationFilter(allMethods, Test.class)) {
            var instance = newInstance();
            var displayName = getDisplayName(testMethod);
            testStat.addTest();
            log.info("* Запуск теста::{}", displayName);
            runAllRequiredMethods(instance, beforeMethods);
            checkIllegalAnnotation(testMethod);
            try {
                runTestMethod(instance, testMethod);
            } catch (Exception e) {
                log.warn("result::ko.\n{}", stackTraceToString(e.getStackTrace()));
                testStat.addFailedTest(displayName);
            } finally {
                runAllRequiredMethods(instance, afterMethods);
            }
        }
        return testStat;
    }

    private String stackTraceToString(StackTraceElement[] stackTraceElements) {
        return Stream.of(stackTraceElements)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
    }

    private List<Method> getListMethodsFromCLass() {
        return Stream.of(clazz.getMethods()).toList();
    }

    private void runTestMethod(T instance, Method testMethod) throws InvocationTargetException, IllegalAccessException {
        testMethod.invoke(instance);
        log.info("резултат::ok");
    }

    private T newInstance() {
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

    private void runAllRequiredMethods(T instance, List<Method> methods) {
        try {
            for (Method method : methods) {
                method.invoke(instance);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <A extends Annotation> List<Method> getMethodsWithAnnotationFilter(List<Method> methods, Class<A> annotationClass) {
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
