package com.example.practice.dto

import com.example.practice.domain.UserAccount
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

class UserDto {
    data class UserSignUp(
        @field:NotNull var userId : String,
        @field:NotNull var userPassword : String,
        @field:Email var email : String?,
        var nickname : String?,
        var memo : String?
    ) {
        internal fun toEntity() : UserAccount {
            return UserAccount(
                userId = userId,
                userPassword = userPassword,
                email = email ?: "No Email",
                nickname = nickname ?: "No NickName",
                memo = memo ?: "No Memo"
            )
        }
    }
}