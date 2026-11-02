package com.giahung.booking.catalog.dto;

import jakarta.validation.constraints.*;

public record CategoryRequest(@NotBlank @Size(max=100) String name){}
