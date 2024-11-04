package efub.session.blog.global.utils;

import java.util.Map;

/**
 * 구글에서 제공하는 사용자 정보를 다루기 위한 유틸리티 클래스
 * 사용자 정보를 담은 attributes를 받아와 이를 통해 특정한 사용자 정보(닉네임과 이메일)를 반환하는 역할을 함
 */
public class OAuth2UserInfo {
    private Map<String, Object> attributes;

    // OAuth2UserInfo 객체 생성자
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    // 사용자 닉네임 반환 메서드
    public String getNickname() {
        return (String) attributes.get("name");
    }

    // 사용자 이메일 반환 메서드
    public String getEmail() {
        return (String) attributes.get("email");
    }
}

