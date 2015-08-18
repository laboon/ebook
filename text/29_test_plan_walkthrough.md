# Developing A Test Plan

Let us walk through developing a test plan for a given list of requirements for a cat-weighing system called _catweigher_ (if you want clever names for programs, this is not the paragraph to find them in).  This extremely useful program will accept one argument indicating the cat's weight in kilograms, and let us know if the cat is underweight, normal weight, or overweight.

```bash
$ catweigher 1.7
Cat Weighing System
Cat is underweight
$ catweigher 83
Cat Weighing System
Cat is overweight
```

As the test cases are developed, take note of the trade-offs that are made, and how decisions about which test cases to include are made.  Also note how we use the ideas which we explored in earlier chapters - especially in equivalence class partitioning and thinking of failure cases - are used to make a well-rounded test plan.

## Requirements

1. __FUN-PARAMETER:__ The system shall accept a single parameter, `CATWEIGHT`, which can only be a single positive floating-point value or positive integer.  If the parameter is not one of these two kinds of values, or if there is not exactly one parameter, the system shall immediately shut down with only the message "Please enter a valid parameter."
2. __FUN-STARTUP-MESSAGE:__ Upon startup, the system shall display "Cat Weighing System" upon the console.
3. __FUN-UNDERWEIGHT:__ If `CATWEIGHT` is less than 3 kilograms, then the message "Cat is underweight" shall be displayed upon the console.
4. __FUN-NORMALWEIGHT:__ If `CATWEIGHT` is equal to or greater than 3 kilograms and less than 6 kilograms, then "Cat is normal weight" shall be displayed upon the console.
5. __FUN-OVERWEIGHT:__ If `CATWEIGHT` is greater than or equal to 6 kilograms, then "Cat is overweight" shall be displayed upon the console.
6. __NF-PERF-TIME:__ The system shall display the appropriate message within two seconds of the program being executed.

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

We should first determine what the expected behavior is before continuing with writing a test plan.  This can be done by checking with the appropriate requirements analysts, systems engineers, product owners, or whoever is in charge of requirements.  If you are working on a less formal team, the correct path forward may be to make an assumption.  However, these assumptions should be noted as part of the test plan!  If one must make assumptions, they should at least be delineated clearly somewhere.  In general, however, you should avoid making assumptions; you want to know what the expected behavior is as precisely as possible.  In this case, let's assume that we went to the project manager (of __course__ _catweigher_ has a project manager) and determined that "Cat Weighing System" should not be displayed if the parameter is not valid.  In other words, the second string of output is correct.

## Plotting Out The Test Plan

I have found that a top-down approach of the requirements of the program is of the most help in creating a test plan.  By a "top-down approach", I mean that a general outline of the test plan will be generated first, and then specific details can be filled in.  This contrasts with a "bottom-up approach", where the specific details of small sections are filled in first, and as more and more small details are filled, a larger picture gradually emerges.

Looking at the requirements, I can mentally divide them into three sections:

1. Input (accepting the parameter):
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

## Fleshing Out The Test Plan

Let's start with the first section, Input.  I notice that there are several possible use cases here:

1. The user enters one valid parameter
2. The user enters no parameters
3. The user enters one parameter, but it is invalid
4. The user enters more than one parameter

In the last three use cases, the expected behavior is the same; the system shuts down and the message "Please enter a valid parameter" is displayed.  In the first use case, the system will continue on and have behavior governed by other requirements.  You can already see here that it's important to have a view of the entire system, instead of looking at requirements solely in and of themselves.  This becomes more and more difficult as the number of requirements grows and the system under test becomes more complex.

Use cases 1, 3, and 4 all have variants which could be tested.  There are numerous values for valid parameters, in the first case, which will have different behaviors as enumerated in the other requirements.  There are an essentially infinite number of single invalid parameters, anything from negative values, to imaginary numbers, to random strings of any length.  For the fourth use case, the only limit to how many parameters the user can enter is up to the operating system; he or she can enter two, three, four or more.  The second use case has no variants; there are no different ways to enter no parameters, there are no different flavors of null.

Depending on the importance of the cat-weighing program, we can decide if we want to test all possible use cases, and how many variants of the particular use case we want to test.  At a bare minimum, you probably want to test the "happy path", that is, the expected use case that a user will follow, which in our case is the user entering one valid parameter.  Other use cases can be considered failure cases, since the expected behavior is to cease execution as an invalid value or set of values has been entered.

```
IDENTIFIER: VALID-PARAMETER-TEST
TEST CASE: Run the program with a valid parameter.
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher 5"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program exits and displays normal output for a 5 kilogram cat.
    The program does not display "Please enter a valid parameter".
```

The FUN-PARAMETER requirement has three failure cases which I'd also like to add tests for.  These are outside the happy path, as they indicate that the user is not using the program correctly.  However, in such cases, we still need to ensure that the system is following the requirements.  Let's add additional test cases for the three failure modes.

```
IDENTIFIER: INVALID-PARAMETER-TEST
TEST CASE: Run the program with an invalid parameter.
PRECONDITIONS: None
INPUT VALUES: "dog"
EXECUTION STEPS: At the command line, run "catweigher dog"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program displays "Please enter a valid parameter" and exits
    without further output.

IDENTIFIER: NO-PARAMETER-TEST
TEST CASE: Run the program without passing in a parameter.
PRECONDITIONS: None
INPUT VALUES: N/A
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program displays "Please enter a valid parameter" and exits
    without further output.

IDENTIFIER: TOO-MANY-PARAMETER-TEST
TEST CASE: Run the program with too many parameters (specifically, four parameters).
PRECONDITIONS: None
INPUT VALUES: 5 6 7 8
EXECUTION STEPS: At the command line, run "catweigher 5 6 7 8"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program displays "Please enter a valid parameter" and exits
    without further output.
```

At this point, there's a reasonable amount of test coverage for this requirement.  On to the next one, FUN-STARTUP-MESSAGE, which it turns out is very simple to test.  There are two possibilities - that the system has been passed in a set of valid parameters, and that it was not passed in a set of valid parameters.  In the first case, the startup message should be displayed; in the second case, it should not, as we determined by resolving the ambiguity in the message requirements earlier.

```
IDENTIFIER: STARTUP-NO-MESSAGE-TEST
TEST CASE: Run the program without passing in a parameter,
    startup message should not appear.
PRECONDITIONS: None
INPUT VALUES: None
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program does not display the message "Cat Weighing System"
    upon the console before exiting.

IDENTIFIER: STARTUP-MESSAGE-TEST
TEST CASE: Run the program passing in a valid parameter,
    startup message should appear.
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: The program displays the message "Cat Weighing System"
    upon the console before displaying the cat's weight status.
```

We now have both possibilities covered.  Note that the postconditions focus on the specific aspect of output to be tested, instead of checking all of the output.  For example, STARTUP-MESSAGE-TEST only checks that the message is displayed, instead of what the specific weight status of a 5-kilogram cat would be.  Although it seems like a minor issue to include more detail - and might even catch a bug or two - this could lead us down a rathole in more complex programs.  If we decide to change the definition of "overweight" in the future.   If you think this is far-fetched, it has already happened for humans, at least in the United States.  In 1998, tens of millions of people suddenly became overweight due to a redefinition of the term "overweight" in regards to body-mass index (BMI).  How easy it would be to happen to our poor cats as well!  If it did, we would have to go through all of our tests one by one, ensuring that even unrelated tests did not accidentally depend upon the old weight status definitions.  By keeping our tests focused on the specific expected behavior, we ensure that our test suite does not become fragile, and easily allow future growth and modification of the software system.

It would be simple to add more edge cases, such as additional invalid and valid inputs, running on different operating systems, running with other programs in the background... the list goes on and on.  However, discretion is the better part of valor.  Spending too much time on a simple requirement like this would in all likelihood be a suboptimal use of resources.  Of course, there is always the chance that displaying a message is of the utmost important, such as a legal requirement or safety notice.  In such cases, obviously more attention would be focused on it.  While this may seem like I am avoiding giving clear rules for how much emphasis to place on each requirement, the point I want to get across is that there are no clear rules.  As a tester, you will have to decide how much to focus on each requirement, subsystem, feature, or other aspect of the system under test.  This will vary based on domain and the particular software project, and you may get it wrong.  I can provide examples and heuristics, but no book can replace the grey matter between your ears.

## The Focus of the Test Plan

Let's move on to the three weight requirements: FUN-UNDERWEIGHT, FUN-NORMALWEIGHT, and FUN-OVERWEIGHT.  This seems like a perfect time to partition the inputs and outputs into equivalence classes, as explained earlier.

```
< 3 kg -> Underweight
>= 3 kg and < 6 kg -> Normal Weight
>= 6 kg -> Overweight
```

Assuming that cats are weighed in increments of one tenth of a kilogram (something else we can verify with the systems engineers or other stakeholders), We can select the explicit boundary values: 2.9, 3, 5.9, and 6 kg.  Now let's add an interior value from each equivalence class: 1.6 kg for Underweight, 5.0 kg for Normal Weight, and 10 kg for Overweight.  We'll also  want to add in implicit boundary values, say 0 and MAXINT.  Finally, let's check some corner cases: a negative number (-13) and a non-numeric string ("quackadoodle doo").  Note how much more emphasis is put on determining a variety of input values for these requirements compared to the startup message.  Since determining the weight status of the cat is core of this application, more testing emphasis is given to it.

```
IDENTIFIER: UNDERWEIGHT-INTERNAL
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: UNDERWEIGHT-LOWER-BOUNDARY
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: UNDERWEIGHT-UPPER-BOUNDARY
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: NORMALWEIGHT-INTERNAL
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: NORMALWEIGHT-LOWER-BOUNDARY
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: NORMALWEIGHT-UPPER-BOUNDARY
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: OVERWEIGHT-INTERNAL
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: OVERWEIGHT-LOWER-BOUNDARY
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: OVERWEIGHT-UPPER-BOUNDARY
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: WEIGHTSTATUS-INVALID-NEGATIVE
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

IDENTIFIER: WEIGHTSTATUS-INVALID-STRING
TEST CASE: Run the program passing in a 
PRECONDITIONS: None
INPUT VALUES: 5
EXECUTION STEPS: At the command line, run "catweigher"
OUTPUT VALUES: N/A
POSTCONDITIONS: 

```

This can seem like a quite a bit of typing for the tests and their relative importance!  Remember, though, that the amount of documentation required will vary based on your company, legal requirements of the system under test, etc.  While this is a relatively in-depth test plan, a plucky start-up may have a simple paragraph listing of various inputs to try and no formally expected behavior of the software.  The onus may be on the tester to realize what is the proper behavior.  While this is going to put more work and require more mental effort on the part of the tester, as well as be ripe for human error to occur, the trade-off is that the test plan will be much more flexible and quick to develop.  In software testing, as in all software engineering, there are very few "absolutely right" answers, just selecting the correct trade-offs.

Depending on the culture of the company and what is expected from the test plan documentation, we could compress the test cases so that each test case describes multiple values.  While I don't recommend this process - it's relatively easy to copy and paste, and having more test cases allows you to have very specific test cases - it does save time when writing.  It also allows each test case to cover more "territory", but adds an additional step to determining where the problem lies if a test case is seen to fail.  Caveats aside, let's show an example of compressing the three "underweight" test cases into one.

```
IDENTIFIER: UNDERWEIGHT-INTERNAL
TEST CASE: Run the program passing in values that mark the cat as underweight.
PRECONDITIONS: None
INPUT VALUES: 0, 1.6, 2.9
EXECUTION STEPS: At the command line, run "catweigher n", where n is equal
    to one of the input values.
OUTPUT VALUES: N/A
POSTCONDITIONS: For each of the input values, the string "Cat is underweight"
    shall be displayed upon the console.
```


## Performance Testing

There's an entire chapter on performance testing later, but for now, let's just go through a very simple performance test.  We want to check
