package com.seventeamproject.common.security.jwt;

import com.seventeamproject.api.admin.enums.AdminRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private final JwtProperties jwtProperties; // application.yml 값 주입

    private SecretKey key; // 서명용 SecretKey
    private JwtParser parser;

    // 애플리케이션 시작 시 Bean 생성 후 SecretKey와 Parser 초기화
    @PostConstruct
    public void init() {

        // Base64 인코딩된 secret → byte[]
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());

        // HMAC SHA용 SecretKey 생성 (HS256 이상)
        this.key = Keys.hmacShaKeyFor(keyBytes);

        // 0.13.x 최신 파서 구성 (재사용)
        this.parser = Jwts.parser()
                .verifyWith(this.key) // 서명 검증 키 설정
                .build(); // JwtParser 생성
    }

    // JWT 생성 (0.13.x 방식은 signWith(key) 단독 사용 가능)
    public String createToken(Long id, String email, AdminRoleEnum role) {

        Date now = new Date();
        Date expiry = new Date(
                now.getTime() + jwtProperties.getAccessTokenValidity()
        );

        return Jwts.builder()
                .subject(email)        // 표준 클레임 sub
                .claim("id", id)      // 커스텀 클레임
                .claim("role", role.name())      // 커스텀 클레임
                .issuedAt(now)            // 발급 시간 iat
                .expiration(expiry)       // 만료 시간 exp
                .signWith(key)            // 0.13.x에서는 알고리즘 자동 추론
                .compact();               // JWT 문자열 생성
    }

    /**
     * 토큰에서 사용자 정보 추출
     * 유효하지 않으면 JwtException 발생
     */
    private Claims extractClaims(String token) {
        if (!StringUtils.hasText(token)) {
            throw new JwtException("Token is empty");
        }
        return parser.parseSignedClaims(token).getPayload();
    }
    public Long getId(String token) {
        return extractClaims(token).get("id", Long.class);
    }
    public String getEmail(String token) {
        return extractClaims(token).getSubject();
    }
    public String getRole(String token) {
        return extractClaims(token).get("role", String.class);
    }
}