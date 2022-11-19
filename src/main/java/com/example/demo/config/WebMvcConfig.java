package com.example.demo.config;

import com.example.demo.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    TokenInterceptor tokenInterceptor;
    static Map<String, String> authorMap;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 用户拦截器
        registry
                .addInterceptor(tokenInterceptor)
                // 需要拦截的请求
                .addPathPatterns("/**");
        //        // 需要放行的请求
        //        .excludePathPatterns("/user/UserLogin")
        //        // 添加swagger-ui的放行路径
        //        .excludePathPatterns(
        //            "/swagger-resources/**",
        //            "/webjars/**",
        //            "/v2/**",
        //            "/swagger-ui.html/**",
        //            "/doc.html/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
    }


    //			@Override
    //			public void addInterceptors(InterceptorRegistry registry) {
    //				registry.addInterceptor(new TestInterceptor())
    //						.addPathPatterns("/static/**")
    //						.excludePathPatterns("/login.html","/css/**","/img/**");
    //			}
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
