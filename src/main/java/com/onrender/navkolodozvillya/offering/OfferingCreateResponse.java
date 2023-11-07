package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.location.Location;
import com.onrender.navkolodozvillya.media.MediaMetadataCreateResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OfferingCreateResponse (
        String title,
        String description,
        Long userId,
        Location location,
        BigDecimal price,
        OfferingCategory category,
        LocalDateTime eventDateTime,
        List<MediaMetadataCreateResponse> mediaMetadataList
) {
}