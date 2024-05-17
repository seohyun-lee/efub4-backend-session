package efub.session.blog.post.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.service.AccountService;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.domain.PostHeart;
import efub.session.blog.post.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;
    private final PostService postService;
    private final AccountService accountService;

    // 좋아요 등록
    public void create(Long postId, Long accountId) {
        Account account = accountService.findAccountById(accountId);
        Post post = postService.findPostById(postId);
        if(isExistsByWriterAndPost(account, post)) {
            throw new RuntimeException("이미 좋아요를 누른 게시물입니다.");
        }
        PostHeart postHeart = PostHeart.builder()
                .post(post)
                .account(account)
                .build();
        postHeartRepository.save(postHeart);
    }

    // 좋아요 삭제
    public void delete(Long postId, Long accountId) {
        Post post = postService.findPostById(postId);
        Account account = accountService.findAccountById(accountId);
        PostHeart postHeart = postHeartRepository.findByWriterAndPost(account, post)
                .orElseThrow(() -> new RuntimeException("좋아요가 존재하지 않습니다."));
        postHeartRepository.delete(postHeart);
    }

    // 좋아요 상태 확인
    public boolean isHeart(Long accountId, Post post){
        Account account = accountService.findAccountById(accountId);
        return isExistsByWriterAndPost(account, post);
    }

    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndPost(Account account, Post post) {
        return postHeartRepository.existsByWriterAndPost(account, post);
    }

    @Transactional(readOnly = true)
    public Integer countPostHeart(Post post) {
        Integer count = postHeartRepository.countByPost(post);
        return count;
    }
}