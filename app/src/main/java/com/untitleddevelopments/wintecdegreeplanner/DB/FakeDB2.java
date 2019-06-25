package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.content.ContentValues;
import android.util.Log;
import java.util.ArrayList;
import com.untitleddevelopments.wintecdegreeplanner.DB.Student;
import static android.content.ContentValues.TAG;

public class FakeDB2 {
    private static final String TAG = "FakeDB2";
    public static void InsertStudents() {
        Log.d(TAG, "InsertStudents: Creating bogus students");
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("Peter1","Jackson","1654123",1,"12/06/2019","",1));
        students.add(new Student("Mike2","Smith","2654123",2,"12/06/2018","",1));
        students.add(new Student("Geoff3","Genner","3654123",3,"01/02/2019","",1));
        students.add(new Student("Navi4","Singh","4654123",4,"12/04/2019","",1));
        students.add(new Student("Maria5","del Mundo","5654123",4,"12/05/2019","",1));
        students.add(new Student("Jonah6","Dearden","6654123",3,"12/06/2019","",1));
        students.add(new Student("Cameron7","Whale","7654123",2,"12/07/2019","",1));
        students.add(new Student("Jacinda8","Adern","8654123",1,"12/08/2019","",0));
        students.add(new Student("Winstone9","Peters","9654123",1,"12/09/2019","",0));
        for(int i = 0; i < students.size(); i++){
            boolean inserted;
            ContentValues contentStudent = new ContentValues();
            contentStudent.put(DBHelper.STUDENT_FIRSTNAME, students.get(i).getFirstname() );
            contentStudent.put(DBHelper.STUDENT_SURNAME,students.get(i).getSurname() );
            contentStudent.put(DBHelper.STUDENT_STUDENTID,students.get(i).getStudentID() );
            contentStudent.put(DBHelper.STUDENT_PHOTOURI, students.get(i).getPhotoURI());
            contentStudent.put(DBHelper.STUDENT_STARTDATE,students.get(i).getStartDate() );
            contentStudent.put(DBHelper.STUDENT_STATUS, students.get(i).getStatus());
            contentStudent.put(DBHelper.STUDENT_STREAM_ID, students.get(i).getStream_ID());
            DBManager.getInstance().openDatabase();
            inserted = DBManager.getInstance().insert(DBHelper.TBL_STUDENT, contentStudent);
            String myMsg = inserted ? " Inserted Yay!" : " Not inserted Bohoo";
            Log.e(TAG, students.get(i).getFullName() + myMsg);
        }
    }
}
