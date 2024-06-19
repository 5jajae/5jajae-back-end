package com.ojajae.domain.dashbord.controller

import com.ojajae.common.web.ResultDTO
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.ojajae.domain.dashbord.form.response.DashboardResponseForm
import com.ojajae.domain.dashbord.service.DashboardService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboard")
class DashboardController(
    private val dashboardService: DashboardService
) {
    @GetMapping("/store/{store}")
    fun getDashboardByStore(@PathVariable store: Int): ResponseEntity<ResultDTO<DashboardResponseForm>> {
        val dashboard = dashboardService.getDashboard(DashboardRequestForm(DashboardType.STORE_COUNT, store))
        return ResponseEntity.ok(ResultDTO.createSuccess("", DashboardResponseForm.of(dashboard)))
    }
}