package com.ojajae.domain.store.service

import com.ojajae.common.DEFAULT_IMAGE_PATH
import com.ojajae.common.exception.CustomException
import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.item_tag.service.ItemTagStoreService
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.form.request.StoreRequestForm
import com.ojajae.domain.store.form.response.StoreDetailResponseForm
import com.ojajae.domain.store.form.response.StoreListResponse
import com.ojajae.domain.store.form.response.StoreListResponseForm
import com.ojajae.domain.store.repository.StoreRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreService(
    private val storeFileService: StoreFileService,
    private val itemTagStoreService: ItemTagStoreService,

    private val storeRepository: StoreRepository,
) {
    @Transactional(readOnly = true)
    fun getStoreList(): StoreListResponseForm {
        val stores = storeRepository.findAll()
        val storeIds = stores.mapNotNull { it.id }
        val storeFiles = storeFileService.findFirstImageByStoreIdIn(storeIds = storeIds).associateBy { it.storeId }
        val tags = itemTagStoreService.findByStoreIdIn(storeIds = storeIds).groupBy { it.storeId }

        return StoreListResponseForm(
            storeList = stores.map {
                StoreListResponse.of(
                    store = it,
                    thumbnailImage = storeFiles[it.id!!]?.imageUrl ?: DEFAULT_IMAGE_PATH,
                    itemTags = tags[it.id!!] ?: emptyList(),
                )
            }
        )
    }

    @Transactional(readOnly = true)
    fun getStore(storeId: Int): ResultDTO<StoreDetailResponseForm> {
        val store: Store = storeRepository.getStore(StoreRequestForm(storeId = storeId))
            ?: throw CustomException("업체를 찾을 수 없습니다.")

        return ResultDTO.createSuccess("", StoreDetailResponseForm.of(store))
    }
}