package com.programming.reactive.domain.publisher;

import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.model.PubSubMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Component
public class PersonPublisher {

    private final Sinks.Many<PubSubMessage> sink;

    public Mono<Person> onPersonCreate(final Person person) {
        return Mono.fromCallable(()-> {
            final Long id = person.getId();
            return new PubSubMessage(id, person);
        })
                .subscribeOn(Schedulers.parallel())
                .doOnNext(this.sink::tryEmitNext)
                .thenReturn(person);
    }
}
