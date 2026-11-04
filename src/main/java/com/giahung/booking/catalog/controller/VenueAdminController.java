package com.giahung.booking.catalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.giahung.booking.catalog.dto.VenueRequest;
import com.giahung.booking.catalog.dto.VenueResponse;
import com.giahung.booking.catalog.service.VenueService;
import jakarta.validation.Valid;

@RestController 
public class VenueAdminController {

    private final VenueService venueService;

    public VenueAdminController(VenueService venueService){
        this.venueService=venueService;
    }

    @PostMapping ("/api/admin/venues")
    @ResponseStatus (HttpStatus.CREATED)
    public VenueResponse create (@Valid @RequestBody VenueRequest venueRequest){
        return venueService.create(venueRequest);
    }

    @PutMapping ("/api/admin/venues/{id}")
    public VenueResponse update(@PathVariable Long id,@Valid @RequestBody VenueRequest venueRequest){
        return venueService.update(id, venueRequest);
    }

    @DeleteMapping ("/api/admin/venues/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Long id){
        venueService.delete(id);
    }
}
