package com.ojajae.domain.store.repository

import com.ojajae.domain.store.entity.QStoreFile.storeFile
import com.ojajae.domain.store.entity.StoreFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface StoreFileRepository : JpaRepository<StoreFile, Int>, StoreFileCustomRepository

interface StoreFileCustomRepository {
    fun findImagesByStoreId(storeId: Int): List<StoreFile>
    fun findFirstImageByStoreIdIn(storeIds: List<Int>): List<StoreFile>
}

class StoreFileCustomImpl : StoreFileCustomRepository, QuerydslRepositorySupport(StoreFile::class.java) {
    override fun findImagesByStoreId(storeId: Int): List<StoreFile> {
        return from(storeFile).where(
            storeFile.storeId.eq(storeId),
        ).fetch()
    }

    override fun findFirstImageByStoreIdIn(storeIds: List<Int>): List<StoreFile> {
        if (storeIds.isEmpty()) {
            return emptyList()
        }

        return from(storeFile).where(
            storeFile.storeId.`in`(storeIds),
            storeFile.order.eq(0),
        ).fetch()
    }
}
