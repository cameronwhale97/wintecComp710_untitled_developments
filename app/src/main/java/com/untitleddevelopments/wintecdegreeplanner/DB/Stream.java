package com.untitleddevelopments.wintecdegreeplanner.DB;

public class Stream {
    private int stream_ID;
    private String name;
    private String iconURI;

    //constructor
    public Stream(int stream_ID, String name, String iconURI) {
        this.stream_ID = stream_ID;
        this.name = name;
        this.iconURI = iconURI;
    }

    //getter setter

    public int getStream_ID() {
        return stream_ID;
    }

    public void setStream_ID(int stream_ID) {
        this.stream_ID = stream_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
    }
}
