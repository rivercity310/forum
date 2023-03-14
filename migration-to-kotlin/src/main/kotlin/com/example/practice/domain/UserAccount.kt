package com.example.practice.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class UserAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long? = null,
    @Column(length = 50, nullable = false, unique = true) val userId : String,
    @Column(length = 255, nullable = false) val userPassword : String,
    @Column(length = 100) val email : String,
    @Column(length = 100) val nickname : String,
    @Column(length = 100) val memo : String,

    @OneToMany(mappedBy = "userAccount", cascade = [CascadeType.ALL])
    val articles : Set<Article> = linkedSetOf(),

    @OneToMany(mappedBy = "commentUserAccount", cascade = [CascadeType.ALL])
    val userComments : Set<ArticleComment> = linkedSetOf()

) : AuditingFields()