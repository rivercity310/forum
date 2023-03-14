package com.example.practice.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ArticleComment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long,
    @Column(length = 500, nullable = false) val content : String,

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article : Article,

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    val commentUserAccount : UserAccount

) : AuditingFields()