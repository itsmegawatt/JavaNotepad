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
}
