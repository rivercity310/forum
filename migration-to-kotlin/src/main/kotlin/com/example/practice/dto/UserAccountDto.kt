package com.example.practice.dto

import com.example.practice.domain.UserAccount
import java.time.LocalDateTime

data class UserAccountDto(
    val userId : String,
    val userPassword : String,
    val email : String,
    val nickname : String,
    val memo : String,
    val createdAt : LocalDateTime,
    val createdBy : String,
    val modifiedAt : LocalDateTime,
    val modifiedBy : String
) {
    companion object {
        internal fun from(entity : UserAccount) : UserAccountDto {
            return UserAccountDto(
                userId = entity.userId,
                userPassword = entity.userPassword,
                email = entity.email,
                nickname = entity.nickname,
                memo = entity.memo,
                createdAt = entity.createdAt!!,
                createdBy = entity.createdBy!!,
                modifiedAt = entity.modifiedAt!!,
                modifiedBy = entity.modifiedBy!!
            )
        }
    }

    internal fun toEntity() : UserAccount {
        return UserAccount(
            userId = userId,
            userPassword = userPassword,
            email = email,
            nickname = nickname,
            memo = memo
        )
    }
}