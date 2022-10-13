package ru.otus.professional;

import com.google.common.collect.ArrayListMultimap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Application {

    private static final String MAP_KEY1 = "key1";
    private static final String KEY1_ELEM4 = "valKey4";
    private static final List<String> LIST_KEY1 = Arrays.asList("valKey3", "valKey2", "valKey1");

    public static void main(String... args) {
        ArrayListMultimap<String, String> mapOfLists = ArrayListMultimap.create();
        mapOfLists.putAll(MAP_KEY1, LIST_KEY1);
        mapOfLists.put(MAP_KEY1, KEY1_ELEM4);

        log("before sort::%s%n", mapOfLists);
        Collections.sort(mapOfLists.get(MAP_KEY1));
        log("after sort::%s%n", mapOfLists);
    }

    private static void log(String pattern, Object ... args) {
        System.out.printf(pattern, args);
    }

}
