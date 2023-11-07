package com.onrender.navkolodozvillya.media;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/media/metadata")
@RequiredArgsConstructor
public class MediaMetadataController {

    private final MediaMetadataService service;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public MediaMetadataCreateResponse createObjectMetadata(@RequestBody MediaMetadataCreateRequest request) {

        return service.create(request);
    }
}
