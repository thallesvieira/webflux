package com.programming.reactive.domain.publisher;

import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.model.PubSubMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Component
public class PersonPublisher {

    private final Sinks.Many<PubSubMessage> sink;
    private final SqsTemplate sqsTemplate;
    private final String SQS = "https://localhost.localstack.cloud:4566/000000000000/sqs-person";

    public Mono<Person> onPersonCreate(final Person person) {
        return Mono.fromCallable(()-> {
            final String id = person.getId();
            return new PubSubMessage(id, person);
        })
                .subscribeOn(Schedulers.parallel())
                .doOnNext(pub->sqsTemplate.send(SQS, pub))
                //.doOnNext(this.sink::tryEmitNext)
                .thenReturn(person);
    }
}
