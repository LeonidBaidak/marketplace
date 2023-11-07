package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.location.Location;
import com.onrender.navkolodozvillya.media.MediaMetadata;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OfferingCreateRequestToOfferingConverter implements Converter<OfferingCreateRequest, Offering> {

    @Override
    public Offering convert(OfferingCreateRequest source) {
        return Offering.builder()
                .name(source.title())
                .description(source.description())
                .location(Location.builder()
                        .id(source.locationId())
                        .build())
                .price(source.price())
                .category(OfferingCategory.values()[source.categoryId()])
                .eventDateTime(source.dateTime())
                .linkedMediaMetadata(source.mediaMetadataList().stream()
                        .map(metadataId -> MediaMetadata.builder()
                                .id(metadataId)
                                .build())
                        .toList())
                .build();
    }
}
