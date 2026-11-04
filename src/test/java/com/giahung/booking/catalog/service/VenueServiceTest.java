package com.giahung.booking.catalog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import com.giahung.booking.catalog.dto.VenueRequest;
import com.giahung.booking.catalog.dto.VenueResponse;
import com.giahung.booking.catalog.entity.Venue;
import com.giahung.booking.catalog.repository.VenueRepository;
import com.giahung.booking.common.exception.ResourceNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith (MockitoExtension.class)
public class VenueServiceTest {
    
    @Mock 
    private VenueRepository venueRepository;

    @InjectMocks    
    private VenueService venueService;

    @Test 
    public void findById_whenNotFound_throwsNotFound(){
        when(venueRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,()->venueService.findById(1L));
    }

    @Test 
    public void findById_whenFound_returnsVenueResponse(){
        when(venueRepository.findById(1L)).thenReturn(Optional.of(new Venue("Camia","Canada","Cambridge")));

        VenueResponse venueResponse=venueService.findById(1L);
        assertEquals("Camia", venueResponse.name());
        assertEquals("Canada", venueResponse.address());
        assertEquals("Cambridge", venueResponse.city());
    }

    @Test 
    public void create_returnsVenueResponse(){
        when(venueRepository.save(any())).thenReturn(new Venue("Camia", "Canada","Cambridge"));

        VenueResponse venueResponse =venueService.create(new VenueRequest("Camia", "Canada","Cambridge"));
        assertEquals("Camia",venueResponse.name());
        assertEquals("Canada", venueResponse.address());
        assertEquals("Cambridge", venueResponse.city());

        verify(venueRepository).save(any());

    }
}
