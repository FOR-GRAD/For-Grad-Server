package umc.forgrad.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class CustomServletWrappingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof StandardMultipartHttpServletRequest) {
            // multipart 요청의 경우, 캐시를 사용하지 않고 원래의 요청을 그대로 사용
            chain.doFilter(request, response);
        } else {
            // 그 외의 요청의 경우, 캐시를 사용
            ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

            chain.doFilter(wrappingRequest, wrappingResponse);

            wrappingResponse.copyBodyToResponse();
        }
    }

}