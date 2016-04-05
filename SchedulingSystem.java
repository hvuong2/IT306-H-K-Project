
package courseschedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

public class SchedulingSystem {

	public static void main(String[] args) {
		// Maximum number of counselor users the program can handle
		final int MAX_COUNSELORS = 30;
		// Maximum number of student users the program can handle
		final int MAX_STUDENTS = 4000;
		
		List<Counselor> counselors = new ArrayList<>();
      
		HashMap<String, Student> students = new HashMap();
		
		boolean exit = false;
		boolean logout = false;
		
		do{
			// Current user that is logged into the system
			User currentUser;
			do{
				currentUser = login();
				if (currentUser instanceof Counselor){
					counselorMenu(students);				
				}
				else{
					studentMenu();
				}
			}
			// Need to determine inner while condition
			while(!logout);
		}
		// Need to determine outer loop while condition
		while(!exit);
			
	      
		// Method to import counselors and their information
		
	}
	
	public static User login(){
		// Prompt and set username of user logging in
		String username = promptForUsername();
		
		// Prompt and set password of user logging in
		String password = promptForPassword();
		
		// Validate login credentials (username + password combination)
		User user = validateLogin(username, password);
		
		// Determine if user is null, if so error message shown
		if (user.equals(null)){
			JOptionPane.showMessageDialog(null, "Invalid login credentials. Please try again.");
			return null;
		}
		// Returns the user that logged in (Counselor or student)
		else
		{
			JOptionPane.showMessageDialog(null, "Login successful!");
			return user;
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
	
	public static String promptForPassword(){
		String password;
		
		// Prompt for password
		do {
			password = JOptionPane.showInputDialog("Please enter password:");
		}
		while (password.equals(""));

		return password;
	}
	
	public static boolean validateLogin(String username, String password){
		// This method will look in the text files to determine if login credentials
		// are valid
	}
	
	public static void counselorMenu(HashMap students){
	      
	      // Holds value of the menu choice selected
	      int menuChoice;
	      
	      do
	      {
	         // Stores menu choice input by the user
	         menuChoice = getMenuOptionCounselor();
	         
	         // Depending on the users input, the task associated with their numerical input will be performed
	         switch(menuChoice)
	         {
	            case 1:
	               
	               break;      
	            case 2:
	               
	               break;     
	            case 3:
	            	   searchForStudent(students);
	               break;
	            case 4:
	            	searchForStudent(students);
		               
	               break;
	            case 5:
	            	searchForStudent(students);
		               
	               break;
	            case 6:
	            	searchForStudent(students);
		               
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

	
	public static  void studentMenu(){

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

	/* viewBasicInformation: Displays basic information of a user
	 * @param: user: The user for which basic information will be displayed
	 */
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
		if (aStudent.getTotalSchedules() == 0){
			// No schedules error message
			JOptionPane.showMessageDialog(null, "Student does not have a current"
												+ "schedule!");
		}
		else if (aStudent.getTotalSchedules() == 1){
			// Displays current schedule
			JOptionPane.showMessageDialog(null, (aStudent.getSchedule(0)));
		}
		else {
			// Loops through all schedules the student has
			for (Schedule e : aStudent.getSchedules()){
				// Finds the schedule with the current year
				if (e.getSchoolYear.equals(yearsToSearch)){
					// Displays current schedule
					JOptionPane.showMessageDialog(null, (e));
					break;
				}
			}
		}
	}
	
	public static void viewPastSchedules(Student aStudent){
		String pastSchedulesOutput = "*** Past Schedules*** \n";
		
		// Determines if the user has at least 1 schedule
		if (aStudent.getTotalSchedules() == 0){
			// No schedules error message
			JOptionPane.showMessageDialog(null, "Student does not have any"
												+ "schedules!");
		}
		else if (aStudent.getTotalSchedules() == 1){
			// Current schedule only error message
			JOptionPane.showMessageDialog(null, "Student only has a current"
					+ "schedule! Please use the View Current Schedule feature"
					+ "to view the current schedule.");
		}
		else {
			
			// Loops through all schedules the student has up to the current schedule
			for (int i = 0; i < (aStudent.getSchedules()-1); i++){
				pastSchedulesOutput += aStudent.getSchedule(i) + "\n\n";
			}
			
			// Displays all previous schedules
			JOptionPane.showMessageDialog(null, pastSchedulesOutput);
		}
	}
	
	/*// COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 * // COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 * // COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 * // COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 * // COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 * // COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 * // COME BACK TO THIS METHOD TO FINISH!!!!!!!!!!!!!!!!!!!!!!!!
	 */
	public static String searchForClass(){
		
		try (BufferedReader classesFile = new BufferedReader(new FileReader("MUST ADD FILE"
				+ "LOCATION TO THIS PART OF THE CODE!!!!!!!!!!!")))
		{

			String sCurrentLine;

			while ((sCurrentLine = classesFile.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return "?????";
	}
	
	
	public static Student searchForStudent(HashMap students){
		// Informs user to enter the username of the student
		JOptionPane.showMessageDialog(null, "Please enter the student's username"
				+ "on the following screen. Press OK to continue.");
		
		// Prompts for username to search
		String username = promptForUsername();
		
		// Searches the HashMap collection of students based on the username
		aStudent = students.get(username);
		
		return aStudent;
	}
	
	
}
