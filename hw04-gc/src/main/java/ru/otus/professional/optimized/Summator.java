package ru.otus.professional.optimized;

public class Summator {


    private int sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;
    private int valCounter = 0;

    //!!! сигнатуру метода менять нельзя
    public void calc(int data) {
        valCounter++;
        if (valCounter == 6_600_000) {
            valCounter = 0;
        }
        sum += data;

        sumLastThreeValues = data + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data;

        var tempVal = (sumLastThreeValues * sumLastThreeValues / (data + 1) - sum);

        for (var idx = 0; idx < 3; idx++) {
            someValue = Math.abs(someValue + tempVal) + valCounter;
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}