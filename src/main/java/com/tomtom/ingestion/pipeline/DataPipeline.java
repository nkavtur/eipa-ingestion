package com.tomtom.ingestion.pipeline;

import com.tomtom.ingestion.poller.DynamicDataApiPoller;
import com.tomtom.ingestion.processor.DynamicDataApiAdapter;
import com.tomtom.ingestion.processor.DynamicDataApiFilter;
import com.tomtom.ingestion.sink.DynamicDataEventSink;
import org.springframework.stereotype.Component;

/**
 * Everything combined in one class:
 * 1. poll the data
 * 2. adapt to our representation
 * 3. filter out unchanged events
 * 4. flush to the sink
 */
@Component
public class DataPipeline implements Runnable {

  private final DynamicDataApiPoller poller;
  private final DynamicDataApiAdapter adapter;
  private final DynamicDataApiFilter filter;
  private final DynamicDataEventSink sink;

  public DataPipeline(DynamicDataApiPoller poller,
                      DynamicDataApiAdapter adapter,
                      DynamicDataApiFilter filter,
                      DynamicDataEventSink sink) {
    this.poller = poller;
    this.adapter = adapter;
    this.filter = filter;
    this.sink = sink;
  }

  public void run() {
    // poll the EIPA API
    poller.stream()
        // adapt to internal models
        .map(adapter)
        // filter out not-changed events
        .filter(filter)
        // send to the down-stream API
        .forEach(sink);
  }
}
