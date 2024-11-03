package efub.session.blog.global.utils;

import java.util.Map;

/**
 * 구글에서 제공하는 사용자 정보를 다루기 위한 유틸리티 클래스
 * 사용자 정보를 담은 attributes를 받아와 이를 통해 특정한 사용자 정보(닉네임과 이메일)를 반환하는 역할을 함
 */
public class OAuth2UserInfo {
    private Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getNickname() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }
}

