package com.ojajae.common.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class ImmutableEntity<T>(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: T? = null,

    @CreatedDate
    @Column(updatable = false)
    open val createdAt: LocalDateTime = LocalDateTime.now(),
)

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class MutableEntity<T>(
    @LastModifiedDate
    @Column(nullable = false)
    open var updatedAt: LocalDateTime = LocalDateTime.now(),
) : ImmutableEntity<T>()

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class DeletableEntity<T>(
    open var deletedAt: LocalDateTime? = null
) : ImmutableEntity<T>()
