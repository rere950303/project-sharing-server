package YHWLTH.sharing.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        if (requestURI.equals("/api/v1/authentication/login")) {
            response.sendRedirect("/api/v1/authentication/error/loginfail");
        } else {
            response.sendRedirect("/api/v1/authentication/error/unauthenticated");
        }
    }
}
