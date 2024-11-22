package com.programming.reactive.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Person {
    private Long id;
    private String name;
    @With
    private PersonStatus personStatus;
}