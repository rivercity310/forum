package com.example.practice.domain

import jakarta.persistence.*

@Entity
class Article(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id : Long,
    @Column(length = 10000, nullable = false) val content : String,
    @Column(length = 255, nullable = false) val title : String,
    @Column(length = 255) val hashtag : String,

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    val userAccount : UserAccount,

    @OneToMany(mappedBy = "article")
    val articleComments : Set<ArticleComment> = linkedSetOf()

) : AuditingFields()