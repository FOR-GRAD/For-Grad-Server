package umc.forgrad.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;

        String requestBody = new String(cachingRequest.getContentAsByteArray(), StandardCharsets.UTF_8);

        log.info("Request Headers : {}", getHeaders(request));

        if ("application/json".equalsIgnoreCase(request.getContentType())) {
            // JSON 형식의 요청 본문
            log.info("RequestBody : {}", objectMapper.readTree(requestBody));
        } else {
            // JSON 형식이 아닌 요청 본문
            log.info("RequestBody : {}", requestBody);
        }
    }

    private Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();

        Enumeration<String> headerArray = request.getHeaderNames();
        while (headerArray.hasMoreElements()) {
            String headerName = headerArray.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

}