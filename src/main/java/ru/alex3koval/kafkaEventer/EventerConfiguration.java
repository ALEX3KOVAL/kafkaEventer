package ru.alex3koval.kafkaEventer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventerConfiguration {
    @Bean
    EventerPreprocessor eventerAppRunner(
        EventerProperties eventerProps,
        ApplicationContext applicationContext
    ) {
        return new EventerPreprocessor(applicationContext, eventerProps);
    }
}
