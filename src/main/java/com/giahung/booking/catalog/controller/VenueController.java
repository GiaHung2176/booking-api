package com.giahung.booking.catalog.controller;

import com.giahung.booking.catalog.dto.VenueResponse;
import com.giahung.booking.catalog.service.VenueService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController 
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService){
        this.venueService=venueService;
    }

    @GetMapping ("/api/venues")
    public Page<VenueResponse> findAll(Pageable pageable){
        return venueService.findAll(pageable);
    }

    @GetMapping ("/api/venues/{id}")
    public VenueResponse findById(@PathVariable Long id){
        return venueService.findById(id);
    }
}
