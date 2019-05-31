package com.untitleddevelopments.wintecdegreeplanner.DB;

public class StudentModule {
    private int student_ID;
    private int module_ID;
    private int completed;

    //constructor

    public StudentModule(int student_ID, int module_ID, int completed) {
        this.student_ID = student_ID;
        this.module_ID = module_ID;
        this.completed = completed;
    }
    //getter setter

    public int getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(int student_ID) {
        this.student_ID = student_ID;
    }

    public int getModule_ID() {
        return module_ID;
    }

    public void setModule_ID(int module_ID) {
        this.module_ID = module_ID;
    }

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
