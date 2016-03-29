import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class Login {
	public static void main(String[] args){
		
		// Create new frame
		JFrame frame = new JFrame();
		
		// Exit when [x] is pressed
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		// Creates dialog box 2x2
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		// Adds panel to frame
		frame.add(panel, BorderLayout.CENTER);
		
		// Username information
		JLabel loginUsername = new JLabel("Username:");
		JTextField username = new JTextField(20);
		panel.add(loginUsername);
		panel.add(username);
		
		// Password information
		JLabel loginPassword = new JLabel("Password:");
		JPasswordField password = new JPasswordField(20);
		panel.add(loginPassword);
		panel.add(password);
		
		// Select user type (Counselor or Student)
		JRadioButton counselor = new JRadioButton("Counselor");
		JRadioButton student = new JRadioButton("Student");
		ButtonGroup userType = new ButtonGroup();
		userType.add(counselor);
		userType.add(student);
		counselor.setSelected(true);
		panel.add(new JLabel("User Type:"));
		panel.add(counselor);
		panel.add(student);
		panel.setVisible(true);
		
		// Two buttons
		String[] options = new String[]{"Login", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, 
				"Student Scheduling System Login",
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		        null, options, options[1]);
		if(option == 0) // pressing OK button
		{
		    char[] pass = password.getPassword();
		    // FOR TESTING PURPOSES -- BE SURE TO DELETE
		    System.out.println("Your password is: " + new String(pass));
		}
	}
}
