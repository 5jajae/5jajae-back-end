package com.ojajae.domain.store.service

import com.ojajae.domain.s3.service.S3Service
import com.ojajae.domain.store.constant.StoreFileType
import com.ojajae.domain.store.entity.StoreFile
import com.ojajae.domain.store.form.response.StoreImageAdminResponse
import com.ojajae.domain.store.repository.StoreFileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StoreFileAdminService(
    private val storeFileRepository: StoreFileRepository,

    private val s3Service: S3Service
) {
    @Transactional
    fun saveAllStoreFiles(files: List<StoreFile>) {
        storeFileRepository.saveAll(files)
    }

    @Transactional
    fun findImagesByStoreIdForUpdate(storeId: Int): List<StoreFile> {
        return storeFileRepository.findAllWithWriteLockByStoreId(storeId)
    }

    @Transactional
    fun deleteByStoreIdAndImageUrls(storeId: Int, imageUrls: List<String>) {
        if (imageUrls.isEmpty()) {
            return
        }

        storeFileRepository.deleteAllByStoreIdAndFileUrlIn(storeId = storeId, fileUrls = imageUrls)
    }

    fun findImagesByStoreId(storeId: Int, fileType: StoreFileType?): List<StoreImageAdminResponse> {
        return storeFileRepository.findImagesByStoreId(storeId, fileType).map {
            StoreImageAdminResponse.of(
                storeFile = it,
                imageUrl = s3Service.getPresignedUrl(it.fileUrl),
            )
        }
    }

    fun findThumbnailImageByStoreId(storeId: Int): StoreImageAdminResponse? {
        val thumbnailImage = storeFileRepository.findImagesByStoreId(storeId, StoreFileType.THUMBNAIL_IMAGE)
        if (thumbnailImage.isNotEmpty()) {
            return StoreImageAdminResponse.of(
                storeFile = thumbnailImage[0],
                imageUrl = s3Service.getS3Url(thumbnailImage[0].fileUrl),
            )
        }
        return null
    }
}
