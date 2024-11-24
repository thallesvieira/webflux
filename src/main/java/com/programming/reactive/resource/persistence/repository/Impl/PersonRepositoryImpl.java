package com.programming.reactive.resource.persistence.repository.Impl;

import com.programming.reactive.domain.gateway.IPersonRepository;
import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.model.PersonStatus;
import com.programming.reactive.resource.persistence.entity.PersonEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Repository
@Slf4j
public class PersonRepositoryImpl implements IPersonRepository {
    //private final IPersonJPARepository personJPARepository;
    private final DynamoDbTemplate dynamoDbTemplate;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public Person save(final Person person) {
        log.info("Saving person {}", person.getName());
        final PersonEntity entity = mapper.map(person, PersonEntity.class);

        return mapper.map(dynamoDbTemplate.save(entity), Person.class);
    }

    @Override
    public Person get(final long id) {
        return null;
//        personJPARepository.findById(id)
//                .map(entity-> mapper.map(entity, Person.class))
//                .orElseThrow(() -> new ExceptionResponse("Person not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Person> getPersonByStatus(PersonStatus status) {
        List<Person> persons = new ArrayList<>();
//        personJPARepository.findAllByPersonStatus(status)
//                .ifPresent(it->
//                    it.forEach(entity-> persons.add(mapper.map(entity, Person.class))));

        return persons;


    }
}
