package umc.forgrad.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import umc.forgrad.apipayload.code.status.ErrorStatus;
import umc.forgrad.exception.GeneralException;

@Component
public class HttpSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HttpSession session = request.getSession();

        if (session.getAttribute("student") == null) {
            throw new GeneralException(ErrorStatus.SESSION_UNAUTHORIZED);
        }

        return true;

    }

}