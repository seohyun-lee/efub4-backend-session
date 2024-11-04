package efub.session.blog.account.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
    private String refreshToken;
}
