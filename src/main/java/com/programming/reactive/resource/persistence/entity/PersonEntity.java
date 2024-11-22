package com.programming.reactive.resource.persistence.entity;

import com.programming.reactive.domain.model.PersonStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "person")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Enumerated(EnumType.STRING)
    private PersonStatus personStatus;
}
