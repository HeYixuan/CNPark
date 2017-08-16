//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframe.configure;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.jwt.crypto.sign.SignatureVerifier;
import org.springframework.security.jwt.crypto.sign.Signer;
import org.springframework.security.oauth2.common.DefaultExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.util.Assert;

public class JwtAccessTokenConverter implements TokenEnhancer, AccessTokenConverter, InitializingBean {
    public static final String TOKEN_ID = "jti";
    public static final String ACCESS_TOKEN_ID = "ati";
    private static final Log logger = LogFactory.getLog(JwtAccessTokenConverter.class);
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    private JsonParser objectMapper = JsonParserFactory.create();
    private String verifierKey = (new RandomValueStringGenerator()).generate();
    private Signer signer;
    private String signingKey;
    private SignatureVerifier verifier;

    public JwtAccessTokenConverter() {
        this.signer = new MacSigner(this.verifierKey);
        this.signingKey = this.verifierKey;
    }

    public void setAccessTokenConverter(AccessTokenConverter tokenConverter) {
        this.tokenConverter = tokenConverter;
    }

    public AccessTokenConverter getAccessTokenConverter() {
        return this.tokenConverter;
    }

    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        return this.tokenConverter.convertAccessToken(token, authentication);
    }

    public OAuth2AccessToken extractAccessToken(String value, Map<String, ?> map) {
        return this.tokenConverter.extractAccessToken(value, map);
    }

    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return this.tokenConverter.extractAuthentication(map);
    }

    public void setVerifier(SignatureVerifier verifier) {
        this.verifier = verifier;
    }

    public void setSigner(Signer signer) {
        this.signer = signer;
    }

    public Map<String, String> getKey() {
        Map<String, String> result = new LinkedHashMap();
        result.put("alg", this.signer.algorithm());
        result.put("value", this.verifierKey);
        return result;
    }

    public void setKeyPair(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        Assert.state(privateKey instanceof RSAPrivateKey, "KeyPair must be an RSA ");
        this.signer = new RsaSigner((RSAPrivateKey)privateKey);
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        this.verifier = new RsaVerifier(publicKey);
        this.verifierKey = "-----BEGIN PUBLIC KEY-----\n" + new String(Base64.encode(publicKey.getEncoded())) + "\n-----END PUBLIC KEY-----";
    }

    public void setSigningKey(String key) {
        Assert.hasText(key);
        key = key.trim();
        this.signingKey = key;
        if(this.isPublic(key)) {
            this.signer = new RsaSigner(key);
            logger.info("Configured with RSA signing key");
        } else {
            this.verifierKey = key;
            this.signer = new MacSigner(key);
        }

    }

    private boolean isPublic(String key) {
        return key.startsWith("-----BEGIN");
    }

    public boolean isPublic() {
        return this.signer instanceof RsaSigner;
    }

    public void setVerifierKey(String key) {
        this.verifierKey = key;
    }

    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
        Map<String, Object> info = new LinkedHashMap(accessToken.getAdditionalInformation());
        String tokenId = result.getValue();
        if(!info.containsKey("jti")) {
            info.put("jti", tokenId);
        } else {
            tokenId = (String)info.get("jti");
        }

        result.setAdditionalInformation(info);
        result.setValue(this.encode(result, authentication));
        OAuth2RefreshToken refreshToken = result.getRefreshToken();
        if(refreshToken != null) {
            DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
            encodedRefreshToken.setValue(refreshToken.getValue());
            encodedRefreshToken.setExpiration((Date)null);

            try {
                Map<String, Object> claims = this.objectMapper.parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());
                if(claims.containsKey("jti")) {
                    encodedRefreshToken.setValue(claims.get("jti").toString());
                }
            } catch (IllegalArgumentException var11) {
                ;
            }

            Map<String, Object> refreshTokenInfo = new LinkedHashMap(accessToken.getAdditionalInformation());
            refreshTokenInfo.put("jti", encodedRefreshToken.getValue());
            refreshTokenInfo.put("ati", tokenId);
            encodedRefreshToken.setAdditionalInformation(refreshTokenInfo);
            DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(this.encode(encodedRefreshToken, authentication));
            if(refreshToken instanceof ExpiringOAuth2RefreshToken) {
                Date expiration = ((ExpiringOAuth2RefreshToken)refreshToken).getExpiration();
                encodedRefreshToken.setExpiration(expiration);
                token = new DefaultExpiringOAuth2RefreshToken(this.encode(encodedRefreshToken, authentication), expiration);
            }

            result.setRefreshToken((OAuth2RefreshToken)token);
        }

        return result;
    }

    public boolean isRefreshToken(OAuth2AccessToken token) {
        return token.getAdditionalInformation().containsKey("ati");
    }

    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(this.tokenConverter.convertAccessToken(accessToken, authentication));
        } catch (Exception var5) {
            throw new IllegalStateException("Cannot convert access token to JSON", var5);
        }

        String token = JwtHelper.encode(content, this.signer).getEncoded();
        return token;
    }

    protected Map<String, Object> decode(String token) {
        try {
            Jwt jwt = JwtHelper.decodeAndVerify(token, this.verifier);
            String content = jwt.getClaims();
            Map<String, Object> map = this.objectMapper.parseMap(content);
            if(map.containsKey("exp") && map.get("exp") instanceof Integer) {
                Integer intValue = (Integer)map.get("exp");
                map.put("exp", new Long((long)intValue.intValue()));
            }

            return map;
        } catch (Exception var6) {
            throw new InvalidTokenException("Cannot convert access token to JSON", var6);
        }
    }

    public void afterPropertiesSet() throws Exception {
        if(this.verifier == null) {
            Object verifier = new MacSigner(this.verifierKey);

            try {
                verifier = new RsaVerifier(this.verifierKey);
            } catch (Exception var5) {
                logger.warn("Unable to create an RSA verifier from verifierKey (ignoreable if using MAC)");
            }

            if(this.signer instanceof RsaSigner) {
                byte[] test = "test".getBytes();

                try {
                    ((SignatureVerifier)verifier).verify(test, this.signer.sign(test));
                    logger.info("Signing and verification RSA keys match");
                } catch (InvalidSignatureException var4) {
                    logger.error("Signing and verification RSA keys do not match");
                }
            } else if(verifier instanceof MacSigner) {
                Assert.state(this.signingKey == this.verifierKey, "For MAC signing you do not need to specify the verifier key separately, and if you do it must match the signing key");
            }

            this.verifier = (SignatureVerifier)verifier;
        }
    }
}
