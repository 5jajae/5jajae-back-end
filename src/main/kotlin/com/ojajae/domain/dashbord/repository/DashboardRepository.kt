package com.ojajae.domain.dashbord.repository

import com.ojajae.domain.dashbord.entity.Dashboard
import com.ojajae.domain.dashbord.entity.DashboardType
import com.ojajae.domain.dashbord.entity.QDashboard
import com.ojajae.domain.dashbord.form.request.DashboardRequestForm
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface DashboardRepository: JpaRepository<Dashboard, Long> {
}

@Repository
interface DashboardCustomRepository {
    fun getDashboard(form: DashboardRequestForm): Dashboard?
}

class DashboardRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory
): DashboardCustomRepository {

    override fun getDashboard(form: DashboardRequestForm): Dashboard? {
        val qDashboard = QDashboard.dashboard

        val whereExpression: BooleanExpression = qDashboard.type.eq(form.type)
        if (form.type == DashboardType.STORE_COUNT) {
            whereExpression.and(qDashboard.storeId.eq(form.storeId))
        }

        val query = jpaQueryFactory
            .selectFrom(qDashboard)
            .where(whereExpression)

        return query.fetchOne()
    }
}