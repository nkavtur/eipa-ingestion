package com.tomtom.ingestion.storage;

import com.tomtom.ingestion.domain.EventData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Redis cache for storing dynamic data events.
 */
@Repository
public interface EventDataRepository extends CrudRepository<EventData, String> {
}
