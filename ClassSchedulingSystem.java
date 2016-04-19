/* Name: Kevin Zuiker and Hong Vuong
 * 
 * Description: This program is a class scheduling system. It will have two different types of
 * users: Counselor and Student. Both of these users extend the abstract User class.
 * Each user will log into the system with a username and password.
 * Plain-text passwords are not stored, but rather hashed passwords using the MD-5 algorithm.
 * Both a student and a counselor share similar basic information such username, password, name,
 * age (integers only), gender (M or F), phone number (in ###-###-### format), and a zip code 
 * (5-digit only). 
 * 
 *	A student can view their basic information which will display their attributes (username, 
 *	name, age, gender, phone number and zip code. They also have an additional attribute called
 *	class standing which is directly related to how many schedules that they currently have.
 *	For example, if they have 0-1 schedules, they are in considered a freshman, 2 schedules they
 *	are considered a sophomore, etc.
 *
 *	A student can also create a current schedule but cannot edit an already created schedule. 
 *	Schedules must be 7 classes exactly. A student can also search for a class, but only
 *	add classes to their schedule dependent upon their class standing. For instance, a freshman
 *	can only sign up for 100 level courses.
 *
 *	In addition, a student can view their current schedule. The program will determine the 
 *	current year and search through a student's schedules.
 *
 *	Lastly, a student can view past schedules. A current schedule is not considered a past 
 *	schedule. The software will cycle through all schedules the student has, if any, to determine
 *	if any past schedules exist. If so, they will be displayed.
 *
 *	A counselor is similar to an administrator. They can perform all actions that a student can.
 *	For example, they can also view basic information and they have the same characteristics 
 *	of a student except they do not have a class standing.
 *
 *	A counselor can create a schedule for a student. Prior to creating a schedule, they will be
 *	prompted to search for a student. If found, they can continue creating a schedule if a 
 *	current schedule does not yet exist.
 *
 *	A counselor can also view the past schedule of a student and view the current schedule of
 *	a student. Again, they must first search for a student. All other processes remain the same.
 *
 *	Counselors are also capable of creating student users. The system will ensure that
 *	additional students are able to be added. The counselor will submit all required information
 *	needed to create a user (username, password, name, age, gender, phone number, zip code)
 *
 *	All users can log out from the main menu and return back to the welcome screen.
 *
 *	When the program initially loads, a list of classes and their details will be read from
 * 	a file and stored into an arraylist. A list of couselor users and their information
 * 	will also be read from a file and stored in a HashMap.
 */




import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class ClassSchedulingSystem {

  public static void main(String[] args) throws Exception {
	  
	final int MAX_STUDENTS = 10000;
	  
	// All counselors at the school
  	HashMap counselors = new HashMap<>();
  	
  	// All students at the school
  	HashMap students = new HashMap<>();
  	
  	/* Validate max students error message
  	for (int i = 0; i < 9999; i++){
  		String username = "hong" + i;
  	  	Student testStudent = new Student(username, "88163c52fdb7520d2da5295dcb52bff0", "hong", 16, 'M', "703-545-5555", "20120");
  	  	students.put(testStudent.getUserName(), testStudent);
  	}
  	*/
  	
  	/* SECTION TO REMOVE
  	 *      BELOW
  	 * 
  	 */
  	// HARDCODED STUDENT -- MUST REMOVE OR TAKE OUT PRIOR TO SUBMISSION!
  	Student testStudent = new Student("hong", "88163c52fdb7520d2da5295dcb52bff0", "hong", 16, 'M', "703-545-5555", "20120");
  	students.put(testStudent.getUserName(), testStudent);
  	// All classes available at the school
  	List<Class> classes = new ArrayList<>();
  	
  	importCounselors(counselors);
  	importClasses(classes);
  	
  	// Logout flag
  	boolean logout = false;
  	
  	// Current user who will log into the system
  	User currentUser;
  	
  	do{
  		while (welcomeScreen()){
  			// Repeat login until user enters valid credentials
  				logout = false;
			try{
				currentUser = login(counselors, students);
				if (currentUser == null){
					break;
				}
			}
			catch(NullPointerException e){
				break;
			}
			
			if (currentUser instanceof Counselor){
				while (!logout){
					logout = counselorMenu(students, counselors, (Counselor) currentUser, classes, MAX_STUDENTS, logout);
					if (logout == true){ 
						JOptionPane.showMessageDialog(null, "You are now being logged out...");
						break; }
				}
			}
			else{
				while (!logout){
					logout = studentMenu(students, counselors, (Student)currentUser, classes, logout);
					if (logout == true){ 
						JOptionPane.showMessageDialog(null, "You are now being logged out...");
						break; }
				}
			}
  		}
  	}
  	while (!logout);
 
  }

	/* importCounselors: Imports all counselors users and their details from a file into an array
	 * @param counselors: HashMap where counselor users will be stored
	 */

	public static void importCounselors(HashMap counselors) throws IOException{
	    try{
	    	// Where to read counselors from file
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin Zuiker\\workspace\\ClassSchedulingSystem\\src\\Counselors.txt"));
		    String line = null;
		    while ((line = br.readLine()) != null) {
		    	
		    // Each value is separated by a ", "
		      String[] values = line.split(", ");
		      
		      // Create counselor (username, hashed password, name, age, gender, phone number)
		      Counselor aCounselor = new Counselor(values[0],values[1],values[2],Integer.parseInt(values[3]),values[4].charAt(0),values[5], values[6]);
		      
		      // Add counselor to collection of counselors
		      counselors.put(aCounselor.getUserName(), aCounselor);
	
		    }
		    br.close();
		    JOptionPane.showMessageDialog(null, "Counselors successfully imported from file.");
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Could not import counselors from file!");
		}
	}

	/* importClasses: Imports all classes their details from a file into an array
	 * @param classes: List of classes where they will be stored
	 */
	public static void importClasses(List<Class> classes) throws IOException{
	    
		try { 
			// Where to read classes from file
			BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Kevin Zuiker\\workspace\\ClassSchedulingSystem\\src\\Classes.txt"));
		    String line = null;
		    while ((line = br.readLine()) != null) {
		      String[] values = line.split(", ");
		      
		      // Creates Instructor using last three values (first name, middle name, last name)
		      Instructor aInstructor =  new Instructor(values[4],values[5],values[6]);
		      
		      // Creates class using id, section, name, available seats, Instructor
		      Class aClass = new Class(values[0], values[1], values[2], Integer.parseInt(values[3]), aInstructor);
		      
		      // Adds the class to the collection of classes
		      classes.add(aClass);
		    }
		    JOptionPane.showMessageDialog(null, "Classes successfully imported from file.");
		    br.close();
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "Could not import classes from file!");
		}
	}

	/* login: Allows users to login to the system
	 * @param counselors: HashMap of counselor users
	 * @param students: HashMap of student users
	 * @return User: Returns the user who logged in (e.g. Student user or Counselor user)
	 */
	public static User login(HashMap counselors, HashMap students) throws Exception {
		// Valid flag
		boolean valid = false;
		
		// Buttons the user can select from
		Object[] options = {"Counselor", "Student"}; 
		
		// Prompt user to select their user type (Counselor or Student)
		int userTypeNum = JOptionPane.showOptionDialog(null, "Select user type:", 
           		"Select User Type", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
           		null, options, options[0]);
		
		// Prompt for username
		String username = promptForUsernameLogin();
		
		// Sets the username to lowercase
		username = username.toLowerCase();
		
		// Prompt for password
		String password = promptForPasswordLogin();

		// Temporary user
		User aUser;
		
		// Selection was "Counselor"
		if (userTypeNum == 0){
			// Search hash map using 'username' as the key
			if (counselors.containsKey(username)){
				// If found, set to temporary User variable
				aUser = (Counselor) counselors.get(username);
				// Validates the hash password to the stored password
				if (aUser.getPassword().equals(password)){
					// Success message
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
				if (aUser.getPassword().equals(password)){
					// Success message
					JOptionPane.showMessageDialog(null, "Login credentials accepted. Logging in...");
					return aUser;
				}
			}
		}
		
		// If credentials are invalid, error message and return null
		JOptionPane.showMessageDialog(null, "Invalid login credentials! Please try again.");
		return null;
	}
	
	/* searchForClass: Allows user to search for a class
	 * @param classes: List of all available classes offered at the school
	 * @param aStudent: Student that the searching is being done for
	 * @param scheduleInProgress: The schedule that is currently being created/edited
	 * @return List<Class>: Returns the user who logged in (e.g. Student user or Counselor user)
	 */
	public static List<Class> searchForClass(List<Class> classes, Student aStudent, Schedule scheduleInProgress){
		// Will be used to hold at search result values
		List<Class> searchResults = new ArrayList<>();

		// What is being searched
		String search;
		do {
			// Prompt user for search criteria
			search = JOptionPane.showInputDialog("Enter class to search for: ");
			if (search == null){
				// User hits cancel button
				throw new IllegalArgumentException();
			}
			if (search.equals("")){
				// Error message
				JOptionPane.showMessageDialog(null, "Your search keyword must not be empty!");
			}

		}
		// Repeat until the user has entered a value other than an empty string
		while (search.equals(""));
		
		// Make all letters upper case (e.g. eng will be changed to ENG)
		search = search.toUpperCase();
		// Retrieves all classes matching the searched criteria
		for (Class course : classes){
			/* Finds classes that match the searched class ID and makes sure the class
			 * has seats available
			 */
			if (course.getId().contains(search) && course.getAvailableSeat() >= 1){
				searchResults.add(course);
			}
		}
		
		//Holds values of class to be removed
		List<Class> classesToBeRemovedFromSearchResults = new ArrayList<>();
		
		// Ensures there were some matched results before proceeding
		if ( searchResults.size() > 0) {
			System.out.println(searchResults.size());
			// Loop through all search results matching searched criteria
			for (Class course : searchResults){
				// Loop through all schedules of student
				if (aStudent.getSchedules().size() > 0){
					for (Schedule schedule : aStudent.getSchedules()) {
						// Loop through all classes for each schedule to find classes already taken
						for (Class classTaken : schedule.getCourses()) {
							// Determines if the class Id's match
							if (classTaken.getId().equalsIgnoreCase(course.getId()))
							{
								// Add class to the removal list
								classesToBeRemovedFromSearchResults.add(course);
							}
						}
					}
				}
				// Determines if the temporary schedule has any added classes
				if (scheduleInProgress.getCourses().size() > 0){
					// Loop through all classes in the temporary schedule
					for (Class courseAlreadyAdded : scheduleInProgress.getCourses()){
						// Determine if class Id's match
						if (courseAlreadyAdded.getId().equalsIgnoreCase(course.getId()))
						{
							// Add class to the removal list
							classesToBeRemovedFromSearchResults.add(course);
							System.out.println("Added class to be removed: Size: " + classesToBeRemovedFromSearchResults.size());
						}
					}
				}
			}
			
			// Determine if there were any classes added to the removal list
			if (classesToBeRemovedFromSearchResults.size() > 0) {
				// Loop through all classes to be removed
				for (int i = 0; i < classesToBeRemovedFromSearchResults.size(); i++) {
					// Remove the class
					searchResults.remove(classesToBeRemovedFromSearchResults.get(i));
					System.out.println("Search results size" + searchResults.size());
				}
			}
		}
		
		// Determine if there are still search results
		if (searchResults.size() > 0) {
			// Determine class standing number (e.g. Freshman = 0 or 1, Sophomore = 2, etc.)
			int classStandingNum = classStandingToNum(aStudent);
			// Remove all classes that a user is not eligible to sign up for
			searchResults = removeIneligbleClassStandingClasses(searchResults, classStandingNum);
		}
			
		// Return search results
		return searchResults;
	}

	/* searchForStudent: Allows user to search for a class
	 * @param students: HashMap of all the students to search
	 * @return Student: Returns a student object if student was found or null if student
	 * 					was not found
	 */
	public static Student searchForStudent(HashMap students){
		// Stores search result (e.g. a Student or null)
		Student searchResult = new Student();
		String search = "";
		
		// Valid flag
		boolean valid = false;
		do{
			search = JOptionPane.showInputDialog("Enter student username to search for: ");
			if (search == null){
				// User hits cancel button
				throw new IllegalArgumentException();
			}
			else if ((!search.equals(null)) && search.equals("")){
				// Error message if value is an empty string
				JOptionPane.showMessageDialog(null, "You must enter a value!");
			}
			else {
				// Sets valid flag
				valid = true;
			}
		}
		// Repeats until the value is not empty
		while (!valid);
		
		// Converts username to all lowercase letters
		search = search.toLowerCase();
		
		// Determines if any students already exist, and if the username exists
		if (students.size() > 0 && students.containsKey(search)){
			// Set the student as the search result
			searchResult = ((Student)(students.get(search)));
		}
		// Sets to null if student was not found
		else { 
			throw new NullPointerException();
		}
		
		// Return the student
		return searchResult;
	}
	/* addStudent: Creates a student user for the program
	 * @param students: Student users in the system
	 * @param counselors: Counselor users in the system
	 * @param MAX_STUDENTS: Maximum number of students that can be added to the system
	 */
	public static void addStudent(HashMap students, HashMap counselors, int MAX_STUDENTS) throws Exception{
		// Determines if there is room left to add additional students
		if (students.size() == MAX_STUDENTS){
			// Error message
			JOptionPane.showMessageDialog(null, "Maximum number of students has been reached!"
					+ "Cannot add another student.");
		}
		else{
			Student studentToCreate = new Student();
			promptAndSetUsername(studentToCreate, students, counselors);
			promptAndSetPassword(studentToCreate);
			promptAndSetName(studentToCreate);
			promptAndSetAge(studentToCreate);
			promptAndSetGender(studentToCreate);
			promptAndSetPhoneNumber(studentToCreate);
			promptAndSetZipCode(studentToCreate);
			
			// Add the student with the username as the key, and the Student as the value
			students.put(studentToCreate.getUserName(), studentToCreate);
			// Success message
			JOptionPane.showMessageDialog(null,  "Student successfully added to the system!");
		}
	}

	/* promptForUsernameLogin: Prompts for username at the login screen
	 * @return: String: Returns the username entered by the user
	 */
	public static String promptForUsernameLogin(){
		// Holds the username
		String username = "";
		
		do{
			// Prompts and stores the username
			username = JOptionPane.showInputDialog("Enter username: ");
			if (username.equals("")){
				// Error message
				JOptionPane.showMessageDialog(null, "You must enter a value!");
			}
		}
		// Repeats until a value was entered
		while (username.equals(""));
		
		// Returns the entered username
		return username;
	}

	/* promptForPasswordLogin: Prompts for password at the login screen
	 * @return: String: Returns the hashed password entered by the user
	 */
	public static String promptForPasswordLogin() throws NoSuchAlgorithmException{
		boolean valid = false;
		String password = "";
		
		do{
			try{
				// Prompt for password
				password = JOptionPane.showInputDialog("Enter password: ");
				
				if (!password.equals("")){
					// Set valid flag to true
					valid = true;
					
					// Hashes password using MD5
					MessageDigest md = MessageDigest.getInstance("MD5");
					md.update(password.getBytes());
					byte[] digest = md.digest();
					StringBuffer sb = new StringBuffer();
					for (byte b : digest) {
						sb.append(String.format("%02x", b & 0xff));
					}
					
					// Converts hashed byte password into a string
					password = sb.toString();
				}
				// Error message if password was not valid
				if (!valid){
					JOptionPane.showMessageDialog(null, "Invalid password. Password cannot"
							+ "be empty! Please try again.");
				}
			}
			catch (NoSuchAlgorithmException e){
				JOptionPane.showConfirmDialog(null, "Could not perform requested hash on the password");
			}
		}
		while (!valid);
		
		// Returns hashed password
		return password;
	}
	
	/* promptAndSetPassword: Prompts and sets password for a student being created
	 * @param: studentToCreate: Student being created
	 * @return: boolean: returns true if there were no isses or false is there were issues
	 */
	public static boolean promptAndSetPassword(Student studentToCreate) throws NoSuchAlgorithmException {
		// Valid flag
		boolean valid = false;
		String password = "";
		
		do{
			password = JOptionPane.showInputDialog("Enter password: ");
			if (password.equals("")){
				JOptionPane.showMessageDialog(null, "Invalid password. Password cannot"
						+ "be empty! Please try again.");
			}
		}
		while (password.equals(""));
		try {
			valid = true;
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			studentToCreate.setPassword(sb.toString());
		}
		catch (NoSuchAlgorithmException e){
			JOptionPane.showConfirmDialog(null, "Could not perform requested hash on the password");
		}
		
		return valid;
	}
	
	/* promptAndSetName: Prompts and sets name for the student being created
	 * @param: studentToCreate: Student being created
	 * @return: boolean: returns true if there were no issues or false is there were issues
	 */
	public static boolean promptAndSetName(Student studentToCreate){
		boolean valid = false;
		do{
			try{
				// Prompt for name
				valid = studentToCreate.setName(JOptionPane.showInputDialog("Please enter your name (must not contain digits): "));
			}
			// Error message if it does not pass validation
			catch (IllegalArgumentException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		// Repeats until valid
		while (!valid);
		
		return valid;
	}
	
	/* promptAndSetAge: Prompts and sets age for the student being created
	 * @param: studentToCreate: Student being created
	 * @return: boolean: returns true if there were no issues or false is there were issues
	 */
	public static boolean promptAndSetAge(Student studentToCreate){
		boolean valid = false;
		do{
			try{
				// Prompt for age
				valid = studentToCreate.setAge(Integer.parseInt(JOptionPane.showInputDialog("Please enter your age (between 14 and 19): ")));
			}
			// Error message if not a number
			catch (NumberFormatException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
			// Error message if it does not pass age validation
			catch (IllegalArgumentException e){
				JOptionPane.showMessageDialog(null, "The age must be greater than 14 and less than 19");
			}
		}
		// Repeats until valid
		while (!valid);
		
		return valid;
	}
	
	/* promptAndSetGender: Prompts and sets gender for the student being created
	 * @param: studentToCreate: Student being created
	 * @return: boolean: returns true if there were no issues or false is there were issues
	 */
	public static boolean promptAndSetGender(Student aStudent){
		// Holds gender value
		char gender;
		// Valid flag
		boolean valid = false;
		
		// Button options
		Object[] options = {"Male", "Female"};   


		do{
			try{
				// Prompt for gender (returns numerical value)
				int genderMorF = JOptionPane.showOptionDialog(null, "Select gender:", 
                   		"Select Gender", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                   		null, options, options[0]);
				
				// Determines Male or Female based on numerical value
				if (genderMorF == 0){
					gender = 'M';
				}
				else { gender = 'F'; }
				
				// Set the gender
				valid = aStudent.setGender(gender);
			}
			catch (IllegalArgumentException e){
				// Error if it does not pass validation
				JOptionPane.showMessageDialog(null, "You must select Male or Female!");
			}
		}
		// Repeats until valid
		while (!valid);
		
		return valid;
	}
	/* promptAndSetPhoneNumber: Prompts and sets phone number for the student being created
	 * @param: studentToCreate: Student being created
	 * @return: boolean: returns true if there were no issues or false is there were issues
	 */
	public static boolean promptAndSetPhoneNumber(Student studentToCreate){
		boolean valid = false;
		do{
			try{
				// Prompt for phone number
				valid = studentToCreate.setPhoneNumber(JOptionPane.showInputDialog("Enter Phone Number (e.g. 555-555-5555): "));
			}
			// Error if it does not pass validation
			catch(IllegalArgumentException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		// Repeats until valid
		while (!valid);
		
		return valid;
	}
	/* promptAndSetZipCode: Prompts and sets zip code for the student being created
	 * @param: studentToCreate: Student being created
	 * @return: boolean: returns true if there were no issues or false is there were issues
	 */
	public static boolean promptAndSetZipCode(Student studentToCreate){
		// Valid flag
		boolean valid = false;
		do{
			try{
				// Prompt for zip code
				valid = studentToCreate.setZipCode(JOptionPane.showInputDialog("Enter 5-Digit Zipcode (e.g. 20120): "));
			}
			// Error if it does not pass validation
			catch(IllegalArgumentException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		// Repeats until valid
		while (!valid);
		
		return valid;
	}
	/* welcomeScreen: Welcome screen of the software. Provides the options to login
	 * or exit the program
	 * @return: boolean: Returns true if the user wishes to login
	 */
	public static boolean welcomeScreen(){
		// Login flag
		boolean login = false;
		
		// Welcome screen options
		Object[] options = {"Login", "Exit Program"};   
		
		// Welcomes users and asks if they wish to login or exit
		int loginNum = JOptionPane.showOptionDialog(null, "Welcome to the Course Scheduling System."
				+ "What would you like to do?", 
                       		"Login or Exit Program", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
                       		null, options, options[0]);
		
		// Set login to true
		if (loginNum == 0){
			login = true;
		}
		else {
			// Exit message and exit software
			JOptionPane.showMessageDialog(null, "Thank you for using the Course Scheduling System!");
			System.exit(0);
		}
		
		return login;
	}
	/* counselorMenu: Performs menu action
	 * @param: students: HashMap of all student users
	 * @param: counselors: HashMap of all counselor users
	 * @param: currentUser: Current counselor user that is logged in
	 * @param: classes: Collection of all classes offered by the school
	 * @param: MAX_STUDENTS: Maximum number of students that the system can handle
	 * @param: logout: Logout flag
	 * @return: boolean: return true if the user wishes to remain logged in or returns
	 * 					false if they selected to logout
	 */
	public static boolean counselorMenu(HashMap students, HashMap counselors, Counselor currentUser, List classes, int MAX_STUDENTS, boolean logout) throws Exception{
	      
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
	            	// View basic information
	            	viewBasicInformation(currentUser);
	            	break;      
	            case 2:
	            	// Create A Schedule
	            	createScheduleProcess(currentUser, students, classes);
	            	break;     
	            case 3:
	            	try{
	            		// Search for student
	            		aStudent = searchForStudent(students);
	            		// View current schedule
	            		viewCurrentSchedule(aStudent);
	            		break;
	            	}
	            	catch (NullPointerException e){
	            		// Error if student not found
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	// User hits cancel button
	            	catch (IllegalArgumentException e){
	            		break;
	            	}
	            case 4:
	            	try{
		            	// Change a student's schedule
		            	changeSchedule(currentUser, students, classes);
		            	break;
	            	}
	            	catch (NullPointerException e){
	            		// Error if student not found
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	// User hits cancel button
	            	catch (IllegalArgumentException e){
	            		break;
	            	}
	            case 5:
	            	try{
	            		// Search for student
	            		aStudent = searchForStudent(students);
	            		// View student's past schedule
	            		viewPastSchedules(aStudent);
	            		break;
	            	}
	            	catch (NullPointerException e){
	            		// Error if student not found
	            		JOptionPane.showMessageDialog(null, "Student not found! Please try again.");
	            		break;
	            	}
	            	// User hits cancel button
	            	catch (IllegalArgumentException e){
	            		break;
	            	}
	            case 6:
	            	try{
		            	// Add/create a a student
	            		addStudent(students, counselors, MAX_STUDENTS);
	            		break;
	            	}
	            	catch (NullPointerException e){
	            		// Error if student not found
	            		JOptionPane.showMessageDialog(null, "Student not found!");
	            		break;
	            	}
	            	// User hits cancel button
	            	catch (IllegalArgumentException e){
	            		break;
	            	}
	            case 7:
	            	// Log out
	            	logout = true;
	            	break;
	         }
	      }
	      // Repeats prompt until they quit
	      while (menuChoice != 7);
	      
	      return logout;
 }

	/* studentMenu: Performs menu action
	 * @param: students: HashMap of all student users
	 * @param: counselors: HashMap of all counselor users
	 * @param: currentUser: Current student user that is logged in
	 * @param: classes: Collection of all classes offered by the school
	 * @param: logout: Logout flag
	 * @return: @return: boolean: return true if the user wishes to remain logged in or returns
	 * 							false if they selected to logout
	 */
	public static boolean studentMenu(HashMap students, HashMap counselors, Student currentUser, List classes, boolean logout){

		// Holds value of the menu choice selected
		int menuChoice;
		  
		do
		{
			// Stores menu choice input by the user
			menuChoice = getMenuOptionStudent();
			 
			// Depending on the users input, the task associated with their numerical input will be performed
			switch(menuChoice)
			{
			    
				case 1:
					// View basic information
					viewBasicInformation(currentUser);
					break;
				case 2:
					// Create a schedule
					createScheduleProcess(currentUser, students, classes);
					break;  
				case 3:
					// View current schedule
					viewCurrentSchedule(currentUser);
					break;
				case 4:
					// View past schedule
					viewPastSchedules(currentUser);
					break;
				case 5:
					// Logs out and returns to login screen
					logout = true;
					break;
			     }
		  }
		  // Repeats prompt until they quit
		  while (menuChoice != 5);
		
		return logout;
 }
	/* getMenuOptionCounselor: Menu for a counselor user
	 * @return: int: Returns a numerical value associated with a menu option
	 */
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
	                                                                      "6. Add a student"  + "\n" +
	                                                                      "7. Log out"));
	         }
	         //If the user does not provide a numeric value, an exception will be thrown
	         catch (NumberFormatException e)
	         {
	            menuChoice = 0;
	         }
	         // Displays an error if the numerical input does not match any of the options
	         if (menuChoice < 1 || menuChoice > 7)
	         {
	            JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a valid menu operation.");
	         }
	      }
	      // Continues to repeat if the user does not select an option from the list
	      while (menuChoice < 1 || menuChoice > 7);
	      
	      // Returns the menu choice selected
	      return menuChoice;
	}
	/* getMenuOptionStudent: Menu for a student user
	 * @return: int: Returns a numerical value associated with a menu option
	 */
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
	                                                                      "3. View current schedule" + "\n" +
	                                                                      "4. View past schedule(s)" + "\n" +
	                                                                      "5. Logout"));
	         }
	         //If the user does not provide a numeric value, an exception will be thrown
	         catch (NumberFormatException e)
	         {
	            menuChoice = 0;
	         }
	         // Displays an error if the numerical input does not match any of the options
	         if (menuChoice < 1 || menuChoice > 5)
	         {
	            JOptionPane.showMessageDialog(null, "Invalid choice. Please enter a valid menu operation.");
	         }
	      }
	      // Continues to repeat if the user does not select an option from the list
	      while (menuChoice < 1 || menuChoice > 5);
	      
	      // Returns the menu choice selected
	      return menuChoice;
	}
	/* viewBasicInformation: View information of a user
	 * @param: user: Current user that is logged in
	 */
	public static void viewBasicInformation(User user){
		String output;
		if (user instanceof Counselor){
			// Header for a counselor
			output = "*** Counselor's Basic Information *** \n\n";
		}
		else {
			// Header for a student
			output = "*** Student's Basic Information *** \n\n";
		}
		// Displays basic information
		JOptionPane.showMessageDialog(null, output + user.toString());
	}
	/* viewCurrentSchedule: View current schedule of a student
	 * @param: aStudent: Student whose schedule will be displayed
	 */
	public static void viewCurrentSchedule(Student aStudent){
		// Get current year
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		int nextYear = (currentYear + 1);
		
		// Concatenates the two years (e.g. 2015-2016)
		String yearsToSearch = currentYear + "-" + nextYear;
		
		String scheduleOutput = "*** Current Schedule ***" + "\n";
				
		// Determines if the user has at least 1 schedule
		if (aStudent.getSchedules().size() == 0){
			// No schedules error message
			JOptionPane.showMessageDialog(null, "Student does not have a current"
												+ "schedule!");
		}
		else if (aStudent.getSchedules().size() == 1){
			// Displays current schedule
			JOptionPane.showMessageDialog(null, scheduleOutput + (aStudent.getSchedules().get(0).toString()));
		}
		else {
			// Loops through all schedules the student has
			for (Schedule schedule : aStudent.getSchedules()){
				// Finds the schedule with the current year
				if (schedule.getSchoolYear().equals(yearsToSearch)){
					// Displays current schedule
					JOptionPane.showMessageDialog(null, scheduleOutput + schedule.toString());
					break;
				}
			}
		}
	}
	/* viewPastSchedules: View past schedule of a student
	 * @param: aStudent: Student whose schedule will be displayed
	 */
	public static void viewPastSchedules(Student aStudent){
		String pastSchedulesOutput = "*** Past Schedules*** \n\n";
		
		// Student does not have any schedule
		if (aStudent.getSchedules().size() == 0){
			// No schedules error message
			JOptionPane.showMessageDialog(null, "Student does not have any"
												+ "schedules!");
		}
		// Student only has a current schedule
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
	/* promptAndSetPassword: Prompts and sets password for a student being created
	 * @param: studentToCreate: Student being created
	 * @param: counselors: HashMap of all counselor users
	 * @param: students: HashMap of all student users
	 * @return: boolean: returns true if there were no issues or false is there were issues
	 */
	public static boolean promptAndSetUsername(Student studentToCreate, HashMap counselors, HashMap students){
		boolean valid = false;
		String username;
		do{
			try{
				do{
					// Prompt for username
					username = JOptionPane.showInputDialog("Please enter your username: ");
				}
				// Repeat until value is entered
				while (username.equals(""));
				
				// Make username lowercase
				username = username.toLowerCase();
			
				// Determines if the username has already been created for a student or counselor
				if (!students.containsKey(username) && !counselors.containsKey(username)){
					// Set the username
					valid = studentToCreate.setUserName(username);;
				}
			}
			catch (IllegalArgumentException e){
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		}
		while (!valid);
		
		return valid;
	}
	/* displayClassesAndSelectClass: Prompts and sets password for a student being created
	 * @param: aStudent: Student for which  schedule is being created
	 * @param: classes: Collection of all classes to be displayed
	 * @return: Class: Returns the class to be added
	 */
	public static Class displayClassesAndSelectClass(List classes, Student aStudent)
	{
		// Search results to display
		String searchResultsDisplay = "";;
		// Default numeber
		int courseToAddNum = -1;
		do{
			searchResultsDisplay += "*** Search Results *** \n\n";
			// Displays classes with a numerical value
			for (int i = 0; i < classes.size(); i++){
				searchResultsDisplay += i+1 + ". " + ((Class)classes.get(i)).getId() + "-" +
						((Class)classes.get(i)).getSection() + " : " + ((Class)classes.get(i)).getClassName() + "\n";
				
			}
			
			searchResultsDisplay += "0. Cancel \n\n" +
									"Please select a course you wish to add.";
			
			boolean valid = false;
			do{
				try {
					// Prompts for numerical selection associated with a class on the menu
					courseToAddNum = Integer.parseInt(JOptionPane.showInputDialog(searchResultsDisplay));
					valid = true;
				}
				// Error if non-numerical value is entered
				catch (NumberFormatException e){
					JOptionPane.showMessageDialog(null, "You must enter a number!");
					courseToAddNum = -1;
				}
			}
			// Repeat until valid
			while (!valid);
		}
		while (courseToAddNum == -1);
		Class classToAdd;
		
		// Add class if they didn't select 0 (Cancel)
		if (courseToAddNum != 0){
			classToAdd = (Class)classes.get(courseToAddNum-1);
		}
		// Set to null if they pressed 0
		else{
			classToAdd = null;
		}
		
		return classToAdd;
	}
	/* removeIneligbleClassStandingClasses: Removes classes that the student is not able to
	 * 										sign up for
	 * @param: searchResults: Class search results
	 * @param: classStandingNum: Numerical equivalent to class standing
	 * @return: List: Returns search results after being filtered
	 */
	public static List removeIneligbleClassStandingClasses(List<Class> searchResults, int classStandingNum){
		List<Class> classesToRemove = new ArrayList<>();
		
		// Ensure search results is not empty
		if (searchResults.size() > 0) {
			// Loop through all search results
			for (int i = 0; i < searchResults.size(); i++){
				System.out.println("Currently on class: " + (i+1) + "/" + searchResults.size());
				// Loop through each character in the class Id
				for (int j = 0; j < (searchResults.get(i).getId().length())-1; j++) {
					System.out.println("searchResults.get(i).getId(): " + searchResults.get(i).getId());
					// Find the first number in class Id and compare to class standing number
					if (Character.isDigit(searchResults.get(i).getId().charAt(j))){
						System.out.println(("(searchResults.get(i).getId().charAt(j) (" + searchResults.get(i).getId().charAt(j)) + ") != classStandingNum " + classStandingNum + ")");
						System.out.println(Character.getNumericValue(searchResults.get(i).getId().charAt(j)) != classStandingNum);
						if (Character.getNumericValue(searchResults.get(i).getId().charAt(j)) != classStandingNum){
							System.out.println("Search Results Size: " + searchResults.size());
							System.out.println("Removed: " + searchResults.get(i).getId());
							classesToRemove.add(searchResults.get(i));
							break;
						}
						else{
							break;
						}
					}
				}
			}
		}
		
		for (int i = 0; i < classesToRemove.size(); i++){
			searchResults.remove(classesToRemove.get(i));
		}
		
		return searchResults;
			
	}
	/* classStandingToNum: Calculates numerical equivalent to student's class standing
	 * @param: aStudent: Student whose class standing to retrieve
	 * @return: int: Numerical equivalent to student's class standing
	 */
	public static int classStandingToNum(Student aStudent){
		int classStandingNum = 0;
        switch(aStudent.getClassStanding())
        {
           case "Freshman":
	           	classStandingNum = 1;
	           	break;      
           case "Sophomore":
        	   classStandingNum = 2;
        	   break;     
           case "Junior":
        	   classStandingNum = 3;
        	   break;
           case "Senior":
        	   classStandingNum = 4;
               break;
               // Should never run
           default:
        	   classStandingNum = 0;
        	   break;
        }
		
        return classStandingNum;
	}
	/* createScheduleProcess: Calculates numerical equivalent to student's class standing
	 * @param: aCurrentUser: Current student
	 * @param: students: HashMap of all students in the system
	 * @param: classes: List of classes
	 */
	public static void createScheduleProcess(User aCurrentUser, HashMap students, List classes){
		// Determine if student user
		if (aCurrentUser instanceof Student) {
				// Cast to student user
	           Student aStudent = (Student) aCurrentUser;
	           // Create a schedule
	           Schedule newSchedule = createASchedule(classes, aStudent);
	           // If null, user cancelled
	           if (newSchedule == null) {
	               JOptionPane.showMessageDialog(null, "The schedule has not been saved.");
	           } else {
	        	   // Save schedule
	               aStudent.saveSchedule(aStudent, newSchedule);
	               // Success messsage
	               JOptionPane.showMessageDialog(null, "Schedule has been successfully saved.");
	           }
       }
		// Determine if student user
       if (aCurrentUser instanceof Counselor) {
    	   try{
    		   // Search for student
    		   Student aStudent = searchForStudent(students);
    		   // Create schedule
	           Schedule newSchedule = createASchedule(classes, aStudent);
	           
	           // Null if user cancelled
	           if (newSchedule == null) {
	        	   JOptionPane.showMessageDialog(null, "The schedule has not been saved.");
	           } else {
	        	   // Save schedule
	               aStudent.saveSchedule(aStudent, newSchedule);
	               // Success messsage
	               JOptionPane.showMessageDialog(null, "Schedule has been successfully saved.");
	           }
    	   }
    	   // Error if student not found
    	   catch (NullPointerException e){
    		   JOptionPane.showMessageDialog(null, "Student not found! Please try again.");
    	   }

       }
	}
	/* createASchedule: Create a schedule
	 * @param: aStudent: A student
	 * @param: classes: List of classes
	 * @return: Schedule: Returns a schedule
	 */
   public static Schedule createASchedule(List classes, Student aStudent) {
       ArrayList<Schedule> schedulesForStudent;
       schedulesForStudent = aStudent.getSchedules();

       int numOfSchedules;
       try{
    	   // Determines number of schedule a student has
    	   numOfSchedules = schedulesForStudent.size();
       }
       catch(NullPointerException e){
    	   numOfSchedules = 0;
       }
       
       // Maximum number of schedules has been reached
       if (numOfSchedules == 4) {
           System.out.println("Student already has 4 schedules. Additional schedules cannot be created.");
           return null;
       }

       // Create temporary schedule with current year
       Schedule aTempSchedule = new Schedule(getCurrentYear());
       
       do{
    	   List results;
    	   try{
	    	   // Search for a class
	    	   results = searchForClass(classes, aStudent, aTempSchedule);
    	   }
    	   // User hits cancel button
    	   catch (IllegalArgumentException e){
    		   break;
    	   }
       
    	   
    	   // Determines if there were any search results
    	   if (results.size() > 0) {
    		   		// Prompt for user to select a course to add
    			   Class selectedClass = displayClassesAndSelectClass(results, aStudent);
    			   if (selectedClass != null) {
    				   // Add class
    				   aStudent.addClass(selectedClass, aTempSchedule);
    			   }
    			   else{
    				   JOptionPane.showMessageDialog(null, "No classes were added.");
    			   }
    	   }
    	   else{
    		   JOptionPane.showMessageDialog(null, "No classes found matching your search!");
    	   }
       }
       // Continue while class schedule is not full and they wish to continue
       while (aTempSchedule.getCourses().size() < 7 && JOptionPane.showConfirmDialog(null, "This schedule currently has " +
    		   aTempSchedule.getCourses().size() + "/7 classes. \nDo you wish to add another class. Click \"No\" to cancel.") ==
               JOptionPane.YES_OPTION);
       
       // Unfinished schedule
       if (aTempSchedule.getCourses().size() != 7){
    	   return null;
       }
       // Full schedule
       else {
    	   return aTempSchedule; }
   }
	/* getCurrentYear: Returns the current school year
	 * @return: String: Returns the current schedule year (e.g. 2016-2017)
	 */
   public static String getCurrentYear(){
	   // Gets current year (e.g. 2016)
	   int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	   // Add 1 to current year
       int nextYear = (currentYear + 1);
       
       // Returns concatenation of current year plus next year (e.g. 2016-2017)
       return currentYear + "-" + nextYear;
   }
	/* changeSchedule: Allows a counselor to change a student's schedule
	 * @param: currentUser: The current counselor who is performing the change
	 * @param: students: HashMap of all students
	 * @param: classes: All available classes at the school
	 */
   public static void changeSchedule(Counselor currentUser, HashMap students, List classes){
	   Student searchedStudent = searchForStudent(students);
	   if (searchedStudent != null){
		   Schedule currentSchedule = returnCurrentSchedule(searchedStudent);
		   if (currentSchedule != null){
			   int option = changeScheduleMenu(currentSchedule);
			   if (option == 0){
				   selectClassToAdd(currentUser, searchedStudent, currentSchedule, classes);
			   }
			   else {
				   selectClassToRemove(currentUser, searchedStudent, currentSchedule);
			   }
		   }
	   }
	   else {
		   JOptionPane.showMessageDialog(null, "Student not found!");
	   }
   }
	/* returnCurrentSchedule: Returns a student's current schedule
	 * @param: aStudent: Student whose current schedule should be returned
	 * @return: Schedule: Student's current schedule
	 */
   public static Schedule returnCurrentSchedule(Student aStudent) {
       int currentYear = Calendar.getInstance().get(Calendar.YEAR);
       int nextYear = (currentYear + 1);

       ArrayList<Schedule> wholeSchedule = aStudent.getSchedules();

       Schedule referenceToBeReturned = null;
       // Concatenates the two years (e.g. 2015-2016)
       String yearsToSearch = currentYear + "-" + nextYear;

       // Determines if the user has at least 1 schedule
       if (wholeSchedule.size() == 0) {
           // No schedules error message
           JOptionPane.showMessageDialog(null, "Student does not any schedules!");
           referenceToBeReturned = null;
       } else if (wholeSchedule.size() == 1) {
           // Displays current schedule
           referenceToBeReturned = wholeSchedule.get(0);
       } else {
           // Loops through all schedules the student has
           for (Schedule e : wholeSchedule) {
               // Finds the schedule with the current year
               if (e.getSchoolYear().equals(yearsToSearch)) {
                   // Displays current schedule
                   referenceToBeReturned = e;
                   break;
               }
           }
       }

       return referenceToBeReturned;
   }
	/* selectClassToRemove: Allows a counselor to remove a class from a student's schedule
	 * @param: currentUser: The current counselor who is performing the change
	 * @param: aStudent: Student who owns the schedule being edited
	 * @param: currentSchedule: Current schedule being edited
	 */
   public static void selectClassToRemove(Counselor currentUser, Student aStudent, Schedule currentSchedule){
	   
	   if (currentSchedule.getCourses().size() >= 1){
		   String removeOutput = "*** Please select a class to remove *** \n\n";
		   for (int i = 0; i < currentSchedule.getCourses().size(); i++){
			   removeOutput += i+1 + ". " + currentSchedule.getCourses().get(i) + "\n";
		   }
		   
		   boolean valid = false;
		   int selection = 0;
		   do
		   {
			   selection = Integer.parseInt(JOptionPane.showInputDialog(removeOutput));
		   }
		   while (selection < 0 || selection > currentSchedule.getCourses().size());
		   currentUser.remove(aStudent, currentSchedule.getCourses().get(selection-1), currentSchedule);
	   }
	   else{
		   JOptionPane.showMessageDialog(null, "You do not have any classes in your schedule to remove!");
	   }
   }
	/* selectClassToAdd: Prompts to counselor to search and add a class
	 * @param: currentUser: The current counselor who is performing the change
	 * @param: aStudent: Student who owns the schedule being edited
	 * @param: currentSchedule: Current schedule being edited
	 * @param: classes: All available classes
	 * @return: int: returns 0 if they wish to add a class or 1 if they wish to remove a class
	 */
   public static void selectClassToAdd(Counselor currentUser, Student aStudent, Schedule currentSchedule, List classes){
	   // Checks whether a class can be added
	   if (currentSchedule.getCourses().size() < 7){
		   // Searches for a class
		   List searchResults = searchForClass(classes, aStudent, currentSchedule);
		   // Adds the class
		   aStudent.addClass(displayClassesAndSelectClass(classes, aStudent), currentSchedule);
	   }
	   // Error if schedule is full
	   else{
		   JOptionPane.showMessageDialog(null, "The schedule is full! Additional classes cannot be added");
	   }
   }
	/* changeScheduleMenu: Prompts to add or remove a class from a schedule
	 * @param: currentSchedule: The schedule being changed
	 * @return: int: returns 0 if they wish to add a class or 1 if they wish to remove a class
	 */
   public static int changeScheduleMenu(Schedule currentSchedule){
	   String changeScheduleMenu = "";
	   // Create a string of all the classes on the schedule
		for (int i = 0; i < currentSchedule.getCourses().size(); i++){
			changeScheduleMenu += i+1 + ". " + ((Class)currentSchedule.getCourses().get(i)).getId() + "\n";
		}
	   changeScheduleMenu += "\nPlease selection an option";
	   
	   // Button options
	   Object[] options = {"Add Class", "Remove Class"}; 
	   // Prompts to add or remove a class
		int optionTypeNum = JOptionPane.showOptionDialog(null, changeScheduleMenu, 
           		"Select Add Remove Class", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
           		null, options, options[0]);
		
		return optionTypeNum;
   }
  }
