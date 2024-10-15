package efub.session.blog;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.session.blog.BlogApplication;
import efub.session.blog.account.dto.SignUpRequestDto;
import efub.session.blog.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest  // 테스트용 애플리케이션 컨텍스트
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = BlogApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
public class AccountControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    protected ObjectMapper objectMapper; // 직렬화, 역질렬화를 위한 클래스

    @Autowired
    protected AccountRepository accountRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }


    /*  회원가입 : 테스트 통과 */
    @Test
    @DisplayName("createAccount : 회원가입 성공")
    public void createAccount() throws Exception{
        /* given */
        final String url = "/accounts";
        final String email = "efub@ewhain.net";
        final String password = "efub1234!";
        final String nickname ="efubBack";
        final SignUpRequestDto requestDto = createDefaultSignUpRequestDto(email , password , nickname);

        /* when */
        final String requestBody = objectMapper.writeValueAsString(requestDto); /* 객체를 JSON으로 직렬화 */

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)); // 설정 내용으로 요청 전송

        /* then */
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").isNotEmpty())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname));
    }


    private SignUpRequestDto createDefaultSignUpRequestDto(String email,String password, String nickname){
        return SignUpRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
