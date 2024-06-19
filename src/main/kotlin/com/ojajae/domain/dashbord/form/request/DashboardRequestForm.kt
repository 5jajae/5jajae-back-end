package com.ojajae.domain.dashbord.form.request

import com.ojajae.domain.dashbord.entity.DashboardType

class DashboardRequestForm(
    val type: DashboardType,
    val storeId: Int?,
)