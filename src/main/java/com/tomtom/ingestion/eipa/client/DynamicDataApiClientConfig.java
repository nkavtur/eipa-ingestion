package com.tomtom.ingestion.eipa.client;

import com.tomtom.ingestion.ApplicationProperties;
import com.tomtom.ingestion.ApplicationProperties.Integration.Eipa;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DynamicDataApiClientConfig {

  private final Eipa eipa;

  public DynamicDataApiClientConfig(ApplicationProperties app) {
    this.eipa = app.getIntegration().getEipa();
  }

  @Bean
  public TokenInterceptor tokenInterceptor() {
    return new TokenInterceptor(eipa.getToken());
  }

}
