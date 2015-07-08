## Developing A Test Plan

Let us walk through developing a test plan for a given list of requirements for a cat-weighing system called "catweigher" (if you want clever names for programs, this is not the paragraph to find them in).  This extremely useful program will accept one argument indicating the cat's weight in kilograms, and let us know if the cat is underweight, normal weight, or overweight.  

```bash
$ catweigher 1.7
Cat Weighing System
Cat is underweight
$ catweigher 83
Cat Weighing System
Cat is overweight
```

As the test cases are developed, take note of the trade-offs that are made, and how decisions about which test cases to include are made.  Also note how we use the ideas which we explored in earlier chapters - especially in equivalence class partitioning and thinking of failure cases - are used to make a well-rounded test plan.

Requirements:

1. FUN-PARAMETER: The system shall accept a single parameter, CATWEIGHT, which can only be a single positive floating-point value or positive integer.  If the parameter is not one of these two kinds of values, or if there is not exactly one parameter, the system shall immediately shut down with only the message "Please enter a valid parameter."
2. FUN-STARTUP-MESSAGE: Upon startup, the system shall display "Cat Weighing System" upon the console.
3. FUN-UNDERWEIGHT: If CATWEIGHT is less than 3 kilograms, then the message "Cat is underweight" shall be displayed upon the console.  
4. FUN-NORMALWEIGHT: If CATWEIGHT is equal to or greater than 3 kilograms and less than 6 kilograms, then "Cat is normal weight" shall be displayed upon the console.  
5. FUN-OVERWEIGHT: If CATWEIGHT is greater than 6 kilograms, then "Cat is overweight" shall be displayed upon the console.
6. NF-PERF-TIME: The system shall display the appropriate message within two seconds of the program being executed.

Although this is a relatively simple program, with only a small set of requirements, there is already some ambiguity.  In real-world applications, you can expect much more, which will often necessitate discussions with systems engineers, requirements analysts, and/or customers to resolve the various ambiguities.  The particular ambiguity in this case is that in FUN-PARAMETER, it says that the system should immediately shut down with __only__ the message "Please enter a valid parameter" if an invalid parameter is entered.  The next requirement, FUN-STARTUP-MESSAGE, says that the message "Cat Weighing System" should be displayed upon startup; it does not mention whether or not this includes times when invalid parameters were entered.  In other words, should the expected behavior be:

```bash
$ catweigher meow
Please enter a valid parameter
```

or

```bash
$ catweigher meow
Cat Weighing System
Please enter a valid parameter
```

We should first determine what the expected behavior is before continuing with writing a test plan.  This can be done by checking with the appropriate requirements analysts, systems engineers, product owners, or whoever is in charge of requirements.  If you are working on a less formal team, the correct path forward may be to make an assumption.  However, these assumptions should be noted as part of the test plan!  If one must make assumptions, they should at least be delineated clearly somewhere.  In general, however, you should avoid making assumptions; you want to know what the expected behavior is as precisely as possible.

In general, I have found that a top-down approach of the requirements of the program is of the most help in creating a test plan.  By a "top-down approach", I mean that a general outline of the test plan will be generated first, and then specific details can be filled in.  This contrasts with a "bottom-up approach", where the specific details of small sections are filled in first, and as more and more small details are filled, a larger picture gradually emerges.

Looking at the requirements, I can mentally divide them into three sections:

1. Input (accepting the parameter)
  1. FUN-PARAMETER
2. Output (displaying messages and results): 
  1. FUN-STARTUP-MESSAGE
  2. FUN-UNDERWEIGHT
  3. FUN-NORMALWEIGHT
  4. FUN-OVERWEIGHT
3. Performance:
  1. NF-PERF-TIME

How did I determine how to sort these out?  I looked for requirements which were related to each other and put each in a "cluster".  The program you're working will probably not have these exact same clusters (unless you also happen to be working on a cat-weighing program, lucky you).  For larger programs, these clusters will often revolve around specific features (e.g., shopping cart, item display, and checkout in an online shopping application) or different sub-systems (e.g., user interface, enemy artificial intelligence, and graphics for a video game).  There is really no "right answer" as to how to cluster requirements together for testing, and as you understand the system better, the clustering may change.  However, by providing a general outline, you can start to get a handle on testing the system holistically.

Looking specifically at the test plan outline we developed here, it is apparent that the second section, Output, has by far the most requirements listed, this does not necessarily mean that it will take the longest amount of time to test, or involve the most work.  In fact, as FUN-STARTUP-MESSAGE is very simple and unchanging, and the three other requirements involve mathematically pure functions, the tests themselves will probably be relatively easy to write.  In real-world instances, some requirements may take orders of magnitude longer to test than others!  Performance and security requirements, among others, can be much more difficult to test than the length of their requirements would suggest.

Let's start with the first section, Input.  I notice that there are several possible use cases here:

1. The user enters one valid parameter
1. The user enters no parameters
2. The user enters one parameter, but it is invalid
3. The user enters more than one parameter

In the last three use cases, the expected behavior is the same; the system shuts down and the message "Please enter a valid parameter" is displayed.  In the first use case, the system will continue on and have behavior governed by other requirements.  You can already see here that it's important to have a view of the entire system, instead of looking at requirements solely in and of themselves.  This becomes more and more difficult as the number of requirements grows and the system under test becomes more complex.

Use cases 1, 3, and 4 all have variants which could be tested.  There are numerous values for valid parameters, in the first case, which will have different behaviors as enumerated in the other requirements.  There are an essentially infinite number of single invalid parameters, anything from negative values, to imaginary numbers, to random strings of any length.  For the fourth use case, the only limit to how many parameters the user can enter is up to the operating system; he or she can enter two, three, four or more.  The second use case has no variants; there are no different ways to enter no parameters, there are no different flavors of null.

Depending on the importance of the cat-weighing program, we can decide if we want to test all possible use cases, and how many variants of the particular use case we want to test.  At a bare minimum, you probably want to test the "happy path", that is, the expected use case that a user will follow, which in our case is the user entering one valid parameter.  Other use cases can be considered failure cases, since the expected behavior is to cease execution as an invalid value or set of values has been entered.

IDENTIFIER: VALID-PARAMETER-TEST
TEST CASE: Run the program with a valid parameter.
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher 5"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program exits and displays normal output for a 5 kilogram cat.  The program does not display "Please enter a valid parameter".

The FUN-PARAMETER requirement has three failure cases which I'd also like to add tests for.  These are outside the happy path, as they indicate that the user is not using the program correctly.  However, in such cases, we still need to ensure that the system is following the requirements.  Let's add additional test cases for the three failure modes.

IDENTIFIER: INVALID-PARAMETER-TEST<br>
TEST CASE: Run the program with an invalid parameter.<br>
PRECONDITIONS: None<br>
INPUT VALUES: "dog"<br>
EXECUTION STEPS: At the command line, run "catweigher dog"<br>
OUTPUT VALUES: N/A<br>
POSTCONDITIONS: The program displays "Please enter a valid parameter" and exits without further output.<br>

IDENTIFIER: NO-PARAMETER-TEST<br>
TEST CASE: Run the program without passing in a parameter.<br>
PRECONDITIONS: None<br>
INPUT VALUES: N/A<br>
EXECUTION STEPS: At the command line, run "catweigher"<br>
OUTPUT VALUES: N/A<br>
POSTCONDITIONS: The program displays "Please enter a valid parameter" and exits without further output.<br>

IDENTIFIER: TOO-MANY-PARAMETER-TEST<br>
TEST CASE: Run the program with too many parameters (specifically, four parameters).<br>
PRECONDITIONS: None<br>
INPUT VALUES: 5 6 7 8<br>
EXECUTION STEPS: At the command line, run "catweigher 5 6 7 8"<br>
OUTPUT VALUES: N/A<br>
POSTCONDITIONS: The program displays "Please enter a valid parameter" and exits without further output.<br>

At this point, there's a reasonable

