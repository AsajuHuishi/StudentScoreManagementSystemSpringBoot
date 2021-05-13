package indi.huishi.shizuo.config;

import indi.huishi.shizuo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: Huishi
 * @Date: 2021/4/28 0:23
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    /**
     * 添加静态资源文件，外部可以直接访问地址
     * https://www.cnblogs.com/kangkaii/p/9023751.html
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    /**
     * 拦截所有请求，只放行负责登录的两个请求路径
     * /** 拦截所有，包括静态资源也会被拦截
     * /* 不包括静态资源的所有路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/user/**","/kaptcha/**","/static/**"); //放行的请求
//        registry.addInterceptor(new TestInterceptor2()).addPathPatterns("/**").excludePathPatterns("/","/login","/static/**"); //放行的请求
    }


}
