package com.ojajae.domain.dashbord.service

import com.ojajae.common.utils.WebTool
import com.ojajae.domain.dashbord.entity.Dashboard
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.ojajae.domain.dashbord.form.request.DashboardSaveRequestForm
import com.ojajae.domain.dashbord.form.request.DashboardSelectForm
import com.ojajae.domain.dashbord.repository.DashboardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class DashboardService(
    private val dashboardRepository: DashboardRepository,
) {
    fun getDashboard(form: DashboardRequestForm): Dashboard? {
        return dashboardRepository.getDashboard(form.toDashboardSelectForm())
    }

    @Transactional
    fun readStoreDetail(form: DashboardSaveRequestForm) {
        val req = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        val clientIp = WebTool.getClientIP(req)
        val findDashboard: Dashboard? = dashboardRepository.getDashboard(form.toDashboardSelectForm(clientIp))

        if (findDashboard == null) {
            val newDashboard = Dashboard(
                dashboardType = form.dashboardType!!,
                storeId = form.storeId,
                ipAddress = clientIp,
                count = 1
            )
            dashboardRepository.save(newDashboard)
        } else {
            findDashboard.count += 1
        }
    }

    @Transactional
    fun readStoreList(clientIP: String) {
        val now = LocalDate.now()
        val findDashboard = dashboardRepository.getDashboard(DashboardSelectForm(
            dashboardType = DashboardType.STORE_LIST,
            storeId = null,
            clientIP = clientIP,
            storedAt = now,
        ))

        if (findDashboard == null) {
            val newDashboard = Dashboard(
                dashboardType = DashboardType.STORE_LIST,
                storeId = null,
                ipAddress = clientIP,
                count = 1,
                storedAt = now,
            )
            dashboardRepository.save(newDashboard)
        } else {
            findDashboard.count += 1
        }
    }
}