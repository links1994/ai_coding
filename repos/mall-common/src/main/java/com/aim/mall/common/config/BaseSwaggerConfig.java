package com.aim.mall.common.config;

import com.aim.mall.common.domain.SwaggerProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger基础配置 - 抽象类，子类实现包扫描配置
 */
@Configuration
public abstract class BaseSwaggerConfig {

    /**
     * 抽象方法 - 各个模块实现此方法返回自己的配置
     */
    public abstract SwaggerProperties swaggerProperties();

    /**
     * OpenAPI 配置 - 文档基本信息
     */
    @Bean
    public OpenAPI createRestApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();
        OpenAPI openAPI = new OpenAPI()
                .info(apiInfo(swaggerProperties));

        if (swaggerProperties.isEnableSecurity()) {
            openAPI.schemaRequirement("Authorization", securityScheme())
                    .addSecurityItem(new SecurityRequirement().addList("Authorization"));
        }
        return openAPI;
    }

    /**
     * 分组API配置 - 根据抽象方法返回的配置创建分组
     */
    @Bean
    public GroupedOpenApi createGroupedOpenApi() {
        SwaggerProperties swaggerProperties = swaggerProperties();

        GroupedOpenApi.Builder builder = GroupedOpenApi.builder()
                .group(swaggerProperties.getGroupName());

        // 设置包扫描路径
        if (swaggerProperties.getApiBasePackage() != null &&
                !swaggerProperties.getApiBasePackage().isEmpty()) {
            builder.packagesToScan(
                    swaggerProperties.getApiBasePackage().toArray(new String[0])
            );
        }
        return builder.build();
    }

    private Info apiInfo(SwaggerProperties swaggerProperties) {
        Contact contact = new Contact()
                .name(swaggerProperties.getContactName())
                .url(swaggerProperties.getContactUrl())
                .email(swaggerProperties.getContactEmail());

        return new Info()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(contact)
                .version(swaggerProperties.getVersion());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("Authorization")
                .description("JWT认证令牌");
    }
}