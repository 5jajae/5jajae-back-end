package com.ojajae.common.repository

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface LockableRepository<T, ID> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findOneWithReadLockById(id: ID): Optional<T>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findOneWithWriteLockById(id: ID): Optional<T>

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findOneWithReadLockOrNullById(id: ID): T?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findOneWithWriteOrNullLockById(id: ID): T?

    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findAllWithReadLockByIdIn(id: List<ID>): List<T>

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllWithWriteLockByIdIn(id: List<ID>): List<T>
}
