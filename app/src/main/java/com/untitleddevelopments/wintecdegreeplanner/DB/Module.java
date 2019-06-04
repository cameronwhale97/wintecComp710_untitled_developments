package com.untitleddevelopments.wintecdegreeplanner.DB;

public class Module {
    private int module_ID;
    private String code;
    private String name;
    private String description;
    private int NZQALevel;
    private int NZQACredits;
    private String coReq;

    //constructor without _ID
    public Module(int module_ID, String code, String name, String description, int NZQALevel, int NZQACredits, String coReq) {
        this.module_ID = module_ID;
        this.code = code;
        this.name = name;
        this.description = description;
        this.NZQALevel = NZQALevel;
        this.NZQACredits = NZQACredits;
        this.coReq = coReq;
    }

    @Override
    public String toString() {
        return "Module{" +
                "module_ID=" + module_ID +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", NZQALevel=" + NZQALevel +
                ", NZQACredits=" + NZQACredits +
                ", coReq='" + coReq + '\'' +
                '}';
    }

    //getter setter
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNZQALevel() {
        return NZQALevel;
    }

    public void setNZQALevel(int NZQALevel) {
        this.NZQALevel = NZQALevel;
    }

    public int getNZQACredits() {
        return NZQACredits;
    }

    public void setNZQACredits(int NZQACredits) {
        this.NZQACredits = NZQACredits;
    }

    public String getCoReq() {
        return coReq;
    }

    public void setCoReq(String coReq) {
        this.coReq = coReq;
    }
}
