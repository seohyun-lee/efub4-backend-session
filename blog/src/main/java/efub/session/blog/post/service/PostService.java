package efub.session.blog.post.service;

import efub.session.blog.account.domain.Account;
import efub.session.blog.account.service.AccountService;
import efub.session.blog.exception.CustomDeleteException;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.dto.PostRequestDto;
import efub.session.blog.post.dto.PostResponseDto;
import efub.session.blog.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static efub.session.blog.exception.ErrorCode.PERMISSION_REJECTED_USER;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final AccountService accountService;

    public Post createNewPost(PostRequestDto dto) {
        Account account = accountService.findAccountById(Long.parseLong(dto.getAccountId()));
        Post post = dto.toEntity(account);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Transactional(readOnly = true)
    public List<Post> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    @Transactional(readOnly = true)
    public long countAllPosts() {
        return postRepository.count();
    }

    @Transactional(readOnly = true)
    public Post findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 Post를 찾을 수 없습니다. id="+postId));
        return post;
    }

    public Long updatePost(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 Post를 찾을 수 없습니다. id="+id));
        Account account = accountService.findAccountById(Long.parseLong(dto.getAccountId()));
        post.update(dto, account);
        return post.getPostId();
    }

    public void deletePost(Long id, Long accountId) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 Post를 찾을 수 없습니다. id="+id));
        if(!postRepository.existsByPostIdAndAccount_AccountId(id, accountId)){
            throw new CustomDeleteException(PERMISSION_REJECTED_USER);
        }
        postRepository.delete(post);
    }

    public List<PostResponseDto> searchPost(String keyword, String writerNickname){
        List<PostResponseDto> responseDtoList = postRepository.search(keyword,writerNickname).stream().map(post ->
            PostResponseDto.from(post, post.getAccount().getNickname())).toList();
        return responseDtoList;
    }
}
