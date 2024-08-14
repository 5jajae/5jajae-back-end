package com.ojajae.domain.dashbord.repository

import com.ojajae.domain.dashbord.entity.Dashboard
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.entity.QDashboard
import com.ojajae.domain.dashbord.entity.QDashboard.dashboard
import com.ojajae.domain.dashbord.form.request.DashboardSelectForm
import com.querydsl.core.types.dsl.BooleanExpression
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.time.LocalDate

interface DashboardRepository: JpaRepository<Dashboard, Int>, DashboardCustomRepository {
}

interface DashboardCustomRepository {
    fun getDashboard(form: DashboardSelectForm): Dashboard?

    fun getDashboardWithWriteLock(form: DashboardSelectForm): Dashboard?

    fun count(form: DashboardSelectForm): Long
}

class DashboardRepositoryImpl: QuerydslRepositorySupport(Dashboard::class.java), DashboardCustomRepository {
    override fun count(form: DashboardSelectForm): Long {
        return from(dashboard)
            .where(
                eqStoreId(form.storeId), eqDashboardType(form.dashboardType)
            )
            .groupBy(dashboard.dashboardType, dashboard.storeId)
            .select(
                dashboard.count.sum()
            )
            .fetchOne()?:0L
    }

    override fun getDashboard(form: DashboardSelectForm): Dashboard? {
        return from(dashboard)
            .where(
                eqStoreId(form.storeId),
                eqDashboardType(form.dashboardType),
                eqIpAddress(form.clientIP),
                eqStoredAt(form.storedAt),
            )
            .fetchOne()
    }

    override fun getDashboardWithWriteLock(form: DashboardSelectForm): Dashboard? {
        val query = querydsl!!.createQuery<Dashboard>().from(dashboard)

        query.setLockMode(LockModeType.PESSIMISTIC_WRITE)

        return query.where(
            eqStoreId(form.storeId),
            eqDashboardType(form.dashboardType),
            eqIpAddress(form.clientIP),
            eqStoredAt(form.storedAt),
        )
        .fetchFirst()
    }

    private fun eqStoreId(storeId: Int?): BooleanExpression? {
        return storeId?.let {
            dashboard.storeId.eq(it)
        }
    }

    private fun eqDashboardType(dashboardType: DashboardType): BooleanExpression {
        return dashboard.dashboardType.eq(dashboardType)
    }

    private fun eqIpAddress(ipAddress: String?): BooleanExpression? {
        if (ipAddress.isNullOrBlank()) {
            return null
        }
        return dashboard.ipAddress.eq(ipAddress)
    }

    private fun eqStoredAt(storedAt: LocalDate?): BooleanExpression? {
        return storedAt?.let {
            dashboard.storedAt.eq(it)
        }
    }
}
