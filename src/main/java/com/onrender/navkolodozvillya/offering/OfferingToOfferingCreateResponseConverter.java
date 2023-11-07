package com.onrender.navkolodozvillya.offering;

import com.onrender.navkolodozvillya.media.MediaMetadataCreateResponse;
import com.onrender.navkolodozvillya.user.UserResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OfferingToOfferingCreateResponseConverter implements Converter<Offering, OfferingCreateResponse> {

    @Override
    public OfferingCreateResponse convert(Offering source) {
        OfferingCreateResponse response = OfferingCreateResponse.builder()
                .title(source.getName())
                .description(source.getDescription())
                .userId(source.getUser().getId())
                .location(source.getLocation())
                .price(source.getPrice())
                .category(source.getCategory())
                .eventDateTime(source.getEventDateTime())
                .mediaMetadataList(source.getLinkedMediaMetadata().stream().map(mediaMetadata -> MediaMetadataCreateResponse.builder()
                                .id(mediaMetadata.getId())
                                .objectKey(mediaMetadata.getObjectKey())
                                .ordinal(mediaMetadata.getOrdinal())
                                .build())
                        .toList())
                .build();
        return response;
    }
}