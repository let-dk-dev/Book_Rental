package com.dk.board_book.config;

import com.dk.board_book.interceptor.AdminCheckInterceptor;
import com.dk.board_book.interceptor.LoggerInterceptor;
import com.dk.board_book.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig  implements WebMvcConfigurer { // web app의,,interceptor 설정 역할

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new LoggerInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**" , "/uploads/**", "/upload/**"); // 수정

        registry.addInterceptor(new LoginCheckInterceptor())
                        .addPathPatterns("/**")
                        .excludePathPatterns("/log*", "/css/**", "/images/**", "/js/**", "/member/**", "/members/**", "/member-count"); // (수정)


        // AdminInterceptor 추가
        registry.addInterceptor(new AdminCheckInterceptor())
                .addPathPatterns("/admin/**"); // /admin 경로에 적용
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        System.out.println("--------------------- addResourceHandlers ---------------------");

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            registry.addResourceHandler("/uploads/**")         //웹에서 접근할 url 경로
                .addResourceLocations("file:///E:/develop/upload-files/");  //실제파일이 위치하는 경로.  마지막에/가 있어야 디렉토리로 인식
        } else {
            registry.addResourceHandler("/uploads/**")         //웹에서 접근할 url 경로
                .addResourceLocations("file:///home/ubuntu/uploads/");      //실제파일이 위치하는 경로.  마지막에/가 있어야 디렉토리로 인식
        }
    }


}
