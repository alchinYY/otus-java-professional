package ru.otus.professional;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        DigitGenerator digitGenerator = new SawDigitMultiThreadGenerator(1, 10, 0);
        DigitGenerator digitGenerator2 = new SawDigitMultiThreadGenerator(1, 10, 0);


        ThreadContainer threadContainer = new ThreadContainer(List.of(digitGenerator, digitGenerator2));
        threadContainer.start(-1);
    }

}
