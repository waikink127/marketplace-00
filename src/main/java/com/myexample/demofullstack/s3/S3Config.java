package com.myexample.demofullstack.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class S3Config {

    @Autowired
    private Environment env;

    @Bean
    public AmazonS3 s3(){
        AWSCredentials credentials = new BasicAWSCredentials(env.getProperty("AWSAccessKeyId"), env.getProperty("AWSSecretKey"));
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(env.getProperty("S3Region"))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
