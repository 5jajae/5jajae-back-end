package com.ojajae.domain.dashbord.form.response

import com.ojajae.domain.dashbord.entity.Dashboard

class DashboardResponseForm(
    val count: Long
) {
    companion object{
        fun of(dashboard: Dashboard?): DashboardResponseForm {
            return DashboardResponseForm(dashboard?.count ?: 0)
        }
    }
}