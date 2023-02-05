package ru.otus.professional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SawDigitMultiThreadGenerator implements DigitGenerator {

    private static final boolean UP = true;
    private static final boolean DOWN = false;


    private final int minValue;
    private final int maxValue;
    private int currentValue;
    private boolean direction;

    public SawDigitMultiThreadGenerator(int minValue, int maxValue, int startValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        currentValue = startValue;

        if (startValue >= maxValue) {
            direction = DOWN;
            currentValue = maxValue;
        } else {
            direction = UP;
            if (startValue <= minValue) {
                currentValue = minValue;
            }
        }

    }

    @Override
    public void generate() {
        generatorLogic();
    }

    private void generatorLogic() {
        log.info("{}", currentValue);

        currentValue = direction == DOWN ? currentValue - 1 : currentValue + 1;

        if (currentValue == minValue || currentValue == maxValue) {
            direction = !direction;
        }
    }
}
