package com.example.lab7.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @ManyToOne
    @JoinColumn(name = "authorizedResource")
    private Resources resources;

    @Column(name = "active", nullable = false)
    private Integer active;

}
