package com.ojajae.domain.dashbord.entity

import com.ojajae.common.entity.MutableEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Dashboard(
    @Enumerated(EnumType.STRING)
    @Column(name = "dashboard_type")
    var dashboardType: DashboardType,

    @Column(name = "store_id")
    var storeId: Int?,

    @Column(name = "ip_address")
    var ipAddress: String?,

    var count: Long = 0
): MutableEntity<Int>()

enum class DashboardType {
    STORE_COUNT
}