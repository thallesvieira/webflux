package com.programming.reactive.application.web.controller;

import com.programming.reactive.application.web.dto.request.PersonDtoRequest;
import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.publisher.PersonPublisher;
import com.programming.reactive.domain.service.IPersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/person")
public class PersonController {

    private final IPersonService personService;
    private final PersonPublisher personPublisher;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Person> createPerson(@RequestBody PersonDtoRequest personDtoRequest) {
        final String name = personDtoRequest.getName();
        log.info("Person to be processed {}", name);
        return this.personService.createPerson(name)
                .flatMap(this.personPublisher::onPersonCreate)
                .doOnNext(next-> log.info("Person processed {}",name));
    }

    @GetMapping(value= "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Person> getPerson(@PathVariable final String id) {
        log.info("Person to be recovered {}", id);
        return this.personService.getPerson(id)
                .doOnNext(next-> log.info("Person recovered {}",id));
    }

    @GetMapping(value= "/approved", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Person> getApprovedPersons() {
        log.info("Person approved to be recovered ");
        return this.personService.getApprovedPersons()
                .doOnNext(next-> log.info("Person approved recovered"));
    }
}
