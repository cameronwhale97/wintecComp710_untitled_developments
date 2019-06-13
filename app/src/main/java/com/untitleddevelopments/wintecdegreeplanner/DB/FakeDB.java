package com.untitleddevelopments.wintecdegreeplanner.DB;

import java.util.ArrayList;




/**
 * Fake DB
 * -------------------------------------------------------------
 *
 * Fake DB to populate/fill your data structures, array, etc.
 * with dummy data for testing.
 *
 * Author: Navjot Singh
 */
public class FakeDB {

    /**
     * This func will fill student names in the given array list
     *
     * @param dataStudents      Array list to be filled with student names
     */
    public static void populateStudentNames(ArrayList<String> dataStudents) {
        dataStudents.add("Peter Avis");
        dataStudents.add("Oscar Armer");
        dataStudents.add("Navi Singh");
        dataStudents.add("Arthur Dykes");
        dataStudents.add("Rosie Downey");
        dataStudents.add("Roy Furse");
        dataStudents.add("Rocky Xyz");
        dataStudents.add("John Doe");
        dataStudents.add("Johny Abc");
    }

}//FakeDB
