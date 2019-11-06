package com.example.vet;

public class appointModel {

    String date;

    public appointModel(String name) {
        this.name = name;
    }

    String name;
    String frmTime;
    String toTime;



    String status;




    String id;

    public appointModel(String name,String date,String frmTime, String toTime,String id,String status) {
        this.date = date;
        this.name = name;
        this.frmTime = frmTime;
        this.toTime = toTime;
        this.id = id;
        this.status=status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrmTime() {
        return frmTime;
    }

    public void setFrmTime(String frmTime) {
        this.frmTime = frmTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
