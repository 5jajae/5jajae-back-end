package com.ojajae.domain.store.service

import com.ojajae.common.DEFAULT_IMAGE_PATH
import com.ojajae.common.exception.NotFoundException
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.ojajae.domain.dashbord.repository.DashboardRepository
import com.ojajae.domain.item_tag.service.ItemTagStoreService
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.exception.StoreException
import com.ojajae.domain.store.form.request.StoreListRequestForm
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
    private val dashboardRepository: DashboardRepository,

    private val storeRepository: StoreRepository,
) {
    @Transactional(readOnly = true)
    fun getStoreList(
        request: StoreListRequestForm,
    ): StoreListResponseForm {
        val stores = storeRepository.getStoreList(request = request)
        val storeIds = stores.mapNotNull { it.id }
        val storeFiles = storeFileService.findFirstImageByStoreIdIn(storeIds = storeIds).associateBy { it.storeId }
        val tags = itemTagStoreService.findByStoreIdIn(storeIds = storeIds).groupBy { it.storeId }

        return StoreListResponseForm(
            stores = stores.map {
                StoreListResponse.of(
                    store = it,
                    thumbnailImage = storeFiles[it.id!!]?.imageUrl ?: DEFAULT_IMAGE_PATH,
                    itemTags = tags[it.id!!] ?: emptyList(),
                )
            }
        )
    }

    @Transactional(readOnly = true)
    fun getStore(storeId: Int): StoreDetailResponseForm {
        val store: Store = storeRepository.getStore(StoreRequestForm(storeId = storeId))
            ?: throw NotFoundException(StoreException.NotFoundStore)
        val storeFiles = storeFileService.findImagesByStoreId(store.id!!)
        val tags = itemTagStoreService.findByStoreId(store.id!!)
        val storeReadCount = dashboardRepository.count(DashboardRequestForm(
            dashboardType = DashboardType.STORE_COUNT,
            storeId = storeId,
        ))

        return StoreDetailResponseForm.of(
            store = store,
            storeReadCount = storeReadCount,
            itemTags = tags,
            imageUrls = storeFiles.map { it.imageUrl },
        )
    }
}