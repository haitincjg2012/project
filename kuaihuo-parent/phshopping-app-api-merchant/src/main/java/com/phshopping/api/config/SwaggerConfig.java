package com.phshopping.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @项目：phshopping-parent
 * @描述：swagger配置
 * @作者： Mr.chang
 * @创建时间：2017/6/15
 * @Copyright @2017 by Mr.chang
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = { "com.ph" })
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
                 aParameterBuilder.name("token").defaultValue("null").description("token值").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
                aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .globalOperationParameters(aParameters)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.phshopping"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        Contact contact=new Contact("Mr.Chang","www.phds315.com","511098425@qq.com");
        return new ApiInfoBuilder()
                .title("普惠商城商户APP接口文档")
                .description("普惠商城商户APP接口文档")
                .contact(contact)
                .version("2.1")
                .license("普惠商城")
                .licenseUrl("www.puhuids315.com")
                .build();
    }
}
