package ru.otus.professional.engine;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestStat {

    long numberOfTests;
    final List<String> failedTests = new ArrayList<>();

    public void addTest() {
        numberOfTests++;
    }

    public void addFailedTest(String name) {
        failedTests.add(name);
    }
    public long getNumberOfCorrectTests() {
        return numberOfTests - failedTests.size();
    }

    private TestStat() {
    }

    static TestStat createNewInstance() {
        return new TestStat();
    }
}
