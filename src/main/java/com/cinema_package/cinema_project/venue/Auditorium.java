package com.cinema_package.cinema_project.venue;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class Auditorium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Screen 1, Audi A

    @ManyToOne
    private Venue venue;

    private int totalSeats;
}
