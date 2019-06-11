package com.untitleddevelopments.wintecdegreeplanner.DB;

public class SPMod {
    private int module_ID;
    private String code;
    private String name;
    private String preReqs;

    //constructor full
    public SPMod(int module_ID, String code, String name, String preReqs) {
        this.module_ID = module_ID;
        this.code = code;
        this.name = name;
        this.preReqs = preReqs;
    }

    public int getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(int module_ID) {
        this.module_ID = module_ID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreReqs() {
        return preReqs;
    }

    public void setPreReqs(String preReqs) {
        this.preReqs = preReqs;
    }
}
