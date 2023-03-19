package com.example.practice.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Table(indexes = [
    Index(columnList = "email", unique = true),
    Index(columnList = "createdAt"),
    Index(columnList = "createdBy")
])
@Entity
class UserAccount(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long = 0L,
    @Column(length = 50, nullable = false, unique = true) val userId : String,
    @Column(length = 255, nullable = false) val userPassword : String,
    @Column(length = 100) val email : String,
    @Column(length = 100) val nickname : String,
    @Column(length = 100) val memo : String,

    @OneToMany(mappedBy = "userAccount", orphanRemoval = true)
    val articles : Set<Article> = linkedSetOf(),

    @OneToMany(mappedBy = "commentUserAccount", orphanRemoval = true)
    val userComments : Set<ArticleComment> = linkedSetOf()

) : AuditingFields()