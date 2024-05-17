package efub.session.blog.comment.repository;

import efub.session.blog.account.domain.Account;
import efub.session.blog.comment.domain.Comment;
import efub.session.blog.comment.domain.CommentHeart;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Integer countByComment(Comment comment);
    List<CommentHeart> findByWriter(Account account);
    boolean existsByWriterAndComment(Account account, Comment comment);
    Optional<CommentHeart> findByWriterAndComment(Account account, Comment comment);
}

