package io.namjune;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJpaAuditing
@EnableSwagger2
public class QnaBoardApplication {

  public static void main(String[] args) {
    SpringApplication.run(QnaBoardApplication.class, args);
  }

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .groupName("qna-board")
        .apiInfo(apiInfo())
        .select()
        .paths(PathSelectors.ant("/api/**"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("QnA Board API")
        .description("QnA Board API")
        .termsOfServiceUrl("http://www-03.ibm.com/software/sla/sladb.nsf/sla/bm?Open")
        .contact("Niklas Heidloff")
        .license("Apache License Version 2.0")
        .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
        .version("2.0")
        .build();
  }
}
