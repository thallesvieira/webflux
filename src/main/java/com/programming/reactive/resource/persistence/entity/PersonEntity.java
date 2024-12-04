package com.programming.reactive.resource.persistence.entity;

import com.programming.reactive.domain.model.PersonStatus;
import lombok.Data;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@DynamoDbBean
public class PersonEntity {
    private String id;
    private String name;
    private PersonStatus personStatus;

    @DynamoDbPartitionKey
    public String getId() {
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
