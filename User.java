package courseschedule;

import java.util.*;

public abstract class User {

    private String userName;
    private String password;
    private String name;
    private int age;
    private char gender;
    private String phoneNumber;
    private String zipCode;

    public User(String userName, String password, String name, int age, char gender, String phoneNumber, String zipCode) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public char getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "Username: " + userName + ", Name:" + name + ", Age: " + age + ", Gender: " + gender + ", PhoneNumber: " + phoneNumber + ", Zip Code: " + zipCode + "\n";
    }

    public boolean setName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (Character.isDigit(name.charAt(i))) {
                throw new IllegalArgumentException("Invalid User Name (no digit allowed)");
            }
        }
        this.name = name;
        return true;
    }

    public boolean setAge(int aAge) {
        if (aAge < 14 || aAge >= 20) {
            throw new IllegalArgumentException();
        }
        this.age = aAge;
        return true;
    }

    public boolean setUserName(String aUserName) {
        String convertedUserName = aUserName.toLowerCase();
        for (int i = 0; i < convertedUserName.length(); i++) {
            if (Character.isDigit(convertedUserName.charAt(i))) {
                throw new IllegalArgumentException("Invalid User Name (no digit allowed)");
            }
        }
        this.userName = convertedUserName;
        return true;
    }

    public boolean setPhoneNumber(String aPhoneNumber) {
        if (aPhoneNumber.matches("\\d{3}\\-\\d{3}-\\d{4}")) {
            this.phoneNumber = aPhoneNumber;
            return true;
        }
        throw new IllegalArgumentException("Invalid Phone Number");
    }

    public boolean setGender(char aGender) {
        if (aGender != 'M' && aGender != 'F') {
            throw new IllegalArgumentException("Invalid Gender input");
        }
        this.gender = aGender;
        return true;
    }

    public void setZipCode(String aZipCode) {
        if (aZipCode.length() != 5) {
            throw new IllegalArgumentException("Invalid Zip Code (must be exactly 5 digit)");
        }
        for (int i = 0; i < aZipCode.length(); i++) {
            if (Character.isDigit(aZipCode.charAt(i)) == false) {
                throw new IllegalArgumentException("Invalid Zip Code (must be only number) ");
            }
        }
        this.zipCode = aZipCode;

    }

    public boolean saveSchedule(Student aStudentReference, Schedule newlyCreatedSchedule) {
        ArrayList<Schedule> scheduleList = aStudentReference.getSchedules();
        boolean isSave = scheduleList.add(newlyCreatedSchedule);

        //update class standing;
        String standing = "";
        int numSchedule = scheduleList.size();
        if (numSchedule == 1) {
            standing = "FRESH MAN";
        }
        if (numSchedule == 2) {
            standing = "SOPHOMORE";
        }
        if (numSchedule == 3) {
            standing = "JUNIOR";
        }
        if (numSchedule == 4) {
            standing = "SENIOR";
        }

        aStudentReference.setClassStanding(standing);

        return isSave;
    }

    public boolean addClass(Class aClass, Schedule tempSchedule) {
        ArrayList aClassArrayListReference = tempSchedule.getCourses();
        boolean isAdd = aClassArrayListReference.add(aClass);
        return isAdd;
    }

    public String viewPastSchedule(Schedule aScheduleReference) {
        String print = "";
        ArrayList aClassArrayListReference = aScheduleReference.getCourses();
        for (int i = 0; i < aClassArrayListReference.size(); i++) {
            print += aClassArrayListReference.get(i).toString() + "\n";
        }
        return print;

    }

}
