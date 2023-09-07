package com.biuea.sportsplatform.core.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient

@Configuration
class AwsConfiguration {
    @Bean
    fun secretsClient(): SecretsManagerClient {
        return SecretsManagerClient.builder()
            .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
            .region(Region.AP_NORTHEAST_2)
            .build()
    }

    @Bean
    fun s3Client(): S3Client {
        return S3Client.create()
    }

    @Bean
    fun s3Presigner(): S3Presigner {
        return S3Presigner.builder()
            .credentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
            .region(Region.AP_NORTHEAST_2)
            .build()
    }
}