package ru.otus.professional.engine;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TestStat {

    private long numberOfTests;
    private final List<String> failedTests = new ArrayList<>();

    public void addTest() {
        numberOfTests++;
    }

    public void addFailedTest(String name) {
        failedTests.add(name);
    }
    public long getNumberOfCorrectTests() {
        return numberOfTests - failedTests.size();
    }
}
