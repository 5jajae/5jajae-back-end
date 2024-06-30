package com.ojajae.domain.dashbord.service

import com.ojajae.common.utils.WebTool
import com.ojajae.domain.dashbord.entity.Dashboard
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.ojajae.domain.dashbord.repository.DashboardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@Service
class DashboardService(
    private val dashboardRepository: DashboardRepository,
) {
    @Transactional(readOnly = true)
    fun getDashboard(form: DashboardRequestForm): Dashboard? {
        return dashboardRepository.getDashboard(form)
    }

    @Transactional
    fun readStoreDetail(form: DashboardRequestForm) {
        val req = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        val clientIp = WebTool.getClientIP(req)
        form.clientIp = clientIp
        val findDashboard: Dashboard? = dashboardRepository.getDashboard(form)

        if (findDashboard == null) {
            val newDashboard = Dashboard(
                dashboardType = DashboardType.STORE_COUNT,
                storeId = form.storeId,
                ipAddress = form.clientIp,
                count = 1
            )
            dashboardRepository.save(newDashboard)
        } else {
            findDashboard.count += 1
        }
    }
}