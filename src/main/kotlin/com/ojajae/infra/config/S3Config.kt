package com.ojajae.infra.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {
    @Value("\${spring.cloud.aws.credentials.accessKey}")
    private lateinit var accessKey: String
    @Value("\${spring.cloud.aws.credentials.secretKey}")
    private lateinit var secretKey: String
    @Value("\${spring.cloud.aws.region}")
    private lateinit var region: String

    @Bean
    fun amazonS3Client(): AmazonS3Client? {
        val awsCredentials = BasicAWSCredentials(
            accessKey,
            secretKey
        )
        return AmazonS3ClientBuilder.standard()
            .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
            .withRegion(region)
            .build() as AmazonS3Client
    }
}