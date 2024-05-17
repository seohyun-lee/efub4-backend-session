package efub.session.blog.comment.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.service.AccountService;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.comment.dto.CommentRequestDto;
import efub.session.blog.comment.repository.CommentRepository;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final AccountService accountService;
    private final PostService postService;
    private final CommentRepository commentRepository;

    /* 댓글 생성 */
    public Comment saveComment(Long postId, CommentRequestDto requestDto) {
        Account writer = accountService.findAccountById(requestDto.getAccountId());
        Post post = postService.findPostById(postId);

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .writer(writer)
                .post(post)
                .build();
        commentRepository.save(comment);

        return comment;
    }

    /* 게시글의 댓글 목록 조회 */
    public List<Comment> findPostCommentList(Long postId) {
        Post post = postService.findPostById(postId);
        return commentRepository.findAllByPost(post);
    }

    /* 작성자의 댓글 목록 조회 */
    public List<Comment> findAccountCommentList(Account writer) {
        return commentRepository.findAllByWriter(writer);
    }


    /* 댓글 수정 */
    public void updateComment(CommentRequestDto requestDto, Long commentId){
        Comment comment = findCommentById(commentId);
        comment.updateComment(requestDto.getContent());
    }

    /* 댓글 삭제 */
    public void deleteComment(Long commentId) {
        Comment comment = findCommentById(commentId);
        commentRepository.delete(comment);
    }

    /* 댓글 아이디 조회 */
    @Transactional(readOnly = true)
    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. id=" + commentId));
    }
}
