package com.ojajae.domain.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.*
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.util.*

@Service
class S3Service(
    private val amazonS3: AmazonS3
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    val tmpDirPath = "/tmp/gsd"

    private val EXPIRE_SECOND = (1000 * 60 * 2).toLong()

    fun getPresignedUrl(fileName: String): String {
        val generatePresignedUrlRequest: GeneratePresignedUrlRequest? = getGeneratePresignedUrlRequest(bucket, fileName)
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString()
    }

    fun uploadFile(path: String, file: MultipartFile): Boolean {
        val tempFile = createTempFile(FilenameUtils.getName(path))

        try {
            file.inputStream.use { `is` ->
                tempFile.outputStream().buffered().use { os ->
                    IOUtils.copy(`is`, os)
                }
            }

            val request = PutObjectRequest(bucket, path, tempFile)

            amazonS3.putObject(request)

            return true
        } catch (e: IOException) {
            throw e
        } catch (e: AmazonS3Exception) {
            throw e
        } finally {
            if (tempFile.exists()) {
                tempFile.delete()
            }
        }
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

    private fun createTempFile(fileName: String?): File {
        val tempDir = createTempDirectory()
        val name = System.currentTimeMillis().toString() + "_" + (fileName ?: (Math.random() * 10000).toInt())
        val tempFile = File(tempDir + name)
        if (tempFile.exists()) tempFile.delete()
        return tempFile
    }

    private fun createTempDirectory(): String {
        File(tmpDirPath).mkdirs()
        return tmpDirPath
    }
}