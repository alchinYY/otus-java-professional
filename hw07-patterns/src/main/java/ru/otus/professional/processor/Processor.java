package ru.otus.professional.processor;

import ru.otus.professional.model.Message;

public interface Processor {

    Message process(Message message);

    //todo: 2. Сделать процессор, который поменяет местами значения field11 и field12

    //todo: 3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
    //         Секунда должна определяьться во время выполнения.
    //         Тест - важная часть задания
    // Обязательно посмотрите пример к паттерну Мементо!
}
