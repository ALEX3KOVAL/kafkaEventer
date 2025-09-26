package ru.alex3koval.kafkaEventer;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Optional;

@ConfigurationProperties("app.eventer")
public record EventerProperties(
    List<TopicParams> topicParams
) {
    public record TopicParams(
        String topic,
        String groupId
    ) {
    }

    public Optional<TopicParams> getByTopic(String topic) {
        return topicParams.stream()
            .filter(elem -> elem.topic().equalsIgnoreCase(topic))
            .findFirst();
    }
}
