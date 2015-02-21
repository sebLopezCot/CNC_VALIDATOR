public class ValidationTests {

	public static double X_MIN_WORK_ENV = 0.0;
	public static double X_MAX_WORK_ENV = 6.0;

	public static double Y_MIN_WORK_ENV = 0.0;
	public static double Y_MAX_WORK_ENV = 3.0;

	public static double Z_MIN_WORK_ENV = -0.02;
	public static double Z_MAX_WORK_ENV = 3.0;
	
	public static double SPINDLE_SPEED = 3500;

	// TESTS TO BE PERFORMED ON EVERY LINE OF CODE

	// This test makes sure the code stays within the defined
	// work envelope.
	public static String workEnvelopTest(CodeBlock block) {
		String msg = "";

		if (block.X != null && (block.X < X_MIN_WORK_ENV || block.X > X_MAX_WORK_ENV)) {
			msg += formatError(block, "X coordinate outside of work envelope.");
		}
		
		if (block.Y != null && (block.Y < Y_MIN_WORK_ENV || block.Y > Y_MAX_WORK_ENV)) {
			msg += formatError(block, "Y coordinate outside of work envelope.");
		}
		
		if(block.Z != null){
			if (block.Z > Z_MAX_WORK_ENV){
				msg += formatError(block, "Z coordinate outside of work envelope.");
			}
			
			if(block.Z < Z_MIN_WORK_ENV){
				msg += formatError(block, "Plunges too deep into piece.");
			}
		}

		return msg;
	}

	// This test makes sure the tool does not perform a ramp in.
	public static String leadInTest(CodeBlock block, CodeBlock lastBlock) {
		String msg = "";
		
		// If there was a block before this
		if(lastBlock != null){
			try{
				// If an x and/or y have changed while z has changed
				if((block.X != lastBlock.X || block.Y != lastBlock.Y) && block.Z != lastBlock.Z){
					// If the bit is already in/will be in the piece
					if(block.Z < 0 || lastBlock.Z < 0){
						msg += formatError(block, "Command ramps into piece at an angle.");
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return msg;
	}

	// This test makes sure the tool is set to the proper spindle speed.
	public static String spindleSpeedTest(CodeBlock block) {
		String msg = "";
		
		if(block.S != null){
			if(block.S != SPINDLE_SPEED){
				msg += formatError(block, "Improper spindle speed.");
			}
		}
		
		return msg;
	}

	// This test searches every line of code incrementally for a block
	// that must be inserted in every file of code.
	public static void missingBlockTest(CodeBlock block) {

	}

	// TESTS TO BE DONE ON EVERY FILE OF CODE
	public static String getMissingBlockErrors() {
		return "";
	}

	private static String formatError(CodeBlock block, String text) {
		return "\n" + "ERR-> " + block.getLineNumber() + "  |   " + text.toUpperCase();
	}
}
