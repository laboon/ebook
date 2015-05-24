## Manual vs Automated Testing

So far, we have focused mostly on manual testing; that is, we actually develop and run the test cases.  If you've done any manual testing, though, you'll know that it is time-consuming and often extremely boring for the tester who has to follow a script (it's much more fun to follow a script when acting than when running software).  If only there were some sort of machine which can be used for running repetitive tasks!

If you're testing software, then chances are very good that you have a computer available to help you with the tests, since you probably have a computer available to you to run the software that you're testing.  There are numerous tools to help you test the software more effectively and efficiently.

With automated testing, you write the tests, using some sort of programming or scripting language or tool, which are then executed by the computer.  These tests can check aspects of the program as specific as individualreturn values from methods all the way up to how an entire graphical interface should look on a screen.

#### Benefits of manual and automated testing

For your software project, should you use manual testing or automatic testing?  There are benefits and drawbacks of each form of testing.

__Benefits of Manual Testing__

1. __It's simple and straightforward.__  There's a reason that this book discussed manual testing first; it's easier to understand the concepts of testing without worrying about the particular syntax or quirks of tools or languages being used.  You're using the same software setup, or at least a very similar setup, as the user.  Most people, even ones who are not very technical, can follow a well-written test plan.  Most non-technical people would have great difficulty understanding a JUnit test, however.
2. __It's cheap.__ At least from a naive perspective, there's no additional up-front cost.
3. __It's easy to set up.__  If you can get the software to compile and run - which hopefully you will be able to do anyways, since software which can't do so is usually not appreciated much by users - then you have everything that you need to do some basic manual testing.  Creating test harnesses, setting up test drivers, and all of the other related work for running automated tests can be bypassed.
4. __There is additional software to learn, purchase, and/or write.__
5. __It's extremely flexible.__  If a user interface changes, it's relatively straightforward to modify the test plan.  If you are doing exploratory testing or other testing outside of a test plan, as long as the tester knows about the change, he or she can modify the steps taken.  Even if the tester does not know about the interface changes, it may be possible for a human being to figure out the differences just by using the software!  This is not possible in automated testing; if an interface changes, then any automated tests exercising that interface will usually fail unless the testing code has been changed to expect the new interface.  This may be a benefit, though; automated tests are far more rigid, which means that they may be better at catching unanticipated modifications.  Humans may not realize that a chance is actually unexpected and just "go with the flow."
6. __You are more likely to test things that users care about.__
7. __Humans testing can catch issues that automated testing does not.__

__Drawbacks of Manual Testing__

1. __It's boring.__
2. __It's often unrepeatable.__
3. __Some tasks are difficult or impossible to test manually.__
4. __Human error is a possibility.__
5. __It is extremely time- and resource-intensive.__

__Benefits of Automated Testing__

1. __No chance of human error during test execution.__
2. __Extremely fast test execution.__
3. __Easy to execute once the system is set up.__
4. __Repeatable__
5. __Analyzable__
6. __Less resource-internsive__
7. __Ideal for testing some aspects of the system which manual testing is bad at testing.__

__Drawbacks of Automated Testing__

1. __Requires extra setup time up-front.__
12. __May not catch some user-facing bugs__
13. __Require learning more to write tests__
14. __Requires more skilled staff__
15. __Only tests what it is looking for.__

#### The Real World

Of the two options, manual and automated testing, which is better?  "Better" is a loaded term, of course; numerous other factors will come into play when making a decision.  The decision will be influenced by epending on the application you're testing, 

1. __How important are the tests?__
2. __How much of an impact does user interface or other "intangible" aspects have on the finished product?__
3. __How often will the tests be run?__
4. __How experienced is the test team?__
5. __What is the schedule for testing?__


the domain of the system under test, how thorough a testing job you want to do, how often you plan on testing, and

In the "real world" of software development, virtually all organizations that I'm familiar with use a mixture of manual and automated tests for their applications, with the emphasis almost always falling more heavily on automated testing.  Overall, automated testing provides many benefits for modern software development, especially being able to quickly run tests after changes are made to ensure that no regression defects have occurred.  Although there is certainly an up-front cost to adding an automated testing framework, for non-trivial projects, the benefits you get from allowing the computer to do the testing work for you will quickly outweigh those drawbacks.  Conversely, releasing software relying solely on the automated tests - without ever checking how the software actually runs for a user - is done only by those organizations whose software is very far removed from user experience, who have an extremely high level of confidence in their automated tests, and/or which are especially foolhardy.

#### Concepts of writing automated tests

#### Overview of tools