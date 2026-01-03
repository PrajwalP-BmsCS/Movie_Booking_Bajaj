package com.cinema_package.cinema_project.venue;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    /* -------- CREATE -------- */
    @CacheEvict(value = "venues", allEntries = true)
    public Venue addVenue(Venue venue) {
        System.out.println("Venue name = " + venue.getName());
        System.out.println("Venue city = " + venue.getCity());
        return venueRepository.save(venue);
    }


    /* -------- READ -------- */
    @Cacheable(value = "venues", key = "'all'")
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    @Cacheable(value = "venue", key = "#id")
    public Venue getVenueById(Long id) {
        return venueRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venue not found"));
    }

    /* -------- DELETE -------- */
    @Caching(evict = {
        @CacheEvict(value = "venue", key = "#id"),
        @CacheEvict(value = "venues", allEntries = true)
    })
    public void deleteVenue(Long id) {
        Venue venue = getVenueById(id);
        venueRepository.delete(venue);
    }
}
