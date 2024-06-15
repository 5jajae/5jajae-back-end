package com.ojajae.domain.dashbord.entity

import com.ojajae.common.entity.BaseEntity
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
    var storeId: Long?,

    @Column(name = "ip_address")
    var ipAddress: String?,

    var count: Long = 0
): BaseEntity()

enum class DashboardType {
    STORE_COUNT
}