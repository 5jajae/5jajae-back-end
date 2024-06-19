package com.ojajae.domain.store.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Entity

@Entity
class Store(
    var name: String,

    var descriptions: String?,

    var address: String,

    var lat: Double,

    var lng: Double,

    var contactNumber: String?,

    var homepage: String?,

    var openingHours: String?,

    var representativeName: String?,

    var identificationNumber: String?,

    var items: String?,
): MutableEntity<Int>()
