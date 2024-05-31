package com.example.lab7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "resources")
@Getter
@Setter
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resourceId", nullable = false)
    private Integer resourceId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

}
