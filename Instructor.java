/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package courseschedule;

/**
 *
 * @author Vuong
 */
public class Instructor {

    private String firstName;
    private String middleName;
    private String lastName;

    public Instructor() {
        this(null, null, null);
    }

    public Instructor(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return  "First Name: " + firstName + ", Middle Name: " + middleName + ", Last Name: " + lastName + "\n" ;
    }

}
