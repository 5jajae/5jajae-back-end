package com.ojajae.domain.store.service

import com.ojajae.domain.s3.service.S3Service
import com.ojajae.domain.store.constant.StoreFileType
import com.ojajae.domain.store.form.response.StoreFileResponse
import com.ojajae.domain.store.repository.StoreFileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreFileService(
    private val storeFileRepository: StoreFileRepository,
    private val s3Service: S3Service
) {
    @Transactional(readOnly = true)
    fun findImagesByStoreId(storeId: Int): List<StoreFileResponse> {
        return storeFileRepository.findImagesByStoreId(storeId, StoreFileType.MAIN_IMAGE).map {
            StoreFileResponse.of(
                storeFile = it,
                imageUrl = s3Service.getPresignedUrl(it.fileUrl),
            )
        }
    }

    @Transactional(readOnly = true)
    fun findThumbnailImageByStoreId(storeId: Int): StoreFileResponse? {
        val list = storeFileRepository.findThumbnailImageByStoreIdIn(listOf(storeId))
        if (list.isNotEmpty()) {
            return StoreFileResponse.of(
                storeFile = list[0],
                imageUrl = s3Service.getS3Url(list[0].fileUrl),
            )
        }
        return null;
    }

    @Transactional(readOnly = true)
    fun findThumbnailImageByStoreIdIn(storeIds: List<Int>): List<StoreFileResponse> {
        return storeFileRepository.findThumbnailImageByStoreIdIn(storeIds).map {
            StoreFileResponse.of(
                storeFile = it,
                imageUrl = s3Service.getS3Url(it.fileUrl),
            )
        }
    }
}
