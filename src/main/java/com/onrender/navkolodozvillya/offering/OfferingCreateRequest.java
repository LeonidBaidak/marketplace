package com.onrender.navkolodozvillya.offering;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OfferingCreateRequest(

        @Size(min = 1, max = 100, message = "Must be less or equal to 100 characters")
        @NotNull(message = "Title must be specified!")
        String title,

        @Size(min = 1, max = 1000, message = "Must be less or equal to 1000 characters")
        String description,

        @NotNull(message = "Location must be specified!")
        Integer locationId,

        @Max(10000)
        BigDecimal price,

        @NotNull(message = "Category must be specified!")
        Integer categoryId,

        @Future
        @NotNull(message = "Date and time must be specified!")
        LocalDateTime dateTime,

        List<Integer> mediaMetadataList
) {
}
