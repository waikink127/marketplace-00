package com.myexample.demofullstack.s3;


public enum S3BucketName {
    PROFILE_IMAGE("carousel-app-123");

    private String bucketName;

    S3BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
