package com.ojajae.domain.s3.service

import com.ojajae.common.exception.BadRequestException
import com.ojajae.common.utils.generateRandomString
import com.ojajae.domain.s3.exception.UploadException
import com.ojajae.domain.s3.form.FileUploadRequestForm
import com.ojajae.domain.s3.form.FileUploadResponseForm
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class S3UploadService(
    private val s3Service: S3Service,
) {
    fun uploadFile(
        prefix: String,
        requestForm: FileUploadRequestForm,
    ): FileUploadResponseForm {
        if (requestForm.file == null) {
            throw BadRequestException(UploadException.FileIsEmpty)
        }

        val extension = requestForm.file.originalFilename?.let {
            FilenameUtils.getExtension(it)
        } ?: "jpg"
        val fileUrl = generateFileUrl(prefix, extension)

        if (s3Service.uploadFile(path = fileUrl, file = requestForm.file)) {
            return FileUploadResponseForm(
                fileUrl = s3Service.getPresignedUrl(fileUrl),
                fileKey = fileUrl,
            )
        }

        throw BadRequestException()
    }

    private fun generateFileUrl(prefix: String, extension: String): String {
        val now = LocalDate.now()
        val monthValue = if (now.monthValue > 10) now.monthValue else "0${now.monthValue}"

        return "ojajae/upload/$prefix/${now.year}/$monthValue/${generateRandomString()}.${extension}"
    }
}
