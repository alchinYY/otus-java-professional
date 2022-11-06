package ru.otus.professional.classloader.cglib;

import lombok.experimental.UtilityClass;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

@UtilityClass
public class Ioc {

    @SuppressWarnings("unchecked")
    public <T> T createInstance(Class<T> clazz) {
        try {
            T testLogging = clazz.getDeclaredConstructor().newInstance();
            MethodInterceptor handler = new LogMethodInterceptor(testLogging);
            return (T) Enhancer.create(TestLogging.class, handler);
        } catch (Exception ex) {
            throw new IocException("Cannot create proxy: " + ex.getMessage(), ex);
        }
    }
}
