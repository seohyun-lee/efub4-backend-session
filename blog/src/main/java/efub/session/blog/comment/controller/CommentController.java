package efub.session.blog.comment.controller;

import efub.session.blog.account.service.AccountService;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.comment.dto.AccountInfoRequestDto;
import efub.session.blog.comment.dto.CommentRequestDto;
import efub.session.blog.comment.dto.CommentResponseDto;
import efub.session.blog.comment.service.CommentHeartService;
import efub.session.blog.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final AccountService accountService;
    private final CommentHeartService commentHeartService;

    // 좋아요 등록
    @PostMapping("/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createCommentLike(@PathVariable final Long commentId, @RequestBody final AccountInfoRequestDto requestDto) {
        commentHeartService.create(commentId, requestDto);
        return "좋아요를 눌렀습니다.";
    }

    // 좋아요 삭제
    @DeleteMapping("/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCommentLike(@PathVariable final Long commentId, @RequestParam final Long accountId) {
        commentHeartService.delete(commentId, accountId);
        return "좋아요가 취소되었습니다.";
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentResponseDto updatePostComment(@PathVariable final Long commentId, @RequestBody @Valid final CommentRequestDto requestDto) {
        commentService.updateComment(requestDto, commentId);
        Comment comment = commentService.findCommentById(commentId);
        return CommentResponseDto.of(comment);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable final Long commentId) {
        commentService.deleteComment(commentId);
        return "성공적으로 삭제되었습니다.";
    }
}