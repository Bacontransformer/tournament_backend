package com.ybk.config;

import com.ybk.interceptor.JwtTokenAdminInterceptor;
import com.ybk.interceptor.JwtTokenLeaderInterceptor;
import com.ybk.interceptor.JwtTokenRefereeInterceptor;
import com.ybk.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    @Autowired
    private JwtTokenLeaderInterceptor jwtTokenLeaderInterceptor;

    @Autowired
    private JwtTokenRefereeInterceptor jwtTokenRefereeInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .addPathPatterns("/admin-match/**")
                .addPathPatterns("/authorize/**")
                .addPathPatterns("/event/**")
                .excludePathPatterns("/admin/test")
                .excludePathPatterns("/admin/save")
                .excludePathPatterns("/admin/login");
        registry.addInterceptor(jwtTokenLeaderInterceptor)
                .addPathPatterns("/leader/**")
                .addPathPatterns("/player/**")
                .addPathPatterns("/team/**")
                .addPathPatterns("/leader-match/**")
                .excludePathPatterns("/leader/register")
                .excludePathPatterns("/leader/login");
        registry.addInterceptor(jwtTokenRefereeInterceptor)
                .addPathPatterns("/referee/**")
                .addPathPatterns("/referee-match/**")
                .excludePathPatterns("/referee/register")
                .excludePathPatterns("/referee/login");
    }
//401是因为login被拦截，WebMvcConfiguration中的放行指令没有生效，将excludePathPatterns("/user/user/login")改为/**/**/login
//authentication 正确的前端传过来的字段名应该是这个，报401的去检查一下改回来就OK了，不是拦截器的问题

    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("羽毛球赛务系统接口文档")
                .version("2.0")
                .description("羽毛球赛务系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("admin接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ybk.controller.admin"))
                .paths(PathSelectors.any())
                .build();
        log.info("开始生成接口文档");
        return docket;
    }

    @Bean
    public Docket docket2() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("羽毛球赛务系统接口文档")
                .version("2.0")
                .description("羽毛球赛务系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("leader端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ybk.controller.leader"))
                .paths(PathSelectors.any())
                .build();
        log.info("开始生成接口文档");
        return docket;
    }

    @Bean
    public Docket docket3() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("羽毛球赛务系统接口文档")
                .version("2.0")
                .description("羽毛球赛务系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("referee端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ybk.controller.referee"))
                .paths(PathSelectors.any())
                .build();
        log.info("开始生成接口文档");
        return docket;
    }

    @Bean
    public Docket docket4() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("羽毛球赛务系统接口文档")
                .version("2.0")
                .description("羽毛球赛务系统接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("visitor接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ybk.controller.visitor"))
                .paths(PathSelectors.any())
                .build();
        log.info("开始生成接口文档");
        return docket;
    }

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        log.info("开始静态资源映射");
    }

    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // 需要为消息转换器设置一个对象转换器，对象转换器可以将java对象序列化为json数据
        converter.setObjectMapper(new JacksonObjectMapper());
        // 将自己的消息转换器加入容器中
        converters.add(0, converter);
    }
}
