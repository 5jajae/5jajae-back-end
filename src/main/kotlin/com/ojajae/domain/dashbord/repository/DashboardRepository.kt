package com.ojajae.domain.dashbord.repository

import com.ojajae.domain.dashbord.entity.Dashboard
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.entity.QDashboard.dashboard
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

interface DashboardRepository: JpaRepository<Dashboard, Long> {
}

@Repository
interface DashboardCustomRepository {
    fun getDashboard(form: DashboardRequestForm): Dashboard?
}

class DashboardRepositoryImpl: QuerydslRepositorySupport(Dashboard::class.java), DashboardCustomRepository {
    override fun getDashboard(form: DashboardRequestForm): Dashboard? {
        return from(dashboard)
            .where(
                eqStoreId(form.storeId),
                eqDashboardType(form.type),
            )
            .fetchOne()
    }

    private fun eqStoreId(storeId: Long?): BooleanExpression? {
        return storeId?.let {
            dashboard.storeId.eq(it)
        }
    }

    private fun eqDashboardType(dashboardType: DashboardType): BooleanExpression {
        return dashboard.dashboardType.eq(dashboardType)
    }
}

//class DashboardRepositoryImpl (
//    private val jpaQueryFactory: JPAQueryFactory
//): DashboardCustomRepository {
//
//    override fun getDashboard(form: DashboardRequestForm): Dashboard? {
//        val qDashboard = QDashboard.dashboard
//
//        val whereExpression: BooleanExpression = qDashboard.dashboardType.eq(form.type)
//        if (form.type == DashboardType.STORE_COUNT) {
//            whereExpression.and(qDashboard.storeId.eq(form.storeId))
//        }
//
//        val query = jpaQueryFactory
//            .selectFrom(qDashboard)
//            .where(whereExpression)
//
//        return query.fetchOne()
//    }
//}