package ru.otus.professional;


import ru.otus.professional.appcontainer.AppComponentsContainerImpl;
import ru.otus.professional.appcontainer.api.AppComponentsContainer;
import ru.otus.professional.config.AppConfig;
import ru.otus.professional.services.GameProcessor;
import ru.otus.professional.services.GameProcessorImpl;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.
Можно добавлять свои исключения.
Раскоментируйте тест:
@Disabled //надо удалить
Тест и демо должны проходить для всех реализованных вариантов
Не называйте свой проект ДЗ "homework-template", это имя заготовки)
PS Приложение представляет собой тренажер таблицы умножения
*/

public class Application {

    public static void main(String[] args) {
        // Опциональные варианты
        //AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig1.class, AppConfig2.class);

        // Тут можно использовать библиотеку Reflections (см. зависимости)
        //AppComponentsContainer container = new AppComponentsContainerImpl("ru.otus.config");

        // Обязательный вариант
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

//         Приложение должно работать в каждом из указанных ниже вариантов
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
//        GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
//        GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

        gameProcessor.startGame();
    }

}
