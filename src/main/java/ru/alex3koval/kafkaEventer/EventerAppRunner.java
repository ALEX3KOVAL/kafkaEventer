package ru.alex3koval.kafkaEventer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.CountDownLatch;

@Slf4j
@RequiredArgsConstructor
class EventerAppRunner implements ApplicationRunner, DisposableBean {
    private final EventerPreprocessor eventerPreprocessor;
    private final ListenersRegistrationConfig listenerRegistrationPropsConfig;
    private final CountDownLatch shutdownLatch = new CountDownLatch(1);

    @Override
    public void run(ApplicationArguments args) {
        listenerRegistrationPropsConfig.forEach(listenerRegistrationProps ->
            eventerPreprocessor.registerListener(
                listenerRegistrationProps.topics(),
                listenerRegistrationProps.listenerClazz(),
                listenerRegistrationProps.payloadClazz()
            )
        );

        try {
            shutdownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void destroy() {
        eventerPreprocessor.stopContainers();
    }
}
