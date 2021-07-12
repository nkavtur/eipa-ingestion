package com.tomtom.ingestion;

import com.tomtom.ingestion.pipeline.DataPipeline;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.tomtom.ingestion"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner cmd(DataPipeline dataPipeline) {
    return args -> dataPipeline.run();
  }
}
