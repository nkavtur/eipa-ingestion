package com.tomtom.ingestion.eipa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EipaData {

  private String pointId;
  private EipaStatus status;
  private String generated;

  public String getPointId() {
    return pointId;
  }

  @JsonProperty("point_id")
  public void setPointId(String pointId) {
    this.pointId = pointId;
  }

  public EipaStatus getStatus() {
    return status;
  }

  public void setStatus(EipaStatus status) {
    this.status = status;
  }

  public String getGenerated() {
    return generated;
  }

  public void setGenerated(String generated) {
    this.generated = generated;
  }
}
