package com.ojajae.domain.dashbord.service

import com.ojajae.domain.dashbord.entity.Dashboard
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.ojajae.domain.dashbord.repository.DashboardCustomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DashboardService(
    private val dashboardRepository: DashboardCustomRepository
) {
    @Transactional(readOnly = true)
    fun getDashboard(form: DashboardRequestForm): Dashboard? {
        return dashboardRepository.getDashboard(form)
    }
}