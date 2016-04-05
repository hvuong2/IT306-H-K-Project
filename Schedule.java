/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseschedule;

import java.util.ArrayList;

/**
 *
 * @author Vuong
 */
public class Schedule {

    private ArrayList<Class> courses;
    private String schoolYear;
    
    public Schedule(String aSchoolYear) {
        courses = new ArrayList<>();
        schoolYear = aSchoolYear;
    }

    public ArrayList<Class> getCourses() {
        return courses;
    }

    public String getSchoolYear() {
        return schoolYear;
    }
    
    

}
