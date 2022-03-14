package YHWLTH.sharing.config;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.response.PageResultDTO;
import YHWLTH.sharing.dto.response.ShareItemListDTO;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerApi(TypeResolver typeResolver) {
        return new Docket(DocumentationType.OAS_30)
                .additionalModels(
                        typeResolver.resolve(CommonResult.class),
                        typeResolver.resolve(UrlResource.class),
                        typeResolver.resolve(PageResultDTO.class),
                        typeResolver.resolve(ShareItemListDTO.class)
                )
                .ignoredParameterTypes(Pageable.class)
                .apiInfo(swaggerInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("YHWLTH.sharing.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("Description for RESTful API of sharing")
                .license("yhw")
                .licenseUrl("https://rere950303.github.io")
                .version("1.4(2022-03-14)")
                .build();
    }
}