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
public class Class {
    
    private String id;
    private String section;
    private String className;
    private int availableSeat;
    private Instructor aInstructor;

    public Class(String id, String section, String className, Instructor aInstructor) {
        this.id = id;
        this.section = section;
        this.className = className;
        this.aInstructor = aInstructor;
    }

    public String getId() {
        return id;
    }

    public String getSection() {
        return section;
    }

    public String getClassName() {
        return className;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public Instructor getaInstructor() {
        return aInstructor;
    }

    @Override
    public String toString() {
        return "Class id: " + id + ", Section: " + section + ", Class Name: " + className + ", Available Seat=" + availableSeat + ", Instructor: " + aInstructor.toString() ;
    }
    
    
}
