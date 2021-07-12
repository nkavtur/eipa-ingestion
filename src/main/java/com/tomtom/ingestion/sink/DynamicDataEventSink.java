package com.tomtom.ingestion.sink;

import com.tomtom.ingestion.domain.DynamicDataEvent;
import java.util.function.Consumer;

/**
 * Final point of the Ingestion App. Represents downstream consumer API.
 */
public interface DynamicDataEventSink extends Consumer<DynamicDataEvent> {

}
