package com.example.practice.repository

import com.example.practice.domain.ArticleComment
import com.example.practice.domain.QArticleComment
import com.querydsl.core.types.dsl.StringExpression
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer
import org.springframework.data.querydsl.binding.QuerydslBindings

interface ArticleCommentRepository :
    JpaRepository<ArticleComment, Long>,
    QuerydslPredicateExecutor<ArticleComment>,
    QuerydslBinderCustomizer<QArticleComment>
{
    fun findByArticle_Id(articleId: Long): List<ArticleComment>

    @JvmDefault
    override fun customize(bindings: QuerydslBindings, root: QArticleComment) {
        bindings.excludeUnlistedProperties(true)
        bindings.including(root.content, root.createdBy)

        bindings.bind(root.content).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase)
    }
}