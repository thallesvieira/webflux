package com.programming.reactive.domain.service;

import com.programming.reactive.domain.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPersonService {
    Mono<Person> createPerson(final String name);
    Mono<Person> getPerson(final String id);
    Mono<Person> approveAccount(String id);
    Flux<Person> getApprovedPersons();
}
