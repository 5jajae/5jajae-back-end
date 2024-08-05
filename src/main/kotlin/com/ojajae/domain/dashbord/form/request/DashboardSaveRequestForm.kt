package com.ojajae.domain.dashbord.form.request

import com.ojajae.domain.dashbord.entity.DashboardType

data class DashboardSaveRequestForm(
    val dashboardType: DashboardType?,
    val storeId: Int,
) {
    fun toDashboardSelectForm(
        clientIp: String?
    ): DashboardSelectForm {
        return DashboardSelectForm(
            dashboardType = this.dashboardType!!
            , storeId = this.storeId
            , clientIP = clientIp
        )
    }
}