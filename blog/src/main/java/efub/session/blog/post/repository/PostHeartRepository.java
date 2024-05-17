package efub.session.blog.post.repository;

import efub.session.blog.account.domain.Account;
import efub.session.blog.post.domain.Post;

import java.util.List;
import java.util.Optional;

import efub.session.blog.post.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Integer countByPost(Post post);
    List<PostHeart> findByWriter(Account account);
    boolean existsByWriterAndPost(Account account, Post post);
    Optional<PostHeart> findByWriterAndPost(Account account, Post post);
}