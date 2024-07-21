package com.ojajae.domain.dashbord.form.request

import com.ojajae.domain.dashbord.entity.DashboardType

data class DashboardSelectForm(
    val dashboardType: DashboardType,
    val storeId: Int?,
    var clientIp: String? = null,
)
