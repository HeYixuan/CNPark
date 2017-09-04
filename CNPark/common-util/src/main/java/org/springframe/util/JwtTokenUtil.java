package org.springframe.util;

import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.stream.Collectors;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;
import static java.lang.System.currentTimeMillis;

@Component
public class JwtTokenUtil {

    @Value("${jwt.issuer}")
    private static String issuer;
    @Value("${jwt.secret}")
    private static String secret;
    @Value("${jwt.expiration}")
    private static Long expiration;

    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     *
     * @param token the JWT token to parse
     * @return the Claims object extracted from specified token or null if a token is invalid.
     */
    public static Claims parseToken(String token) {
        Claims claims = Jwts.parser()
                .requireIssuer(issuer)
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public Date createExpirationDate(){
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    public static Date getExpirationDate(String token) {
        Date expiration;
        try {
            final Claims claims = parseToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration  = null;
        }
        return expiration;
    }

    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails info) {
        final Claims claims = parseToken(token);
        final String username = claims.getSubject();
        return (username.equals(info.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param subject the user for which the token will be generated
     * @return the JWT token
     */
    public String createToken(String subject) {
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", SignatureAlgorithm.RS256.getValue())
                .setSubject(subject)
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(createExpirationDate())
                .compressWith(CompressionCodecs.GZIP)
                .signWith(HS512, secret);

        return builder.compact();

    }

    /**
     *
     * </p>生成密钥</p>
     * @param base64Key base64编码密钥
     * @return
     * @date 2017年7月8日
     */
    private SecretKey generalKey(String base64Key) {
        byte[] secretBytes = Base64.decodeBase64(base64Key);
        SecretKey key = new SecretKeySpec(secretBytes, SignatureAlgorithm.RS256.getJcaName());
        return key;
    }

    public static void main(String [] args){
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

        System.out.println("*************************************");
        String token = jwtTokenUtil.createToken("admin");
        System.err.println(token);
        System.out.println("*************************************");

        System.err.println("parse: " + jwtTokenUtil.parseToken(token));
        System.err.println("ExpirationDate: "+ getExpirationDate(token));
        System.err.println("Invalid " + isTokenExpired(token));
    }

}
