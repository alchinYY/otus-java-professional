# Курс OTUS ["Разработчик Java"](https://otus.ru/lessons/java-professional/?utm_source=github&utm_medium=free&utm_campaign=otus)

## Домашняя работа по занятию "Байт код, class-loader, инструментация, asm"

1. Проект на gradle
2. Java 17
3. cglib


Для запуска небходимо использовать (--add-opens java.base/java.lang=ALL-UNNAMED):

```
./gradlew build
java --add-opens java.base/java.lang=ALL-UNNAMED -jar hw05-classloader/build/libs/hw05-classloader-cglib-0.1.jar 

```