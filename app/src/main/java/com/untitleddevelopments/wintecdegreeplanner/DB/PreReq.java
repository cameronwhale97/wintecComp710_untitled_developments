package com.untitleddevelopments.wintecdegreeplanner.DB;

public class PreReq {
    private int module_ID;
    private int preReq_ID;

    //constructor

    public PreReq(int module_ID, int preReq_ID) {
        this.module_ID = module_ID;
        this.preReq_ID = preReq_ID;
    }
    //getter setter

    public int getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(int module_ID) {
        this.module_ID = module_ID;
    }

    public int getPreReq_ID() {
        return preReq_ID;
    }

    public void setPreReq_ID(int preReq_ID) {
        this.preReq_ID = preReq_ID;
    }
}
