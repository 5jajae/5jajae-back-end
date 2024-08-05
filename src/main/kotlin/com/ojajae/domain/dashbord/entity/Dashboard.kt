package com.ojajae.domain.dashbord.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate

@Entity
class Dashboard(
    @Enumerated(EnumType.STRING)
    @Column(name = "dashboard_type")
    var dashboardType: DashboardType,

    @Column(name = "store_id")
    var storeId: Int? = null,

    @Column(name = "ip_address")
    var ipAddress: String?,

    var count: Long = 0,

    var storedAt: LocalDate? = null,
) : MutableEntity<Int>()

enum class DashboardType {
    STORE_LIST
    , STORE_COUNT
    , STORE_CALL
    , STORE_SHARE
}