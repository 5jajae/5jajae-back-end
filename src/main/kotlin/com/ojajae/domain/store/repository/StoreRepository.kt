package com.ojajae.domain.store.repository

import com.ojajae.common.repository.LockableRepository
import com.ojajae.common.utils.fetchPage
import com.ojajae.domain.item_tag.entity.QItemTagStore.itemTagStore
import com.ojajae.domain.item_tag.form.request.StorePageRequestForm
import com.ojajae.domain.store.entity.QStore.store
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.form.request.StoreListRequestForm
import com.ojajae.domain.store.form.request.StoreDetailRequestForm
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface StoreRepository: JpaRepository<Store, Int>, StoreCustomRepository, LockableRepository<Store, Int>

interface StoreCustomRepository {
    fun getStoreList(request: StoreListRequestForm): List<Store>

    fun getStore(form: StoreDetailRequestForm): Store?

    fun getStorePage(requestForm: StorePageRequestForm, pageable: Pageable): Page<Store>
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
            .fetch()
    }

    override fun getStore(form: StoreDetailRequestForm): Store? {
        return from(store)
            .where(
                eqStoreId(form.storeId)
            )
            .fetchOne()
    }

    override fun getStorePage(requestForm: StorePageRequestForm, pageable: Pageable): Page<Store> {
        return from(store)
            .where(
                nameLike(requestForm.name),
            )
            .orderBy(store.id.desc())
            .fetchPage(pageable = pageable)
    }

    private fun nameLike(name: String?): BooleanExpression? {
        return name?.let {
            store.name.like("%$it%")
        }
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