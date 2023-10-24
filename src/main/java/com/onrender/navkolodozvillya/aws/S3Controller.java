package com.onrender.navkolodozvillya.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final AmazonClient amazonClient;

    @RequestMapping("/")
    ModelAndView index (ModelAndView modelAndView) {

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @PostMapping("/upload")
    public String acceptData(InputStream dataStream) throws Exception {
//        File fileToUpload = new File("image");
//        copyInputStreamToFile(dataStream, fileToUpload);
////        amazonClient.upload(fileToUpload);
        return amazonClient.upload(dataStream);
    }

    private void copyInputStreamToFile(InputStream input, File file) {

        try (OutputStream output = new FileOutputStream(file)) {
            input.transferTo(output);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
