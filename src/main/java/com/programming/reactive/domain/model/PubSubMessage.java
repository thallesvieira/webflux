package com.programming.reactive.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PubSubMessage {
    private String id;
    private Person person;
}
