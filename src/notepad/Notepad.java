package notepad;

import javax.swing.*; // for the main JFrame design
import java.awt.*; // for the GUI
import java.awt.event.*; // for event handling
import java.util.Scanner; // for reading from a file
import java.io.*; // for writing to a file

public class Notepad extends JFrame implements ActionListener {
	private TextArea textArea = new TextArea("", 0,0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	private MenuBar menuBar = new MenuBar();
	private Menu file = new Menu();
	private MenuItem openFile = new MenuItem();
	private MenuItem saveFile = new MenuItem();
	private MenuItem close = new MenuItem();
	
	public Notepad() {
		this.setSize(950, 550); // set initial size of the window
		this.setTitle("Java Notepad"); // set the title of the window
		this.getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill automatically
		setDefaultCloseOperation(EXIT_ON_CLOSE); // set the default close operation (exit when it gets closed)

		menuBarConstructor(); // Add menu bar to window
		textAreaConstructor(); // Add Text Area to window

		fileMenuConstructor(); // Add fileMenu to the menuBar
		openConstructor(); // add open menu item to file menu
		saveConstructor(); // add save menu item to file menu
		closeConstructor(); // add close menu item to file menu
	}

	private void menuBarConstructor() {
		this.setMenuBar(this.menuBar);
	}

	private void textAreaConstructor() {
		this.getContentPane().add(textArea);
		this.textArea.setFont(new Font("Century Gothic", Font.BOLD, 16)); // sets a default font for the text area
	}

	private void fileMenuConstructor() {
		this.file.setLabel("File"); // Sets the label of the file menu option to "File"
		this.file.setFont(new Font("Arial", Font.PLAIN, 16));
		this.menuBar.add(this.file); // adds the menu option to the menu
	}

	private void openConstructor() {
		this.openFile.setLabel("Open");
		this.openFile.addActionListener(this); // add an action listener so we know when it's been clicked
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); //set a keyboard shortcut as C-o
		this.file.add(this.openFile); // adds the file option to the menu
	}

	private void saveConstructor() {
		this.saveFile.setLabel("Save"); // Gives the save button a label
		this.saveFile.addActionListener(this); // Gives it an action listener
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false)); // Keyboard shortcut of C-s
		this.file.add(saveFile); //Adds the save button to the menu
	}

	private void closeConstructor() {
		this.close.setLabel("Close");
		this.close.addActionListener(this);
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4)); // Ctrl F4 closes menu
		this.file.add(close);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.openFile)
			openAction();
		if (e.getSource() == this.saveFile)
			saveAction();
		if (e.getSource() == this.close)
			closeAction();
		
	}

	private void openAction() {
		JFileChooser open = new JFileChooser(); // Open up a file chooser to browse for a file to open
		int option = open.showOpenDialog(this); // Get the option that the user selected which is approve or cancel
		if (option == JFileChooser.APPROVE_OPTION) {
			this.textArea.setText(""); // Clear the text area before applying file contents
			// Create a scanner to read the file
			// open.getSelectedFile().getPath() will get the path to the file
			try {
				Scanner scan = new Scanner(new FileReader(open.getSelectedFile().getPath()));
				while (scan.hasNext()) //while there's still something to read
					this.textArea.append(scan.nextLine() + "\n"); // Append the line to TextArea
				scan.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	private void saveAction() {
		JFileChooser save = new JFileChooser(); // Again open the file chooser
		int option = save.showSaveDialog(this); // Similar to the open dialog but for saving
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
				out.write(this.textArea.getText()); // Get the contents of the text area and add it to buffer
				out.close(); // Close the file stream
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}


	private void closeAction() {
		this.dispose(); // Frees up all resources and closes program
	}
	
	public static void main(String[] args) {
		Notepad app = new Notepad();
		app.setVisible(true);
	}
	
	
}
