package com.tomtom.ingestion.eipa.dto;

import java.util.List;

public class EipaDataResponse {

  private List<EipaData> data;
  private String generated;

  public List<EipaData> getData() {
    return data;
  }

  public void setData(List<EipaData> data) {
    this.data = data;
  }

  public String getGenerated() {
    return generated;
  }

  public void setGenerated(String generated) {
    this.generated = generated;
  }
}
