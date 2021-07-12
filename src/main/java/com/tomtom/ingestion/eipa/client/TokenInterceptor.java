package com.tomtom.ingestion.eipa.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * Adding DEV key to the url path.
 */
public class TokenInterceptor implements RequestInterceptor {

  private final String token;

  public TokenInterceptor(String token) {
    this.token = token;
  }

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.uri(token, true);
  }
}
