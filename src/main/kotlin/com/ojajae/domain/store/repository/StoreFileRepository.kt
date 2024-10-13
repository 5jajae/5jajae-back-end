package com.ojajae.domain.store.repository

import com.ojajae.domain.store.constant.StoreFileType
import com.ojajae.domain.store.entity.QStoreFile.storeFile
import com.ojajae.domain.store.entity.StoreFile
import com.querydsl.core.types.dsl.BooleanExpression
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
    fun findImagesByStoreId(storeId: Int, fileType: StoreFileType?): List<StoreFile>
    fun findThumbnailImageByStoreIdIn(storeIds: List<Int>): List<StoreFile>
}

class StoreFileRepositoryImpl : StoreFileCustomRepository, QuerydslRepositorySupport(StoreFile::class.java) {
    override fun findImagesByStoreId(storeId: Int, fileType: StoreFileType?): List<StoreFile> {
        return from(storeFile).where(
            storeFile.storeId.eq(storeId),
            eqFileType(fileType),
        ).fetch()
    }

    override fun findThumbnailImageByStoreIdIn(storeIds: List<Int>): List<StoreFile> {
        if (storeIds.isEmpty()) {
            return emptyList()
        }

        return from(storeFile).where(
            storeFile.storeId.`in`(storeIds),
            storeFile.fileType.eq(StoreFileType.THUMBNAIL_IMAGE),
        ).fetch()
    }

    private fun eqFileType(fileType: StoreFileType?): BooleanExpression? {
        if (fileType != null) {
            return storeFile.fileType.eq(fileType)
        }
        return null
    }
}
