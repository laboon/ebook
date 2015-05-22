## Manual vs Automated Testing

So far, we have focused mostly on manual testing; that is, we actually develop and run the test cases.  If you've done any manual testing, though, you'll know that it is time-consuming and often extremely boring for the tester who has to follow a script (it's much more fun to follow a script when acting than when running software).  If only there were some sort of machine which can be used for running repetitive tasks!

If you're testing software, then chances are very good that you have a computer available to help you with the tests, since you probably have a computer available to you to run the software that you're testing.  There are numerous tools to help you test the software more effectively and efficiently.

With automated testing, you write the tests, using some sort of programming or scripting language or tool, which are then executed by the computer.  These tests can check aspects of the program as specific as individualreturn values from methods all the way up to how an entire graphical interface should look on a screen.

#### Benefits of manual and automated testing

For your software project, should you use manual testing or automatic testing?  There are benefits and drawbacks of each form of testing.

__Benefits of Manual Testing__

1. It's simple and straightforward.  There's a reason that this book discussed manual testing first; it's easier to understand the concepts of testing without worrying about the particular syntax or quirks of tools or languages being used.  You're using the same software setup, or at least a very similar setup, as the user.  Most people, even ones who are not very technical, can follow a well-written test plan.  Most non-technical people would have great difficulty understanding a JUnit test, however.
2. It's cheap.
3. It's easy to set up.
4. There is additional software to learn, purchase, or write.
5. It's extremely flexible.
6. You can focus on things that users care about.
7. Humans testing can catch issues that automated testing does not.

__Drawbacks of Manual Testing__

1. It's boring.
2. It's often unrepeatable.
3. Some tasks are difficult or impossible to test manually.
4. Human error is a possibility.
5. It is extremely time- and resource-intensive.

__Benefits of Automated Testing__

1. No chance of human error during test execution.
2. Extremely fast test execution.
3. Easy to execute once the system is set up.
4. Repeatable
5. Analyzable
6. Less resource-internsive
7. Ideal for testing some aspects of the system which manual testing is bad at testing.

__Drawbacks of Automated Testing__

1. Requires extra setup time up-front.
2. May not catch some user-facing bugs
3. Require learning more to write tests
4. Requires more skilled staff
5. Only tests what it is looking for.

#### Concepts of writing automated tests

#### Overview of tools