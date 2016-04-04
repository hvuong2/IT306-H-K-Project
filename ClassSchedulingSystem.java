import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class ClassSchedulingSystem {

  public static void main(String[] args) throws Exception {
	  
	final int MAX_STUDENTS = 10000;
	  
	// All counselors at the school
  	HashMap counselors = new HashMap();
  	
  	// All students at the school
  	HashMap students = new HashMap();
  	
  	// All classes available at the school
  	List<Class> classes = new ArrayList();
  	
  	importCounselors(counselors);
  	importClasses(classes);
  	
  	boolean login = false;
  	boolean logout = false;
  	
  	do {
  		while (welcomeScreen()){
  			User currentUser = login(counselors, students);
			if (currentUser instanceof Counselor){
				while (!logout){
					counselorMenu(students, (Counselor) currentUser);
				}
			}
			else{
				while (!logout){
					studentMenu((Student)currentUser);
				}
			}
  		}
  	}
  	while (welcomeScreen());
  	
	User currentUser = login(counselors, students);
  	
	// TO DELETE LATER!!!!!!!!!!!!!
	//Student student = new Student();
  	//List<Class> searchedClass = searchForClass(classes, student);
  }
  
	public static void importCounselors(HashMap counselors) throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin Zuiker\\workspace\\TestImport\\src\\CounselorsTest.txt"));
	    String line = null;
	    while ((line = br.readLine()) != null) {
	    	
	    // Each value is separated by a ", "
	      String[] values = line.split(", ");
	      
	      // Create counselor (username, hashed password, name, age, gender, phone number)
	      Counselor aCounselor = new Counselor(values[0],values[1],values[2],Integer.parseInt(values[3]),values[4].charAt(0),values[5]);
	      
	      // Add counselor to collection of counselors
	      counselors.put(aCounselor.getUserName(), aCounselor);

	    }
	    br.close();

	}
	
	public static void importClasses(List classes) throws IOException{
	    BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin Zuiker\\workspace\\TestImport\\src\\Classes.txt"));
	    String line = null;
	    while ((line = br.readLine()) != null) {
	      String[] values = line.split(", ");
	      
	      // Creates Instructor using last three values (first name, middle name, last name)
	      Instructor aInstructor =  new Instructor(values[4],values[5],values[6]);
	      
	      // Creates class using id, section, name, available seats, Instructor
	      Class aClass = new Class(values[0], values[1], values[2], Integer.parseInt(values[3]),aInstructor);
	      
	      // Adds the class to the collection of classes
	      classes.add(aClass);
	    }
	    br.close();
	}
	
	public static User login(HashMap counselors, HashMap students) throws Exception {
		
		Object[] options = {"Counselor", "Student"}; 
		int userTypeNum = JOptionPane.showOptionDialog(null, "Select user type:", 
           		"Select User Type", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
           		null, options, options[0]);
		
		// Prompt for username
		String username = promptForUsername();
		
		// Prompt for password
		String password = promptForPassword();
		
		
		// Algorithm finds hash of the entered password
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		StringBuffer sb = new StringBuffer();
		for (byte b : digest) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		User aUser;
		
		// Selection was "Counselor"
		if (userTypeNum == 0){
			// Search hash map using 'username' as the key
			if (counselors.containsKey(username)){
				// If found, set to temporary User variable
				aUser = (Counselor) counselors.get(username);
				
				// Validates the hash password to the stored password
				if (aUser.getPassword().equals(sb.toString())){
					
					JOptionPane.showMessageDialog(null, "Login credentials accepted. Logging in...");
					return aUser;
				}
			}
		}
		// Selection was "Student"
		else {
			// Search hash map using 'username' as the key
			if (students.containsKey(username)){
				aUser = (Student) students.get(username);
				// Validates the hash password to the stored password
				if (aUser.getPassword().equals(sb.toString())){
					JOptionPane.showMessageDialog(null, "Login credentials accepted. Logging in...");
					return aUser;
				}
			}
		}
		
		// If credentials are invalid, return null
		return null;
	}
	
	public static List<Class> searchForClass(List<Class> classes, Student aStudent){
		List<Class> searchResults = new ArrayList<>();

		String search = JOptionPane.showInputDialog("Enter class to search for: ");
		
		// Make all letters upper case (e.g. eng will be changed to ENG)
		search = search.toUpperCase();
		// Retrieves all classes matching the searched criteria
		for (Class course : classes){
			/* Finds classes that match the searched class ID and makes sure the class
			 * has seats available
			 */
			if (course.getId().equals(search) && course.getSeatsAvailable() >= 1){
				searchResults.add(course);
			}
		}
		
		// Loop through all search results matching searched criteria
		for (Class course : searchResults){
			// Loop through all schedules of student
			for (Schedule schedule : aStudent.getSchedules()) {
				// Loop through all classes for each schedule to find classes already taken
				for (Class classTaken : schedule.getCourses()) {
					if (classTaken.getId().equalsIgnoreCase(course.getId()))
					{
						searchResults.remove(course);
					}
				}
			}
		}
		
			
			// Make sure seats are available
		return searchResults;
	}
	
	public static Student searchForStudent(HashMap students){
		// Stores search results (e.g. a Student or null)
		Student searchResult = new Student();
		
		String search = JOptionPane.showInputDialog("Enter student username to search for: ");
		
		// Converts username to all lowercase letters
		search = search.toLowerCase();
		
		// 
		if (students.containsKey(search)){
			searchResult = ((Student)(students.get(search)));
		}
		
		return searchResult;
	}
	
	public static void addStudent(HashMap students, HashMap counselors, int MAX_STUDENTS) throws Exception{
		if (students.size() == MAX_STUDENTS){
			JOptionPane.showMessageDialog(null, "Maximum number of students has been reached!"
					+ "Cannot add another student.");
		}
		else{
			String username = promptForUsername(students, counselors);
			String password = promptForPassword();
			String name = promptForName();
			int age = promptForAge();
			char gender = promptForGender();
			String phoneNumber = promptForPhoneNumber();
				
			Student aStudent = new Student(username, password, name, age, gender, phoneNumber);
			students.put(username, aStudent);
			JOptionPane.showMessageDialog(null,  "Student successfully added to the system!");
		}
	}
	
	public static String promptForUsername(HashMap students, HashMap counselors){
		boolean valid = false;
		String username = "";
		
		do{
			username = JOptionPane.showInputDialog("Enter username: ");
			username = username.toLowerCase();
			if (!students.containsKey(username) && !counselors.containsKey(username)){
				valid = true;
			}
		}
		while (!valid);
		
		return username;
	}

	public static String promptForPassword() throws Exception{
		boolean valid = false;
		String password = "";
		
		do{
			password = JOptionPane.showInputDialog("Enter password: ");
			
			if (!password.equals("")){
				valid = true;
				
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(password.getBytes());
				byte[] digest = md.digest();
				StringBuffer sb = new StringBuffer();
				for (byte b : digest) {
					sb.append(String.format("%02x", b & 0xff));
				}
				
				password = sb.toString();
			}
			if (!valid){
				JOptionPane.showMessageDialog(null, "Invalid password. Password cannot"
						+ "be empty! Please try again.");
			}
		}
		while (!valid);
		
		return password;
	}
	
	public static String promptForName(){
		boolean valid = false;
		String name = "";
		
		do{
			name = JOptionPane.showInputDialog("Enter name: ");
			if (!name.equals("")){
				valid = true;
			}
		}
		while (!valid);
		
		return name;
	}
	public static int promptForAge(){
		boolean valid = false;
		int age = 0;
		
		do{
			try {
				age = Integer.parseInt(JOptionPane.showInputDialog("Enter age: "));
				valid = true;
			}
			catch (NumberFormatException e){
				
			}
			if (!valid){
				JOptionPane.showMessageDialog(null, "Invalid age! Please try again.");
			}
		}
		while (!valid);
		
		return age;
	}
	
	public static char promptForGender(){
		char gender = '\u0000';
		Object[] options = {"Male", "Female"};   
		int genderMorF = JOptionPane.showOptionDialog(null, "Select gender:", 
                       		"Select Gender", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                       		null, options, options[0]);
		if (genderMorF == 0){
			gender = 'M';
		}
		else { gender = 'F'; }
		
		return gender;
	}
	
	public static String promptForPhoneNumber(){
		boolean valid = false;
		String phoneNumber = "";
		do{
			phoneNumber = JOptionPane.showInputDialog("Enter Phone Number (e.g. (555) 555-555: ");
			if (phoneNumber.matches("\\d{3}\\-\\d{3}-\\d{4}")){
				valid = true;
			}
		}
		while (!valid);
		
		return phoneNumber;
	}
	
	public static boolean welcomeScreen(){
		boolean login = false;
		
		Object[] options = {"Login", "Exit Program"};   
		int loginNum = JOptionPane.showOptionDialog(null, "Welcome to the Course Scheduling System."
				+ "What would you like to do?", 
                       		"Login or Exit Program", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                       		null, options, options[0]);
		
		if (loginNum == 0){
			login = true;
		}
		else {
			JOptionPane.showMessageDialog(null, "Thank you for using the Course Scheduling System!");
			System.exit(0);
		}
		
		System.out.println(login);
		return login;
	}
	
	public static void counselorMenu(HashMap students, Counselor currentUser){
	      
	      // Holds value of the menu choice selected
	      int menuChoice;
	      
	      do
	      {
	         // Stores menu choice input by the user
	         menuChoice = getMenuOptionCounselor();
	         Student aStudent;
	         // Depending on the users input, the task associated with their numerical input will be performed
	         switch(menuChoice)
	         {
	            case 1:
	            	viewBasicInformation(currentUser);
	               break;      
	            case 2:
	               
	               break;     
	            case 3:
	            	aStudent = searchForStudent(students);
	            	if (aStudent.equals(null)){
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	else{
	            		
	            	}
	               break;
	            case 4:
	            	aStudent = searchForStudent(students);
	            	if (aStudent.equals(null)){
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	else{
	            		
	            	}
	               break;
	            case 5:
	            	aStudent = searchForStudent(students);
	            	if (aStudent.equals(null)){
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	else{
	            		
	            	}
	               break;
	            case 6:
	            	aStudent = searchForStudent(students);
	            	if (aStudent.equals(null)){
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	else{
	            		
	            	}
	               break;
	            case 7:
	            	// Exits the entire program
	            	System.exit(0);
	            	break;
		               
	         }
	      }
	      // Repeats prompt until they quit
	      while (menuChoice != 7);
 }

	
	public static  void studentMenu(Student aStudent){

		// Holds value of the menu choice selected
		int menuChoice;
		  
		do
		{
			// Stores menu choice input by the user
			menuChoice = getMenuOptionStudent();
			 
			// Depending on the users input, the task associated with their numerical input will be performed
			switch(menuChoice)
			{
			    // Add a server to the network
				case 1:
			   
				break;
				// Print a report for a specific server          
				case 2:
			   
				break;  
				// Quits the program          
				case 3:
		           
					break;
				case 4:
		               
					break;
				case 5:
					// Logs out and returns to login screen
					return;
			     }
		  }
		  // Repeats prompt until they quit
		  while (menuChoice != 5);
 }
	
	public static int getMenuOptionCounselor(){
	      int menuChoice;
	      
	      do
	      {
	         try
	         {
	            // Prompts user to select an option from the menu
	            menuChoice = Integer.parseInt(JOptionPane.showInputDialog("Please select a numerical option from the list below: " +
	                                                                      "\n\n" +
	                                                                      "1. View basic information" + "\n" +
	                                                                      "2. Create a student’s schedule" + "\n" +
	                                                                      "3. View a student’s schedule"  + "\n" +
	                                                                      "4. Change a student’s schedule"  + "\n" +
	                                                                      "5. View student’s past schedule"  + "\n" +
	                                                                      "6. Add a student"));
	         }
	         //If the user does not provide a numeric value, an exception will be thrown
	         catch (NumberFormatException e)
	         {
	            menuChoice = 0;
	         }
	         // Displays an error if the numerical input does not match any of the options
	         if (menuChoice < 1 || menuChoice > 6)
	         {
	            JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a valid menu operation.");
	         }
	      }
	      // Continues to repeat if the user does not select an option from the list
	      while (menuChoice < 1 || menuChoice > 6);
	      
	      // Returns the menu choice selected
	      return menuChoice;
	}
	
	public static int getMenuOptionStudent(){
	      int menuChoice;
	      
	      do
	      {
	         try
	         {
	            // Prompts user to select an option from the menu
	            menuChoice = Integer.parseInt(JOptionPane.showInputDialog("Please select a numerical option from the list below: " +
	                                                                      "\n\n" +
	                                                                      "1. View basic information" + "\n" +
	                                                                      "2. Create a schedule" + "\n" +
	                                                                      "3. View their current schedule" + "\n" +
	                                                                      "4. View past schedule(s)"));
	         }
	         //If the user does not provide a numeric value, an exception will be thrown
	         catch (NumberFormatException e)
	         {
	            menuChoice = 0;
	         }
	         // Displays an error if the numerical input does not match any of the options
	         if (menuChoice < 1 || menuChoice > 4)
	         {
	            JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a valid menu operation.");
	         }
	      }
	      // Continues to repeat if the user does not select an option from the list
	      while (menuChoice < 1 || menuChoice > 4);
	      
	      // Returns the menu choice selected
	      return menuChoice;
	}
	
	public static void viewBasicInformation(User user){
		JOptionPane.showMessageDialog(null, user.toString());
	}
	
	public static void viewCurrentSchedule(Student aStudent){
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int previousYear = (currentYear - 1);
		
		// Concatenates the two years (e.g. 2015-2016)
		String yearsToSearch = previousYear + "-" + currentYear;
		
		String scheduleOutput = "*** Current Schedule ***" + "\n";
				
		// Determines if the user has at least 1 schedule
		if (aStudent.getSchedules().size() == 0){
			// No schedules error message
			JOptionPane.showMessageDialog(null, "Student does not have a current"
												+ "schedule!");
		}
		else if (aStudent.getSchedules().size() == 1){
			// Displays current schedule
			JOptionPane.showMessageDialog(null, (aStudent.getSchedules().get(0)));
		}
		else {
			// Loops through all schedules the student has
			for (Schedule schedule : aStudent.getSchedules()){
				// Finds the schedule with the current year
				if (schedule.getSchoolYear().equals(yearsToSearch)){
					// Displays current schedule
					JOptionPane.showMessageDialog(null, (schedule));
					break;
				}
			}
		}
	}
	
	public static void viewPastSchedules(Student aStudent){
		String pastSchedulesOutput = "*** Past Schedules*** \n";
		
		// Determines if the user has at least 1 schedule
		if (aStudent.getSchedules().size() == 0){
			// No schedules error message
			JOptionPane.showMessageDialog(null, "Student does not have any"
												+ "schedules!");
		}
		else if (aStudent.getSchedules().size() == 1){
			// Current schedule only error message
			JOptionPane.showMessageDialog(null, "Student only has a current"
					+ "schedule! Please use the View Current Schedule feature"
					+ "to view the current schedule.");
		}
		else {
			
			// Loops through all schedules the student has up to the current schedule
			for (int i = 0; i < (aStudent.getSchedules().size()-1); i++){
				pastSchedulesOutput += aStudent.getSchedules().get(i) + "\n\n";
			}
			
			// Displays all previous schedules
			JOptionPane.showMessageDialog(null, pastSchedulesOutput);
		}
	}
	
	public static String promptForUsername(){
		String username;
		
		// Prompt for username
		do {
			username = JOptionPane.showInputDialog("Please enter username:");
		}
		while (username.equals(""));
		
		return username;
	}
	
	public Class displayClassSearchResults(ArrayList classes)
	{
		String searchResultsDisplay = "*** Search Results *** \n\n";
		for (int i = 0; i < classes.size(); i++){
			searchResultsDisplay += i+1 + ". " + ((Class)classes.get(i)).getId() + "\n";
			
		}
		
		searchResultsDisplay += "0. Cancel \n\n" +
								"Please select a course you wish to add.";
		
		int courseToAddNum = Integer.parseInt(JOptionPane.showInputDialog(searchResultsDisplay));
		
		Class classToAdd;
		
		if (courseToAddNum != 0){
			classToAdd = (Class)classes.get(courseToAddNum-1);
		}
		else{
			classToAdd = null;
		}
		
		return classToAdd;
	}

  }
