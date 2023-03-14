package com.example.practice.repository

import com.example.practice.domain.UserAccount
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserAccount, Long> {
    fun findByUserId(userId : String) : UserAccount?
}