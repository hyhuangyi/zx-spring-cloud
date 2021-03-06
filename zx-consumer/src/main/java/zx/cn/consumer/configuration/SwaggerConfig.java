package zx.cn.consumer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket zxApi() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").defaultValue("Bearer ").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(zxInfo())
                .groupName("api-1")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(aParameters)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("zx.cn.consumer.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 展示分组
     * @return
     */
    @Bean
    public Docket zxApi1() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Authorization").defaultValue("Bearer ").description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(zxInfo())
                 .groupName("api-2")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(aParameters)
                .forCodeGeneration(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("zx.cn.consumer.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数
     * @return
     */
    private ApiInfo zxInfo() {
        return new ApiInfoBuilder()
                .title("zx-consumer 接口列表")
                .description("zx-consumer==>create at 2019/12/05")
                .version("2.0")//版本
                .build();
    }
}
