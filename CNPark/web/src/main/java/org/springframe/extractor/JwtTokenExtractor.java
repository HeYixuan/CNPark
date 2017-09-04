package org.springframe.extractor;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;

import javax.servlet.http.HttpServletRequest;

public class JwtTokenExtractor implements TokenExtractor {
    @Override
    public Authentication extract(HttpServletRequest request) {
        return null;
    }
}
