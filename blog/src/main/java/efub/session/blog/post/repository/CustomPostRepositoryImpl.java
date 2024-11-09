package efub.session.blog.post.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import efub.session.blog.account.domain.QAccount;
import efub.session.blog.post.domain.Post;
import efub.session.blog.post.domain.QPost;
import java.util.List;

public class CustomPostRepositoryImpl implements CustomPostRepository{

    private final JPAQueryFactory queryFactory;

    public CustomPostRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Post> search(String keyword, String writerNickname) {

        //QueryDSL용 클래스 (Q도메인 클래스) - 해당 엔티티의 필드에 접근 가능
        QPost post = QPost.post;
        QAccount account =  QAccount.account;

        //BooleanBuilder: 조건을 동적으로 추가할 수 있게 해주는 QueryDSL 클래스
        BooleanBuilder builder = new BooleanBuilder();

        //작성자 조건 추가
        if(writerNickname != null && !writerNickname.isEmpty()){
            builder.and(post.account.nickname.eq(writerNickname));
        }

        //키워드 조건 추가
        if(keyword != null && !keyword.isEmpty()){
            builder.and(post.title.containsIgnoreCase(keyword)
                .or(post.content.containsIgnoreCase(keyword)));
        }

        return queryFactory
            .selectFrom(post)
            .join(post.account, account).fetchJoin()
            .where(builder)
            .fetch();
    }
}
