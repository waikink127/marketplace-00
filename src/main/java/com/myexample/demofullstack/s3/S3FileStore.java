package com.myexample.demofullstack.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class S3FileStore {
    @Autowired
    private AmazonS3 s3;

    public void save(String path, String fileName, Optional<Map<String, String>> metadata, InputStream inputStream) {

        ObjectMetadata objectMetadata = new ObjectMetadata();
        metadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                objectMetadata.setContentType(map.get("Content-Type"));
                map.forEach(objectMetadata::addUserMetadata);
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Fail to store content", e);
        }
    }

}
