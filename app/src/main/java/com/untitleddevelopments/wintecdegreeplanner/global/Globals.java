package com.untitleddevelopments.wintecdegreeplanner.global;

import com.untitleddevelopments.wintecdegreeplanner.DB.Module;
import com.untitleddevelopments.wintecdegreeplanner.DB.SPMod;
import com.untitleddevelopments.wintecdegreeplanner.DB.Stream;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import com.untitleddevelopments.wintecdegreeplanner.ui.StuPlan.PageViewModel;

public class Globals {

    private static int student_ID;
    private static int year;
    private static int stream_ID;
    private static int module_ID;

    private static Student student;
    private static Module module;
    private static Stream stream;
    private static SPMod spMod;


    private static PageViewModel pageViewModel;

    public static synchronized void setStudent_ID(int student_ID) { Globals.student_ID = student_ID;    }
    public static synchronized int getStudent_ID() {
        return Globals.student_ID;
    }

    public static synchronized void setYear(int year) {
        Globals.year = year;
    }
    public static synchronized int getYear() {
        return Globals.year;
    }

    public static synchronized void setStream_ID(int stream_ID) {
        Globals.stream_ID = stream_ID;
    }
    public static synchronized int getStream_ID() {
        return Globals.stream_ID;
    }

    public static synchronized void setModule_ID(int module_ID) {
        Globals.module_ID = module_ID;
    }
    public static synchronized int getModule_ID() {
        return Globals.module_ID;
    }

    public static synchronized void setStudent(Student student) {
        Globals.student = student;
    }
    public static synchronized Student getStudent() {
        return Globals.student;
    }

    public static synchronized void setModule(Module module) {
        Globals.module = module;
    }
    public static synchronized Module getModule() {
        return Globals.module;
    }

    public static synchronized void setStream(Stream stream) {
        Globals.stream = stream;
    }
    public static synchronized Stream getStream() {
        return Globals.stream;
    }

    public static synchronized void setSPMod(SPMod spMod) {
        Globals.spMod = spMod;
    }
    public static synchronized SPMod getSPMod() {
        return Globals.spMod;
    }


}//Globals
