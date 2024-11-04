package efub.session.blog.account.controller;

import efub.session.blog.account.dto.TokenRequestDto;
import efub.session.blog.account.dto.TokenResponseDto;
import efub.session.blog.account.service.AuthService;
import efub.session.blog.global.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    /**
     * 현재 인증된 사용자 emial 조회
     */
    @GetMapping("/me")
    public ResponseEntity<String> getEmail(){
        return ResponseEntity.status(HttpStatus.OK).body(SecurityUtils.getCurrentUserEmail());
    }

    /**
     * 액세스토큰 재발급 처리 메서드
     */
    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> reissuedAccessToken(@RequestBody TokenRequestDto requestDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.reissueAccessToken(requestDto.getRefreshToken()));
    }

}
