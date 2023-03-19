package com.example.practice.domain

import jakarta.persistence.*

@Table(indexes = [Index(columnList = "content"), Index(columnList = "createdAt")])
@Entity
class ArticleComment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long = 0L,
    @Column(length = 500, nullable = false) val content : String,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article : Article,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_account_id")
    val userAccount : UserAccount

) : AuditingFields()