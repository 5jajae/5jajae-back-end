package com.ojajae.domain.store.service

import com.ojajae.common.DEFAULT_IMAGE_PATH
import com.ojajae.common.exception.NotFoundException
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.form.request.DashboardSelectForm
import com.ojajae.domain.dashbord.repository.DashboardRepository
import com.ojajae.domain.item_tag.service.ItemTagStoreService
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.exception.StoreException
import com.ojajae.domain.store.form.request.StoreListRequestForm
import com.ojajae.domain.store.form.request.StoreDetailRequestForm
import com.ojajae.domain.store.form.request.StoreFavoritesRequestForm
import com.ojajae.domain.store.form.response.StoreDetailResponseForm
import com.ojajae.domain.store.form.response.StoreFavoritesResponseForm
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
        val stores = storeRepository.getStoreList(request = request.toStoreSearch())
        val storeIds = stores.mapNotNull { it.store.id }
        val storeFiles = storeFileService.findThumbnailImageByStoreIdIn(storeIds = storeIds).associateBy { it.storeId }
        val tags = itemTagStoreService.findByStoreIdIn(storeIds = storeIds).groupBy { it.storeId }

        return StoreListResponseForm(
            stores = stores.map {
                StoreListResponse.of(
                    store = it.store,
                    thumbnailImage = storeFiles[it.store.id!!]?.imageUrl ?: DEFAULT_IMAGE_PATH,
                    itemTags = tags[it.store.id!!] ?: emptyList(),
                    distance = it.distance,
                )
            }
        )
    }

    @Transactional(readOnly = true)
    fun getStore(storeId: Int): StoreDetailResponseForm {
        val store: Store = storeRepository.getStore(StoreDetailRequestForm(storeId = storeId))
            ?: throw NotFoundException(StoreException.NotFoundStore)
        val storeFiles = storeFileService.findImagesByStoreId(store.id!!)
        val thumbnailImage = storeFileService.findThumbnailImageByStoreId(store.id!!)
        val tags = itemTagStoreService.findByStoreId(store.id!!)
        val storeReadCount = dashboardRepository.count(
            DashboardSelectForm(
                dashboardType = DashboardType.STORE_COUNT,
                storeId = storeId,
            )
        )

        return StoreDetailResponseForm.of(
            store = store,
            storeReadCount = storeReadCount,
            itemTags = tags,
            thumbnailUrl = thumbnailImage?.imageUrl,
            imageUrls = storeFiles.map { it.imageUrl },
        )
    }


    @Transactional(readOnly = true)
    fun getFavorites(storeFavoritesRequestForm: StoreFavoritesRequestForm): StoreFavoritesResponseForm {
        val stores = storeRepository.getStoreList(request = storeFavoritesRequestForm.toStoreSearch())
        val storeIds = stores.mapNotNull { it.store.id }
        val storeFiles = storeFileService.findThumbnailImageByStoreIdIn(storeIds = storeIds).associateBy { it.storeId }
        val tags = itemTagStoreService.findByStoreIdIn(storeIds = storeIds).groupBy { it.storeId }

        return StoreFavoritesResponseForm(
            stores = stores.map {
                StoreListResponse.of(
                    store = it.store,
                    thumbnailImage = storeFiles[it.store.id!!]?.imageUrl ?: DEFAULT_IMAGE_PATH,
                    itemTags = tags[it.store.id!!] ?: emptyList(),
                    distance = it.distance,
                )
            }
        )
    }
}