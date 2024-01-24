package umc.forgrad.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WenConfig implements WebMvcConfigurer {

    private final HttpSessionInterceptor httpSessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpSessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }

}
