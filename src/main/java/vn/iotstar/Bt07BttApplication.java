package vn.iotstar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;

import vn.iotstar.config.StorageProperties;
import vn.iotstar.service.IStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class) // thêm cấu hình storage
public class Bt07BttApplication {

	public static void main(String[] args) {
		SpringApplication.run(Bt07BttApplication.class, args);
	}

	// thêm cấu hình storage
	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}
	
	@Bean
	public GroupedOpenApi publicApi() {
	    return GroupedOpenApi.builder()
	            .group("public")
	            .pathsToMatch("/**")
	            .build();
	}

	@Bean
	public OpenAPI springShopOpenAPI() {
	    return new OpenAPI()
	            .info(new Info().title("BT07-BTT API")
	            .description("Bùi Thanh Tùng - 23110357")
	            .version("v1.0")
	            .license(new License().name("Apache 2.0").url("http://springdoc.org")))
	            .externalDocs(new ExternalDocumentation()
	            .description("SpringShop Wiki Documentation")
	            .url("https://springshop.wiki.github.org/docs"));
	}
}
