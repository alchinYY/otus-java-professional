package ru.otus.professional.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.professional.utils.ObjectMapperUtils;

import java.util.List;

import static org.modelmapper.config.Configuration.AccessLevel.*;

@Configuration
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper(
            List<PropertyMap<?, ?>> propertyMaps,
            List<Converter<?, ?>> converters
    ) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);
        propertyMaps.forEach(modelMapper::addMappings);
        converters.forEach(modelMapper::addConverter);
        return modelMapper;
    }

    @Bean
    public ObjectMapperUtils objectMapperUtils(ModelMapper mapper) {
        return new ObjectMapperUtils(mapper);
    }

}
