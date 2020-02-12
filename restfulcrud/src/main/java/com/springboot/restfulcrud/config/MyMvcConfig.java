package com.springboot.restfulcrud.config;


import com.springboot.restfulcrud.component.MyLocaleResolver;
import com.springboot.restfulcrud.interceptors.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration // use Configuration class can extend SpringMVC config
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    //设置视图映射规则
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器发送/bosheng 请求来到 success 页面
        registry.addViewController("/bosheng").setViewName("success");


//        // set default webpage way2: set in configuration file
//        registry.addViewController("/").setViewName("index");

    }

    // all WebMvcConfigurerAdaptor Components will auto-configure
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("login");
                registry.addViewController("/index.html").setViewName("login");
                registry.addViewController("/main.html").setViewName("dashboard");
            }

            //注册拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
//                super.addInterceptors(registry);
                // /** 拦截任何路径下任何请求
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
                        .excludePathPatterns("/index.html","/","/user/login");
            }
        };
        return adapter;
    }
    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}
