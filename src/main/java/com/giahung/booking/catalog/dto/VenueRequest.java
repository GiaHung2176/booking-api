package com.giahung.booking.catalog.dto;

import jakarta.validation.constraints.*;

public record VenueRequest(@NotBlank @Size(max=255)String name,@NotBlank @Size(max=255)String address,@NotBlank @Size(max=100)String city){}
