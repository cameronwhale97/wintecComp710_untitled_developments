package com.untitleddevelopments.wintecdegreeplanner.DB;

public class ModuleStream {
    private int module_ID;
    private int stream_ID;

    //constructor
    public ModuleStream(int module_ID, int stream_ID) {
        this.module_ID = module_ID;
        this.stream_ID = stream_ID;
    }
    //getter setter

    public int getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(int module_ID) {
        this.module_ID = module_ID;
    }

    public int getStream_ID() {
        return stream_ID;
    }

    public void setStream_ID(int stream_ID) {
        this.stream_ID = stream_ID;
    }
}
