package com.ojajae.domain.member.repository

import com.ojajae.domain.member.entity.Member
import com.ojajae.domain.member.entity.QMember.member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface MemberRepository: JpaRepository<Member, Int>, MemberCustomRepository {
}

interface MemberCustomRepository {
    fun findByUsername(username: String): Member?
}

class MemberRepositoryImpl: QuerydslRepositorySupport(Member::class.java), MemberCustomRepository {
    override fun findByUsername(username: String): Member? {
        return from(member)
            .where(member.username.eq(username))
            .fetchOne()
    }
}
