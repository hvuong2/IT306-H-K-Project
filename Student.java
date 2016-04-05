/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseschedule;

import java.util.*;

public class Student extends User {

    private ArrayList<Schedule> schedules;
    private String classStanding;

    public Student(String userName, String password, String name, int age, char gender, String phoneNumber, String zipCode) {
        super(userName, password, name, age, gender, phoneNumber, zipCode);
        this.schedules = new ArrayList<>();
        classStanding = "FRESHMAN";

    }

    public void setClassStanding(String classStanding) {
        this.classStanding = classStanding;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public String toString() {
        return super.toString() + "Student standing: " + classStanding;
    }
}
