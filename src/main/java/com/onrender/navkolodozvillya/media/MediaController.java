package com.onrender.navkolodozvillya.media;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
@RequestMapping("/media")
@RequiredArgsConstructor
public class MediaController {

    private final BinaryStorageClient binaryStorageClient;

    @PostMapping("/upload")
    public String uploadObject(InputStream dataStream) {
        return binaryStorageClient.upload(dataStream);
    }

    @GetMapping("/download")
    public String downloadObject(String objectKey) {
        return binaryStorageClient.download(objectKey);
    }
}
