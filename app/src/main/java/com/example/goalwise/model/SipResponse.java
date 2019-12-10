package com.example.goalwise.model;

import java.util.List;

public class SipResponse {

    private List<DataDto> data;
    private String status;

    public List<DataDto> getData() {
        return data;
    }

    public void setData(List<DataDto> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
