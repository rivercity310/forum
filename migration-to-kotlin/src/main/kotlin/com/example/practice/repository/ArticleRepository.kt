package com.example.practice.repository

import com.example.practice.domain.Article
import com.example.practice.domain.QArticle
import com.querydsl.core.types.dsl.StringExpression
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer
import org.springframework.data.querydsl.binding.QuerydslBindings
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
interface ArticleRepository :
    JpaRepository<Article, Long>,
    QuerydslPredicateExecutor<Article>,
    QuerydslBinderCustomizer<QArticle>
{
    fun findByTitle(title: String, pageable: Pageable) : Page<Article>

    @JvmDefault
    override fun customize(bindings: QuerydslBindings, root: QArticle): Unit {
        bindings.excludeUnlistedProperties(true)
        bindings.including(root.title, root.content, root.hashtag, root.createdBy)

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase)
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase)
    }
}