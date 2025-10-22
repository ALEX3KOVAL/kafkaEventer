package ru.alex3koval.kafkaEventer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.alex3koval.eventingImpl.manager.EventListenerManager;

@Configuration
public class EventerConfiguration {
    @Bean
    EventerAppRunner eventHandlingAppRunner(
        EventerPreprocessor eventerPreprocessor,
        ListenersRegistrationConfig listenersRegistrationConfig
    ) {
        return new EventerAppRunner(eventerPreprocessor, listenersRegistrationConfig);
    }

    @Bean
    EventerPreprocessor eventerPreprocessor(
        EventerProperties eventerProps,
        ApplicationContext applicationContext,
        EventListenerManager eventListenerManager
    ) {
        return new EventerPreprocessor(applicationContext, eventerProps, eventListenerManager);
    }
}
