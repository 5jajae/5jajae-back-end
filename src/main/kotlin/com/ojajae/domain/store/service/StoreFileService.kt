package com.ojajae.domain.store.service

import com.ojajae.domain.store.form.response.StoreFileResponse
import com.ojajae.domain.store.repository.StoreFileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreFileService(
    private val storeFileRepository: StoreFileRepository,
) {
    @Transactional(readOnly = true)
    fun findFirstImageByStoreIdIn(storeIds: List<Int>): List<StoreFileResponse> {
        return storeFileRepository.findFirstImageByStoreIdIn(storeIds).map {
            StoreFileResponse.of(it)
        }
    }
}
