package com.tomtom.ingestion.eipa.client;

import com.tomtom.ingestion.eipa.dto.EipaDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple rest client for the Dynamic Data API.
 */
@FeignClient(
    value = "dynamic-client",
    url = "${application.integration.eipa.url}",
    configuration = DynamicDataApiClientConfig.class
)
public interface DynamicDataApiClient {

  @GetMapping(value = "/reader/export-data/dynamic")
  EipaDataResponse getDynamicData();

}
