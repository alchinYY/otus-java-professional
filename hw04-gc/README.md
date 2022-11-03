# Курс OTUS ["Разработчик Java"](https://otus.ru/lessons/java-professional/?utm_source=github&utm_medium=free&utm_campaign=otus)

## Домашняя работа по работе с настройкой GC

1. Проект на gradle
2. Java 17



| Параметры VM | Значение времени |
| -----------                          | ----------- |
| -Xms256m -Xmx256m | Exception in thread "main" java.lang.OutOfMemoryError: Java heap space |
| -Xms384m -Xmx384m | spend msec:12245, sec:12 |
| -Xms512m -Xmx512m | spend msec:11748, sec:11 |
| -Xms700m -Xmx700m | spend msec:11259, sec:11 |
| -Xms750m -Xmx750m | spend msec:11053, sec:11 |
| -Xms850m -Xmx850m | spend msec:10912, sec:10 |
| -Xms1g -Xmx1g | spend msec:10695, sec:10 |
| -Xms2g -Xms2g | spend msec:10619, sec:10 |
| -Xms4g -Xmx4g | spend msec:10842, sec:10|


Выходит, на моем комрьютере оптимально запускать можно на 1g без особой потери производительности (+- 1 сек не критично)
Почему не 850? - повторные запуски иногда приводили к повышению значения



##Оптимизация кода

Убрал класс Data, он нам не нужнен, можно передавать int.

Все Integer преобразовал в int
Убрал заполнение ArrayList. Нам можно считать общее количество элементов, которые прошли через нас

Вынес до цикла расчет кусочка

```
var tempVal = (sumLastThreeValues * sumLastThreeValues / (data + 1) - sum);

```

Если убрать еще кусок
```
if (idx % 10_000_000 == 0) {
System.out.println(LocalDateTime.now() + " current idx:" + idx);
}
```
Можно сэкономить еще 100 мсек. Но считал с выводом


| Параметры VM      | Значение времени       |
| ----------------- | ---------------------- |
| -Xms1m -Xmx1m     | Too small maximum heap |
| -Xms2m -Xmx2m     | GC triggered before VM initialization completed. Try increasing NewSize, current value 1331K. |
| -Xms4m -Xmx4m     | spend msec:671, sec:0  |
| -Xms8m -Xmx8m     | spend msec:674, sec:0  |
| -Xms32m -Xmx32m   | spend msec:697, sec:0  |
| -Xms64m -Xmx64m   | spend msec:702, sec:0  |
| -Xms128m -Xmx128m | spend msec:712, sec:0  |
