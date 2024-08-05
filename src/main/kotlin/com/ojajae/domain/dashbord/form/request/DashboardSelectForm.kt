package com.ojajae.domain.dashbord.form.request

import com.ojajae.domain.dashbord.entity.DashboardType
import java.time.LocalDate

data class DashboardSelectForm(
    val dashboardType: DashboardType,
    val storeId: Int?,
    val storedAt: LocalDate? = null,
    var clientIP: String? = null,
)
