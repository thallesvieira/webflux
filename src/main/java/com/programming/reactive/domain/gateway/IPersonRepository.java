package com.programming.reactive.domain.gateway;

import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.model.PersonStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IPersonRepository {
    Person save(final Person person);
    Person get(final long id);
    List<Person> getPersonByStatus(final PersonStatus status);
}