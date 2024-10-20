package com.ojajae.domain.s3.service

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.Headers
import com.amazonaws.services.s3.model.*
import com.ojajae.common.S3_FILE_URL_PREFIX
import net.coobird.thumbnailator.Thumbnails
import org.apache.commons.io.FilenameUtils
import org.apache.commons.io.IOUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.Transparency
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO

@Service
class S3Service(
    private val amazonS3: AmazonS3,
) {
    @Value("\${spring.cloud.aws.s3.bucket}")
    private lateinit var bucket: String

    val tmpDirPath = "/tmp/gsd"

    private val EXPIRE_SECOND = (1000 * 60 * 30).toLong() // 30분

    fun getPresignedUrl(fileName: String): String {
        val generatePresignedUrlRequest: GeneratePresignedUrlRequest? = getGeneratePresignedUrlRequest(bucket, fileName)
        return amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString()
    }

    fun getS3Url(filePath: String): String {
        return S3_FILE_URL_PREFIX + filePath;
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

    fun uploadThumbnailFile(path: String, file: MultipartFile): Boolean {
        val tempFile = createTempFile(FilenameUtils.getName(path))
        val thumbnail = ImageIO.read(file.inputStream)

        try {
            Thumbnails.of(thumbnail)
                .size(1200, 630)
                .imageType(if(thumbnail.transparency == Transparency.OPAQUE) BufferedImage.TYPE_INT_RGB else BufferedImage.TYPE_INT_ARGB)
                .toFile(tempFile)

            val request = PutObjectRequest(bucket, path, tempFile)

            amazonS3.putObject(request)
        } catch(e: AmazonS3Exception) {
            throw e
        } finally {
            if (tempFile.exists()) {
                tempFile.delete()
            }
        }

        return true
    }

    fun deleteFile(path: String) {
        val request = DeleteObjectRequest(bucket, path)

        amazonS3.deleteObject(request)
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
        var expTimeMillis: Long = expiration.time
        expTimeMillis += EXPIRE_SECOND
        expiration.time = expTimeMillis
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