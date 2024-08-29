package com.ojajae.domain.member.service

import com.ojajae.domain.member.entity.Member
import com.ojajae.domain.member.entity.UserDetailsImpl
import com.ojajae.domain.member.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val member: Member = memberRepository.findByUsername(username) ?: throw UsernameNotFoundException("계정을 찾을 수 없습니다.")
        return UserDetailsImpl(member)
    }
}

