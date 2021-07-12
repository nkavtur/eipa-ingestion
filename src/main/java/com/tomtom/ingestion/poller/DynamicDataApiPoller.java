package com.tomtom.ingestion.poller;

import com.tomtom.ingestion.eipa.client.DynamicDataApiClient;
import com.tomtom.ingestion.eipa.dto.EipaData;
import com.tomtom.ingestion.eipa.dto.EipaDataResponse;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Starting point of the Ingestion App.
 * Fetches data from the remote API, and includes generated timestamp into each element of the data stream.
 */
@Component
public class DynamicDataApiPoller {

  private final Logger logger = LoggerFactory.getLogger(DynamicDataApiPoller.class);

  private final DynamicDataApiClient dynamicDataApiClient;

  public DynamicDataApiPoller(DynamicDataApiClient dynamicDataApiClient) {
    this.dynamicDataApiClient = dynamicDataApiClient;
  }

  public Stream<EipaData> stream() {
    logger.debug("Fetching dynamic data API...");

    EipaDataResponse dynamicDataApiResponse = dynamicDataApiClient.getDynamicData();
    String generatedTs = dynamicDataApiResponse.getGenerated();
    logger.debug("Fetched dynamic data response, generatedTs = {}", generatedTs);

    return dynamicDataApiResponse.getData().stream().peek(d -> d.setGenerated(generatedTs));
  }
}
