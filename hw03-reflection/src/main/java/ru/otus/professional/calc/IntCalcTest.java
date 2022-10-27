package ru.otus.professional.calc;

import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.engine.anotation.After;
import ru.otus.professional.engine.anotation.Before;
import ru.otus.professional.engine.anotation.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class IntCalcTest {

    private final IntCalc intCalc = new IntCalc();

    @Before
    @After
    public void printSeparator() {
        System.out.println("-----------------------");
    }

    @Before
    public void setUp() {
        log.info("Подготовка к тестированию");
    }

    @After
    public void tearDown() {
        log.info("Избавляемся от тестовых артефактов");
    }

    @Test
    public void sumTest() {
        assertThat(intCalc.sum(1, 5)).isEqualTo(6);
    }

    @Test("Сумма")
    public void sumTestNamed() {
        assertThat(intCalc.sum(1, 3)).isEqualTo(4);
    }

    @Test("Сумма c аннотацией @Before")
    @Before
    public void sumTestNamedWithBefore() {
        log.debug("@Before отработает, а тест с данной аннотацией упадет");
    }

    @Test("Тест, который должен упасть")
    public void sumFailTestNamed() {
        assertThat(intCalc.sum(1, 3)).isEqualTo(5);
    }

    @Test("C параметрами должен упасть, такое не предусмотрено фреймворком")
    public void sumTestNamed(int a, int b) {
        assertThat(intCalc.sum(a, b)).isEqualTo(4);
    }
}

