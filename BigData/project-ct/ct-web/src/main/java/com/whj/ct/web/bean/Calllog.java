package com.whj.ct.web.bean;

public class Calllog {
    private int id;
    private int telid;
    private int dateid;
    private int sumcall;
    private int sumduration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelid() {
        return telid;
    }

    public void setTelid(int telid) {
        this.telid = telid;
    }

    public int getDateid() {
        return dateid;
    }

    public void setDateid(int dateid) {
        this.dateid = dateid;
    }

    public int getSumcall() {
        return sumcall;
    }

    public void setSumcall(int sumcall) {
        this.sumcall = sumcall;
    }

    public int getSumduration() {
        return sumduration;
    }

    public void setSumduration(int sumduration) {
        this.sumduration = sumduration;
    }

    public Calllog(int id, int telid, int dateid, int sumcall, int sumduration) {

        this.id = id;
        this.telid = telid;
        this.dateid = dateid;
        this.sumcall = sumcall;
        this.sumduration = sumduration;
    }
}
