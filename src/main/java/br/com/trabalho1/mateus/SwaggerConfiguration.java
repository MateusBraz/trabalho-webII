package br.com.trabalho1.mateus;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    //    http://localhost:8081/swagger-ui.html#/
    @Bean
    public Docket api() {
        Contact contato = new Contact("Mateus Braz", "https://www.linkedin.com/in/mateus-braz-348b1116a/", "mateus.braz@ufms.com.br");

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.trabalho1.mateus"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("Mateus Braz	API	Documentation")
                                .description("Esta	é	a	documentação	interativa	da	Rest	API  do trabalho de Web II.	Tente	enviar	algum	request	;)")
                                .version("1.0")
                                .contact(contato)
                                .build());
    }
}
