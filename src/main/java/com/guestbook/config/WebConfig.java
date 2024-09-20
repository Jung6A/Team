package com.guestbook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // application.properties 또는 application.yml에서 설정된 파일 업로드 경로를 주입받음
    @Value("${uploadPath}") // 파일 업로드 경로 (파일 시스템)
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/**로 시작하는 URL 요청이 들어오면 리소스를 처리할 핸들러 설정
        registry.addResourceHandler("/images/**")
                // 두 가지 경로에서 리소스를 찾도록 설정
                // 첫 번째 경로: 파일 시스템에 있는 업로드된 파일 경로
                .addResourceLocations("file:" + uploadPath + "/")
                // 두 번째 경로: 클래스패스에 있는 static/images 폴더
                .addResourceLocations("classpath:/static/images/")
                // 캐시 기간 설정 (초 단위로 설정, 3600초 = 1시간 동안 브라우저가 캐시 유지)
                .setCachePeriod(3600)
                // 리소스 체인을 활성화하여 리소스 처리 흐름을 최적화
                .resourceChain(true)
                // PathResourceResolver를 사용하여 경로에 따라 리소스를 찾아서 제공
                .addResolver(new PathResourceResolver());
    }
}
