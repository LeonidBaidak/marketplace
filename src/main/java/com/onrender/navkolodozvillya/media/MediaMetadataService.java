package com.onrender.navkolodozvillya.media;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaMetadataService {

    private final MediaMetadataRepository repository;

    public MediaMetadataCreateResponse create(MediaMetadataCreateRequest request) {
        MediaMetadata toSave = MediaMetadata.builder()
                .objectKey(request.getObjectKey())
                .ordinal(request.getOrdinal())
                .build();

        MediaMetadata saved = repository.save(toSave);
        return MediaMetadataCreateResponse.builder()
                .id(saved.getId())
                .objectKey(saved.getObjectKey())
                .ordinal(saved.getOrdinal())
                .build();
    }
}
