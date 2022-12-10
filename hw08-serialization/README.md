# Курс OTUS ["Разработчик Java"](https://otus.ru/lessons/java-professional/?utm_source=github&utm_medium=free&utm_campaign=otus)

## Домашняя работа по работе с сериализацией

1. Проект на gradle
2. Java 17
3. Jackson

В связи с ограничениями на изменение классов было принято решение создать класс MeasurementDeserializer. С помощью него
можно обойти ограничение отсутствия конструктора без параметров для Measurement.


Пример регистрации десериализатора приведен ниже

```java

    SimpleModule module = new SimpleModule();
    module.addDeserializer(Measurement.class, new MeasurementDeserializer());

    return new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY,true)
            .registerModule(module)
            .findAndRegisterModules();

```

В связи с ограничением на правку тестов, пришлось прибегнуть к необходимости подключать маппер внутри конструктора в
соответствующих классах сериализации и десериализации json.

```java
    public FileSerializer(String fileName, ObjectMapper objectMapper) {
        resultFile = new File(fileName);
        this.objectMapper = objectMapper;
    }

    public FileSerializer(String fileName) {
        this(fileName, BeanConfiguration.getObjectMapper());
    }
```

```java
    public ResourcesFileLoader(String fileName, ObjectMapper objectMapper) {
        this.fileName = fileName;
        this.objectMapper = objectMapper;
    }

    public ResourcesFileLoader(String fileName) {
        this(fileName, BeanConfiguration.getObjectMapper());
    }
```

