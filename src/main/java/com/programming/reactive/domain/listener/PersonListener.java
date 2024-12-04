package com.programming.reactive.domain.listener;

import com.programming.reactive.domain.model.PubSubMessage;
import com.programming.reactive.domain.service.IPersonService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@RequiredArgsConstructor
@Component
@Slf4j
public class PersonListener implements InitializingBean {

    private final Sinks.Many<PubSubMessage> sink;
    private final IPersonService personService;

    @SqsListener("sqs-person")
    public void listener(PubSubMessage message) {
        Flux.defer(()-> {
            log.info("On next message - {}", message.getId());
            return this.personService.approveAccount(message.getId());
        })
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext(it -> log.info("Person processed on listener"))
            .subscribe();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.sink.asFlux()
            .delayElements(Duration.ofSeconds(2))
            .subscribe(
                next -> {
                    log.info("On next message - {}", next.getId());
                    this.personService.approveAccount(next.getId())
                        .doOnNext(it -> log.info("Person processed on listener"))
                        .subscribe();
                },
                error -> {
                    log.error("On pub-sub listener observe error", error);
                },
                () -> {
                    log.info("On pub-sub listener complete");
                }
            );
    }
}
