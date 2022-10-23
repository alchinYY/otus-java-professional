package ru.otus.professional;

import ru.otus.professional.calc.IntCalcTest;
import ru.otus.professional.engine.TestStarter;

public class Application {
    public static void main(String[] args) {
        TestStarter.start(IntCalcTest.class);
    }
}
