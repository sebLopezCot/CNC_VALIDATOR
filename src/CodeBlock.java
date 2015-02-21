public class CodeBlock {

	public Integer N = null;
	public Integer G = null;
	public Double X = null;
	public Double Y = null;
	public Double Z = null;
	public Double R = null;
	public Double S = null;
	public Integer T = null;

	public CodeBlock(String lineOfCode, CodeBlock lastCodeBlock) {

		// Load values from line of code into CodeBlock
		String buffer = new String(lineOfCode);

		while (buffer.length() > 0) {
			boolean subblockFound = false;

			for (int i = buffer.length() - 1; i >= 0; i--) {
				// If a subblock is found
				if (isLetter(buffer.charAt(i))) {
					loadSubblock(buffer.substring(i)); // Load the subblock into
														// this CodeBlock

					buffer = buffer.substring(0, i); // update the string to
														// remove last subblock

					// Look for the next subblock of code
					subblockFound = true;
					break;
				}
			}

			// If there was an error in the code, don't infinite loop
			if (!subblockFound) {
				break;
			}
		}
		
		// Fill in any values that carry over from last line of code
		if(lastCodeBlock != null){
			int count = 0;
			if(X != null) count++;
			if(Y != null) count++;
			if(Z != null) count++;
			if(R != null) count++;
			
			if(count > 0){
				if(G == null) G = lastCodeBlock.G;
				if(X == null) X = lastCodeBlock.X;
				if(Y == null) Y = lastCodeBlock.Y;
				if(Z == null) Z = lastCodeBlock.Z;
				if(R == null) R = lastCodeBlock.R;
			}
		}
	}

	private static boolean isLetter(char letter) {
		// Uppercase only
		if (letter > 64 && letter < 91) {
			return true;
		} else {
			return false;
		}
	}

	private void loadSubblock(String subblock) {

		switch (subblock.charAt(0)) {
		case 'N':
			N = getIntegerValue(subblock);
			break;

		case 'G':
			G = getIntegerValue(subblock);
			break;

		case 'X':
			X = getDoubleValue(subblock);
			break;

		case 'Y':
			Y = getDoubleValue(subblock);
			break;

		case 'Z':
			Z = getDoubleValue(subblock);
			break;

		case 'R':
			R = getDoubleValue(subblock);
			break;

		case 'S':
			S = getDoubleValue(subblock);
			break;

		case 'T':
			T = getIntegerValue(subblock);
			break;

		}
	}

	private static Integer getIntegerValue(String subblock) {
		try {
			return Integer.valueOf(subblock.substring(1));
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private static Double getDoubleValue(String subblock) {
		try {
			return Double.valueOf(subblock.substring(1));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public String getLineNumber(){
		String val = "N";
		
		if(N <= 9){
			val += "0";
		}
		
		val += N.toString();
		
		return val;
	}
}
