package com.example.demo;

import com.example.demo.controller.common.storage.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@MapperScan("com.example.demo.mapper")
@EnableScheduling
@EnableTransactionManagement

public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            //	storageService.deleteAll();
            storageService.init();
        };
    }
}
/*
@Configuration
public class testConfig implements WebMvcConfigurer {

     //这段代码的意思是，添加拦截器,添加拦截对象，排除拦截对象。
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login.html","/css/**","/img/**");
    }


}


public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //有session说明登陆成功
        Object session = request.getSession().getAttribute("logionSession");

        if (loginUser==null){//没有登陆
            request.setAttribute("msg","没有权限，请先登陆");
            request.getRequestDispatcher("login.html").forward(request,response);
            return false;
        }else {
            return true;
        }


    }
}
 */
