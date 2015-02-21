import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;

public class Validator {

	private static CodeBlock lastBlock = null;
	
	public static String returnErrorMsgs(File srcFile) {
		String errors = "";

		try {
			// Load file line by line 
			FileInputStream stream = new FileInputStream(srcFile);

			BufferedReader br = new BufferedReader(
					new InputStreamReader(stream));

			String line = null;
			while ((line = br.readLine()) != null) {
				// Append the error message for the current line
				errors += createErrorMsgForLine(line) + "\n";
			}

			br.close();

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					"Could not find specified file " + srcFile.getName());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problem opening file "
					+ srcFile.getName());
		}

		return errors;
	}
	
	private static String createErrorMsgForLine(String line){
		String errorLine = "";
		
		// Clear the line of extraneous text
		line = cleanLine(line);
		
		// Parse line with CodeBlock object
		CodeBlock block = new CodeBlock(line, lastBlock);
		
		// Perform tests and append error messages
		errorLine += ValidationTests.workEnvelopTest(block);
		errorLine += ValidationTests.leadInTest(block, lastBlock);
		errorLine += ValidationTests.spindleSpeedTest(block);
		
		// Save this code block to be viewed by the last
		lastBlock = block;
		
		return errorLine;
	} 
	
	private static String cleanLine(String line){
		// Remove comments
		if(line.contains(";")){
			line = line.substring(0, line.indexOf(";"));
		}
		
		// Remove spaces and tabs
		line = line.replace(" ", "");
		line = line.replace("\t", "");
		line = line.replace("\n", "");
		
		return line;
	}
}
