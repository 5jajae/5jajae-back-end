package com.ojajae

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.net.URL
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
class S3Test @Autowired constructor(
    private val amazonS3: AmazonS3
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    @Test
    fun test() {
        val fileName = "file/1.png"
        val generatePresignedUrlRequest: GeneratePresignedUrlRequest? = getGeneratePresignedUrlRequest(bucket, fileName)
        val url: URL = amazonS3.generatePresignedUrl(generatePresignedUrlRequest)
        println(url.toString())
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

    private fun getPresignedUrlExpiration(): Date? {
        val expiration = Date()
        var expTimeMillis: Long = expiration.getTime()
        expTimeMillis += (1000 * 60 * 2).toLong()
        expiration.setTime(expTimeMillis)
        return expiration
    }
}