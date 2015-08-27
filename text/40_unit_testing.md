# An Introduction to Unit Testing

In the last chapter, we discussed the benefits and drawbacks of automated testing compared to to manual testing.  Now, we'll start to dive in to a specific kind of automated testing, unit testing, which will enable you to test the code directly.  Unit testing can help you to ensure that individual methods and functions operate in an expected manner.

## Unit Tests: The Very Idea

What are unit tests?  In a nutshell, they test the smallest "units" of functionality, which in traditional object-oriented programming, usually means methods, functions, or objects.  It's the ultimate in white-box testing; in order to properly write unit tests, you'll need to understand the implementation of a system at a very deep level.  They allow you to check that the methods that you write do exactly what you intend them to do, given a specific input.

Some examples of unit tests are checking that:

1. A `.sort` method returns `[1, 2, 3]` when you pass in `[3, 2, 1]`
2. Passing in a null reference as an argument to a function throws an exception
3. A `.formatNumber` method returns a properly formatted number
4. Passing in a string to a function that takes an integer argument does not crash the program
5. An object has `.send` and `.receive` methods
6. Constructing an object with default parameters sets the `default` attribute to true

As you can see, these functions are all testing aspects of the software which are not directly visible to the end user.  A user is never going to be in the position of looking at a particular method or object.  They won't care about what methods an object has, or what value variables are set to, or what happens when a null pointer is passed in as an argument.  They will be able to see the _results_ of those things happening, of course, but they won't be able to specifically see the code that caused it.

Unit testing is usually done by the developer writing the code.  The developer is intimately familiar with the codebase and should know what data needs to be passed in, which edge cases and corner cases exist, what the boundary values are, etc.  Only occasionally will a white-box tester have to write unit tests in the absence of a developer.

Despite this, it's very important to understand unit testing, even if you are not a developer.  In many organizations, software testers help with unit testing procedures.  This doesn't have to mean writing them; it could be developing tools to help make writing unit tests easier, or verifying that unit tests are testing what the developer thought they were testing, or ensuring that the tests are appropriate and cover all essential cases.

We have seen earlier that the user will never directly see the aspects of the software that unit tests are testing.  What's the point, then?  Wouldn't it make more sense to just test everything from a user perspective?  That way, we'd only catch defects that impact the user.  We wouldn't have to waste time worrying about things at a low level.  It would probably be more time efficient if we didn't have to write unit tests, right?  Unfortunately, that's not the case.  There are numerous reasons for writing unit tests in addition to systems-level tests.  Note, though, that there are many kinds of defects which unit tests are not very good at catching, which is why unit testing should be only part of your testing strategy.

1. __Problems are found earlier.__ Unit tests are normally written along with the code itself.  There's no need to wait for (usually longer-running) systems tests to run, or a manual test to be developed, or for a build to get into the hands of the testers.  The developer finds problems while still developing.  Like most things, the sooner a problem is found in a process, the cheaper, faster and easier it will be to fix.

2. __Faster turnaround time.__ If a problem is found, there's no need for a build to occur, or to get into testers' hands.  The developer finds a problem when executing the unit test suite, and can immediately go back and start fixing it.  If the developer has to wait for a tea tester to look at the software, file a defect, and get assigned that defect, it may be quite a while before the developer actually fixes it.  In that time, he or she may have forgotten some implementation details, or at the very least will have probably swapped out some of the information and will require time for a context shift.  The faster a defect can be found, the faster it can be fixed.

3. __Developers understand their code.__ Writing tests allows the developer to understand what the expected behavior of the function is.  It also allows the developer to add tests for things that he or she knows might be problematic for a specific function.  For example, a developer may know that a sort function may not sort numeric strings correctly, because certain flags need to be set to ensure that it treats them as numbers instead of strings (so that "1000" is bigger than "99", which it would not be if the function treated them as strings, since "9" is bigger than "1").  A black-box tester may not realize that this is what is happening "under the hood" and so not think to test this particular edge case.

4. __Living documentation.__ The tests provide a kind of "living documentation" to the code.  They explain what a codebase is supposed to do in a different way than the actual code and any comments or documentation for the software.  Failing tests are updated; they are either removed or changed as the software itself changes.  Unlike traditional documentation, if unit tests are executed on a regular basis (such as before being merged to baseline), then it's impossible for the tests to become obsolete.  Obsolete tests generally do not pass, and this is a giant indicator to get them fixed.

5. __Alternative implementation.__ One way to think of tests is as a different implementation of the software.  In a sense, a program is just a listing of what a computer should do given certain input---*IF* `foo` is less than five, *THEN* print out "`small foo`".  *WHEN* a new bar object is created, *SET* the `baz` variable to false.  A comprehensive test suite provides a similar service, saying what the program should do in a slightly different way.  There's always room for error, but if you're implementing it twice, once as a test and once as code, then there's less chance that both will be written incorrectly.

5. __Able to tell if code changes caused issues elsewhere.__ Programs nowadays can be rather large and complicated, and it's not always easy---or even humanly possible---to know whether a change you're making will have unintended consequences elsewhere.  The author has lost count of how many times he has meant to, say, change the background color of a screen, and caused a stack overflow in some recursive function elsewhere in the code which required the background color to be green for some reason.  However, by having a relatively complete test suite, the developer can check easily if he or she is breaking anything obvious elsewhere in the codebase.  It's not foolproof, but it certainly makes it easier to avoid problems.

## An Example in Natural Language

Before diving into the code, let's examine a unit test in natural language.  Let's say that we're implementing a Linked List class, and we would like to test equality.  When we make two lists that have the same data in them, they will show as equal, even though they are not the exact same Linked List in object.  How could we specify a test for this case?

"Create two linked lists, _a_ and _b_, each with data for the nodes equal to 1 &rarr; 2 &rarr; 3.  When they are compared with the equality operator, they should be seen as equal."

We can see that there are three steps here, which correspond to some of the steps in a manual test.  This should not be surprising.  After all, the basic concepts of testing do not change, despite the level of abstraction of a particular test.  The steps are:

1. __Preconditions:__ First, we need to generate the preconditions of the test.  If you recall from several chapters back, the preconditions are those conditions which must be met before the actual test starts.  In this case, the preconditions are that we have two different linked lists, named _a_ and _b_, and that they contain the exact same data, 1 &rarr; 2 &rarr; 3, all of which are the same data type.

2. __Execution Steps:__ These make up the behavior we intend to test in the unit test. In our example, the equality operator is applied between the two linked lists _a_ and _b_, and will return a Boolean value.

3. __Expected Behavior:__ Remember that the key principle to testing software is that expected behavior should equal observed behavior.  The unit test should specify what the expected behavior is, then check if it's equal to the observed behavior (i.e., the actual result of the test).  In the world of unit testing, this is called an __assertion__---the test _asserts_ that the expected behavior is equal to the observed behavior.  In our case, our test will assert that the returned value of comparing the two linked lists is true.

## Turning Our Example Into a Unit Test

It would be entirely possible to unit test this in a "manual" fashion.  Just generate a quick program which creates a linked list _a_, creates a linked list _b_, apply the equality operator,and finally add a conditional checking if the result of that value is true.  If it is, print out "test passed!"; otherwise, print out "test failed!".  However, usually we use a testing framework to automate much of the work and ensure that we are testing in a consistent and coherent manner.

For this book, we will be using the JUnit unit testing framework.  JUnit (http://junit.org) is an instance of the xUnit testing framework, which originally comes from SUnit, a testing framework for Smalltalk.  As a side note, Smalltalk is one of those languages that was years ahead of its time.  If you want to see what the future of software engineering will be like, one of the best things to do is to go look at what cool features languages had twenty years ago that were considered too slow or too immature or too "academic".  Usually, these cool features will come back into vogue years later, with the community of whatever language they've ended up in loudly trumpeting their novelty (see: garbage collection, macros, metaprogramming).

Fuddy-duddy rants aside, the JUnit test framework allows us to create unit tests that have much of the "behind-the-scenes" work taken care of.  The developer can then focus on generating the test logic and understanding what is being tested, and instead of wasting time writing out conditionals printing "yay, test passed" or "boo, test failed".

Although we will be covering JUnit, as it is a popular and easy-to-understand testing framework, it is far from the only unit testing framework in existence.  Among many other Java testing frameworks, there is TestNG, a more fully-featured framework; JTest, which includes the ability to automatically generate unit tests; and cucumber-jvm, which helps to write tests in a more human-readable format.  All of these have their benefits and drawbacks.  If you're interested in finding more potential unit testing frameworks, just do a web search for "unit testing frameworks _your language of choice_".

Keep in mind, though, that the particular implementation of testing framework you use isn't nearly as important as the concepts you learn and can apply.  When you are reading this chapter, worry less about the syntax, and more about understanding the concepts of unit testing.  Think about how aspects of unit testing are both similar and different to concepts that you have already learned in manual testing.

The following is an implementation of a unit test checking for linked list equality, as per above.  Don't worry if you don't understand all of the code; over the next few sections it will be explained thoroughly.

```java
import static org.junit.Assert.*;

public class LinkedListTest {

    @Test
    public void testEquals123() {
        // Preconditions - a and b both have 1 -> 2 -> 3
        LinkedList<Integer> a = new LinkedList<Integer>( [1,2,3] );
        LinkedList<Integer> b = new LinkedList<Integer>( [1,2,3] );

        // Execution steps - run equality operator
        boolean result = a.equals(b);

        // Postconditions: Expected behavior - assert that result is true
        assertEquals(result, true);
    }

}
```

Looking over this test, it is easy to see the parallels with a manual test.  There are some preconditions to set up the test, some execution steps to run the test, and some postconditions, where we can check the observed behavior against the expected behavior.  As mentioned in an earlier chapter, the key concept to keep in mind when software testing is that some behavior is expected under some circumstances, and when those circumstances are reached, a test should check that the observed behavior is in fact the expected behavior.  While unit tests check smaller units of functionality than most black-box tests, the core concept remains the same.

### Preconditions

Before the test can be run, you need to set up the necessary preconditions for the test.  These are similar to preconditions in a manual test, only instead of focusing on the system as a whole, you focus on setting things up for the particular method to be called.  In the example above, the unit test is going to check that two linked lists with equal values (specifically 1 &rarr; 2 &rarr; 3) will be regarded as equal by the .equals method of a linked list object.  In order to test that two linked lists are equal, first we must created the two linked lists, and set their nodes to the same set of values.  This code simply creates them and puts them into variables `a` and `b`.  We will use these two linked lists in the next phase of the unit test.

### Execution Steps

Here is the actual equality check.  We determine whether or not `a.equals(b)` is true or not, and put the Boolean result in the variable `result`.

### Assertions

Remember that assertions are checking the expected behavior against the observed behavior of the execution steps.  Specifically, we are using the assertion `assertEquals`, which checks that the value in result should equal `true`.  If it does, the test passes and we can say that under these circumstances, the `.equals()` methods works correctly.  If it does not, then the test fails.

There are a variety of assertions one can use.  Some can be interchanged; for example, instead of asserting that `result` should be equal to `true`, we could instead of directly asserted that result should be true.  In Java:

```java
assertTrue(result);
```

A list of some of the most commonly-used assertions, along with some trivial examples of their usage, include:

1. __assertEquals:__ Assert that two values are equal to each other, e.g. `assertEquals((2 * 2), 4)`.
2. __assertTrue:__ Assert that the expression evaluates to true, e.g. `assertTrue(7 == 7)`.
3. __assertFalse:__ Assert that the expression evaluates to false, e.g. `assertFalse(2 < 1)`.
4. __assertNull:__ Assert that a value is null, e.g. `assertNull(uninitializedVariable)`.
5. __assertSame:__ Assert not only that the two values are equal, but that they point to the exact same object.  Example:
```java
Integer a = Integer(7);
Integer b = Integer(7);
Integer c = a;
assertSame(a, b); // False; values are same, but point to different object
assertSame(a, a); // True; both are the same reference to the same object
assertSame(a, c); // True; these are different references to the same object
```

Additionally, there are several "not" variants of these assertions, such `assertNotEquals`, which will check that the original assertion is not true.  For example, `assertNotEquals((1 + 1), 17)`.  In my experience, these are used much less often.  You want to check for a specific _expected_ behavior, if at all possible, not that it's _not unexpected_ behavior.  Checking that something does not exist could be an indication that the test is fragile or not thought through.  Imagine that you have written a method which will generate 19th-century Romantic poems.  You know that these poems should never start with the word "homoiconicity", so you write a test to that effect:

```java
@Test
public void testNoLispStuff() {
    String poem = PoemGenerator.generate("19-th_Century_Romantic");
    String firstWord = poem.split(" ");
    assertNotEquals(firstWord, "homoiconicity");
}
```

When your poem starts with "Behold", "Darling", or "Limpid", this test will pass.  However, it will also pass if the poem starts with "%&*()_" or "java.lang.StackOverflowError".  Test should, in general, look for _positive_ behavior as opposed to the absence of _negative_ behavior.  Imagine testing that a "welcome" box does _not_ show up on a web page.  If the URL for the page returns a `500 Internal Server Error`, the test will still pass.  Think very carefully about failure cases when testing for the absence of a particular behavior.

### Ensuring that Tests are Testing What You Expect

One of the simplest ways to do this is to first ensure that your tests fail!  While we'll go into detail on a development strategy that always calls for tests to fail first in the chapter on TDD, a quick change to a test can often prove that it's not just passing all the time because you're mistakenly asserting that `true == true`, for example.

In the linked list equality test above, what could you change to ensure that your tests are testing what you think they are testing?

What if you changed the first linked list, _a_, to contain the data 1 &rarr; 2?

```java
    @Test
    public void testEquals123() {
        LinkedList<Integer> a = new LinkedList<Integer>( [1, 2] );
        LinkedList<Integer> b = new LinkedList<Integer>( [1, 2, 3] );
        boolean result = a.equals(b);
        assertEquals(result, true);
    }
```

Or 7 &rarr; 8 &rarr; 9?

```java
    @Test
    public void testEquals123() {
        LinkedList<Integer> a = new LinkedList<Integer>( [7, 8, 9] );
        LinkedList<Integer> b = new LinkedList<Integer>( [1, 2, 3] );
        boolean result = a.equals(b);
        assertEquals(result, true);
    }
```

Or you changed the equality check to an _inequality_ check?

```java
    @Test
    public void testEquals123() {
        LinkedList<Integer> a = new LinkedList<Integer>( [1, 2, 3] );
        LinkedList<Integer> b = new LinkedList<Integer>( [1, 2, 3] );
        boolean result = !(a.equals(b);)
        assertEquals(result, true);
    }
```

In all of these instances, the test should fail.  You can then rest a little easier, knowing that your test isn't tautological (always passing, no matter what the code does).

## Problems With Unit Testing

Unit testing with the techniques we've learned so far will get us far, but won't get us all the way.  Just using the assertions and testing code that we've gone over so far, there's no way to check, for example, that a particular string will be printed out, or that a window will appear, or that another method is called... all very important things.  After all, if all methods did was return different values with different input, never displaying them to the user or interacting with the environment in any way, we'd have no way of knowing what was happening with our programs.  Our only output would be the generally increasing noisiness of the fan and heat of the CPU.

Any behavior aside from returning a value is called a __side effect__.  Displaying a window, printing some text, connecting to another computer over the network---all of these are, from a terminological perspective, side effects of computation.  Even setting a variable or writing data to disk are side effects.  Functions and methods without side effects, that only receive input from parameters, are called __pure__.  Pure functions will always return the same result given the same input values, and may be called an infinite number of times without modifying any other aspects of the system.  Functions and method with side effects, or who may present different results based on something other than values passed in as parameters, are __impure__.  Some languages, such as Haskell, make a strong differentiation between pure and impure functions, but Java does not.

An example of a pure function would be a mathematical function, such as the square root function, as in the following Java code:

```java
public double getSquareRoot(double val) {
    return Math.sqrt(val);
}
```

Assuming no floating-point rounding errors or errors of that nature, the square root of 25 will always be 5, no matter what global variables are set, no matter what time or date it is, no matter what.  There are also no side effects from calling a square root function; it's not as though a window pops up every time your system calculates a square root.

An example of an impure function would be printing out statistics from global variables, or any method which outputs something to the console or screen, or depends upon variables that are not specifically passed in.  In general, if you see a method with a void return, it's probably impure---a pure function with a void return type would be absolutely useless, since the returned value is its only way of communicating with the rest of the program.  Here is an example of an impure function:

```java
public void haveFunAtDuckPond(DuckPond duckPond) {
    System.out.println("Having fun at the duck pond");
    duckPond.haveFun();
}
```

Pure functions are usually easier to test, because passing in the same values will always return the same value, and it's easy to test for input and output with standard unit test procedures.  Impure functions are more difficult, since you may not have a return value to assert against.   Additionally, they may depend upon or modify parts of the code outside of this particular method.  Here's an example of an impure method which would be very difficult to test, since its dependencies and output are not localized.  In the following code, all variables prefixed with `_global` are defined and set external to the method:

```java
public void printAndSave() {
    String valuesToPrint = DatabaseConnector.getValues(_globalUserId);
    valuesToSave = ValuesModifier.modify(valuesToPrint);
    writeToFile(_globalLogFileName, valuesToSave);
    printToScreen(_globalScreenSettings, valuesToPrint);
}
```

Contrast this to the square root function, where we know what exactly we're passing in.   Since those are the only values the function has access to, and the only value we care about is the one that is returned, it is easy to check the results of specific calculations.  In the `printAndSave()` method, though, there are dependencies on several external variables.  Running this code once may cause everything to work properly; running it again, when those variables are set to different values, may cause everything to fail.  It's difficult to know where all to check for the expected behavior.  It's writing something to a file, it looks like, but we'll need to figure out the file name and location based on the value of the variable at a particular point in time.  We'll also need to figure out what the `_globalUserId` is to determine what the right values are, and how it will be displayed by looking at the value of `_globalScreenSetting`.  In any of these cases, they could be set by a large number of external factors, and the values of the output depend on things that we may not have direct control over.  All of these come together to make testing impure methods a much more difficult task.

This does not mean that impure functions are bad!  As we've seen, they're absolutely necessary if you want to do anything other than make your processor warm.  After all, printing anything to the screen is technically a side effect.  However, by keeping as many functions pure as possible, and limiting impure functions to certain areas, you will make testing the system much easier.  You can think of this process as "quarantining" the impure functions, so that you know where difficulties in testing might lie.

