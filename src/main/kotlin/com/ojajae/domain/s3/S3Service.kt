package com.ojajae.domain.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class S3Service(
    private val amazonS3: AmazonS3
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    private val EXPIRE_SECOND = (1000 * 60 * 2).toLong()

    fun getPresignedUrl(fileName: String): String {
        val generatePresignedUrlRequest: GeneratePresignedUrlRequest? = getGeneratePresignedUrlRequest(bucket, fileName)
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString()
    }

    private fun getGeneratePresignedUrlRequest(bucket: String, fileName: String): GeneratePresignedUrlRequest? {
        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucket, fileName)
            .withMethod(HttpMethod.GET)
            .withExpiration(getPresignedUrlExpiration())
        generatePresignedUrlRequest.addRequestParameter(
            Headers.S3_CANNED_ACL,
            CannedAccessControlList.PublicRead.toString()
        )
        return generatePresignedUrlRequest
    }

    private fun getPresignedUrlExpiration(): Date {
        val expiration = Date()
        var expTimeMillis: Long = expiration.getTime()
        expTimeMillis += EXPIRE_SECOND
        expiration.setTime(expTimeMillis)
        return expiration
    }
}