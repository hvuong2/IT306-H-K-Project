import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchForClass {
	public static void main(String[] args){
		// Creates dialog box 2x2
		JPanel panel = new JPanel(new GridLayout(2, 2));
		
		// Username information
		JLabel searchClass = new JLabel("Class (e.g. ENG101):");
		JTextField searchedClass = new JTextField(20);
		panel.add(searchClass);
		panel.add(searchedClass);
		
		// Two buttons
		String[] options = new String[]{"Search", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, 
				"Search for Class",
				JOptionPane.NO_OPTION, JOptionPane.QUESTION_MESSAGE,
		        null, options, options[1]);
		if(option == 0) // pressing OK button
		{
			// CODE TO SEARCH DATABASE/FILE FOR CLASS
		}
		else {
			// WRITE CODE TO DISPLAY PREVIOUS MENU (CREATE SCHEDULE) 
		}
	}
}
