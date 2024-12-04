package com.programming.reactive.domain.service.impl;

import com.programming.reactive.domain.gateway.IPersonRepository;
import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.model.PersonStatus;
import com.programming.reactive.domain.service.IPersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements IPersonService {
    private final IPersonRepository personRepository;

    @Override
    public Mono<Person> createPerson(final String name) {
        final Person person = new Person();
                person.setName(name);
                person.setId(String.valueOf(UUID.randomUUID()));
                person.setPersonStatus(PersonStatus.PENDING);

        return Mono.fromCallable(() -> {
            log.info("Saving new person");
            return this.personRepository.save(person);
        })
            .subscribeOn(Schedulers.boundedElastic())
            .doOnNext(ps-> log.info("Person created {}", ps.getName()));

    }

    @Override
    public Mono<Person> getPerson(String id) {
        return Mono.fromCallable(() -> {
            log.info("Recovering person {}", id);
            return personRepository.get(id);
        })
        .subscribeOn(Schedulers.boundedElastic())
        .doOnNext(ps-> log.info("Person recovered {}", ps.getId()));
    }

    @Override
    public Mono<Person> approveAccount(String id) {
        return getPerson(id)
            .flatMap(person -> Mono.fromCallable(() -> {
                log.info("Processing person {} to approved", id);
                return this.personRepository.update(person.withPersonStatus(PersonStatus.APPROVED));
            })
                .subscribeOn(Schedulers.boundedElastic())
            );
    }

    @Override
    public Flux<Person> getApprovedPersons() {
        return Flux.defer(()-> {
            log.info("Recovering persons with status Approved");
            List<Person> persons = personRepository.getPersonByStatus(PersonStatus.APPROVED);
            return Flux.fromIterable(persons);
        })
        .switchIfEmpty(Flux.defer(() -> {
            log.warn("No persons found with status Approved");
            return Flux.empty();
        }))
        .subscribeOn(Schedulers.boundedElastic())
                .doOnComplete(() -> log.info("Finished recovering persons."));
    }
}
