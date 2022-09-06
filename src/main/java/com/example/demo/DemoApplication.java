package com.example.demo;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.example.demo.controller.common.storage.StorageService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
      }

      @Override
      public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> messageConverter : converters) {
          // System.out.println("aaaaaaaaaaa"+messageConverter);
        }
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

      @Bean
      public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();

        PaginationInnerInterceptor paginationInnerInterceptor =
            new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true); // 合理化
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
      }
    };
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
