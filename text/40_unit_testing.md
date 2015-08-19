# Unit Testing

In the last chapter, we discussed the benefits and drawbacks of automated testing compared to to manual testing.  Now, we'll start to dive in to a specific kind of automated testing, unit testing, which will enable you to test the code directly and ensure that individual methods and functions operate in an expected manner.

## Unit Tests: The Very Idea

What are unit tests?  In a nutshell, they test the smallest "units" of functionality, which in traditional object-oriented programming, usually means things such as methods, functions, or objects.  It's the ultimate in white-box testing; in order to properly write unit tests, you'll need to understand the implementation of a system at a very deep level.  They allow you to ensure that the code that you write does exactly what you intend it do, given a specific input.

Some examples of unit tests might be:

1. Testing that a .sort method returns `[1, 2, 3]` when you pass in `[3, 2, 1]`
2. Testing that passing in a null reference as an argument to a function throws an exception
3. Testing that a `formatNumber` method returns a properly formatted number
4. Checking that passing in a string to a function that returns an integer does not crash the program
5. Testing that an object has `.send` and `.receive` methods
6. Testing that constructing an object with default parameters sets the `.default` attribute to true

As you can see, these functions are testing things which are invisible to the end user.  A user is never going to be in the position of looking at a particular method or object.  They won't care about what methods an object has, or what value variables are set to, or what happens when a null pointer is passed in as an argument.  They will be able to see the _results_ of those things happening, of course, but they won't be able to specifically see the code that caused it.

Unit testing is usually done by the developer writing the code, as part of the software development process.  The developer is intimately familiar with the codebase and should know what data needs to be passed in, which edge cases and corner cases exist, what the boundary values are, etc.  Only occasionally will a white-box tester have to write unit tests in the absence of a developer.

However, it's very important to understand unit testing, even if you are not a developer!  In many organizations, software testers help with unit testing procedures.  This doesn't have to mean writing them; it could be developing tools to help make writing unit tests easier, or verifying that unit tests are testing what the developer thought they were testing, or ensuring that the tests are appropriate and cover all essential cases.

We have seen earlier that the user will never directly see the aspects of the software that unit tests are testing.  What's the point, then?  Wouldn't it make more sense to just test everything from a user perspective?  That way, we'd only catch bugs that impact the user.  We wouldn't have to waste time worrying about things at a low level.  It would probably be more time efficient if we didn't have to write unit tests, right?  Unfortunately, that's not the case.  There are numerous reasons for writing unit tests in addition to systems-level tests (note that this said "in addition to"... there are quite a few problems that unit testing would almost certainly not catch, which is why like all testing strategies, it isn't a silver bullet).

1. _Problems are found earlier_ - Unit tests are normally written along with the code itself.  There's no need to wait for (usually longer-running) systems tests to run, or a manual test to be developer, or even for a build to get into the hands of the testers.  The developer finds problems while still developing.  Like most things, the sooner a problem is found in a process, the cheaper it will be to fix.

2. __Faster turnaround time__ - If a problem is found, there's no need for a build to occur, or to get into testers' hands.  The developer finds a problem when executing the unit test suite, and can immediately go back and start fixing it.  If the developer has to wait for a tester to look at the software, file a defect, and get assigned that defect, it may be quite a while before the developer can actually fix it.  In that time, he or she may have forgotten some implementation details, or at the very least will have probably swapped out some of the information and will require time for a context shift.  The faster a bug can be found, the faster it can be fixed.

3. __Developers understand their code__ - Writing tests allows the developer to understand what the expected behavior of the function is.  It also allows the developer to add tests for things that he or she knows might be problematic for a specific function.  For example, a developer may know that a sort function may not sort numeric strings correctly, because certain flags need to be set to ensure that it treats them as numbers instead of strings (so that "1000" is bigger than "99", which it would not be if the function treated them as strings, since "9" is bigger than "1").  A black-box tester may not realize that this is what is happening "under the hood" and so not think to test this particular edge case.

4. __Living documentation__ - The tests provide a kind of "living documentation" to the code.  They explain what a codebase is supposed to do in a different way than the actual code and any comments or documentation for the software.  Failing tests are updated; they are either removed or changed as the software itself changes.  Unlike traditional documentation, if unit tests are executed on a regular basis (such as before being merged to baseline), then it's impossible for the tests to become obsolete.  Obsolete tests don't pass, and tests that don't pass should not be merged to baseline.

5. __Alternative implementation__ - One way to think of tests is as a different implementation of the software.  In a sense, a program is just a listing of what a computer should do given certain input - *IF* foo is less than five, *THEN* print out "small foo".  *WHEN* a new bar object is created, *SET* the baz variable to false.  A comprehensive test suite provides a similar service, saying what the program should do in a slightly different way.  There's always room for error, but if you're implementing it twice, once as a test and once as code, then there's less chance that both will be written wrong in exactly the same way.

5. __Able to tell if code changes caused issues elsewhere__ - Programs nowadays can be rather large and complicated, and it's not always easy - or even humanly possible - to know whether a change you're making will have unintended consequences elsewhere.  The author has lost count of how many times he has meant to, say, change the background color of a screen, and caused a stack overflow in some recursive function elsewhere in the code which required the background color to be green for some reason.  However, by having a relatively complete test suite, the developer can check easily if he or she is breaking anything obvious elsewhere in the codebase.  It's not foolproof, but it certainly makes it easier to avoid problems.

## An Example in Natural Language

Before diving into the code, let's examine a unit test in natural language.  Let's say that we're implementing a Linked List class, and we would like to test equality.  That way, when we make two lists that have the same data in them, they will show as equal, even though they are not the exact same Linked List in memory.  How could we specify a test for this case?

"Create two linked lists, _a_ and _b_, each with data for the nodes equal to 1 -> 2 -> 3.  When there are compared with the equality operator, they should be seen as equal."

We can see that there are three steps here, which correspond to some of the steps in a manual test.  This should not be surprising.  After all, the basic concepts of testing do not change, despite the level of abstraction of a particular test.  The steps are:

1. _Preconditions_ - First, we need to generate the preconditions of the test.  If you recall from several chapters back, the preconditions are those conditions which must be met before the actual test starts.  In this case, the preconditions are that we have two different linked lists, named _a_ and _b_, and that they contain the exact same data, 1 -> 2 -> 3, all of which are the same data type.

2. _Execution Steps_ - This is what is actually done in the test.  In our example, the equality operator is applied between the two linked lists _a_ and _b_, returning a Boolean value.

3. _Expected Behavior_ - Remember that the key principle to testing software is that expected behavior should equal observed behavior.  The unit test should specify what the expected behavior is, then check if it's equal to the observed behavior (i.e., the actual result of the test).  In the world of unit testing, this is called an _assertion_ - the test _asserts_ that the expected behavior is equal to the observed behavior.  In our case, our test will _assert_ that the returned value of comparing the two linked lists is true.

## Turning Our Example Into a Unit Test

It would be entirely possible to unit test this in a "manual" fashion.  Just generate a quick program which creates a linked list _a_, creates a linked list _b_, apply the equality operator,and finally add a conditional checking if the result of that value is true.  If it is, print out "test passed!"; otherwise, print out "test failed!".  However, usually we use a testing framework to automate much of the work and ensure that we are testing in a consistent and coherent manner.

For this book, we will be using the JUnit unit testing framework.  JUnit (http://junit.org) is an instance of the xUnit testing framework, which originally comes from SUnit, a testing framework for Smalltalk.  As a side note, Smalltalk is one of those languages that was years ahead of its time.  If you want to see what the future of software engineering will be like, one of the best things to do is to go look at what cool features languages had twenty years ago that were considered too slow or too immature or too "academic".  Usually, these cool features will come back into vogue years later, with the community of whatever language they've ended up in loudly trumpeting their novelty (see: garbage collection, macros, metaprogramming).

Fuddy-duddy rants aside, the JUnit test framework allows us to create unit tests that have much of the "behind-the-scenes" work taken care of.  The developer can then focus on generating the test logic and understanding what is being tested, and instead of wasting time writing out conditionals printing "yay, test passed!" or "boo, test failed" in the appropriate cases.

Although we will be covering JUnit, as it is a popular and easy-to-understand testing framework, it is far from the only unit testing framework in existence.  Just in the world of Java testing, there is TestNG, a more fully-featured framework; JTest, which includes the ability to automatically generate unit tests; and cucumber-jvm, which allows us to behavior-driven testing (and which we'll be discussing in our chapter on that topic).  If you're really interested, just do a search for "testing frameworks _your language of choice_".

Keep in mind, though, that the particular implementation of testing framework you use isn't nearly as important as the concepts you learn and can apply.  Worry less about the syntax, and more about understanding the concepts of unit testing and how they differ, and how they are similar to, concepts that you have already learned in manual testing.

The following is an implementation of a unit test checking for linked list equality, as per above.  Don't worry if you don't understand all of the code; over the next few sections it will be explained thoroughly.

```java
import static org.junit.Assert.*;

public class LinkedListTest

       @Test
       public void testEquals123() {

              // Preconditions - a axnd b both have 1 -> 2 -> 3
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

Before the test can be run, you need to set up the necessary preconditions for the test.  These are similar to preconditions in a manual test, only instead of focusing on the system as a whole, you focus on setting things up for the particular method to be called.  In the example above, the unit test is going to check that two linked lists with equal values (specifically 1 -> 2 -> 3) will be regarded as equal by the .equals method of a linked list object.  In order to test that two linked lists are equal, first we must created the two linked lists, and set their nodes to the same set of values.  This code simply creates them and puts them into variables `a` and `b`.  We will use these two linked lists in the next phase of the unit test.

### Execution Steps

Here is the actual equality check.  We determine whether or not `a.equals(b)` is true or not, and put the Boolean result in the variable `result`.

### Assertions

Remember that assertions are checking the expected behavior against the observed behavior of the execution steps.  Specifically, we are using the assertion `assertEquals`, which checks that the value in result should equal `true`.  If it does, the test passes and we can say that under these circumstances, the `.equals()` methods works correctly.  If it does not, then the test fails.

There are a variety of assertions one can use.  Some can be interchanged; for example, instead of asserting that `result` should be equal to `true`, we could instead of directly asserted that result should be true.  In Java:

```java
assertTrue(result);
```

A list of some of the most commonly-used assertions, along with some trivial examples of their usage, include:

1. __assertEquals__ Assert that two values are equal to each other, e.g. `assertEquals((2 * 2), 4)`.
2. __assertTrue__ Assert that the expression evaluates to true, e.g. `assertTrue(7 == 7)`.
3. __assertFalse__ Assert that the expression evaluates to false, e.g. `assertFalse(2 < 1)`.
3. __assertNull__ Assert that a value is null, e.g. `assertNull(uninitializedVariable)`.
4. __assertSame__ Assert not only that the two values are equal, but that they point to the exact same object.  Example:
```java
Integer a = Integer(7);
Integer b = Integer(7);
Integer c = a;
assertSame(a, b); // False; values are same, but point to different object
assertSame(a, a); // True; both are the same reference to the same object
assertSame(a, c); // True; these are different references to the same object
```

Additionally, there are several "not" variants of these assertions, such `assertNotEquals`, which will check that the original assertion is not true.  For example, `assertNotEquals((1 + 1), 17)`.  In my experience, these are used much less often, and are usually a code smell.  You want to check for a specific __expected__ behavior, if at all possible, not that it's __not unexpected__ behavior.

### Ensuring that Tests are Testing What You Expect

One of the simplest ways to do this is to first ensure that your tests fail!  While we'll go into detail on a development strategy that always calls for tests to fail first in the chapter on TDD, a quick change to a test can often prove that it's not just passing all the time because you're mistakenly asserting that `true == true`, for example.

In the linked list equality test above, what could you change to ensure that your tests are testing what you think they are testing?

  What if you changed the first linked list, _a_, to contain the data 1 -> 2?

```java
       @Test
       public void testEquals123() {
              LinkedList<Integer> a = new LinkedList<Integer>( [1, 2] );
	      LinkedList<Integer> b = new LinkedList<Integer>( [1, 2, 3] );
	      boolean result = a.equals(b);
	      assertEquals(result, true);
       }
```

Or 7 -> 8 -> 9?

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

In all of these instances, the test should fail.  You can then rest a little easier, knowing that your test isn't tautological, passing no matter what the code does.

## Problems?

Unit testing with the techniques we've learned so far will get us far, but won't get us all the way.  Just using the assertions and testing code that we've gone over so far, there's no way to check, for example, that a particular string will be printed out, or that a window will appear, or that another method is called... all very important things.  After all, if all methods did was return different values with different input, never displaying them to the user or interacting with the environment in any way, we'd have no way of knowing what was happening with our programs.  Our only output would be the generally increasing noisiness of the fan and heat of the CPU.

Behavior aside from returning a specific value is called a "side effect".  Displaying a window, printing some text, connecting to another computer over the network - all of these are, from a terminological perspective, side effects of computation.  Functions and methods without side effects, and that only receive input from parameters, are called "pure"; functions and method with side effects, or who may present different results based on something other than values passed in as parameters, are "impure".  Some languages, such as Haskell, make a strong differentiation between pure and impure functions, but Java does not.

An example of a pure function would be a mathematical function, such as the square root function, as in the following Java code:

```java
public double getSquareRoot(double val) {
       return Math.sqrt(val);
}
```

Assuming no floating-point rounding errors or errors of that nature, the square root of 25 will always be 5, no matter what global variables are set, no matter what time or date it is, no matter what.  There are also no side effects from calling a square root function; it's not as though a window pops up every time your system calculates a square root.

An example of an impure function would be printing out statistics from global variables, or really any method which outputs something to the console or screen, or depends upon variables that are not specifically passed in.  In general, if you see a method with a void return, it's probably impure - a pure function with a void return type would be absolutely useless, since the returned value is its only way of communicating with the rest of the program.  Here is an example of an impure function:

```java
public void haveFunAtDuckPond(DuckPond duckPond) {
    duckPond.haveFun();
}
```

Pure functions are much easier to test, because passing in the same values will always return the same value, and it's easy to test for input and output with standard unit test procedures.  Impure functions are more difficult, since you may not have a return value to assert against, and they may depend upon or modify parts of the code outside of this particular method!  Here's an example of an impure method which would be very difficult to test, since its dependencies and output are not localized.  In the following code, all variables prefixed with `_global` are defined and set external to the method.

```java
public void printAndSave() {
    String valuesToPrint = DatabaseConnector.getValues(_globalUserId);
    valuesToSave = ValuesModifier.modify(valuesToPrint);
    writeToFile(_globalLogFileName, valuesToSave);
    printToScreen(_globalScreenSettings, valuesToPrint);
}
```

As opposed to a square root function, where we know what exactly we're passing in, and thus what values the function has access to, there are dependencies on several external variables.  Running this code once may cause everything to work properly; running it again, when those variables are set to different values, may cause everything to fail.  It's difficult to know where all to check for the expected behavior.  It's writing something to a file, it looks like, but we'll need to figure out the file name and location based on the value of the variable at a particular point in time.  We'll also need to figure out what the `_globalUserId` is to determine what the right values are, and how it will be displayed by looking at the value of `_globalScreenSetting`.  In any of these cases, they could be set by a large number of external factors, and the values of the output depend on things that we may not have direct control over.  All of these come together to make testing impure methods a much more difficult task.

This does not mean that impure functions are bad!  As we've seen, they're absolutely necessary (or, at least, you need some clever way around them, like monads in Haskell) if you want to do anything other than make your processor warm.  After all, printing anything to the screen is technically a side effect.  However, by keeping as many functions pure as possible, and limiting impure functions to certain areas, you will make testing the system much easier.  I like to think of it as "quarantining" the impure functions so that I know where difficulties in testing will lie.

## Test Doubles

A unit test should be a localized test; that is, it should check the particular method or function under test, and not worry about other aspects of the system.  If there is a test failure, we want to make sure that the failure is due to the code in that particular method, not on something it relies upon.  Software is often interconnected, however, and a particular method which relies upon other methods or classes may not work correctly if those other units of code do not work correctly.

In the following method, we will have fun at a duck pond.  Calling `.haveFunAtDuckPond` with a Duck `d` will feed the duck `numFeedings` number of times, and then return the amount of fun, which directly in proportion to how many times the duck is fed.  The duck will quack each time that it is fed (note: we are feeding the duck pieces of regulation duck chow.  Don't feed ducks bread, it's not actually good for them!).  If a null duck is passed in, or the number of feedings is zero or fewer, then it simply returns 0 as the amount of fun (null ducks and negative feeding are both equally not fun).   However, let us assume that the implementation of Duck is faulty, and calling the `.quack()` method results in a `QuackingException`.

```java
public int haveFunAtDuckPond(Duck d, int numFeedings) {
    if (d == null || numFeedings <= 0) { return 0; }
    int amountOfFun = 0;
    for (int j=0; j < numFeedings; j++) {
        amountOfFun++;
        d.feed();
        d.quack();
    }
    return amountOfFun;
}
```

Even though the code in this method works perfectly for all inputs, it requires that a working duck be present.  Otherwise, any non-negative number of feedings with a valid duck will cause an exception to be thrown.  If we see a unit test for this particular method failing, we will naturally think that the problem is in this method.  Only after examining it will we understand that the problem actually lies elsewhere.  How can we test this code when the code it depends upon doesn't work?

I wouldn't have asked the question if I didn't have an answer - __test doubles__.  Test doubles are "fake" objects which you can use in your tests to "stand in" for other objects in the codebase.  This has numerous benefits aside from hiding pieces of the codebase that don't work.  Test doubles also allow you to __localize__ the source of errors.  If our tests for `haveFunAtTheDuckPond()` fail, then the problem should lie in that particular method, not in one of the classes or methods that it depends upon.

JUnit does not support test doubles directly, but you can use other libraries in order to use them.  For this (and the next few sections), we will use Mockito to enable doubles, mocks, verification, and stubbing.  I know we haven't defined these terms yet, but isn't it exciting to know what's coming next?

Here is an example of using a test double with JUnit and Mockito to test a method which relies on a test double object.  Note that the Mockito calls all test doubles "mocks", even if they don't use the capabilities of a mock object (described later in the chapter).

```java
// Class to test
public class Horse {

    public int leadTo(Water w) {
        w.drink();
        return 1;
    }

}
```

```java
// Unit test for class
import static org.junit.Assert.*;

import org.junit.*;

public class HorseTest {

    // Test that a leading a horse to water will return 1
    @Test
    public void testWaterDrinkReturnVal() {
        Horse horse = new Horse();
        // We are making a test double for water
        Water mockWater = Mockito.mock(Water.class);
        int returnVal = h.leadTo(mockWater);
        assertEquals(returnVal, 1);
    }
}

```

We have now created a "fake" object instead of passing in an actual instantiation of the Water class.  Water is now quarantined and cannot cause a failure in our code.  If the test fails, it's the fault of this particular method.  Therefore, whenever there is a failure, we'll theoretically know exactly where to look in the codebase to determine the issue.  Whenever you use doubles, however, you are also dependent upon your assumptions of how the code you depend upon is supposed to work in the actual program.  There is nothing stopping you from, say, creating test doubles that have a method that the actual class does not.  In this case, your tests will work fine but the actual program will not.

Doubles can also be used to speed up the execution of tests.  Think of a Database object which writes information to out to a database and returns the status code.  Under ordinary circumstances, since the program needs to write out to disk and perhaps even access the network.  Let's say for the sake of argument, that this takes one second.  This may not seem like an incredibly long time to wait, but multiply it by all tests that access the database; even a relatively small program may have hundreds or even thousands of unit tests.

You should never, ever, ever use a double for the current class under test!  If you do this, you're no longer testing anything, really, as you are creating a fake object for the actual thing that you are testing.

Test doubles should be used, as often as possible, when the code that you are unit testing uses a different class than the class under test.  Sometimes it will be difficult to do.  In order to minimize issues, you should pass in the object as a parameter whenever possible, as opposed to relying on class-level variables or even worse, global variables.

For example, let's refactor the following class to make it more amenable to use test doubles:

```java
public class Dog

    DogFood _df = null;

    public void setDogFood(DogFood df) {
        df = _df;
    }

    public int eatDinner() {
        _df.eat();
        return 1;
    }
}
```

If we wanted to test this, we would have to create an object and set the value separately from the actual test execution.

```java
public class DogTest {

    @Test
    public void eatDinnerTest() {
        Dog d = new Dog();
        d.setDogFood(Mockito.mock(DogFood.class));
        int returnVal = d.eatDinner();
        assertEquals(returnVal, 1);
    }

}
```

It would be even more difficult if `_df` did not have a nice setter function, but instead was created internally or only as a byproduct of an entirely different method.  For example,

```java
public class Dog

    DogFood _df  = null;
    DogDish _dd  = null;
    DogWater _dw = null;

    public void setUpDogStuff() {
        _dd = new DogDish();
        _df = new DogFood();
        _dw = new DogWater();
    }

    public int eatDinner() {
        _df.eat();
        return 1;
    }
}
```

If we were to write a test, we have no way of making doubles for the objects!  Even if we then refactored `setUpDogStuff()` to accept `DogDish`, `DogFood`, and `DogWater` parameters, we would be forced to work with additional items when all we care about is `DogFood`.

However, if we just pass in `DogFood` as a parameter to the method, like so:

```java
public class Dog

    public int eatDinner(DogFood df) {
        df.eat();
        return 1;
    }
}
```

The test would then look like:

```java
public class DogTest {

    @Test
    public void testEatDinner() {
        Dog d = new Dog();
        int returnVal = d.eatDinner(Mockito.mock(DogFood.class));
        assertEquals(returnVal, 1);
    }
}
```

This will enable much easier and much more focused testing.  You'll also note the code has several other benefits aside from increased testability.  It's easier to read and understand, it's shorter, and there are fewer chances for errors.  In the original version, it would be very easy for a programmer to forget to set the `_df` variable to a `DogFood` object at some point, causing a null pointer exception whenever the dog tried to eat.  While still possible, this is less likely when you are passing in the object directly to the method.  We'll discuss more of the benefits of writing testable code - and why testable code is good code, and vice versa - in a later chapter.

## Stubs

If doubles are fake objects, stubs are fake methods.  In the above examples, we didn't care what calling `.eat()` on the `DogFood` object did; we just didn't want it to call an actual `DogFood` object.  In many cases, though, we expect a certain return value when a method is called.  Let's modify the `.eat()` method on `DogFood` so that it returns an integer indicating how tasty the dog food is.

```java
public class Dog {

    public int eatDinner(DogFood df) {
        int tastiness = df.eatDinner();
        return tastiness;
    }

}
```

If we were just using `df` as a normal test double, then there is no telling what `df.eat()` will return.  Specifically, the answer varies by test framework - some will always return a default value, some will call out the real object, some will throw an exception.  This should just be a piece of trivia, though - you shouldn't call methods on a double object unless you have stubbed them.  The whole reason for making a test double is so that you have an object that you have specified, instead of relying on external definitions.  So let's say that our doubled `DogFood` object has a (scientifically determined) tastiness level of 13.  We can specify that whenever the .eat() method is called on our doubled object, then just return a value of 13.  We have made a __stub__ of the method.

```java
public class DogTest {

    @Test
    public void testEatDinner() {
        Dog d = new Dog();
        DogFood mockedDogFood = Mockito.mock(DogFood.class);
        mockedDogFood.when(mockedDogFood.eat()).thenReturn(13);
        int returnVal = d.eatDinner(mockedDogFood);
        assertEquals(returnVal, 13);
    }

}
```

Now when the `mockedDogFood` object has its `.eat()` method called, it will return the value 13.  Once again, we have "walled off" other methods by re-creating fakes of them that act as we think they should.  Note that we can even stub methods that don't exist, thus allowing us to not only test classes that have errors, but even ones that don't even have all of their methods written yet.

## Mocks and Verification

"Yes, yes, this is all fine," I can hear you saying, "but you didn't answer the original question!  We are still dependent on asserting on a value that is returned from a method, and thus won't be able to test methods without a return value!"  Remember the method that we wanted to test from earlier in the chapter:

```java
public class Person {
    public void haveFunAtDuckPond(DuckPond duckPond) {
       duckPond.haveFun();
    }
}
```

There is no return value and thus nothing on which to assert.  The only way to test this method is to ensure that the `.haveFun()` method on the `duckPond` object was called.  We can do this using a special kind of test double called a __mock__.  A mock object will allow you to assert that a particular method was called on the mocked object.  In the above instance, instead of asserting that a particular value is returned (since no value is ever returned), you instead can make a "meta-assertion" that `.haveFun()` is called.  This is called __verification__ since you are __verifying__ that a method has been called.

```java
public void testHaveFunAtDuckPond() {
    Person p = new Person();
    DuckPond dp = Mockito.mock(DuckPond.class);
    Mockito.verify(duckPond.haveFun(), times(1));
    p.haveFunAtDuckPond();
}
```

Note, in this case, that there is no traditional assertion.  The test case ends with the execution steps, viz., calling `.haveFunAtDuckPond()`.  The assertion is actually set when you verify that `.haveFun()` will be called one time on the mocked `DuckPond` object.

## Setup and Teardown

Oftentimes, there are particular things that you want the tests for a particular class to do before each of the tests start, and after each of the tests end.  For example, you may set up a mocked database connection, and want to use it as a precondition for for all of the tests, instead of repeating the same line of code to create it in each individual test case .  JUnit provides annotations for creating `setUp()` and `tearDown()` methods for cases such as this.  Specifically, you can add an `@Before` annotation for any methods you want to have run before each individual test case, and an `@After` annotation for any methods you want to run after.  Although you could theoretically name these methods something other than `setUp()` and `tearDown()`, these are common terms that you will see very often.

Here is an example of using `setUp()` and `tearDown()` methods.

```java
public class BirdTest

    DatabaseConnection _dbc = null;

    // Set up a mocked database connection
    @Before
    public void setUp() throws Exception {
        mockedDbc = Mockito.mock(DatabaseConnection.class);
        _dbc = setupFakeDbConnection(mockedDbc);
    }

    // Tear down our mocked database connection
    @After
    public void tearDown() throws Exception {
        _dbc = null;
    }

    // Tests that a newly created bird has a default fluffiness
    // level of 1
    @Test
    public void testFluffyBird() {
        Bird b = new Bird(_dbc);
        assertEquals(b.fluffinessLevel == 1);
    }

    // Tests that a newly created bird is pretty
    @Test
    public void testPrettyBird() {
        Bird b = new Bird(_dbc);
        assertTrue(b.isPretty());
    }
}
```

Note that `@Before` and `@After` methods are called before __each__ test case, not one time before all of them and one time after all of them.  In the above instance, `setUp` will be called twice and `tearDown` will be called twice.

Also note that while there is nothing stopping you from prefacing multiple methods with `@Before` or `@After` annotations, it's usually not necessary and will just make it more difficult to follow the code.  If you do make multiple methods with these annotations, they will run in a deterministic order (that is, if you run it again, they will run in the same order).  However, since tests should not depend upon each other, this ordering is not specified and is liable to change whenever you update your code and/or JUnit version.  It is possible to specify the order that tests will run in, using the `@FixMethodOrder` annotation, but you want to avoid doing that if at all possible.  Honestly, I feel a little guilty even writing down that it is possible.

Getting back to setup and teardown procedures specifically, if you have complicated ones, using multiple annotations is usually unnecessary. You can still use a single method, but call out to other methods as helpers, instead of annotating numerous methods.

## Testing System Output

One particular use case that `@After` and `@Before` annotations can help you with is checking for system output.  Console output is a very common item to check for, but testing for it in Java is non-intuitive.  Although it's possible to just pass in a System object with each method, which you could then mock and stub, this is not idiomatic Java code and will add lots of additional code (and most likely complexity) to your codebase.  The best solution that I have seen for checking it involves using the `setOut()` methods of `System` to put the output of `System.out` and `System.err` in `ByteArrayOutputStream`.  (Full disclosure: I saw this on Stack Overflow at [http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println](http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println).  Yes, even authors of books go online to look for answers sometimes.)

Here is an example of how to check for specific output from a Java program using this technique:

```java

public class Kangaroo {

    public void hop() {
        System.out.println("Hoppity hop!");
    }

}

public class KangarooTest {
    private ByteArrayOutputStream out = new ByteArrayOutputStream();


    @Before
    public void setUp() {
	System.setOut(new PrintStream(out));
    }

    @After
    public void tearDown() {
        System.setOut(null);
    }

    @Test
    public void testHop() {
        Kangaroo k = new Kangaroo();
        k.hop();
        assertEquals("Hoppity hop!", out.toString());
    }
}
```

## Testing Private Methods

There's quite an argument on whether or not it makes sense to test private methods.  It's a great way to start a flame war amongst software developers and testers on your favorite social networking site.  I'll provide you with the arguments of both sides, so that you can make a decision yourself, and then I'll let you know my opinion.

Those who argue that private methods should never be tested say that any calls from the rest of the program (i.e., the rest of the world from the class's standpoint) will have to come in through the public methods of the class.  Those public methods will themselves access private methods; if they don't, then what's the point of having those private methods?  By testing only the public interfaces of classes, you're minimizing the number of tests that you have and focusing on the tests and code that matter.

Those who argue that private methods should always be tested point to the fact that private methods are still code, even if they're not called directly from outside the class.  Unit testing is supposed to test functionality at the lowest levels possible, which is usually the method or function call.  If you're going to start testing higher up the abstraction ladder, then why not just do systems-level testing?

My opinion is that, like most engineering questions, the correct answer depends on what you're trying to do and what the current codebase is like.  As a side note, with most technical questions, saying "it depends" is a great way to be right, no matter what.  Let's take a look at a few examples.

Let's imagine that we're adding a cat picture taking service to Rent-A-Cat.

```java

public class Picture {

       private void setupCamera() {
       	       // ...
       }

       private void turnOffCamera() {
       	       // ...
       }

       private Image takePicture() {
               // ...
       }

       public Image takePicture() {
       	      setupCamera();
	      Image i = takePicture();
	      turnOffCamera();
	      return i;
       }

}


```

This code is relatively simple, and it's easy to see that all of the private methods will be called and tested by the public methods.  From the standpoint of a user of this class, it's easy to test that things work correctly - if a valid image is returned, then the method worked.

Now let's imagine some code inside the image transformation library might be called from the above code.

```java

public class ImageLibrary {

    public Image transform(Image image, int inSize, int outSize, String format,
      boolean color, boolean reduce, boolean dither) {
        if (inSize < outSize && format.equals("jpg") || dither == false) {
            return privateMethod1(image, inSize, outSize);
        } else if (inSize < outSize && format.equals("png") || dither == true) {
            return privateMethod2(image, inSize, outSize);
        } else if (outSize > inSize || (color == false && reduce == false) {
            return privateMethod3(image, inSize, outSize);
        } else {
            // Imagine lots more if..then..else if statements here
            // You get the idea
        }
    }

    // Lots of private methods here

}

```

Think of all the complicated tests that would be needed for this one method!  Even then, you're not focusing on the actual image transformations.  Many of your tests would just be focused on ensuring that the right method was called!

One could argue that this isn't a well-designed piece of code and should be refactored, ideally with the private methods put into, say, an `ImageTransformer` class, where the methods would be public and could easily be unit tested.  I wouldn't disagree.  However, the fact of the matter is that in the real world, there is often code like this lying around, and the tester is not always in a position to tell management that the company needs to spend a few months burning off technical debt instead of adding new features.  If your goal is to test the software, and test it well, you'll probably have to test the occasional private method.

## Using Reflection to Test Private Methods in Java

In Java, there's no way to directly call private methods from a unit test, although this is definitely not the case in other languages (such as with Ruby's `.send(:method_name)` method, which bypasses the concept of "private" entirely).  However, using the reflection library, we can "reflect" what's the structure of the class is at runtime.  The reflection library is built in to the Java language, so you don't need to install anything else to use it.

Let's give an example - if you've never worked with reflection before, it can be a bit strange.  Say we want to write a class which tells the user what methods are available in that class.  Without reflection, this is impossible in Java; how can you know what other methods exist without hard-coding them into a String or something along those lines?  However, this is very simple using reflection.

```java
import java.lang.reflect.Method;

public class ReflectionFun {

	public void printQuack() {
		System.out.println("Quack!");
	}

	public static void main(String[] args) {

		Method[] methods = ReflectionFun.class.getMethods();

		// Get all methods from class and any from superclasses callable
		// on this class.
		System.out.println("All methods:");
		for (Method method : methods){
		    System.out.println(method.getName());
		}
	}
}
```

When we run this program, we get the following output:

```
All methods:
main
printQuack
wait
wait
wait
equals
toString
hashCode
getClass
notify
notifyAll
```

This is pretty cool, we can then do things like check to see if a method exists before calling it, or let the programmer know what methods exist.  If you've ever used a language like Ruby, where you can quickly check what methods are available on an object, you can see how useful this can be.  If you're new to a codebase, and you know that you want to do something related to quacking, but you're not sure if you want to call `displayQuack()`, or `quackify()`, or `quackAlot()`, or whatever, you can do a quick method listing and see that the method you are looking for is `printQuack`.

You may have noticed that there are many more methods here than are listed in the `ReflectionFun` class.  This is because the `getMethods()` method returns a list of _all_ methods callable on an object (that is, public methods).  Since all objects in Java descend from the `Object` class, any of the public methods on the `Object` class will also appear here.

You'll also note that there are three different `wait` methods listed.  This is simply because Java considers methods with the same name but different argument lists as different methods.  Reviewing the Java API, we can see that the following three methods exist:

```java
public void wait();
public void wait(long timeout);
public void wait(long timeout, int nanos);
```

Reviewing the code above, you can see that the `methods[]` array actually contains methods as objects!  This may not seem strange in a functional language, but if you are only used to straightforward Java programming, it may seem a bit weird.  If the concept of a method or function existing as a first-class object seems strange to you, my first recommendation is to learn Haskell or another functional programming language until it seems like second nature.  If you don't particularly have the time for that, just think of them as functions that you can pick up and carry around, then do something with them at a later date, instead of having them only be in one place.

Now that we have this list of methods, we can actually invoke them by name, by passing in the name of the method to which we'd like to have a reference to the `getMethod()` method:

```java
import java.lang.reflect.Method;

public class ReflectionFun {

	public void printQuack() {
		System.out.println("Quack!");
	}

	public static void main(String[] args) {
	       try {
			System.out.println("Call public method (printQuack):");
			Method method = ReflectionFun.class.getMethod("printQuack");
			Object returnValue = method.invoke(rf);

		} catch (NoSuchMethodException nsmex) {
			System.err.println("No such method!");
		}
	}
}
```

This displays:

```
Call public method (printQuack):
Quack!
```

Using this, you could add a way to manually test and call methods, by having the user enter a string and trying to call a method by that name on the object.  The point is, we now have run-time control of what methods to call.  This is very useful for metaprogramming and programmer interfaces such as REPLs (read-eval-print-loop systems, which lets you enter some code, see the results, and repeat).  However, so far, it has not helped us much with testing private methods.  However, now that you understand reflection, some minor tweaks to our existing code and we can access them easily.

First, you can't use the `getMethod()` or `getMethods()` methods, as they only return publicly available methods.  Instead, you need to use `getDeclaredMethod()` or `getDeclaredMethods()` methods, which have two key differences.

1. They only return methods declared in that specific class.  They will not return methods defined in superclasses.
2. They return public, private, and protected methods.

Therefore, if we wanted a list of _all_ methods defined on `ReflectionFun`, we could use the `getDeclaredMethods()` method.  Just for fun, let's also add a private `printQuock()` method to go along with our public `printQuack()` method (my definition of "fun" may be slightly different than yours):


```java

import java.lang.reflect.Method;

public class ReflectionFun {

	public void printQuack() {
		System.out.println("Quack!");
	}

	public void printQuock() {
	       System.out.println("Quock!");
	}

	public static void main(String[] args) {

	       System.out.println("Declared methods:");
	       Method[] methods = ReflectionFun.class.getDeclaredMethods();
	       for(Method method : methods){
	       	       System.out.println(method.getName());
	       }

	}
}
```

The output of this program is:

```java
Declared methods:
main
printQuack
printQuock
```

Since we once again have just a list of Method objects, we can invoke them.  There's only one small snag - we first need to set that method to "accessible" before calling it, using the `setAccessible()` method.  It accepts a boolean parameter to determine whether or not the method should be accessible outside the class.

```java

import java.lang.reflect.Method;

public class ReflectionFun {

	public void printQuack() {
		System.out.println("Quack!");
	}

	private void printQuock() {
		System.out.println("Quock!");
	}

	public static void main(String[] args) {
	       try {
			System.out.println("Call private method (printQuock):");
			Method method2 = ReflectionFun.class.getDeclaredMethod("printQuock");
			method2.setAccessible(true);
			Object returnValue = method2.invoke(rf);
		} catch (NoSuchMethodException nsmex) {
			System.err.println("No such method!");
		}
	}
}
```

This will output:

```
Call private method (printQuack):
Quock!
```

We can combine this with the other unit testing we've learned earlier in the chapter to write a unit test for a private method.

```java
// TODO: TEST HERE
```

## Unit test structure

### Basic Outline

Unit tests in Java are usually grouped by class, and further by method; they mirror the structure of the program.  Since unit tests are white-box tests which interact closely with the code, this makes finding errors in the codebase based upon the particular failing test case much easier than with integration or manual tests.

### What To Test?

Exactly what you test will vary based upon the domain of software you are testing and the amount of time you have for testing, as well as organizational standards and other external factors.  Of course, stating this doesn't give you any direction at all, and similar caveats could probably be put in front of every paragraph of this book.  There are definitely some heuristics to follow, many of which follow directly from some of the items discussed when developing a test plan.

Ideally, you should look at the method and think of the various success and failure cases, determine the equivalence classes, and think about some good boundary and interior values might be to test from those equivalence classes.  You want to also focus on testing common use cases over use cases which rarely occur, at least at first.  If you are writing more safety-critical software, often it makes sense to focus on testing for failure before checking the happy path.  Personally, I often work on a base case first, and then think of possible failure cases after that.  Oftentimes, I will go back with a profiler to see what code is executed is most often and add extra test cases for that.  I may try to construct a mental model of what is called often instead of using a profiler.  I will definitely think of from where the inputs to the method are coming.  If they are from a system that I have no control over (note that this definition includes users as "systems I have no control over, and thus any kind of user input), and may be sending unanticipated values, I will definitely spend more time thinking of possible failure cases and checking for edge cases.

You don't want to create a test suite that takes so long to run that people don't run it often, but a well-designed unit test suite with appropriate doubles, mocks, stubs, and the like should run very fast even when there are many tests.  I would err on the side of creating too many tests rather than too few, at first.  As you determine how many tests are necessary for the particular piece of software you're working on, you can start making trade-offs between the amount of time for development and the amount of testing.


### Unit Tests Should Have Few Assertions (Preferably Only One) And Simple, Direct Names

When unit tests fail, they should point you exactly what went wrong, and where.  They should not be large "one size fits all" tests.   It is so fast to run a properly-written unit test (rarely taking more than a few tens of milliseconds) that the extra execution time and work involved in writing more tests is absolutely dwarfed by the amount of time that you will save when unit tests tell you exactly what went wrong when it failed.  A unit test with lots of assertions shows a lack of thought towards what exactly the unit test was supposed to check for.  Consider the following example:

```java
public class CatTest {

    @Test
    public void testCatStuff() {
        Cat c = new Cat();
        c.setDefaults();
        assertTrue(c.isAGoodKitty());
        assertEquals(c.numKittens() == 0);
        assertFalse(c.isUgly());
        assertNull(c.owner());
    }

}
```

When this test fails, what is wrong?  It could be that a newly created cat is not a good kitty, as it should be.  It could be that there's an error with the number of kittens that a newly created cat has.  It could be that the cat was erroneously assigned an owner.  You'd have to track down the particular assertion in the test code, because seeing that "cat stuff" has failed does not tell you anything.  If the name of the test case failing does not let you know what the failure is and preferably give you a very good pointer to where the failure lies, you are probably testing too much in each individual test case.  There are numerous cases where I've gone in and fixed code just based on seeing the name of the test case that failed, without ever looking at the actual test code.  This is a result of well-specified, directed tests.

### Unit Tests Should Be Independent

Unit tests should not be dependent on run order.  That is, Test-2 should not depend on any side effects or result from Test-1.  JUnit, and most other unit testing frameworks, will not run individual test cases in a predetermined order.  By avoiding dependencies on each other and allowing each test to run independently, failures are localized to a particular test.  Imagine a testing framework that is just like JUnit but runs tests sequentially.  Now imagine writing the following code:

```java
public class CatTest {

    int _whiskersLength = 5;

    @Test
    public void testGrowWhiskers() {
       Cat c = new Cat(5);
       _whiskersLength = c.growWhiskers();
       assertEquals(c.whiskersLength, 6);
    }

    @Test
    public void testWhiskersLength() {
       Cat c = new Cat(5);
       assertEquals(_whiskersLength, c.getWhiskersLength());
    }

}
```

Now imagine that we run this JUnit, which executes test cases in a random order; sometimes the second test would pass, and sometimes it would fail!

There is yet another benefit to creating tests which have no dependencies on other tests.  Independent tests can be run in parallel, possibly decreasing execution time dramatically.  On a modern multi-core machine, you may find yourself running tests many times more quickly if they can be run independently.

### Try To Make Tests Better Every Time You Touch Them

Oftentimes, you will need to work with legacy code, including code which was poorly written or does not have good test coverage.  Although it may be tempting to simply continue working in the same manner that the code was originally written, you should try to do what you can to make the testing of the code better.  Keep your code easily testable, and if necessary, build wrappers around code that is difficult to test.

There is more discussion on writing testable code in the chapter Writing Testable Code (with a title like that, what else did you expect to find in it?)

## Code Coverage

Code coverage tells you how much of the codebase is actually tested by the code.  Since defining exactly what is meant by "how much of the codebase" can be complex, there are numerous kinds of code coverage.  The simplest form of code coverage is method coverage; this measures what percentage of methods have any tests that call into them.  For example, imagine a class Turtle with two methods, `crawl()` and `eat()`.

```java
public class Turtle {

    public void crawl() { ... }

    public void eat() { ... }

}
```

If we have a test calling `crawl()`, but none calling `eat()`, then we have 50% code coverage (one of two methods are tested).  Even if we had a hundred tests calling `crawl()`, checking out all of the various different ways that a turtle can crawl, but none testing `eat()`, we still have 50% code coverage.  Only once we add at least one test checking `eat()` will we have 100% code coverage.

A more detailed form of code coverage is statement coverage.  This measures what percentage of programming statements were actually executed by at least one test.  A programming statement is the smallest "chunk" of code that still makes sense as an individual unit of code; in Java, these are generally the pieces of code separated by semicolons.  Some examples of statements include:

1. `int j = 7;`
2. `System.out.println("Bark!");`
3. `foo--;`

Note that "statement" does not mean "a line of code"!  For example, in Java, you may have a line such as:

```java
doSomething(k); k++;
```

This is actually two statements, they just happen to be on the same line.

When developers discuss "code coverage", they usually mean specifically "statement coverage".  Compared to method coverage, using statement coverage provides vastly more granularity in knowing what parts of the codebase have actually been tested.  For example, let's extend out our Turtle class so that we can see what the code is like inside the various methods.

```java
public class Turtle {

    CurrentLocation _loc = World.getCurrentLocation();

    GroundType _g = World.getGroundType(_loc);

    public void crawl() {
       if (_g == DIRT) {
           move(SLOWLY);
       } else if (_g == GRASS) {
           eat();
           move(MORE_SLOWLY);
       } else {
           move(EVEN_MORE_SLOWLY);
       }
    }

    public void eat() {
       System.out.println("Yum yum");
    }

}
```

Using method coverage, having just one test for `eat()` or just one for `crawl()` would show 50% code coverage.  This hides the fact that `crawl()` is much more complicated than `eat()` and any single test would miss several different possible outcomes, whereas eat() can be tested very well with just one test.  It also would not let us know what particular lines were not tested - we would have to examine the test code to determine whether it was testing that the turtle could move on dirt, grass, or whatever else.  Statement coverage output can tell us exactly which lines were never executed during a test run, so we know exactly what kinds of tests to add to ensure that we are checking that each line has been tested at least once.

There are other variants of code coverage such as branch coverage, which measures which percentage of conditionals (if statements, case statements, etc.) have been tested.  However, these other kinds of code coverage are usually only used for much more specialized testing.  Chances are that you will deal with the statement and method coverage much more often.

Having a statement or method covered by tests does not mean that all defects in that unit of code have been found by the test!  It's easy to imagine defects slipping through method coverage.  In our Turtle example, if there was some sort of problem when the turtle was on grass, but our test only checked the case that the turtle was on dirt, we can see how method coverage would say that `crawl()` has been checked but defects can still creep through.  At a lower level of abstraction, statement coverage does not check for all variations in the way a particular statement could be executed.  Let's consider the following single-method class and its associated test case:

```java
public class Cow {

    public int moo(int mooLevel) {
        int timesToMoo = Math.ceil(100 / mooLevel);
        for (int j=0; j < timesToMoo; j++) {
            System.out.println("moo!");
        }
        return timesToMoo;
    }
}

public class CowTest {

    @Test
    public void mooTest() {
        Cow c = new Cow();
        int mooTimes = c.moo(20);
        assertEquals(mooTimes, 5);
    }

}
```

From a code coverage perspective, we have 100% code coverage and 100% method coverage - the only method in the class is called from a test, and every statement in the method is executed.  However, calling `moo()` with a `mooLevel` parameter of 0 will cause a `DivideByZeroException` to be thrown.  This defect will not be discovered by the test case, despite every statement being executed.  Although not foolproof, ensuring that you've checked every equivalence class may help to ameliorate situations like this somewhat.

Code coverage is a powerful tool, but it's not a silver bullet.  It's a way to see what areas of the codebase might need more testing, but it should not assure you that any code that has been covered is guaranteed free of defects.

## Unit Testing as Part of a Complete Testing Plan

Just like your favorite nutritionally-suspect cereals, unit testing should not be your entire breakfast, or your entire test plan.  Unit testing is great for checking individual methods and low-level functionality, but it is not good at seeing how everything fits together.  It's even worse when trying to determine what the end product will look like; all of the individual methods may work, but together they form something which doesn't meet any of the requirements.

When you are testing, you should remember to perform some manual testing, integration testing, and depending upon your needs, other kinds of testing such as security testing or performance testing.  Relying heavily on one particular kind of testing is a recipe for missing important defects.
