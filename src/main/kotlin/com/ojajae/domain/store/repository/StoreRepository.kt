package com.ojajae.domain.store.repository

import com.ojajae.domain.item_tag.entity.QItemTagStore.itemTagStore
import com.ojajae.domain.store.entity.QStore.store
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.form.request.StoreListRequestForm
import com.ojajae.domain.store.form.request.StoreRequestForm
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface StoreRepository: JpaRepository<Store, Int>, StoreCustomRepository

interface StoreCustomRepository {
    fun getStoreList(request: StoreListRequestForm): List<Store>
    fun getStore(form: StoreRequestForm): Store?
}

class StoreRepositoryImpl: QuerydslRepositorySupport(Store::class.java), StoreCustomRepository {
    override fun getStoreList(request: StoreListRequestForm): List<Store> {
        return from(store)
            .leftJoin(itemTagStore).on(itemTagStore.storeId.eq(store.id))
            .where(
                addressLike(request.address),
                itemTagIdIn(request.itemTagIds),
            )
            .groupBy(store)
            .select(store)
            .orderBy(store.id.desc())
            .fetch()
    }

    override fun getStore(form: StoreRequestForm): Store? {
        return from(store)
            .where(
                eqStoreId(form.storeId)
            )
            .fetchOne()
    }

    private fun addressLike(address: String?): BooleanExpression? {
        return address?.let {
            store.address.like("%$it%")
        }
    }
    private fun itemTagIdIn(itemTagIds: List<Int>?): BooleanExpression? {
        if (itemTagIds.isNullOrEmpty()) {
            return null
        }

        return itemTagStore.itemTagId.`in`(itemTagIds)
    }

    private fun eqStoreId(storeId: Int?): BooleanExpression? {
        return storeId?.let {
            store.id.eq(it)
        }
    }
}