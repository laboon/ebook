# Test Plans

Now that you understand requirements, and the basic theory and terminology of software testing, it's time to work on making __test plans__.  Test plans lay out an entire plan for testing a system under test.  They are then executed - that is, a person or automated testing tool goes through the steps listed in the test plan - and the results are compared to what actually happened.  That is, we will be checking the expected behavior of a system (what we write down in our test plan) against the observed behavior (what we see the system actually doing).

## The Basic Layout of a Test Plan

A test plan is, at its core, simply a collection of __test cases__.  Test cases are the individual tests that make up a test plan.  Let's assume that you are creating a test plan for testing a new app that tells you if a cup of coffee is too hot to drink.  Assuming that the app marketers liked cold coffee and decided not to implement anything about the coffee temperature being too low, there are two requirements:

* __FUN-COFFEE-TOO-HOT.__ If the coffee temperature is measured at 175 degrees Fahrenheit or higher, the app shall display the `TOO HOT` message.
* __FUN-COFFEE-JUST-RIGHT.__ If the coffee temperature is measured at less than 175 degrees Fahrenheit, the app shall display the `JUST RIGHT` message.

How would we develop a test plan for our coffee temperature app?  There is one input - the coffee temperature measured - and two possible outputs, one of the set `["TOO HOT", "JUST RIGHT"]`.  (We can ignore for now that I think most people would find coffee at 45 degrees Fahrenheit to definitely not be "JUST RIGHT".)

A single input value and one of two possible output values is a simple case of equivalence class partitioning, so let's partition up those equivalence classes.

* __JUST-RIGHT:__ [-INF, -INF + 1, ... 173, 174] -> `JUST RIGHT`
* __TOO-HOT:__    [175, 176, ... INF - 1, INF] -> `TOO HOT`

Our boundary values are 174 and 175, as they mark the division between the two equivalence classes.  Let's also use two interior values, 135 for the JUST-RIGHT class and 200 for the TOO-HOT class.  For this particular sample test plan, we will ignore the implicit boundary values of infinity and negative infinity (or the system's concept of these, MAXINT and -MAXINT).

Using these values, and a general idea of what we would like to test, we can start to create test cases.  Although different tools and companies will have different templates for entering test cases, this is a relatively standard one that can be applied or modified for most software projects.

```
IDENTIFIER:
TEST CASE:
PRECONDITIONS:
INPUT VALUES:
EXECUTION STEPS:
OUTPUT VALUES:
POSTCONDITIONS:
```

### Overview of the Fields

1. __Identifier__ - An identifier, such as "16", "DB-7", or "DATABASE-TABLE-DROP-TEST", which uniquely identifies the test case.
2. __Test Case__ - A description of the test case and what it is testing.
3. __Preconditions__ - Any preconditions for the state of the system or world before the test begins.
4. __Input Values__ - Any values input directly to the test.
5. __Execution Steps__ - The actual steps of the test, to be executed by the tester.
6. __Output Values__ - Any values output directly by the test.
7. __Postconditions__ - Any postconditions of the state of the system or world which should hold true after the test has been executed.

Don't worry if you still have some questions on these definitions.  In the following sections, we will go more deeply into all of them and provide examples.

### Identifier

Just as requirements have identifiers, test cases do as well.  These provide a short and simple way to refer to the test case.  In many instances, these are just numbers, but also could use more complex systems like the one described in the section on naming requirements.

Test plans are usually not as large as all of the requirements for a program; once they get big enough, individual tests plans are grouped under larger test suites.  Thus, often the identifier is just a number.  If you are using automated test tracking software, then it will usually auto-number these for you.

### Test Case (or Summary)

In this field, a succinct summary of what the test case is supposed to test, and how, is provided.  In this way, someone reviewing the test case can tell what exactly is meant to be tested, and sometimes how it is to be tested.  It is usually possible to determine this by a careful examination of the preconditions, input values, and execution steps, but it is usually easier for a human to just read what the test is supposed to do.

__Examples:__

1. Ensure that on-sale items can be added to the cart and will have their price automatically reduced.
2. Ensure that passing a non-numeric string will result in the square root function throwing an `InvalidNumber` exception.
3. When the system detects that the internal temperature has reached 150 degrees Fahrenheit, ensure that it displays an error message and shuts down within five seconds.
4. Ensure that if the operating system switches time zones midway through a computation, that computation will use the original time zone when reporting results.

### Preconditions

A test often requires that the system is in a certain state before the test itself can be run.  While one could theoretically consider putting the system into such a state as part of the execution of the test (see the section on Execution Steps, below), in general it makes more sense that certain __preconditions__ must be met before the test can start.  This will be necessary for many tests which are not testing mathematically pure functions.

Example preconditions include:

1. The system is already running
2. The database contains a user `Joe` with password `EXAMPLE`
3. The `SORT_ASCEND` flag is set to true
4. There are already three items in the shopping cart

Let's look a little bit further at the last example, and see why it makes more sense to have this as a precondition rather than an actual part of the test.  We would like to test that adding an item when there are already three items in the cart makes the cart display four items.  Describing the test in English, you can already see that one naturally does not mention adding the first three items.  From the perspective of this particular test, the specific steps for adding these items is irrelevant; all that matters is that at the beginning of the test, there are three items in the cart.

From a pragmatic perspective, being able to place preconditions on the test provides both flexibility and brevity to the tests.  Suppose that instead of putting the precondition that three items needed to be in the cart, we included the following execution steps in the test:

1. Search for item "1XB"
2. Select the "1XB" item
3. Click the "Add to Cart" button three times

This may work fine the first time you run the test case.  However, this test is very __fragile__ - there are numerous ways for it to break if the system changes out from underneath.  What if the "1XB" item no longer exists?  What if the Search functionality has a defect where items that start with "1" cannot be found?  What if the "Add to Cart" button name has changed?

There is a drawback from a brevity standpoint, as well.  We have just added three execution steps where there was only one precondition.  Brevity, aside from being the soul of wit, is also helpful in ensuring that the important parts of a test are focused upon.  Boilerplate text is the enemy of attention and focus.

The dividing line between preconditions and execution steps can sometimes be an art rather than a science.  In general, the more safety-critical the domain, the more precise the preconditions will be.  As an example, let's say that you are testing an image-sharing site, where all images are public and visible to any other users.  The test case involves checking that the correct image shows up on the screen when a user goes to the correct URL.   The following preconditions may be enough for this test case:

1. The user has logged in
2. The image has been posted to the URL `/pictures/foo`.

However, if we were testing banking software and were using that image display to warn of an invalid transaction, there would probably be more preconditions, and the ones that did exist would be more specific.

1. User X has logged in with password Y
2. User X has no warnings or `STOP CHECK` notices on their account
3. The savings account of user X contains $0.00.
4. The checking account of user X contains $50.00.
5. User X has no accounts with the bank other than savings and checking accounts.
6. User X has attempted to withdraw $50.01 from the user's checking account.

In both cases, the execution steps will be the same, or at least very similar - go to a URL and check that a particular image shows up.  However, the state of the system would have been much more detailed in the case of the banking software.  This is not only because the system itself is much more complex, but also because a failure would be much significant for the bank system than the image-sharing system.  In such a case, it makes sense to specify exactly what should happen and what should be in place before the execution steps.  The more exactly you write the preconditions, the easier it will be to reproduce the same situation exactly, and as we have discussed, reproducibility is the key to fixing a problem.

### Input Values

Whereas preconditions are aspects of the system that are set before the test is run, __input values__ are those values passed directly in to the functionality under test.  This difference can be a subtle one, so let's explore a few examples.

Imagine we have a sorting routine, `billSort`, which we think should be twenty times faster than any sorting algorithm out there.  However, rather than taking at face value `billSort`'s assertion that it will always produce the correct result, we are developing tests for it.  Our particular implementation uses a global variable, `SORT_ASCENDING`.  Depending on whether `SORT_ASCENDING` (a boolean flag) is set to true or false, it will either sort ascending (from the lowest value to the highest - e.g., 'a', 'b', 'c') or sort descending (from the highest value to the lowest - e.g., 'c', 'b', 'a').  If we are going to test this sorting routine, setting the flag would count as a precondition, as this is something which needs to be set up before the test.  The array `['a', 'c', 'b']` would be the input values; these values are sent directly in for testing.

Another way to think of the difference between input values and preconditions is thinking of the tests as methods.  This is probably a good exercise for you to do anyway - we'll definitely be doing more of it when we get to the chapter on unit tests!

```java
public boolean testArraySort() {

  // PRECONDITIONS
  SORT_ASCENDING = true;

  // INPUT VALUES
  int[] vals = [1, 2, 3];

  // New, improved billSort method! :)
  billSorted = billSort(vals);

  // Old, busted built-in Java sort. :(
  normalSorted = Arrays.sort(vals);

  if (Arrays.equals(billSorted, normalSorted)) {
    // Our arrays are equal, test passes!
    return true;
  } else {
    // Our arrays are not equal, test fails.
    return false;
  }
}
```

Note that because you aren't sending the `SORT_ASCENDING` flag in to the functionality under test (specifically, the `billSort()` method), then it is not considered an input value.  However, the `vals` array is passed in to the `billSort()` method, so it is considered an input value.

Isn't it possible to redesign the system so as to send in the flag as an argument to the `billSort()` method, though?

```java
  // Arguments = values array, SORT_ASCENDING flag
  billSorted = billSort(vals, true);
```

This is certainly possible, and in this case one could consider `SORT_ASCENDING` an input value as opposed to a precondition.  Whether something is a precondition or an input value often depends on the implementation of a program.  If we were writing this in a language such as Haskell, for example, where side effects are extremely limited, functions such as this would almost never have any preconditions other than 'the program is running'.

Sorting is a mathematically pure concept, but much of what a tester tests is not so straightforward.  In such cases, it can often be difficult to determine what counts as input values and what counts as preconditions.  As a heuristic, if someone selects or enters a value, as part of the execution of the test case, it should be considered an input value, otherwise it is a precondition.  For example, if the test case checks for a user logging in as different names, then the login name would be an input value.  If the test is checking for the ability to add items to a cart when logged in as a certain user, the login name would be a precondition, as it is not entered directly in the test case, but should be done before the test even starts.

### Execution Steps

Now that the preconditions and input values for a test case have been determined, it's time to actually run the test case.  The steps taken when running the test case are called the __execution steps__.  These are what the tester actually does - and must do specifically! - when running the test case.  Whereas with preconditions, it is sufficient to simply get an end result (similar to requirements, where the goal is to specify what is to be done, now how to do it), with execution steps, it is critical to follow them precisely.  Execution steps are often incredibly specific, depending on the domain, as it is of paramount importance to follow them exactly.

Let's start with a simple example.  We are testing an e-commerce software system and checking that adding one item to the cart, when the cart is already empty, will display a "1" as the number of items in the cart.  The precondition is that the cart contains zero items.  This may have been accomplished in a variety of ways: the user has never logged on before; the user is already logged on and  bought any items that were in the cart, resetting the counter; or any existing items that were in the cart have been removed without being bought.  From the point of view of this case, it does not matter how this point (i.e., the cart containing zero items) has been reached, only that it does.

Conversely, the actual execution steps should be spelled out very clearly.

1. Search for item "SAMPLE-BOX" by selecting the "Search" text box, entering `SAMPLE-BOX`, and hitting the "Search" button.
2. An item labeled "SAMPLE-BOX" should be displayed.  Click on the button labeled  "Add Item to Cart" next to the picture of the SAMPLE-BOX.
3. Inspect the "Number of Items in Cart = x" label at the top of the screen.

Note that these steps are relatively explicit.  It is important to write the steps down in enough detail that if a problem occurs, it will be easily reproducible.  If the execution steps had just mentioned to "Add an item", our tester could have chosen any item from the inventory.  If the problem is with the item selected (say, adding SAMPLE-PLANT never increments the number of items in the cart, but SAMPLE-BOX does), then it may be difficult to figure out exactly what the problem is.  Proper defect reporting can help ameliorate this issue, but it can be prevented entirely by ensuring that the execution steps are specified correctly.  Of course, it's possible to go overboard with this:

1. Move mouse cursor to pixel (170, 934) by moving right hand 0.456 inches from previous location using the computer mouse.  This location should correspond with a text box labeled "Search".
2. Apply one pound of pressure for 200 milliseconds to the left button of the mouse, using your right index finger.
3. After 200 milliseconds, quickly remove pressure from the left button of the mouse.  Ensure that a cursor now exists and is blinking at a rate of 2 Hz in the text box... (et cetera)

In general, it's best to set the level of specification to the ability and knowledge of the people who will actually be executing the tests (or, in the case of automated tests, of the programs that will actually be executing the execution steps).  If you have in-house testers that are very familiar with both the software and domain being tested, it may only be necessary to say "Set the _frobinator_ to 'FOO' using the primary dial."  This is specific enough that a user who is familiar with the system will unambiguously be able to follow the steps.  However, not everybody will be as familiar with the system as the writer of the tests.  Many times, the people who actually execute the tests are contractors, outsourced, or simply relatively new testers on the project.  For an outside tester who is not familiar with "frobinization" (and surprisingly, there are a few people out there who are not), it may be necessary to specify what needs to be done in much more detail:

1. Open the "PRIMARY" control panel by selecting "Dials... Primary" from the menu at the top of the screen.
2. Select the purple dial labeled "FROBINATOR".  Move the dial to the right from its initial position until the "STATUS" text box reads "FOO".

As a final note, there is no such thing as a _frobinator_ or _frobinization_.

### Output Values

Values that are returned directly from the functionality being tested are __output values__.  When dealing with strictly mathematical functions, these are very easy to determine - a mathematical function by definition takes in some input value or values, and sends out some output value or values.  For example, for an absolute value function, the function takes in some number x, and if x < 0, it returns -x; otherwise, it returns x.  Testing the function with -5 and checking that it returns 5, it is obvious that the input value is -5 and the output value is 5.  There are no preconditions; sending in -5 should always return 5, no matter what global variables are set, no matter what is in the database, no matter what is displayed on the screen.  There are no postconditions; the function shouldn't display anything else on the screen, or write something to the database, or set a global variable.

Once again, though, computer programs don't consist solely of mathematical functions, and so we must learn to distinguish postconditions from output values.

### Postconditions

Any side effects or other state of the system that are either caused by the functionality, or should be in effect some other way after the execution steps have been completed, but are not output values,  are __postconditions__.  Postconditions may be directly caused by the functionality under test, such as a warning message being displayed, or some data written to the database, or a global variable being set.  However, a postcondition is any condition which needs to be in place after the execution steps are complete, and may not be directly impacted by the functionality.  For example, a postcondition may be that a global variable was __not__ set, or that the system is still running, or that a thread was not killed.

### A Note: Expected Behavior vs Observed Behavior

Although we've discussed the difference between output values and postconditions at length, the fact is that in many cases the difference doesn't really matter, or is too academic to make much fuss about.  A similar argument could be made on preconditions and input values.

The principle idea to keep in mind when writing a test case is that the point of the test case is that:

```
When the system is in state X,
And the following actions Y are performed,
I expect Z to happen
```

That value Z is the crux of the test - it is the expected behavior.  It is impossible to test for something if you don't know what you expect to happen.  Just like Lewis Carroll said, "if you don't know where you're going, any road will get you there."  Similarly, when writing a test case, you need to know where you eventually want the test case to go, otherwise there's no way to check that you got to where the system should be.

## Developing a Test Plan

Before starting to write any test plan, one must think about the end goal.  How detailed does the test plan need to be?  What kind of edge cases should be checked?  What are the potential risks of unknown defects?  These answers will be very different when you are testing an online children's game or software for monitoring a nuclear reactor.  Based on the context and domain of the software under test, even software with similar requirements may require very different strategies for designing a test plan.

The simplest - and often the best - way to develop a detailed test plan is to read the requirements and determine ways to test each of them individually.  There is usually quite a bit of thought put into requirements development, and since the goal of the system is to meet the requirements, it makes sense to ensure that all of the requirements are in fact, tested.  It also provides a very straightforward path to generate a test plan.  Here's the first requirement, write some test cases; here's the second requirement, write some more test cases; repeat until all requirements are covered.

For each requirement, you should think of at least the "happy path" for that requirement and at least one test case for it.  That is, what is situation under normal operating parameters that this requirement can be shown to be met?  For example, if you have a requirement that a particular button should be enabled if a value is less than 10, and disabled if 10 or greater, at a bare minimum you would want to have tests that check if the button is enabled for a value less than 10 and one that checks that it is disabled if the value is greater than or equal to 10.  This tests both equivalence classes of the requirement (value < 10 and value >= 10).

You will also want to think of cases that test the various boundaries, as well as all the equivalence classes.  Continuing the above example, let us assume that you have a test case for the value 5 and a test case for the value 15, thus ensuring that you have at least one value for each equivalence class.  You might also want to add test cases to check the boundary between the two equivalence classes, so you add test cases for 9 and 10.

How many of these edge cases and corner cases you'd like to add, as well as how extensive a testing of interior and boundary values you would like to perform, will vary depending on the amount of time and resources that you have available for testing, the software domain, and the level of risk that your organization is comfortable taking.  Remember that testing exhaustively is, for all intents and purposes, impossible.  There is a sliding scale of how much time and energy one wants to put in to writing and executing tests, and no right answer.  Determining a compromise between speed of development and ensuring quality is a key part of the job for a software tester.

Determining test cases for non-functional requirements (quality attributes) of the system can often be difficult.  You should try to ensure that the requirements themselves are testable, and think of ways to quantify any test cases that you can think of for those requirements.

Unfortunately, however, simply having a correspondence between all requirements and a test case for each does not always mean that you have developed a good test plan.  You may have to add additional tests to ensure that requirements work together in tandem, or check for cases from the user's point of view that may not map directly to requirements or flow directly from them.  Even more importantly, you will need to gain an understanding of the context that the software exists in.  Having domain knowledge of the field can help you understand basic use cases, how the system interacts with the environment, and possible failure modes, and how users would expect the system to recover from those failure modes.  If nobody on the team understands the domain of the software, it may be worthwhile to discuss the software with a subject matter expert (SME) before writing a test plan.

Understanding the programming environment that the software is written in can also facilitate writing a test plan.  Although this technically veers into grey-box testing as opposed to black-box testing, since you as a tester will know some of the internal implementation details, it can provide valuable insight in knowing where potential errors may lurk.  Allow me to give an example.  In Java, dividing by zero, as in the code below, will throw a `java.lang.ArithmeticException`.

```java
int a = 7 / 0;
```

Therefore, when testing a program written in Java, you can assume that dividing by zero is essentially one equivalence class; if it occurs, then the same event should happen afterwards, whatever that event happens to be (e.g., perhaps the exception is caught and the message "Error Divide by Zero" is printed to the console).

JavaScript (yes, technically I mean ECMAScript 5, for anyone who wants to know the particulars) does not throw an exception when dividing by zero.  However, depending on the numerator, when the denominator is zero, you may get different results!

```javascript
> 1 / 0
Infinity

> -1 / 0
-Infinity

> 0 / 0
NaN
```

Dividing a positive value by zero returns `Infinity`, dividing by a negative number returns `-Infinity`, and dividing zero by zero returns `NaN` (Not a Number).  This means that dividing by zero, despite being one "internal equivalence class" in Java programs, is three different ones in JavaScript programs.  Knowing this, you would want to test that the program can handle all of these return values, and not assume that you had checked all edge cases simply because you had checked for dividing one by zero.  As a side note, this is an actual example from a test plan that I wrote, and it was used to find several defects.


## Executing a Test Plan

Executing a test plan is called a __test run__.  One way to think of test runs is as the equivalent of an object and a class.  Executing a test plan generates a test run similar to how instantiating a class creates an object.  The test plan is the map of where to go, whereas the test run is the voyage that the traveler makes.

Executing a test plan should be a relatively simple process, assuming that you have developed the test plan appropriately.  After all, you have spent time ensuring that the preconditions are reasonable to set up, that the input values are specified, that the execution steps are detailed enough to follow, and that output values are postconditions are feasible to test.  At this point, actually executing the tests should be a relatively mechanical process (and this is one of the reasons that automated testing is possible).  You should be able to send someone into a room with the software and the appropriate hardware to run it on, and some number of hours later, depending on how long the test plan is, the person will come out of the room with a fully-tested system.

Unfortunately, this beautiful vision does not always come to pass.  In the process of executing a test case, the test case can have different statuses.  There will be a final status for each test case, although the status is liable to change during the execution of the test run.  There is also often a "null" or "untested" status which means that that particular test case has not yet been executed.

Although there is no universal status repository, these are a representative sampling of the kinds of test cases that might be encountered in your testing career.  The names may change, but these six provide good coverage of all the situations that your test case may be in.

1. Passed
2. Failed
3. Paused
4. Running
5. Blocked
6. Error

A __passed__ test is one in which all of the expected behavior (i.e., the output values and postconditions) match the observed behavior.  Colloquially, one could say that it's a test where everything worked.

Conversely, a __failed__ test in which at least one aspect of the observed behavior was not equal to the expected behavior.  This difference could be in either the output values or the postconditions.  For example, if a square root function returned that the square root of 4 was 322, then that test case would be marked "failed".  If a test case had a postcondition that a message `ERROR: ELEPHANTS CAN'T DANCE` appears on the screen, but the error message in fact reads `ERROR: ELEPHANTS CAN'T DEFENESTRATE`, then once again the test case has failed.  Whenever a test case is marked failed, there should be a corresponding defect filed.  This could be a new defect, or it could be that a known defect has caused multiple problems - for example, errors for all animals are saying that they can't defenestrate when the actual issue is that they can't dance.  If there is no defect associated with a failed test case, then either the test case wasn't important enough to test, or the defect found wasn't important enough to file.  If either is the case, you should rethink your test case!

A __paused__ test is one that has started, but had to be put on hold for some period of time.  This allows other testers and managers to know the status of a test and the progress a tester has been made.  It also ensures that another tester doesn't step in and start doing the test that has already been started by another tester.  A test case may be paused for quotidian reasons, like the tester going to lunch, or something directly related to the system under test, such as leaving the lab to get new test data.  In any case, the assumption is that the tester will get back to working on this test as soon as he or she returns, not that the test itself cannot be executed (that is covered by the "blocked" status, below).

A __running__ test is one which has started, but has not yet completed, and thus the final result is not yet known.  This is usually used in cases where the test takes a long time to complete, and the tester would like other testers to know that it is being executed.  Although technically all tests enter a running state for a brief period of time (when the tester is executing the execution steps), unless there is some sort of automation, this status is only set when the test is long-running.

In some cases, a test cannot be executed at the present time.  This can be due to external factors (such as a piece of testing equipment not being available) or internal factors (such as a piece of functionality not being completed, or impossible to test due to other defects present in the system).  In such cases, the test can be marked as __blocked__.  This indicates that the test cannot currently be run, although it may be run in a future test case when the issues blocking its execution have been removed.

Finally, in some cases a test case simply cannot be executed, either now or in the future, due to a problem with the test case itself.  In such cases, the test status can be marked as "__error__".  Tests marked as in error could have an issue with the test contradicting the requirements, such as a requirement saying that the background color of a web page should be blue, but the system under test is actually a command-line application.  It could be a problem with the expected behavior of a program, for example, saying that the square root of 25 should result in "poodle".  Oftentimes, a test marked "error" may be the result of a simple typo, but it could point to a fundamental problem with the development team's or testing team's understanding of the software.  Test cases marked "error", unlike those marked "blocked", are not expected to be run again until the error is resolved.

## Test Plan / Run Tracking

Although you could execute a test plan for fun or for your own sense of self-improvement, in most cases you want to record what the results of the test plan were.  This can be done using custom test tracking software, a simple spreadsheet program, or even just a notebook.  In some cases, this will be necessary due to the regulatory environment, but even if it is not required, keeping track of what tests have passed and which have not will be extremely useful.

When tracking a test run, there are several pieces of information that you will want to include.

1. The date the test was executed
1. The name or other identifier (e.g., login or ID number) of the tester
1. The name or other identifier of the system under test
2. An indication of what code was under test.  This may be a tag, a link, a version number, a build number, or some other form of identification.
3. The test plan the test run corresponds with
4. The final status of each test case.  Note that temporary statuses, such as PAUSED, should be changed to the final status before finishing the test run.
5. A list of any defects filed as a result of the test case, if any, or other reasons for any status that is not PASSED.

An example of a test run might be:

```
Date: 21 May 2014
Tester Name: Jane Q. Tester
System: Meow Recording System (MRS)
Build Number: 342
Test Plan: Meow Storage Subsystem Test Plan

Results:
TEST 1: PASSED
TEST 2: PASSED
TEST 3: FAILED (Filed defect #714)
TEST 4: BLOCKED (feature not yet implemented)
TEST 5: PASSED
TEST 6: FAILED (Due to known defect #137)
TEST 7: ERROR (Apparent typo in test plan; need to verify with Systems Engineering)
TEST 8: PASSED
TEST 9: PASSED
```

If a test fails, then a defect should be filed.  A defect indicates that the system is not operating as designed; the expected behavior does not match the observed behavior.  There are cases where a defect will not be filed despite a test not passing; for example, if the test is failing due to an already-known defect, there is no need to file it again.  More detail on filing defects is included in the following chapter.

If a test is blocked, then the reason that it is blocked should be noted.  This may be something beyond the test team's control, such as the feature not being implemented yet, or it may be something that can be ameliorated, such as not having the proper equipment.  Having the rationale for the reason that a test was not completed included in the test run results not only provides valuable documentation about the status, it also may enable management or others to find a way around the problem in the future.  For example, if part of the test plan requires a specific piece of hardware, having documentation that this lack of equipment is causing tests to not be run may provide an impetus for management to buy more equipment, or for other engineers to provide alternative solutions.

Tests with the status `ERROR` should hopefully be a rarity.  If an erroneous test is found, however, it behooves the tester to note why he or she thinks that the test is in error.  An idea of how to rectify it - or at least on how to get more information on it - should be included as part of the result of that test case.

## Traceability Matrices

Now that we have a list of requirements and a test plan to test them, what else remains?  Surely, we can all go home and enjoy a beverage of our choice after a long day toiling in the data mines.  However, there is one more thing to discuss on the topic.  We have informally developed tests that we suppose will meet requirements, but we can double-check that our requirements and test plan are in sync by building a __traceability matrix__.

A traceability matrix is simply a way of determining which requirements match up with which test plans, and displaying it as an easy-to-understand diagram.  They consist of a list of the requirements (usually just the requirement identifiers) and a list of the test case numbers which correspond to those requirements (i.e., the ones that test specific aspects of that requirement).

As an example, let's return to our completed requirements specification for the coffee temperature sensing application:

* __FUN-COFFEE-TOO-HOT.__ If the coffee temperature is measured at 175 degrees Fahrenheit or higher, the app shall display the `TOO HOT` message.
* __FUN-COFFEE-JUST-RIGHT.__ If the coffee temperature is measured at less than 175 degrees Fahrenheit, but more than 130 degrees Fahrenheit, the app shall display the `JUST RIGHT` message.
* __FUN-COFFEE-TOO-COLD.__ If the coffee temperature is measured at 130 degrees Fahrenheit or less, the app shall display the `TOO COLD` message.
* __FUN-TEA-ERROR.__ If the liquid being measured is actually tea, the app shall display the `SORRY, THIS APP DOES NOT SUPPORT TEA` message.

We write down the identifiers of the requirements, and leave a space for the test plan identifiers.

```
FUN-COFFEE-TOO-HOT:
FUN-COFFEE-JUST-RIGHT:
FUN-COFFEE-TOO-COLD:
FUN-TEA-ERROR:
```

Now look through the completed test plan, and determine which test cases correspond to testing these specific requirements.  For each test case which does, write down its identifier next to the requirement.

```
FUN-COFFEE-TOO-HOT: 1, 2
FUN-COFFEE-JUST-RIGHT: 3, 4, 5
FUN-COFFEE-TOO-COLD: 6, 7
FUN-TEA-ERROR: 8
```

It's easy to see that for each requirement, there is at least one test covering it.  If there were another requirement, say:

__FUN-COFFEE-FROZEN.__ If the coffee is in a solid and not a liquid state, then the app shall display `THIS COFFEE CAN ONLY BE EATEN, NOT DRUNK` message.

and we tried to create a traceability matrix, it would be very easy to see that there were no tests checking for this requirement.

```
FUN-COFFEE-TOO-HOT: 1, 2
FUN-COFFEE-JUST-RIGHT: 3, 4, 5
FUN-COFFEE-TOO-COLD: 6, 7
FUN-TEA-ERROR: 8
FUN-COFFEE-FROZEN:
```

Conversely, traceability matrices can allow us to determine if we have any "useless" tests which are not testing any specific requirements.  For example, let's say that we have created a "Test Case 9":

```
IDENTIFIER: 9
TEST CASE: Determine if app properly reads poodle temperature.
PRECONDITIONS: Poodle is alive and in reasonably good health, with a normal poodle
    body temperature of 101 degrees Fahrenheit.
INPUT VALUES: None
EXECUTION STEPS: Point sensor at poodle for five seconds.  Read display.
OUTPUT VALUES: None
POSTCONDITIONS: "POODLE IS OK" message is displayed upon screen.
```

Our traceability matrix will once again have a gap in it, but this time on the requirements side.  Test case 9 does not match up with any of the requirements, and may be a superfluous test.

```
FUN-COFFEE-TOO-HOT: 1, 2
FUN-COFFEE-JUST-RIGHT: 3, 4, 5
FUN-COFFEE-TOO-COLD: 6, 7
FUN-TEA-ERROR: 8
???: 9
```

Occasionally, in the "real world", there may be some sanity checks that may not officially line up with a specific requirement.  For example, if a systems engineer did not put in a specific requirement for reliability, but the test plan may include a test for ensuring that the system works even when running for an entire day.  This is certainly not a best practice, but it does happen occasionally.  If this occurs, the best course of action would be to create a requirement for reliability that it can be tested against.

Of course, a traceability matrix provides a very simple overview of the test coverage.  The fact that every requirement has been tested does not mean that each requirement has been tested thoroughly.  For example, what if the system has issues with extremely hot coffee?  The highest temperature we checked for was 200 degrees Fahrenheit, but it may fail at 201 degrees Fahrenheit.  There's no verification in the traceability matrix itself that the tests are good, either.  If we had tested whether or not the system was meeting the FUN-COFFEE-TOO-HOT requirement by dunking the system in ice water, but said that that test case lined up with the FUN-COFFEE-TOO-HOT requirement, there's no way tell just by looking at the traceability matrix.

Traceability matrices are a good way to double-check your work and report to others outside of your team how well covered the system is from a test perspective.  In time crunches, you or your manager may decide that certain parts or functions of the system are more important to test than others, and so you may not even write tests for these less-important aspects.  Again, this is not a good practice, but at least you can use a traceability matrix to keep track of where the gaps in your testing coverage are.

Customers and management, especially in highly regulated fields such as defense contracting and medicine, may also requirement traceability matrices as a way of proving that the systems have been tested to at least a baseline level.
