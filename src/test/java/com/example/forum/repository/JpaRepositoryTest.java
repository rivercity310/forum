package com.example.forum.repository;

import com.example.forum.config.JpaConfig;
import com.example.forum.domain.Article;
import com.example.forum.domain.ArticleComment;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Disabled("Spring Data Rest 통합 테스트 제외")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@ActiveProfiles("testdb")
@Transactional
@SpringBootTest
class JpaRepositoryTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given & When
        List<Article> articles = articleRepository.findAll();
        List<ArticleComment> articleComments = articleCommentRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(130);

        assertThat(articleComments)
                .isNotNull()
                .hasSize(300);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();
        Article article = Article.of("new article", "new content", "#spring");

        // When
        Article savedArticle = articleRepository.save(article);

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Commit
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot3";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.save(article);

        // Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("update 테스트")
    @Commit
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given (cascading 옵션 작동 테스트)
        Article article = articleRepository.findById(2L).orElseThrow();

        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);
    }
}