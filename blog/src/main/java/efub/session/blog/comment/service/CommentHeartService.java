package efub.session.blog.comment.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.service.AccountService;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.comment.dto.AccountInfoRequestDto;
import efub.session.blog.comment.repository.CommentHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentHeartService {
    private final CommentService commentService;
    private final CommentHeartRepository commentHeartRepository;
    private final AccountService accountService;

    /* 댓글 좋아요 등록 */
    public void create(Long commentId, AccountInfoRequestDto requestDto) {
    }

    /* 댓글 좋아요 삭제 */
    public void delete(Long commentId, Long accountId) {
    }

    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndComment(Account account, Comment comment) {
        return commentHeartRepository.existsByWriterAndComment(account, comment);
    }

    @Transactional(readOnly = true)
    public Integer countCommentHeart(Comment comment) {
        Integer count = commentHeartRepository.countByComment(comment);
        return count;
    }

}

