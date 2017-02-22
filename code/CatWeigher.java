public class CatWeigher {

    /**
     * Given a list of arguments from the command line, return
     * the weight of the cat in double format.
     * If there are any issues - a different number of arguments than
     * one was sent in, or the argument cannot be parsed as a double -
     * then a CatWeightException is thrown.
     * @param args Array of arguments from command line
     * @return the weight of the cat 
     * @throws CatWeightException if cat weight cannot be calculated
     */
    public static double getWeight(String[] args) throws CatWeightException {
	double toReturn;
	if (args.length !=1) {
	    throw new CatWeightException();
	} else {
	    try {
		toReturn = Double.parseDouble(args[0]);
	    } catch (NumberFormatException nfex) {
		throw new CatWeightException();
	    }
	}
	return toReturn;
    }

    /**
     * Main method.
     * @param args Arguments from the command line.
     */
    public static void main(String[] args) {

	// Get the weight of the cat from the command line.
	// If there are any issues, print the error message and exit.
	
	double catWeight = 0.0;
	try {
	    catWeight = getWeight(args);
	} catch (CatWeightException cwex) {
	    System.out.println("Please enter a valid parameter.");
	    System.exit(1);
	}

	// If a valid weight was passed in, describe the cat's
	// weight as per the requirements.
	
	System.out.println("Cat Weighing System");
	   
	if (catWeight < 3.0) {
	    System.out.println("Cat is underweight.");
	} else if (catWeight < 6.0) {
	    System.out.println("Cat is normal weight.");
	} else {
	    System.out.println("Cat is overweight.");
	}
    }

    static class CatWeightException extends Exception {
	// Class intentionally left blank
    }
}
