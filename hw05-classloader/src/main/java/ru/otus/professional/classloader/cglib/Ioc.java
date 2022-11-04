package ru.otus.professional.classloader.cglib;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import ru.otus.professional.classloader.annotation.Log;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j(topic = "proxy-logging")
@UtilityClass
public class Ioc {

    @SuppressWarnings("unchecked")
    public <T> T createInstance(Class<T> clazz) {
        try {
            T testLogging = clazz.getDeclaredConstructor().newInstance();
            MethodInterceptor handler = createMethodInterceptor(testLogging);
            return (T) Enhancer.create(TestLogging.class, handler);
        } catch (Exception ex) {
            throw new IocException("Cannot create proxy: " + ex.getMessage(), ex);
        }
    }

    private MethodInterceptor createMethodInterceptor(Object originalObject) {
        return (obj, method, methodArgs, proxy) -> {
            if (method.isAnnotationPresent(Log.class)) {
                logInfoAboutMethod(method, methodArgs);
                return method.invoke(originalObject, methodArgs);
            }
            return method.invoke(originalObject, methodArgs);
        };
    }

    private void logInfoAboutMethod(Method method, Object[] args) {
        log.info("executed |METHOD:{} |PARAMS:{} |RESULT:{} |",
                method.getName(),
                createArgsString(args),
                method.getReturnType().getSimpleName()
        );
    }

    private String createArgsString(Object[] args) {
        return Stream.of(args)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}
