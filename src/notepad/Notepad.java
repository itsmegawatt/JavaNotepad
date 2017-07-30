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
	private Menu shortcuts = new Menu();
	private MenuItem clearTextArea = new MenuItem();

	public Notepad() {
		this.setSize(950, 550); // set initial size of the window
		this.setTitle("Java Notepad"); // set the title of the window
		this.getContentPane().setLayout(new BorderLayout()); // the BorderLayout bit makes it fill automatically
		setDefaultCloseOperation(EXIT_ON_CLOSE); // set the default close operation (exit when it gets closed)

		constructMenuBar(); // Add menu bar to window
		constructtextArea(); // Add Text Area to window

		constructfileMenu(); // Add fileMenu to the menuBar
		constructopenMenuItem(); // add open menu item to file menu
		constructsaveMenuItem(); // add save menu item to file menu
		constructcloseMenuItem(); // add close menu item to file menu
		
		constructshortcutsMenu(); // Add the shortcuts menu to the menuBar
		constructclearTextAreaMenuItem(); //Adds the clear button to shortcuts menu
	}

	private void constructMenuBar() {
		this.setMenuBar(this.menuBar);
		this.menuBar.setFont(new Font("Arial", Font.PLAIN, 16));
	}

	private void constructtextArea() {
		this.getContentPane().add(textArea);
		this.textArea.setFont(new Font("Century Gothic", Font.BOLD, 16)); // sets a default font for the text area
	}

	private void constructfileMenu() {
		this.file.setLabel("File"); // Sets the label of the file menu option to "File"
		this.menuBar.add(this.file); // adds the menu option to the menu
	}

	private void constructopenMenuItem() {
		this.openFile.setLabel("Open");
		this.openFile.addActionListener(this); // add an action listener so we know when it's been clicked
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false)); //set a keyboard shortcut as C-o
		this.file.add(this.openFile); // adds the file option to the menu
	}

	private void constructsaveMenuItem() {
		this.saveFile.setLabel("Save"); // Gives the save button a label
		this.saveFile.addActionListener(this); // Gives it an action listener
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false)); // Keyboard shortcut of C-s
		this.file.add(saveFile); //Adds the save button to the menu
	}

	private void constructcloseMenuItem() {
		this.close.setLabel("Close");
		this.close.addActionListener(this);
		this.close.setShortcut(new MenuShortcut(KeyEvent.VK_F4)); // Ctrl F4 closes menu
		this.file.add(close);
	}

	private void constructshortcutsMenu() {
		this.shortcuts.setLabel("Shortcuts");
		this.menuBar.add(this.shortcuts);
	}
	
	private void constructclearTextAreaMenuItem() {
		this.clearTextArea.setLabel("Clear");
		this.clearTextArea.addActionListener(this);
		this.shortcuts.add(clearTextArea);
	}
	
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == this.openFile)
			openAction();
		if (event.getSource() == this.saveFile)
			saveAction();
		if (event.getSource() == this.close)
			closeAction();
		if (event.getSource() == this.clearTextArea)
			textAreaClear();
	}

	private void openAction() {
		JFileChooser file = new JFileChooser(); // Open up a file chooser to browse for a file to open
		int option = file.showOpenDialog(this); // Get the option that the user selected which is approve or cancel
		if (option == JFileChooser.APPROVE_OPTION) {
			textAreaClear();
			try {
				loadFile(file);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	private void loadFile(JFileChooser file) throws FileNotFoundException {
		// Create a scanner to read the file		
		// open.getSelectedFile().getPath() will get the path to the file
		Scanner scan = new Scanner(new FileReader(file.getSelectedFile().getPath()));
		while (scan.hasNext()) //while there's still something to read
			this.textArea.append(scan.nextLine() + "\n"); // Append the line to TextArea
		scan.close();
	}

	private void textAreaClear() {
		this.textArea.selectAll();
		this.textArea.setText(null); // Clear the text area before applying file contents
	}

	private void saveAction() {
		JFileChooser file = new JFileChooser(); // Again open the file chooser
		int option = file.showSaveDialog(this); // Similar to the open dialog but for saving
		if (option == JFileChooser.APPROVE_OPTION) {
			try {
				saveFile(file);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	private void saveFile(JFileChooser file) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(file.getSelectedFile().getPath()));
		out.write(this.textArea.getText()); // Get the contents of the text area and add it to buffer
		out.close(); // Close the file stream
	}

	private void closeAction() {
		this.dispose(); // Frees up all resources and closes program
	}
	
	public static void main(String[] args) {
		Notepad app = new Notepad();
		app.setVisible(true);
	}
	
	
}
