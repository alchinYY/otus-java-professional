package ru.otus.professional.utility;

public class ThreadUtility {

    public static void sleep(long second) {
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
