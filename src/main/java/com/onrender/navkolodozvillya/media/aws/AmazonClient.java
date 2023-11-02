package com.onrender.navkolodozvillya.media.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.onrender.navkolodozvillya.media.BinaryStorageClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

//TODO Note, that service is a prototype!
@Service
public class AmazonClient implements BinaryStorageClient {

    private AmazonS3 s3client;

    @Value("${amazon.properties.bucketName}")
    private String bucketName;
    @Value("${amazon.properties.accessKey}")
    private String accessKey;
    @Value("${amazon.properties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        this.s3client = new AmazonS3Client(credentials);
        BasicAWSCredentials creds = new BasicAWSCredentials(this.accessKey, this.secretKey);
        s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public String upload(InputStream inputStream) {
        String fileKey = generateFileName();
        uploadFileToS3bucket(fileKey, inputStream);
        return fileKey;
    }

    public String download(String fileKey) {
        return null;
    }

    //generate unique name to the uploadfile
    private String generateFileName() {
        return new Date().getTime() + "-" + UUID.randomUUID();
    }

    //upload files to s3
    private void uploadFileToS3bucket(String fileKey, InputStream inputStream) {
        PutObjectResult result = s3client.putObject(new PutObjectRequest(bucketName, fileKey, inputStream,
                new ObjectMetadata()));

    }

    //TODO !! Think about approach
    private void downloadFileFromS3bucket(String fileKey) {
        S3Object s3Object = s3client.getObject(bucketName, fileKey);
        s3Object.getObjectContent();

    }
}