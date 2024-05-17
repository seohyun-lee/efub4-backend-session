package efub.session.blog.comment.domain;

import efub.session.blog.account.domain.Account;
import efub.session.blog.global.entity.BaseTimeEntity;
import efub.session.blog.post.domain.Post;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) /* 기본 생성자의 접근 제어를 PROTECTED 로 설정 -> 무분별한 객체 생성을 체크함. */
public class Comment extends BaseTimeEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) /* 지연 로딩을 명시함. */
    @JoinColumn(name = "account_id", updatable = false) /* FK 칼럼 지정 */
    private Account writer;

    @ManyToOne(fetch = FetchType.LAZY)  /* 지연 로딩을 명시함. */
    @JoinColumn(name = "post_id", updatable = false) /* FK 칼럼 지정 */
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentHeart> commentHeartList = new ArrayList<>();

    /* 빌더 패턴 이용 */
    @Builder
    public Comment(String content, Account writer, Post post){
        this.content = content;
        this.writer = writer;
        this.post = post;
    }

    public void updateComment(String content) { this.content = content; }
}
