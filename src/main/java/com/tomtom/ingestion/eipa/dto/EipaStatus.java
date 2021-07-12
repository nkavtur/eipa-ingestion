package com.tomtom.ingestion.eipa.dto;

public class EipaStatus {

  private int availability;
  private int status;
  private String ts;

  public int getAvailability() {
    return availability;
  }

  public void setAvailability(int availability) {
    this.availability = availability;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getTs() {
    return ts;
  }

  public void setTs(String ts) {
    this.ts = ts;
  }
}
