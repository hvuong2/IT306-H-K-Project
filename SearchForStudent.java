import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchForStudent {
	public static void main(String[] args){
		// Creates dialog box 2x2
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		// Username information
		JLabel searchUsername = new JLabel("Username:");
		JTextField studentUsername = new JTextField(20);
		panel.add(searchUsername);
		panel.add(studentUsername);
		
		// Two buttons
		String[] options = new String[]{"Search", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, 
				"Search for Student",
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		        null, options, options[1]);
		if(option == 0) // pressing OK button
		{
			// CODE TO SEARCH DATABASE/FILE FOR STUDENTS
		}
		else {
			// WRITE CODE TO DISPLAY MAIN MENU FOR COUNSELOR
		}
	}
}
