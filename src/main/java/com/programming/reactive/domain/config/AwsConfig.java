package com.programming.reactive.domain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import java.net.URI;

@Configuration
public class AwsConfig {
    @Value("${AWS_ACCESS_URL}")
    private String localstackUrl;// = "http://localhost:4566";
    @Value("${AWS_ACCESS_KEY_ID}")
    private String awsAccessKeyId;// = "dummyAccessKey";
    @Value("${AWS_SECRET_ACCESS_KEY}")
    private String awsSecretAccessKey;// = "dummySecretKey";

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey)
                        )
                )
                .endpointOverride(URI.create(localstackUrl))
                .region(Region.US_EAST_1)
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey)
                        )
                )
                .endpointOverride(URI.create(localstackUrl))
                .region(Region.US_EAST_1)
                .build();
    }
}
