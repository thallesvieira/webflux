package com.programming.reactive.resource.persistence.entity;

import com.programming.reactive.domain.model.PersonStatus;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

//@Entity
//@Table(name = "person")
@Data
@DynamoDbBean
public class PersonEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
//    @Enumerated(EnumType.STRING)
    private PersonStatus personStatus;

    @DynamoDbPartitionKey
    public Long getId() {
        return id;
    }

    @DynamoDbAttribute("person_status")
    public PersonStatus getPersonStatus() {
        return personStatus;
    }

    public static String getTableName() {
        return "person";
    }
}
