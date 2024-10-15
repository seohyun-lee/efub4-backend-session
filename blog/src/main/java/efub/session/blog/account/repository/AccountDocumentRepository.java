package efub.session.blog.account.repository;

import efub.session.blog.account.domain.AccountDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

/* AccountDocument의 Repository */
public interface AccountDocumentRepository extends MongoRepository<AccountDocument , String> {

}
