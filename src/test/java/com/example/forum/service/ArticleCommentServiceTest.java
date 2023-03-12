package com.example.forum.service;

import com.example.forum.domain.Article;
import com.example.forum.domain.ArticleComment;
import com.example.forum.domain.UserAccount;
import com.example.forum.dto.ArticleCommentDto;
import com.example.forum.dto.UserAccountDto;
import com.example.forum.repository.ArticleCommentRepository;
import com.example.forum.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {
    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleCommentRepository articleCommentRepository;
    @Mock private ArticleRepository articleRepository;

    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments() {
        // Given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));

        // When
        List<ArticleCommentDto> actual = sut.searchArticleComment(articleId);

        // Then
        then(articleRepository).should().findById(articleId);
        assertThat(actual)
                .hasSize(1)
                .first()
                .hasFieldOrPropertyWithValue("content", expected.getContent());
    }

    @DisplayName("댓글 정보를 입력하면, 댓글 저장")
    @Test
    void givenArticleCommentInfo_whenSavingArticleComment_thenSavesArticleComment() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willReturn(createArticle());
        given(articleCommentRepository.save(any(ArticleComment.class))).willReturn(null);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).should().save(any(ArticleComment.class));
    }

    @DisplayName("댓글 저장을 시도했는데 맞는 게시글이 없으면, 경고 로그를 찍고 아무것도 안한다")
    @Test
    void givenNoneExiststentArticle_whenSavingArticleComment_thenLogsSituationAndDoesNothing() {
        // Given
        ArticleCommentDto dto = createArticleCommentDto("댓글");
        given(articleRepository.getReferenceById(dto.articleId())).willThrow(EntityNotFoundException.class);

        // When
        sut.saveArticleComment(dto);

        // Then
        then(articleRepository).should().getReferenceById(dto.articleId());
        then(articleCommentRepository).shouldHaveNoInteractions();
    }

    @DisplayName("댓글 정보를 입력하면, 댓글을 수정한다")
    @Test
    void givenArticleCommentInfo_whenUpdatingArticleComment_thenUpdatesArticleComment() {
        // Given
        String oldComment = "comment";
        String updatedComment = "댓글";
        ArticleComment articleComment = createArticleComment(oldComment);
        ArticleCommentDto dto = createArticleCommentDto(updatedComment);
        given(articleCommentRepository.getReferenceById(dto.id())).willReturn(articleComment);

        // When
        sut.updateArticleComment(dto);

        // Then
        then(articleCommentRepository).should().getReferenceById(dto.id());
        assertThat(articleComment.getContent())
                .isNotEqualTo(oldComment)
                .isEqualTo(updatedComment);
    }

    @DisplayName("댓글 ID를 입력하면 댓글을 삭제한다")
    @Test
    void givenArticleCommentId_whenDeletingArticleComment_thenDeletesArticleComment() {
        // Given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentRepository).deleteById(articleCommentId);

        // When
        sut.deleteArticleComment(articleCommentId);

        // Then
        then(articleCommentRepository).should().deleteById(articleCommentId);
    }

    private Article createArticle() {
        return Article.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private ArticleCommentDto createArticleCommentDto(String comment) {
        return ArticleCommentDto.of(
                1L,
                1L,
                createUserAccountDto(),
                comment,
                LocalDateTime.now(),
                "seungsu",
                LocalDateTime.now(),
                "seungsu"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "SEUNGSU",
                "1111",
                "h970126@gmail.com",
                "seungsu",
                "memo.....",
                LocalDateTime.now(),
                "seungsu",
                LocalDateTime.now(),
                "seungsu"
        );
    }

    private ArticleComment createArticleComment(String content) {
        return ArticleComment.of(
                Article.of(
                        createUserAccount(),
                        "title",
                        "content",
                        "hashtag"
                ),
                createUserAccount(),
                content
        );
    }

    private UserAccount createUserAccount() {
        return UserAccount.of(
                "seungsu",
                "password",
                "h970126@gmail.com",
                "seungsu",
                null
        );
    }
}