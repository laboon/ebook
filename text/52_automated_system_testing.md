# Automated System Testing

It turns out that most testers' idea of a fun time is not mindlessly running a test plan.  It's also very error-prone - after several hours of following a test plan, you may start to mistype or not notice errors in the output of the system under test.  You may be envious of those white-box testers who got the chance to automate much of their work away with unit testing.  Fear not!  It is very easy to automate systems level tests, as well.

In the "Test Plan Walkthrough" chapter, we created a test plan for a hypothetical program, CatWeigher, which would let us know if a cat was overweight, underweight, or normal weight.  In this chapter, we will create this program and automate it using a tool called __expect__.  Although _expect_ uses yet another programming language, _Tcl_, we will not need to delve deeply into it in order to create useful automated tests for our system.

## Expect Basics

## Testing With Expect

## Creating CatWeigher

If you'll recall from the "Test Plan Walkthrough", our Catweigher program should meet the following requirements:

1. _FUN-PARAMETER:_ The system shall accept a single parameter, `CATWEIGHT`, which can only be a single positive floating-point value or positive integer.  If the parameter is not one of these two kinds of values, or if there is not exactly one parameter, the system shall immediately shut down with only the message "Please enter a valid parameter."
2. _FUN-STARTUP-MESSAGE:_ Upon startup, the system shall display "Cat Weighing System" upon the console.
3. _FUN-UNDERWEIGHT:_ If `CATWEIGHT` is less than 3 kilograms, then the message "Cat is underweight" shall be displayed upon the console.
4. _FUN-NORMALWEIGHT:_ If `CATWEIGHT` is equal to or greater than 3 kilograms and less than 6 kilograms, then "Cat is normal weight" shall be displayed upon the console.
5. _FUN-OVERWEIGHT:_ If `CATWEIGHT` is greater than or equal to 6 kilograms, then "Cat is overweight" shall be displayed upon the console.
6. _NF-PERF-TIME:_ The system shall display the appropriate message within two seconds of the program being executed.

We will implement six tests, one for each of the requirements.  This will obviously not test all of the different edge, corner, and failure cases of the program.  However, as a proof of concept, they should provide a good foundation on how to automate a manual test plan.  You should be able to take these ideas and implement any additional automated system tests that you like.

I have included a simple, self-contained Java program, below, which implements all of the functionality of the CatWeigher program.  One of the things which I find interesting is that reviewing the code, it's relatively simple to map the equivalence classes to the various conditional branches.  You can see that there is a correlation between what how the program is described in the requirements and the code itself.

```java
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
```

Running this by itself would require us to type in `java CatWeigher *args*`, which, per our earlier examples, is not how the program should run.  Recall that the examples of running the program showed us running the program `catweigher` with arguments listed after it, like so:

```sh
$ catweigher 2.5
Cat Weighing System
Cat is underweight
```

If you are using a Unix, Linux, or OS X system, then you can create a shell script file called catweigher which calls your compiled CatWeigher program and passes in any arguments from the command line to it.  Simply create a file called catweigher, and insert the following line of text into it.

```sh
java CatWeigher "$@"
```

You should then ensure that the program is executable by calling `chmod +x catweigher`.

If you are using a Windows system, you will want to create a batch file instead of a shell script file, but the idea is the same.  Simply create a catweigher file with the following text:

```sh
java CatWeigher %*
```

Determining how to do this on other operating systems is left as an exercise for the reader.  Of course, you are also free to just replace any mention of `catweigher` with the slightly longer `java CatWeigher` if you prefer.

## Automating The CatWeigher Test Plan

## Warning: When You Should Not Use Expect

While expect is a very useful tool, there are several situations where you should not use it.

1. _When it is necessary to have expect store a password_: Storing a password in a plaintext file is never a good idea.  The only good places for passwords are your brain or an encrypted location.  Anybody who has access to the expect script will know the password!  As a workaround, you can have the user type the password at the beginning of execution of the script and storing the value in a variable.
2. _When the text interface is likely to change_: Tests written in expect tend to be quite fragile.  Even the slightest change in the text returned or expected to be sent will cause your tests to fail.  This is not a problem for well-specified or stable systems, but many systems under test are neither of these.
3. _When you have access to an API_: expect scripts implicitly check both the calculation and display of data.  Often your tests will not be as concerned about the exact 
```

```