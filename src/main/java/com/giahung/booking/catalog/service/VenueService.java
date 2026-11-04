package com.giahung.booking.catalog.service;

import com.giahung.booking.catalog.dto.VenueRequest;
import com.giahung.booking.catalog.dto.VenueResponse;
import com.giahung.booking.catalog.entity.Venue;
import com.giahung.booking.catalog.repository.VenueRepository;
import com.giahung.booking.common.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service 
public class VenueService {

    private final VenueRepository venueRepository;

    private VenueResponse toResponse (Venue venue){
        return new VenueResponse(venue.getId(),venue.getName(),venue.getAddress(),venue.getCity(),venue.getCreatedAt(),venue.getUpdatedAt());
    }

    private Venue getVenueOrThrow(Long id){
        return venueRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Venue not found"));
    }
    
    public VenueService(VenueRepository venueRepository){
        this.venueRepository=venueRepository;
    }

    public Page<VenueResponse> findAll(Pageable pageable){
        return venueRepository.findAll(pageable).map(this::toResponse);
    }

    public VenueResponse findById(Long id){
        Venue venue=getVenueOrThrow(id);
        return toResponse(venue); 
    }

    public VenueResponse create (VenueRequest venueRequest){
        Venue venue =new Venue(venueRequest.name(),venueRequest.address(),venueRequest.city());
        Venue saved=venueRepository.save(venue);
        return toResponse(saved);
    }

    @Transactional 
    public VenueResponse update(Long id,VenueRequest venueRequest){
        Venue venue=getVenueOrThrow(id);
        venue.update(venueRequest.name(),venueRequest.address(), venueRequest.city());
        return toResponse(venue);
    }
}
