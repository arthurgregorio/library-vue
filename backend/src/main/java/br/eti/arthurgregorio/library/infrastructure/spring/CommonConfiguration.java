package br.eti.arthurgregorio.library.infrastructure.spring;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.module.jsr310.Jsr310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 03/04/2020
 */
@EnableAsync
@Configuration
@EnableScheduling
public class CommonConfiguration {

    /**
     *
     * @return
     */
    @Bean
    public ModelMapper modelMapper() {

        final ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setFieldAccessLevel(AccessLevel.PRIVATE)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.registerModule(new Jsr310Module());

        return mapper;
    }
}
