import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class MainWindow {

	public static final String TITLE = "CNC VALIDATOR";
//	public static final int WIDTH = 640, HEIGHT = 480;
	
	JFrame frame;
	JFileChooser openDialog;
	File currentFile = null;
	JTextArea text;
	JScrollPane scroll;
	
	
	public MainWindow() {
		// Setup window
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(WIDTH, HEIGHT);
		
		// Setup open dialog
		openDialog = new JFileChooser();
		
		// Setup scroll display
		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(new TitledBorder(new EtchedBorder(), "Output"));
		
		text = new JTextArea(16, 58);
		text.setBorder(new EmptyBorder(5,5,5,5));
		text.setFont(new Font("Arial", 1, 14));
		text.setEditable(false);
		
		scroll = new JScrollPane(text);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		middlePanel.add(scroll);
		
		// Setup menubar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem openItem = new JMenuItem("Open...");
		JMenuItem reverifyItem = new JMenuItem("Re-verify");
		
		// Open Command
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ask the user to pick a file and then load it
				int returnVal = openDialog.showOpenDialog(null);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					currentFile = openDialog.getSelectedFile();
					
					// Print errors to the user
					generateOutput();
				}
			}
		});
		
		// Re-verify command
		reverifyItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Print errors to the user
				generateOutput();
			}
		});
		
		fileMenu.add(openItem);
		fileMenu.add(reverifyItem);
		
		frame.setJMenuBar(menuBar);
		frame.add(middlePanel);
		
		frame.pack(); // Size proportionally
		
		// Show window
		frame.setVisible(true);
	}
	
	private void generateOutput(){
		if(currentFile != null){
			// Print errors out to user
			String errors = Validator.returnErrorMsgs(currentFile);
			
			if(errors.replace("\n", "").equals("")){
				text.setText("No errors found in " + currentFile.getName());
			} else {
				text.setText(errors);
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					   public void run() { 
					       scroll.getVerticalScrollBar().setValue(0);
					   }
				});
			}
		}
	}
}
