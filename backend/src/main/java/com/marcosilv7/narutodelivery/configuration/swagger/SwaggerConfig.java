package com.marcosilv7.narutodelivery.configuration.swagger;

import com.google.common.base.Predicate;
import com.marcosilv7.narutodelivery.configuration.api.Api;
import com.marcosilv7.narutodelivery.configuration.security.WebSecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;


import static com.google.common.base.Predicates.or;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiEntregaPedidos() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Core")
                .apiInfo(apiInfoCore())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(pathsCore()).build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,requestGET())
                .globalResponseMessage(RequestMethod.POST,requestPOST())
                .globalResponseMessage(RequestMethod.PUT,requestPUT())
                .globalResponseMessage(RequestMethod.DELETE,requestDELETE())
                .pathMapping("/");
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null,
                Api.TOKEN_TEST, ApiKeyVehicle.HEADER, WebSecurityConfig.JWT_TOKEN_HEADER_PARAM, ",");
    }

    private ApiInfo apiInfoCore() {
        return new ApiInfoBuilder()
                .title("Rumbo Al Mundial 2018")
                .description("Modulo encargado del core del negocio")
                .contact(new Contact("Rumbo al mundial Dev", "", ""))
                .version("1.0")
                .build();
    }

    private List<ResponseMessage> requestGET(){
        return newArrayList(
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Error interno el servidor.")
                        .responseModel(new ModelRef("ErrorResponse"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .responseModel(new ModelRef("ErrorResponse"))
                        .message("Recurso no encontrado.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .responseModel(new ModelRef("ErrorResponse"))
                        .message("La autenticación es requerida.")
                        .build());
    }

    private List<ResponseMessage> requestPOST(){
        return newArrayList(new ResponseMessageBuilder()
                        .code(500)
                        .message("Error interno el servidor.")
                        .responseModel(new ModelRef("ErrorResponse"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("La autenticación es requerida.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("La peticion tiene errores de validacion.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(409)
                        .message("Algún recurso relacionado no se encontró y/o se encontro errores de validacion de negocio.")
                        .build());
    }

    private List<ResponseMessage> requestPUT(){
        return newArrayList(new ResponseMessageBuilder()
                        .code(500)
                        .message("Error interno el servidor.")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("La autenticación es requerida.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("La peticion tiene errores de validacion.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("No se encontró el recurso a modificar o algún recurso relacionado no se encontró")
                        .build(),
                new ResponseMessageBuilder()
                        .code(422)
                        .message("Ocurrieron errores en las validaciones de negocio.")
                        .build());
    }


    private List<ResponseMessage> requestDELETE(){
        return newArrayList(new ResponseMessageBuilder()
                        .code(500)
                        .message("Error interno el servidor.")
                        .responseModel(new ModelRef("Error"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("La autenticación es requerida.")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("El recurso a eliminar no se encontro")
                        .build(),
                new ResponseMessageBuilder()
                        .code(422)
                        .message("Ocurrieron errores en las validaciones de negocio.")
                        .build());
    }

    private Predicate<String> pathsCore() {
        return or(
                regex("/api/v1/pedidos/.*"),
                regex(Api.PROFILE_PATH+"/.*"),
                regex(Api.USER_PATH+"/.*"));
    }

}
