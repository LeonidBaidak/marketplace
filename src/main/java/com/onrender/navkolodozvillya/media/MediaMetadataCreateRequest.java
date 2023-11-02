package com.onrender.navkolodozvillya.media;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class MediaMetadataCreateRequest {

    @Size(min = 1, max = 255, message = "Must be less than 255 characters")
    @NotNull(message = "objectKey must be specified!")
    private String objectKey;
    private Integer ordinal;
}
