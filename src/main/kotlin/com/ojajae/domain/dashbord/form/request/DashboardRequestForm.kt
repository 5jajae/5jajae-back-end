package com.ojajae.domain.dashbord.form.request

import com.ojajae.domain.dashbord.entity.DashboardType

data class DashboardRequestForm(
    val dashboardType: DashboardType,
    val storeId: Int?,
    var clientIp: String? = null,
) {
    fun toDashboardSelectForm(): DashboardSelectForm {
        return DashboardSelectForm(
            dashboardType = this.dashboardType
            , storeId = this.storeId
            , clientIP = this.clientIp
        )
    }
}