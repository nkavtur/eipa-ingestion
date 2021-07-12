package com.tomtom.ingestion.processor;

import com.tomtom.ingestion.domain.DynamicDataEvent;
import com.tomtom.ingestion.domain.EventData;
import com.tomtom.ingestion.domain.Status;
import com.tomtom.ingestion.eipa.dto.EipaAvailabilityConstants;
import com.tomtom.ingestion.eipa.dto.EipaData;
import com.tomtom.ingestion.eipa.dto.EipaStatus;
import com.tomtom.ingestion.eipa.dto.EipaStatusConstants;
import java.time.ZonedDateTime;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Adapts Remote API data model to our internal model.
 */
@Component
public class DynamicDataApiAdapter implements Function<EipaData, DynamicDataEvent> {

  private final Logger logger = LoggerFactory.getLogger(DynamicDataApiAdapter.class);

  @Override
  public DynamicDataEvent apply(EipaData remoteData) {
    logger.trace("Adapting eiapData for pointId = {}..", remoteData.getPointId());

    String pointId = remoteData.getPointId();
    ZonedDateTime originalTs = ZonedDateTime.parse(remoteData.getGenerated());

    // adapt status
    Status status = adapt(remoteData.getStatus());

    return DynamicDataEvent.valueOf(
        EventData.valueOf(pointId, status, originalTs)
    );
  }

  private Status adapt(EipaStatus status) {
    if (status == null) {
      return Status.UNKNOWN;
    }

    if (status.getStatus() == EipaAvailabilityConstants.NOT_AVAILABLE) {
      return Status.OUT_OF_ORDER;
    }

    if (status.getStatus() == EipaStatusConstants.FREE) {
      return Status.AVAILABLE;
    }

    if (status.getStatus() == EipaStatusConstants.BUSY) {
      return Status.OCCUPIED;
    }

    return Status.UNKNOWN;
  }

}
