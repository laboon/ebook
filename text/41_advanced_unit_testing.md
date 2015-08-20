# Advanced Unit Testing

Although you can go relatively far with the skills learned in the last chapter, there are still aspects of unit testing which won't be possible with those techniques.  In this chapter, we'll go further in depth on how to construct more complex test cases.

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
public class Dog {

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
public class Dog {

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
public class Dog {

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
public class BirdTest {

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

### Using Reflection to Test Private Methods in Java

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

```
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
            ReflectionFun rf = new ReflectionFun();
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
public class LaboonStuff {
    private int laboonify(int x) { return x; }
}

@Test
public void testPrivateLaboonify() {
    try {
        Method method = MathStuff.class.getDeclaredMethod("laboonify");
	method.setAccessible(true);
	LaboonStuff ls = new LaboonStuff();
        Object returnValue = method.invoke(ls, 4);
        int foo = ((Integer) returnValue).intValue();
	assertEquals(foo, 4);
    } catch (NoSuchMethodException nsmex) {
        // The method does not exist
        fail();
    }
}
```

It is plain to see that testing even simple private methods can include quite a bit of boilerplate code.  In many cases, you will probably want to wrap the private method access code in a separate helper method.

## Unit Test Structure

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
