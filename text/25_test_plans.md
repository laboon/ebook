## Test Plans

Now that you understand requirements, and the basic theory and terminology of software testing, it's time to work on making __test plans__.  Test plans lay out an entire plan for testing a system under test.  They are then executed - that is, a person or automated testing tool goes through the steps listed in the test plan - and the results are compared to what actually happened.  That is, we will be checking the expected behavior of a system (what we write down in our test plan) against the observed behavior (what we see the system actually doing).

#### The Basic Layout of a Test Plan

A test plan is, at is core, simply a collection of __test cases__.  Test cases are the individual tests that make up a test plan.  Let's assume that you are creating a test plan for testing a new app that tells you if a cup of coffee is too hot to drink.  Assuming that the app marketers liked cold coffee and decided not to implement anything about the coffee temperature being too low, there are two requirements:

FUN-COFFEE-TOO-HOT. If the coffee temperature is measured at 175 degrees or higher, the app shall display the "TOO HOT" message.

FUN-COFFEE-JUST-RIGHT. If the coffee temperature is measured at less than 175 degrees, the app shall display the "JUST RIGHT" message.

How would we develop a test plan for our coffee temperature app?  There is one input - the coffee temperature measured - and two possible outputs, one of the set ["TOO HOT", "JUST RIGHT"].  We can ignore for now that I think most people would find coffee at 45 degrees Fahrenheit to definitely not be "JUST RIGHT".  A single input value and one of two possible output values is a simple case of equivalence class partitioning, so let's partition up those equivalence classes.

JUST-RIGHT: [-INF, -INF + 1, ... 173, 174] -> "JUST RIGHT"
TOO-HOT:    [175, 176, ... INF - 1, INF] -> "TOO HOT"

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

##### Identifier

Just as requirements have identifiers, test cases do as well.  These provide a short and simple way to refer to the test case.  In many instances, these are just numbers, but also could use more complex systems like the one described in the section on naming requirements.

##### Test Case (or Summary)

##### Preconditions

##### Input Values

##### Execution Steps

##### Output Values

##### Postconditions


#### Developing a Test Plan

#### Developing Test Cases

#### Executing a Test Plan

#### Test Plan / Run Tracking

#### Traceability Matrices

Now that we have a list of requirements and a test plan to test them, what else remains?  Surely, we can all go home and enjoy a beverage of our choice after a long day toiling in the data mines.  However, there is one more thing to discuss on the topic.  We have informally developed tests that we suppose will meet requirements, but we can double-check that our requirements and test plan are in sync by building a __traceability matrix__.

A traceability matrix is simply a way of determining which requirements match up with which test plans, and displaying it as an easy-to-understand diagram.  They consist of a list of the requirements (usually just the requirement identifiers) and a list of the test case numbers which correspond to those requirements (i.e., the ones that test specific aspects of that requirement).

As an example, let's return to our completed requirements specification for the coffee temperature sensing application.

FUN-COFFEE-TOO-HOT. If the coffee temperature is measured at 175 degrees Fahrenheit or higher, the app shall display the "TOO HOT" message.

FUN-COFFEE-JUST-RIGHT. If the coffee temperature is measured at less than 175 degrees Fahrenheit, but more than 130 degrees Fahrenheit, the app shall display the "JUST RIGHT" message.

FUN-COFFEE-TOO-COLD. If the coffee temperature is measured at 130 degrees Fahrenheit or less, the app shall display the "TOO COLD" message.

FUN-TEA-ERROR. If the liquid being measured is actually tea, the app shall display the "SORRY, THIS APP DOES NOT SUPPORT TEA" message.

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

It's easy to see that for each requirement, there is at least one test covering it.  If there were another requirement, say, 

FUN-COFFEE-FROZEN. If the coffee is in a solid and not a liquid state, then the app shall display "THIS COFFEE CAN ONLY BE EATEN, NOT DRUNK" message.

and we tried to create a traceability matrix, it would be very easy to see that there were no tests checking for this requirement.

```
FUN-COFFEE-TOO-HOT: 1, 2
FUN-COFFEE-JUST-RIGHT: 3, 4, 5
FUN-COFFEE-TOO-COLD: 6, 7
FUN-TEA-ERROR: 8
FUN-COFFEE-FROZEN:
```

Conversely, traceability matrices can allow us to determine if we have any "useless" tests which are not testing any specific requirements.  For example, let's say that we have created a "Test Case 9".

```
IDENTIFIER: 9
TEST CASE: Determine if app properly reads poodle temperature.
PRECONDITIONS: Poodle is alive and in reasonably good health, with a normal poodle body temperature of 101 degrees Fahrenheit.
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
??? : 9
```

Occasionally, in the "real world", there may be some sanity checks that may not officially line up with a specific requirement.  For example, if a systems engineer did not put in a specific requirement for reliability, but the test plan may include a test for ensuring that the system works even when running for an entire day.  This is certainly not a best practice, but it does happen occasionally.  If this occurs, the best course of action would be to create a requirement for reliability that it can be tested against.  

Of course, a traceability matrix provides a very simple overview of the test coverage.  The fact that every requirement has been tested does not mean that each requirement has been tested thoroughly.  For example, what if the system has issues with extremely hot coffee?  The highest temperature we checked for was 200 degrees Fahrenheit, but it may fail at 201 degrees Fahrenheit.  There's no verification in the traceability matrix itself that the tests are good, either.  If we had tested whether or not the system was meeting the FUN-COFFEE-TOO-HOT requirement by dunking the system in ice water, but said that that test case lined up with the FUN-COFFEE-TOO-HOT requirement, there's no way tell just by looking at the traceability matrix.

Traceability matrices are a good way to double-check your work and report to others outside of your team how well covered the system is from a test perspective.  In time crunches, you or your manager may decide that certain parts or functions of the system are more important to test than others, and so you may not even write tests for these less-important aspects.  Again, this is not a good practice, but at least you can use a traceability matrix to keep track of where the gaps in your testing coverage are.

Customers and management, especially in highly regulated fields such as defense contracting and medicine, may also requirement traceability matrices as a way of proving that the systems have been tested to at least a baseline level.
