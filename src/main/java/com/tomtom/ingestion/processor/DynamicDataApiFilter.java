package com.tomtom.ingestion.processor;

import com.tomtom.ingestion.domain.DynamicDataEvent;
import com.tomtom.ingestion.domain.EventData;
import com.tomtom.ingestion.storage.EventDataRepository;
import java.util.Objects;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 'Meat' of the ingestion app.
 *
 * The logic is pretty straightforward:
 * 1. Lookup for the existing event data in the redis cache.
 * 2. Persist new event to redis.
 * 3. Compare existing with new one.
 *
 * I use pointId as a key, though it's mentioned in the docs, it might be suddenly changed.
 * I have not come up with better alternative, without consuming & merging data from other APIs.
 */
@Component
public class DynamicDataApiFilter implements Predicate<DynamicDataEvent> {

  private final Logger logger = LoggerFactory.getLogger(DynamicDataApiFilter.class);

  private final EventDataRepository storage;

  public DynamicDataApiFilter(EventDataRepository storage) {
    this.storage = storage;
  }

  @Override
  public boolean test(DynamicDataEvent newEvent) {
    logger.trace("Looking up for event data for pointId = {}", newEvent.getPointId());
    EventData existingEventData = storage.findById(newEvent.getPointId()).orElse(null);

    logger.trace("Persisting event data for pointId = {}", newEvent.getPointId());
    EventData newEventData = newEvent.getData();
    storage.save(newEvent.getData());

    boolean equal = Objects.equals(newEventData, existingEventData);
    if (equal) {
      logger.debug(
          "Skipping event for point_id = {}. Status {} did not change",
          newEventData.getPointId(), newEventData.getStatus());
    }

    return !equal;
  }
}
