package com.ojajae.domain.store.service

import com.ojajae.domain.s3.S3Service
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
    fun findFirstImageByStoreIdIn(storeIds: List<Int>): List<StoreFileResponse> {
        return storeFileRepository.findFirstImageByStoreIdIn(storeIds).map {
            StoreFileResponse.of(
                storeFile = it,
                imageUrl = s3Service.getPresignedUrl(it.fileUrl),
            )
        }
    }
}
