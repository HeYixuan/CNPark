package org.springframe.listener;

import org.springframe.domain.system.JwtUser;
import org.springframe.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.text.JsonMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        System.err.println("authentication.getName() :" + authentication.getName());
        authentication.getPrincipal();
        String jwtToken = jwtTokenUtil.createToken(authentication.getName());
        response.setHeader("X-API-TOKEN", "Bearer " + jwtToken);
        JwtUser jwtUser = JwtUser.build(authentication.getName(), authentication.getAuthorities());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(JsonMapper.INSTANCE.toJson(jwtUser));
        //super.onAuthenticationSuccess(request, response, authentication);
    }
}
