package com.cinema_package.cinema_project.movie;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.cinema_package.cinema_project.venue.Show;
import com.cinema_package.cinema_project.venue.Venue;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movie implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(
            name = "movie_id_sequence",
            sequenceName = "movie_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movie_id_sequence"
    )
    private int id;

    @OneToMany(
        mappedBy = "movie",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<Show> shows;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    private String title;
    private String director;
    private String description;
    private String genre;
    private LocalDate date;
    private int price;

    public Movie(int id, String title, String director, String description, String genre, LocalDate date, int price, Venue venue) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.description = description;
        this.genre = genre;
        this.date = date;
        this.price = price;
        this.venue = venue;
    }

    public Movie() {}
    
    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getDirector() {return director;}
    public void setDirector(String director) {this.director = director;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getGenre() {return genre;}
    public void setGenre(String genre) {this.genre = genre;}

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public LocalDate getDate() {return date;}
    public void setDate(LocalDate date) {this.date = date;}

    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}
}