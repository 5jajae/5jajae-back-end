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
): MutableEntity<Int>() {
    fun update(param: Store) {
        this.name = param.name
        this.descriptions = param.descriptions
        this.address = param.address
        this.lat = param.lat
        this.lng = param.lng
        this.contactNumber = param.contactNumber
        this.homepage = param.homepage
        this.openingHours = param.openingHours
        this.representativeName = param.representativeName
        this.identificationNumber = param.identificationNumber
        this.items = param.items
    }
}
