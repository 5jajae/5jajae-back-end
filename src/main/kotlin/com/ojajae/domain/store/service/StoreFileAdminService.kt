package com.ojajae.domain.store.service

import com.ojajae.domain.s3.S3Service
import com.ojajae.domain.store.entity.StoreFile
import com.ojajae.domain.store.form.response.StoreFileAdminResponse
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

    fun findImagesByStoreId(storeId: Int): List<StoreFileAdminResponse> {
        return storeFileRepository.findImagesByStoreId(storeId).map {
            StoreFileAdminResponse.of(
                storeFile = it,
                imageUrl = s3Service.getPresignedUrl(it.fileUrl),
            )
        }
    }
}
