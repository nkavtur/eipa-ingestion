package com.tomtom.ingestion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("application")
public class ApplicationProperties {

  private Integration integration;

  public Integration getIntegration() {
    return integration;
  }

  public void setIntegration(Integration integration) {
    this.integration = integration;
  }

  public static class Integration {

    private Eipa eipa;
    private Sink sink;

    public Eipa getEipa() {
      return eipa;
    }

    public Sink getSink() {
      return sink;
    }

    public void setSink(Sink sink) {
      this.sink = sink;
    }

    public void setEipa(Eipa eipa) {
      this.eipa = eipa;
    }

    public static class Eipa {

      private String url;
      private String token;

      public String getUrl() {
        return url;
      }

      public void setUrl(String url) {
        this.url = url;
      }

      public String getToken() {
        return token;
      }

      public void setToken(String token) {
        this.token = token;
      }
    }

    public static class Sink {
      private String url;

      public String getUrl() {
        return url;
      }

      public void setUrl(String url) {
        this.url = url;
      }
    }
  }


}
