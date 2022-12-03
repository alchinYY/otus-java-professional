package ru.otus.professional.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final File resultFile;
    private final ObjectMapper objectMapper;

    public FileSerializer(String fileName, ObjectMapper objectMapper) {
        resultFile = new File(fileName);
        this.objectMapper = objectMapper;
    }

    public FileSerializer(String fileName) {
        this(fileName, BeanConfiguration.getObjectMapper());
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            objectMapper.writeValue(resultFile, data);
        } catch (IOException ex) {
            throw new FileProcessException(ex);
        }
    }
}
