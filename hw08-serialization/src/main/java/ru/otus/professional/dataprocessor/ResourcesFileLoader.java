package ru.otus.professional.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.professional.model.Measurement;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String fileName;
    private final ObjectMapper objectMapper;

    public ResourcesFileLoader(String fileName, ObjectMapper objectMapper) {
        this.fileName = fileName;
        this.objectMapper = objectMapper;
    }

    public ResourcesFileLoader(String fileName) {
        this(fileName, BeanConfiguration.getObjectMapper());
    }

    @Override
    public List<Measurement> load() {
        try(var is = getInputStreamFromClassLoader(fileName)) {
            return objectMapper.readValue(is, new TypeReference<>() {});
        } catch (IOException ex) {
            throw new FileProcessException(ex);
        }
    }

    private InputStream getInputStreamFromClassLoader(String fileName) {
        return getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);
    }

}
