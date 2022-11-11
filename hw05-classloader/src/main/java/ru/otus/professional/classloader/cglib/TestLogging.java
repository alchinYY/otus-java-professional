package ru.otus.professional.classloader.cglib;


import ru.otus.professional.classloader.annotation.Log;

public class TestLogging {

    @Log
    public void calculation(int a) {
        System.out.println("пометил аннотацией, 1 аргумент");
    }

    @Log
    public void calculation(int a, int b) {
        System.out.println("пометил аннотацией, 2 аргумента");
    }

    @Log
    public void calculation(int a, int b, String c) {
        System.out.println("пометил аннотацией, 3 аргумента");
    }

    public void calculation(int a, int b, String c, int d) {
        System.out.println("не пометил аннотацией, 4 аргумента");
    }

    @Log
    public String stringCalculation(String a, String b) {
        return a + " " + b;
    }

    @Log
    public void calculation() {
        System.out.println("пометил аннотацией, 0 аргументов");
    }
}
