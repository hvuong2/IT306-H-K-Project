import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainMenu {
	  public static void main(String[] a) {
		    JFrame frame = new JFrame();

		    // Menu options for a counselor
		    String counselorOptions[] = {"View basic information", 
		    		"View a student's schedule", "Change a student's schedule", 
		    		"Create a student's schedule", "View student's past schedule", 
		    		"Add a student"};
		    
		    // Menu options for a student
		    String studentOptions[] = {"View their basic information", 
		    		"Create a schedule", "View their current schedule", 
		    		"View past schedules"};
		    
		    // Menu prompt for counselor
		    JOptionPane.showInputDialog(frame, "Select an option from the list:", "Counselor", JOptionPane.QUESTION_MESSAGE,
		        null, counselorOptions, "Titan");
		    
		    // Menu prompt for student
		    JOptionPane.showInputDialog(frame, "Select an option from the list:", "Student", JOptionPane.QUESTION_MESSAGE,
		        null, studentOptions, "Titan");
		  }
}
