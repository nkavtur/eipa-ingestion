package com.tomtom.ingestion.sink.dumb;

import com.tomtom.ingestion.ApplicationProperties;
import com.tomtom.ingestion.domain.DynamicDataEvent;
import com.tomtom.ingestion.sink.DynamicDataEventSink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Dumb http implementation of the downstream consumer app.
 * Events get posted to the simple POST endpoint.
 */
@Component
public class DumbHttpSink implements DynamicDataEventSink {

  private final static Logger logger = LoggerFactory.getLogger(DumbHttpSink.class);

  private final RestTemplate restTemplate;
  private final ApplicationProperties.Integration.Sink sink;

  public DumbHttpSink(RestTemplate restTemplate, ApplicationProperties properties) {
    this.restTemplate = restTemplate;
    this.sink = properties.getIntegration().getSink();
  }

  @Override
  public void accept(DynamicDataEvent dynamicDataEvent) {
    logger.trace("Posting {} to sink = {}", dynamicDataEvent, sink.getUrl());
    restTemplate.postForLocation(sink.getUrl(), dynamicDataEvent);
  }
}
