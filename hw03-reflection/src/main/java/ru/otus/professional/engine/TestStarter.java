package ru.otus.professional.engine;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class TestStarter {

    public <T> void start(Class<T> clazz) {
        log.info("Запуск тестирования класса {}\n", clazz.getSimpleName());
        TestStat testStat = TestEngine.executeAllTests(clazz);
        printStatistic(testStat);
    }

    private void printStatistic(TestStat testStat) {

        log.info("Всего тестов:: {}",testStat.getNumberOfTests());
        log.info("Всего успешно пройдено тестов:: {}", testStat.getNumberOfCorrectTests());
        log.info("Список упавших тестов:\n* {}", String.join("\n* ", testStat.getFailedTests()));

    }

}
