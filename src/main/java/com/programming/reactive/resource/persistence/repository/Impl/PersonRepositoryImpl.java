package com.programming.reactive.resource.persistence.repository.Impl;

import com.programming.reactive.domain.exception.ExceptionResponse;
import com.programming.reactive.domain.gateway.IPersonRepository;
import com.programming.reactive.domain.model.Person;
import com.programming.reactive.domain.model.PersonStatus;
import com.programming.reactive.resource.persistence.entity.PersonEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

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
    public Person update(final Person person) {
        log.info("Update person {}", person.getName());
        final PersonEntity entity = mapper.map(person, PersonEntity.class);

        return mapper.map(dynamoDbTemplate.update(entity), Person.class);
    }

    @Override
    public Person get(final String id) {
        var key = Key.builder().partitionValue(id).build();

        try {
            return mapper.map(dynamoDbTemplate.load(key, PersonEntity.class), Person.class);
        } catch (Exception ex) {
            throw new ExceptionResponse("Person not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Person> getPersonByStatus(PersonStatus status) {
        List<Person> persons = new ArrayList<>();
        var exp = Expression.builder().expression("person_status = :status")
                .putExpressionValue(":status", AttributeValue.builder().s(status.name()).build()).build();

        ScanEnhancedRequest scanRequest = ScanEnhancedRequest.builder()
                .filterExpression(exp)
                .build();

        dynamoDbTemplate.scan(scanRequest, PersonEntity.class)
                .items().stream()
                .forEach(entity-> persons.add(mapper.map(entity, Person.class)));

        return persons;
    }
}
