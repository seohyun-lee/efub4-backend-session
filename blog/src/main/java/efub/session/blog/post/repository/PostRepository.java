package efub.session.blog.post.repository;

import efub.session.blog.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository{
    boolean existsByPostIdAndAccount_AccountId(Long id, Long accountId);
}
