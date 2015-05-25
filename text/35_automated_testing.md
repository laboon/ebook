## Manual vs Automated Testing

So far, we have focused mostly on manual testing; that is, we actually develop and run the test cases.  If you've done any manual testing, though, you'll know that it is time-consuming and often extremely boring for the tester who has to follow a script (it's much more fun to follow a script when acting than when running software).  If only there were some sort of machine which can be used for running repetitive tasks!

If you're testing software, then chances are very good that you have a computer available to help you with the tests, since you probably have a computer available to you to run the software that you're testing.  There are numerous tools to help you test the software more effectively and efficiently.

With automated testing, you write the tests, using some sort of programming or scripting language or tool, which are then executed by the computer.  These tests can check aspects of the program as specific as individualreturn values from methods all the way up to how an entire graphical interface should look on a screen.

#### Benefits of manual and automated testing

For your software project, should you use manual testing or automatic testing?  There are benefits and drawbacks of each form of testing.

__Benefits of Manual Testing__

1. __It's simple and straightforward.__  There's a reason that this book discussed manual testing first; it's easier to understand the concepts of testing without worrying about the particular syntax or quirks of tools or languages being used.  You're using the same software setup, or at least a very similar setup, as the user.  Most people, even ones who are not very technical, can follow a well-written test plan.  Most non-technical people would have great difficulty understanding a JUnit test, however.
2. __It's cheap.__ At least from a naive perspective, there's no additional up-front cost.  It's very easy to perform informal testing even without writing a test plan - just run the software and try thinking of base cases and edge cases on the fly.
3. __It's easy to set up.__  If you can get the software to compile and run - which hopefully you will be able to do anyways, since software which can't do so is usually not appreciated much by users - then you have everything that you need to do some basic manual testing.  Creating test harnesses, setting up test drivers, and all of the other related work for running automated tests can be bypassed.
4. __There is additional software to learn, purchase, and/or write.__ Although there are free options available for testing tools, keep in mind that purchase price is not the only price you pay when using a software product for development.  There is engineering time to be spent on learning how the tool works, the syntax of any scripting language it uses, and working around the inevitable bugs that it contains.  Researching the most appropriate testing tool to use, having test engineers learn to use it, and writing (and debugging) the scripts for it can all reduce the amount of time available for actually running the tests and thinking about test design.
5. __It's extremely flexible.__  If a user interface changes, it's relatively straightforward to modify the test plan.  If you are doing exploratory testing or other testing outside of a test plan, as long as the tester knows about the change, he or she can modify the steps taken.  Even if the tester does not know about the interface changes, it may be possible for a human being to figure out the differences just by using the software!  This is not possible in automated testing; if an interface changes, then any automated tests exercising that interface will usually fail unless the testing code has been changed to expect the new interface.  This may be a benefit, though; automated tests are far more rigid, which means that they may be better at catching unanticipated modifications.  Humans may not realize that a chance is actually unexpected and just "go with the flow."
6. __You are more likely to test things that users care about.__ Manual testing can only be done on the software running as a user would run it; it's a form of black-box testing.  By doing this, the tester is seeing the software the way a user sees it, and will tend to use the software in similar ways.  Not only does this mean that testers will gain an understanding of how the software is used, and gain domain knowledge in the area, it also means that the focus will be on aspects of the software that users care about.  Users do not care about specific values returned from specific functions buried deep within the code; they care if the final results are correct.  In manual testing, your focus will almost automatically be on the functionality of the software that the users care about the most, because the testers will also be users.
7. __Humans testing can catch issues that automated testing does not.__ Automated tests will find failures for anything that they have been written to check.  However, if they are not written to check some aspect of the software, even if it would easily noticed by the human eye, it will not note it as a failure.  As an example, imagine a simple word-counting program.  Automated tests check that it gives the correct answer in response to all sorts of different input.  However, it also sends some additional escape characters to the terminal that changes the screen's background color to purple.  This would be immediately recognizable and filed as a defect by a human tester, but unless there are automated tests checking specifically for background color of future output, it will go unnoticed.

__Drawbacks of Manual Testing__

1. __It's boring.__ Have you ever spent an entire day pressing a button, then writing down the result, then pressing a button, then writing down a result, ad nauseum?  It's a great way to burn out and alienate employees.  If that's not your goal, however, you want to keep the amount of boring tasks down to a minimum.
2. __It's often unrepeatable.__ If a tester does something slightly out of the ordinary and finds a defect, he or she may not be able to reproduce the exact steps that caused the defect to show itself.  This is to some extent ameliorated by well-defined and well-specified test plans, but even if you have extremely granular instructions, you may find it difficult to reproduce the __exact__ steps taken to get to a certain point.  For example, imagine a test case for a web browser test plan.  One of the execution steps is to "open a new tab".  However, there are multiple ways to do this; does the tester select the New Tab option from the menu, or use a keyboard shortcut, or right-click on a link and open it in a new tab?  Perhaps the defect only occurs under one of these situations.  With automated tests, you will be able to re-run the test very precisely, using the same steps each time.
3. __Some tasks are difficult or impossible to test manually.__ Let us assume that you are testing the performance of a system where one of the requirements states that there should be less than a 50 millisecond delay between receiving a request and returning a response.  Since human reaction time is more than four times the maximum delay, even if the display is instantaneous, it would be impossible to check manually.  There may be other aspects of the program which are invisible to the user, but only directly testable via programs or other tools, such as the number of threads running or the value of a variable.
4. __Human error is a possibility.__
5. __It is extremely time- and resource-intensive.__
6. __It limits you to black-box testing.__

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

In the "real world" of software development, virtually all organizations that I'm familiar with use a mixture of manual and automated tests for their applications, with the emphasis almost always falling more heavily on automated testing.  Overall, automated testing provides many benefits for modern software development, especially being able to quickly run tests after changes are made to ensure that no regression defects have occurred.  Although there is certainly an up-front cost to adding an automated testing framework, for non-trivial projects, the benefits you get from allowing the computer to do the testing work for you will quickly outweigh those drawbacks.  Conversely, releasing software relying solely on the automated tests - without ever checking how the software actually runs for a user - is done only by those organizations whose software is very far removed from user experience, who have an extremely high level of confidence in their automated tests, and/or which are especially foolhardy.

#### Concepts of writing automated tests

#### Overview of tools