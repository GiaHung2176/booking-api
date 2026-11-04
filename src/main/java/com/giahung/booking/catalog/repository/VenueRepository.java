package com.giahung.booking.catalog.repository;

import com.giahung.booking.catalog.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue,Long> {
    
}
