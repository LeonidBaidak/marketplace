package com.onrender.navkolodozvillya.media;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media/metadata")
@RequiredArgsConstructor
public class MediaMetadataController {

    private final MediaMetadataService service;

    @PostMapping("/create")
    public MediaMetadataCreateResponse createObjectMetadata(@RequestBody MediaMetadataCreateRequest request) {

        return service.create(request);
    }
}
