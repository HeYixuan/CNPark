package org.springframe.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframe.constans.MobilePlatform;
import org.springframe.security.SpringSecurityService;
import org.springframe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    private final static String AUTHORIZATION_HEADER = "X-API-TOKEN";
    private final static String TOKEN_PREFIX = "Bearer ";
    private final static String VERSION_HEADER = "X-Version";
    private final static String DEVICE_HEADER = "X-Device";

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private SpringSecurityService springSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Filtering B on...........................................................");
        String version = request.getHeader(VERSION_HEADER);
        String device = request.getHeader(DEVICE_HEADER);
        String header = request.getHeader(AUTHORIZATION_HEADER);
        String uri = request.getRequestURI();

        // 不过滤的请求链接
        boolean permit = false || uri.startsWith("/login");

        if (permit) {
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.isBlank(header) || !header.startsWith(TOKEN_PREFIX)){
            logger.error("Request header X-API-TOKEN is empty");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // 校验平台和接口版本号，校验平台取值
        if(StringUtils.isBlank(version) || StringUtils.isBlank(device) || !MobilePlatform.contains(device)){
            logger.error("Request header X-Version or X-Device is empty");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        final String token = header.substring(7);
        String username = jwtTokenUtil.getUsername(token);
        if (!StringUtils.isBlank(username) && null == SecurityContextHolder.getContext().getAuthentication()){
            UserDetails userDetails = springSecurityService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().print("Invalid token error.");
                return ;
            }
        }
        filterChain.doFilter(request, response);
    }

}
