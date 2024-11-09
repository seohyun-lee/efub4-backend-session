package efub.session.blog.querydsl;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.repository.AccountRepository;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QueryDslTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    public void insertDataForSearch(){
        Account account1 = Account.builder()
            .email("ewha@example.com")
            .password("pw1234")
            .nickname("ewhaaaaaaa")
            .build();
        Account account2 = Account.builder()
            .email("fubi@example.com")
            .password("pw1234")
            .nickname("fubiiiiiii")
            .build();

        accountRepository.save(account1);
        accountRepository.save(account2);

        Post post1 = Post.builder()
            .account(account1)
            .title("eWHa")
            .content("good")
            .build();
        Post post2 = Post.builder()
            .account(account1)
            .title("cat or dog")
            .content("cat")
            .build();
        Post post3 = Post.builder()
            .account(account2)
            .title("univ")
            .content("EWHA")
            .build();
        Post post4 = Post.builder()
            .account(account2)
            .title("ewha university")
            .content("efub")
            .build();

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
    }
}
