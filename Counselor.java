/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseschedule;

import java.util.ArrayList;

public class Counselor extends User {

    public Counselor(String userName, String password, String name, int age, char gender, String phoneNumber, String zipCode) {
        super(userName, password, name, age, gender, phoneNumber, zipCode);
    }

    public boolean remove(Student aStudentReference, Class aClass, Schedule currentSchedule) {
        ArrayList aClassArrayListReference = currentSchedule.getCourses();
        boolean aTemp = aClassArrayListReference.remove(aClass);
        return aTemp;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
