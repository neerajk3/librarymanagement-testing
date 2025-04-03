/**
 * 
 */
package com.librarymanagement.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Neerajkumar
 *
 */
@Configuration
public class SpringConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .description("Library Management Apis")
                        .title("Library Management Apis")
                        .version("0.0.1-SNAPSHOT")
                        .build())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.librarymanagement.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

}
