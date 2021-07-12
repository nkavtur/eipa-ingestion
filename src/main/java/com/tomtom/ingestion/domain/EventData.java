package com.tomtom.ingestion.domain;

import static java.util.Objects.requireNonNull;

import java.time.ZonedDateTime;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("EventData")
public class EventData {

  @Id
  private final String pointId;
  private final Status status;
  private final ZonedDateTime originalTs;

  private EventData(String pointId, Status status, ZonedDateTime originalTs) {
    this.pointId = requireNonNull(pointId);
    this.status = requireNonNull(status);
    this.originalTs = requireNonNull(originalTs);
  }

  public static EventData valueOf(String pointId, Status status, ZonedDateTime originalTs) {
    return new EventData(pointId, status, originalTs);
  }

  public String getPointId() {
    return pointId;
  }

  public Status getStatus() {
    return status;
  }

  public ZonedDateTime getOriginalTs() {
    return originalTs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EventData data = (EventData) o;
    return status == data.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(status);
  }

  @Override
  public String toString() {
    return "EventData{" +
        "pointId='" + pointId + '\'' +
        ", status=" + status +
        ", originalTs=" + originalTs +
        '}';
  }
}
