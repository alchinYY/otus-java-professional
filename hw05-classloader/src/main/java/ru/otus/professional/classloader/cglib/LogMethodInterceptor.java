package ru.otus.professional.classloader.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import ru.otus.professional.classloader.annotation.Log;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class LogMethodInterceptor implements MethodInterceptor {

    private final Set<Method> logMethods;
    private final Object realObject;

    public LogMethodInterceptor(Object realObject) {
        this.realObject = realObject;

        logMethods = Stream.of(realObject.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if(logMethods.contains(method)) {
            logInfoAboutMethod(method, args);
        }
        return method.invoke(realObject, args);
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
