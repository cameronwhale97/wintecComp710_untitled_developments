package com.untitleddevelopments.wintecdegreeplanner.DB;

public class PreReq {
    private int preReq_ID;
    private String Code;

    //constructor

    public PreReq(int preReq_ID, String name) {
        this.preReq_ID = preReq_ID;
        Code = name;
    }

    public int getPreReq_ID() {
        return preReq_ID;
    }

    public void setPreReq_ID(int preReq_ID) {
        this.preReq_ID = preReq_ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String name) {
        Code = name;
    }
}
