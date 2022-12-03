package ru.otus.professional.dataprocessor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.experimental.UtilityClass;
import ru.otus.professional.model.Measurement;

@UtilityClass
public class BeanConfiguration {

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    private static final ObjectMapper objectMapper = objectMapperConfigurer();

    private static ObjectMapper objectMapperConfigurer() {

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Measurement.class, new MeasurementDeserializer());

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .registerModule(module)
                .findAndRegisterModules();
    }
}
