package com.example.goalwise.model;

import java.util.List;

public class DataDto {

    private String fundname;
    private Long minsipamount;
    private Long minsipmultiple;
    private List<Integer> sipdates;

    public String getFundname() {
        return fundname;
    }

    public void setFundname(String fundname) {
        this.fundname = fundname;
    }

    public Long getMinsipamount() {
        return minsipamount;
    }

    public void setMinsipamount(Long minsipamount) {
        this.minsipamount = minsipamount;
    }

    public Long getMinsipmultiple() {
        return minsipmultiple;
    }

    public void setMinsipmultiple(Long minsipmultiple) {
        this.minsipmultiple = minsipmultiple;
    }

    public List<Integer> getSipdates() {
        return sipdates;
    }

    public void setSipdates(List<Integer> sipdates) {
        this.sipdates = sipdates;
    }
}
