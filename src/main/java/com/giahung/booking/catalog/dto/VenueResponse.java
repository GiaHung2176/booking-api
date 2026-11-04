package com.giahung.booking.catalog.dto;

import java.time.Instant;

public record VenueResponse(Long id,String name,String address,String city,Instant createdAt,Instant updatedAt){}
