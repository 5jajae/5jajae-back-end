package com.ojajae.domain.member.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserDetailsImpl(
    private val member: Member
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        val authorityList: MutableList<GrantedAuthority> = ArrayList()
        authorityList.add(SimpleGrantedAuthority(member.role))
        return authorityList
    }

    override fun getPassword(): String {
        return member.password
    }

    override fun getUsername(): String {
        return member.username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

