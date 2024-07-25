package com.ojajae.domain.item_tag.repository

import com.ojajae.common.repository.LockableRepository
import com.ojajae.domain.item_tag.entity.ItemTag
import org.springframework.data.jpa.repository.JpaRepository

interface ItemTagRepository: JpaRepository<ItemTag, Int>, LockableRepository<ItemTag, Int>
