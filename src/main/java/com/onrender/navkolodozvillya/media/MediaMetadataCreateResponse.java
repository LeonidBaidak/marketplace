package com.onrender.navkolodozvillya.media;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MediaMetadataCreateResponse {
    private final Integer id;
    private final String objectKey;
    private final Integer ordinal;
}
