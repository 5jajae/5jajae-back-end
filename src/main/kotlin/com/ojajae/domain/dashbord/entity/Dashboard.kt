package com.ojajae.domain.dashbord.entity

import com.ojajae.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "tb_dashboard")
class Dashboard(
    @Enumerated(EnumType.STRING)
    @Column(name = "dashboard_type")
    var type: DashboardType,

    @Column(name = "store_id")
    var storeId: Long?,

    @Column(name = "ip_address")
    var ipAddress: String?,

    var count: Long = 0
): BaseEntity()

enum class DashboardType {
    STORE_COUNT
}