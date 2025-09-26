package ru.alex3koval.kafkaEventer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import ru.alex3koval.eventingContract.EventListener;
import ru.alex3koval.eventingImpl.manager.EventListenerManager;

import java.util.List;

@RequiredArgsConstructor
public class EventerPreprocessor {
    private final ApplicationContext applicationContext;
    private final EventerProperties eventerProps;
    EventListenerManager eventListenerManager;

    public <T, R> void registerListener(
        List<String> topics,
        Class<? extends EventListener<T, R>> eventListenerClazz,
        Class<T> payloadClazz
    ) {
        topics.forEach(topic -> {
            EventerProperties.TopicParams topicParams = eventerProps
                .getByTopic(topic)
                .orElseGet(null);

            if (topicParams == null) {
                throw new RuntimeException("Не найдена конфигурация по топику: " + topic);
            }

            if (!topicParams.topic().equalsIgnoreCase(topic)) {
                throw new RuntimeException(
                    String.format(
                        "Топик из конфигурации не совпал с топиком для поиска: %s и %s",
                        topicParams.topic(),
                        topic
                    )
                );
            }

            eventListenerManager.registerListener(
                topicParams.topic(),
                topicParams.groupId(),
                applicationContext.getBean(eventListenerClazz),
                payloadClazz
            );
        });
    }
}
