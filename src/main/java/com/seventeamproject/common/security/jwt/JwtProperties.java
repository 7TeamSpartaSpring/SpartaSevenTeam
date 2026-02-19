package com.seventeamproject.common.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter // getter 자동 생성
@Setter // setter 자동 생성
@Component // Spring Bean 등록
@ConfigurationProperties(prefix = "jwt")
// application.yml의 jwt.* 값을 매핑
public class JwtProperties {

    private String secret;               // jwt.secret 매핑
    private long accessTokenValidity;    // jwt.access-token-validity 매핑
}