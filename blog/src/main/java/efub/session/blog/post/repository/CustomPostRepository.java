package efub.session.blog.post.repository;

import efub.session.blog.post.domain.Post;
import java.util.List;

public interface CustomPostRepository {
    List<Post> search(String keyword, String writerNickname);
}
