package com.tomtom.ingestion.domain;

import static java.util.Objects.requireNonNull;

import java.time.ZonedDateTime;
import java.util.UUID;

public class DynamicDataEvent {

  private final String id;
  private final ZonedDateTime ts;
  private final Type type;
  private final EventData data;

  public DynamicDataEvent(String id, ZonedDateTime ts, Type type, EventData data) {
    this.id = requireNonNull(id, "'id' cannot be null");
    this.ts = requireNonNull(ts, "'ts' cannot be null");
    this.type = requireNonNull(type, "'type' cannot be null");
    this.data = requireNonNull(data, "'data' cannot be null");
  }

  public static DynamicDataEvent valueOf(EventData data) {
    return new DynamicDataEvent(
        UUID.randomUUID().toString(),
        ZonedDateTime.now(),
        Type.STATUS_UPDATE,
        data
    );
  }

  public String getPointId() {
    return this.data.getPointId();
  }

  public String getId() {
    return id;
  }

  public ZonedDateTime getTs() {
    return ts;
  }

  public Type getType() {
    return type;
  }

  public EventData getData() {
    return data;
  }

  @Override
  public String toString() {
    return "DynamicDataEvent{" +
        "id='" + id + '\'' +
        ", ts=" + ts +
        ", type=" + type +
        ", data=" + data +
        '}';
  }
}
