package com.ojajae.domain.item_tag.service

import com.ojajae.common.exception.BadRequestException
import com.ojajae.common.utils.generateRandomString
import com.ojajae.domain.item_tag.exception.ItemTagException
import com.ojajae.domain.item_tag.form.request.ItemTagSaveRequestForm
import com.ojajae.domain.item_tag.form.response.ItemTagAdminListResponseForm
import com.ojajae.domain.item_tag.repository.ItemTagRepository
import com.ojajae.domain.s3.S3Service
import org.apache.commons.io.FilenameUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class ItemTagAdminService(
    private val itemTagRepository: ItemTagRepository,

    private val s3Service: S3Service,
) {
    @Transactional
    fun saveItemTag(requestForm: ItemTagSaveRequestForm) {
        val file = requestForm.image ?: throw BadRequestException(ItemTagException.ItemTagFileIsEmpty)
        val extension = file.originalFilename?.let {
            FilenameUtils.getExtension(it)
        } ?: "jpg"
        val fileUrl = generateFileUrl(extension)

        s3Service.uploadFile(path = fileUrl, file = file)

        itemTagRepository.save(requestForm.toEntity(imageUrl = fileUrl))
    }

    fun getItemTagList(): List<ItemTagAdminListResponseForm> {
        return itemTagRepository.findAll().map {
            ItemTagAdminListResponseForm.of(itemTag = it).apply {
                this.imageUrl = s3Service.getPresignedUrl(it.imageUrl)
            }
        }
    }

    @Transactional
    fun updateItemTag(
        itemTagId: Int,
        requestForm: ItemTagSaveRequestForm,
    ) {
        val prev = itemTagRepository.findOneWithWriteOrNullLockById(id = itemTagId) ?: return
        var fileUrl = prev.imageUrl

        requestForm.image?.also { file ->
            val extension = file.originalFilename?.let {
                FilenameUtils.getExtension(it)
            } ?: "jpg"
            fileUrl = generateFileUrl(extension)

            s3Service.uploadFile(path = fileUrl, file = file)

            itemTagRepository.save(requestForm.toEntity(imageUrl = fileUrl))
        }

        prev.update(param = requestForm.toEntity(imageUrl = fileUrl))
    }

    private fun generateFileUrl(extension: String): String {
        val now = LocalDate.now()

        return "ojajae/upload/item_tags/${now.year}/${now.monthValue}/${generateRandomString()}.${extension}"
    }
}
