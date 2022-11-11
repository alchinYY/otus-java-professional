package ru.otus.professional.classloader;

import ru.otus.professional.classloader.cglib.Ioc;
import ru.otus.professional.classloader.cglib.TestLogging;

//--add-opens java.base/java.lang=ALL-UNNAMED

public class Application {

    public static void main(String[] args) {

        var testLogging = Ioc.createInstance(TestLogging.class);
        testLogging.calculation(1);
        testLogging.calculation(2, 3);
        testLogging.calculation(2, 3, "a");
        testLogging.calculation(2, 3, "a", 5);
        testLogging.calculation();

        System.out.println(testLogging.stringCalculation("Hello", "World"));
    }

}
