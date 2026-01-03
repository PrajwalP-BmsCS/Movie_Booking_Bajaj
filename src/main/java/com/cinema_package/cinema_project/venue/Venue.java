package com.cinema_package.cinema_project.venue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cinema_package.cinema_project.movie.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "venue")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Venue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @OneToMany(
        mappedBy = "venue",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Movie> movies = new ArrayList<>();

    /* getters & setters */

    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<Movie> getMovies() { return movies; }
}