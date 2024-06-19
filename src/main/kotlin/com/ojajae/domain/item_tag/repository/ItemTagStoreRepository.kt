package com.ojajae.domain.item_tag.repository

import com.ojajae.domain.item_tag.entity.ItemTagStore
import com.ojajae.domain.item_tag.entity.QItemTag.itemTag
import com.ojajae.domain.item_tag.entity.QItemTagStore.itemTagStore
import com.ojajae.domain.item_tag.vo.ItemTagStoreVO
import com.querydsl.core.types.Projections
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface ItemTagStoreRepository : JpaRepository<ItemTagStore, Int>, ItemTagStoreCustomRepository

interface ItemTagStoreCustomRepository {
    fun findByStoreIdIn(storeIds: List<Int>): List<ItemTagStoreVO>
}

class ItemTagStoreCustomRepositoryImpl : ItemTagStoreCustomRepository, QuerydslRepositorySupport(ItemTagStore::class.java) {
    override fun findByStoreIdIn(storeIds: List<Int>): List<ItemTagStoreVO> {
        if (storeIds.isEmpty()) {
            return emptyList()
        }

        return from(itemTagStore)
            .leftJoin(itemTag).on(itemTag.id.eq(itemTagStore.itemTagId))
            .where(itemTagStore.storeId.`in`(storeIds))
            .select(
                Projections
                    .bean(ItemTagStoreVO::class.java,
                        itemTag.id,
                        itemTagStore.storeId,
                        itemTag.name,
                        itemTag.imageUrl)
            )
            .fetch()
    }
}
