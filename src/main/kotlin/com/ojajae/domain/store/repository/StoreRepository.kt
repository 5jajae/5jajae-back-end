package com.ojajae.domain.store.repository

import com.ojajae.common.repository.LockableRepository
import com.ojajae.common.utils.fetchPage
import com.ojajae.domain.item_tag.entity.QItemTagStore.itemTagStore
import com.ojajae.domain.store.constant.StoreListSortType
import com.ojajae.domain.store.form.request.StorePageRequestForm
import com.ojajae.domain.store.entity.QStore.store
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.form.request.StoreDetailRequestForm
import com.ojajae.domain.store.repository.search.StoreSearch
import com.ojajae.domain.store.vo.StoreAppListVO
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberTemplate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface StoreRepository : JpaRepository<Store, Int>, StoreCustomRepository, LockableRepository<Store, Int>

interface StoreCustomRepository {
    fun getStoreList(request: StoreSearch): List<StoreAppListVO>

    fun getStore(form: StoreDetailRequestForm): Store?

    fun getStorePage(requestForm: StorePageRequestForm, pageable: Pageable): Page<Store>
}

class StoreRepositoryImpl : QuerydslRepositorySupport(Store::class.java), StoreCustomRepository {
    override fun getStoreList(request: StoreSearch): List<StoreAppListVO> {
        val distance = Expressions.numberTemplate(
            Double::class.java,
            "ST_Distance_Sphere(point({0}, {1}), point({2}, {3}))",
            store.lng, store.lat, request.lng, request.lat
        )
        val query = from(store)
            .leftJoin(itemTagStore).on(itemTagStore.storeId.eq(store.id))
            .where(
                addressLike(request.address),
                itemTagIdIn(request.itemTagIds),
                distanceLimit(request.distanceLimit, distance),
            )
            .groupBy(store)
            .select(
                Projections.constructor(
                    StoreAppListVO::class.java,
                    store.`as`("store"),
                    distance.`as`("distance"),
                )
            )

        when(request.sort) {
            StoreListSortType.LATEST-> query.orderBy(store.id.desc())
            StoreListSortType.DISTANCE-> query.orderBy(distance.asc())
        }

        return query.fetch()
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

    private fun distanceLimit(distanceLimit: Double?,  distance: NumberTemplate<Double>): BooleanExpression? {
        return distanceLimit?.let {
            distance.loe(Expressions.constant(it))
        }
    }
}