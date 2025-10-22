package ru.alex3koval.kafkaEventer;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import ru.alex3koval.eventingContract.EventListener;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ListenersRegistrationConfig {
    private final List<Props<?>> props;

    public void forEach(Consumer<Props<Object>> action) {
        props.forEach(t -> action.accept((Props<Object>) t));
    }

    public record Props<T>(
        List<String> topics,
        Class<? extends EventListener<T, Mono<?>>> listenerClazz,
        Class<T> payloadClazz
    ) {
    }
}
