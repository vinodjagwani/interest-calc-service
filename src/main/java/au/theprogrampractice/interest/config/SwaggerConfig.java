package au.theprogrampractice.interest.config;


import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalTime;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .genericModelSubstitutes(Mono.class, Flux.class, Flux.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("au.theprogrampractice.interest"))
                .paths(PathSelectors.any()).build()
                .securitySchemes(Lists.newArrayList(new ApiKey("token", "Authorization", "header")))
                .securityContexts(Lists.newArrayList(SecurityContext.builder().securityReferences(defaultAuth()).build()))
                .forCodeGeneration(true)
                .directModelSubstitute(LocalTime.class, String.class).apiInfo(apiInfo());
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TheProgramPractice Australia Interest Apis")
                .contact(new Contact("Assignment", "", "")).version("1.0").build();
    }

    public List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        return Lists.newArrayList(new SecurityReference("token", new AuthorizationScope[]{authorizationScope}));
    }
}
