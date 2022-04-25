package co.com.meli.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;


@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfiguration {

    public static final String MUTANT_TAG = "Mutant Service API";

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("co.com.meli.api.web"))
                .paths(PathSelectors.any())
                .build()
                .tags(new Tag(MUTANT_TAG, "API para la validacion de Mutantes a traves del DNA humano"))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mutant Service API")
                .contact(new Contact("Lino Rojas Lizarazo",
                        "https://www.linkedin.com/in/lino-rojas-lizarazo-ingeniero-sistemas",
                        "liro246@gmail.com"))
                .version("1.0.0")
                .build();
    }

}