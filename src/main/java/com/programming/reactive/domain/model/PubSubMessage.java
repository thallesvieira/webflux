package com.programming.reactive.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PubSubMessage {
    private Long id;
    private Person person;
}
