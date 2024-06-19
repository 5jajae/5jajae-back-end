package com.ojajae.domain.store.repository

import com.ojajae.domain.store.entity.QStore.store
import com.ojajae.domain.store.entity.Store
import com.ojajae.domain.store.form.request.StoreRequestForm
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

interface StoreRepository: JpaRepository<Store, Int>, StoreCustomRepository {

}

interface StoreCustomRepository {
    fun getStore(form: StoreRequestForm): Store?
}

class StoreRepositoryImpl: QuerydslRepositorySupport(Store::class.java), StoreCustomRepository {
    override fun getStore(form: StoreRequestForm): Store? {
        return from(store)
            .where(
                eqStoreId(form.storeId)
            )
            .fetchOne()
    }

    private fun eqStoreId(storeId: Int?): BooleanExpression? {
        return storeId?.let {
            store.id.eq(it)
        }
    }
}