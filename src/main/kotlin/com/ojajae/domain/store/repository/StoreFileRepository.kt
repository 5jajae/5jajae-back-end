package com.ojajae.domain.store.repository

import com.ojajae.domain.store.entity.QStoreFile.storeFile
import com.ojajae.domain.store.entity.StoreFile
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface StoreFileRepository : JpaRepository<StoreFile, Int>, StoreFileCustomRepository {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllWithWriteLockByStoreId(storeId: Int): List<StoreFile>

    @Modifying
    @Query("DELETE FROM StoreFile WHERE storeId = :storeId AND fileUrl IN (:fileUrls)")
    fun deleteAllByStoreIdAndFileUrlIn(storeId: Int, fileUrls: List<String>)
}

interface StoreFileCustomRepository {
    fun findImagesByStoreId(storeId: Int): List<StoreFile>
    fun findThumbnailImageByStoreIdIn(storeIds: List<Int>): List<StoreFile>
}

class StoreFileCustomImpl : StoreFileCustomRepository, QuerydslRepositorySupport(StoreFile::class.java) {
    override fun findImagesByStoreId(storeId: Int): List<StoreFile> {
        return from(storeFile).where(
            storeFile.storeId.eq(storeId),
        ).fetch()
    }

    override fun findThumbnailImageByStoreIdIn(storeIds: List<Int>): List<StoreFile> {
        if (storeIds.isEmpty()) {
            return emptyList()
        }

        return from(storeFile).where(
            storeFile.storeId.`in`(storeIds),
            storeFile.sort.eq(0),
        ).fetch()
    }
}
