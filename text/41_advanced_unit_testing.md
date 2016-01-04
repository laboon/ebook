# Advanced Unit Testing

Although you can go relatively far with the skills learned in the last chapter, there are still aspects of unit testing which won't be possible with those techniques.  In this chapter, we'll go further in depth on how to construct more complex test cases.

## Test Doubles

A unit test should be a localized test; that is, it should check the particular method or function under test, and not worry about other aspects of the system.  If there is a test failure, we want to make sure that the failure is due to the code in that particular method, not something else which that method relies upon.  Software is often interconnected, and a particular method which relies upon other methods or classes may not work correctly if those other units of code do not work correctly.

In the following method, we will have fun at a duck pond.  Calling `.haveFunAtDuckPond()` with a `Duck d` will feed the duck `numFeedings` number of times.  The method will then return the amount of fun, which is directly in proportion to how many times the duck is fed.  The duck will quack each time that it is fed.  Note that we are feeding the duck pieces of regulation duck chow.  Don't feed ducks bread, it's not actually good for them!  If a null duck is passed in, or the number of feedings is zero or fewer, then it simply returns 0 as the amount of fun (null ducks and negative feeding are both equally not fun).   Let us further assume that the implementation of `Duck` is faulty, and calling the `.quack()` method results in a `QuackingException`:

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

I wouldn't have asked the question if I didn't have an answer---__test doubles__.  Test doubles are "fake" objects which you can use in your tests to "stand in" for other objects in the codebase.  This has numerous benefits aside from hiding pieces of the codebase that don't work.  Test doubles also allow you to localize the source of errors.  If our tests for `haveFunAtTheDuckPond()` fail, then the problem should lie in that particular method, not in one of the classes or methods that the method depends upon.

JUnit does not support test doubles directly, but there are libraries that you can install alongside JUnit that do.  For the next few sections, we will use Mockito to enable doubles, mocks, verification, and stubbing.  I know we haven't defined these terms yet, but isn't it exciting to know what's coming next?

Here is an example of using a test double with JUnit and Mockito to test a method which relies on a test double object.  Note that the Mockito calls all test doubles "mocks", even if they don't use the capabilities of a mock object (described later in the chapter):

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
        assertEquals(1, returnVal);
    }

}
```

We have now created a "fake" object instead of passing in an actual instantiation of the Water class.  Water is now "quarantined" and cannot cause a failure in our code.  If the test fails, it's the fault of this particular method.  Therefore, whenever there is a failure, we'll know exactly where to look in the codebase to determine the issue.  Whenever you use doubles, however, you are also dependent upon your assumptions of how the code you depend upon is supposed to work in the actual program.  There is nothing stopping you from, say, creating test doubles that have a method that the actual class does not.  In this case, your tests will work fine but the actual program will not.

Doubles can also be used to speed up the execution of tests.  Think of a Database object which writes information to out to a database and returns the status code.  Under ordinary circumstances, the program needs to write out to disk and perhaps even access the network.  Let's say that this takes one second.  This may not seem like an incredibly long time to wait, but multiply it by all the tests that access the database.  Even a relatively small program may have hundreds or even thousands of unit tests, adding minutes or hours to the amount of time each test run takes.

You should never, ever, ever use a double for the current class under test!  If you do this, you're no longer testing anything, really, as you are creating a fake object for the actual thing that you are testing.

Test doubles should be used, as often as possible, when the code that you are unit testing uses a different class than the class under test.  Sometimes it will be difficult to do.  In order to minimize issues, you should pass in the object as a parameter whenever possible, as opposed to relying on member variables or global variables.  Even worse, and more common, are methods that generate and use objects entirely internally.  It is often not possible to use test doubles for these methods.  Let's take a look at an example of this:

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

If we were to write a test for this class, we have no way of making doubles for the objects internal to the class!  Even if we then refactored `setUpDogStuff()` to accept `DogDish`, `DogFood`, and `DogWater` parameters, we would be forced to work with additional items when all we care about is `DogFood`.

Let's refactor the method a bit to make it somewhat more amenable to test doubles:

```java
public class Dog {

    DogFood _df = null;
    DogDish _dd  = null;
    DogWater _dw = null;

    public void setDogFood(DogFood df) {
        df = _df;
    }

    public void setDogDish(DogDish dd) {
        dd = _dd;
    }

    public void setDogWater(DogWater dw) {
        dw = _dw;
    }

    public int eatDinner() {
        _df.eat();
        return 1;
    }

}
```

If we wanted to test this, we would have to create an object and set the value separately from the actual test execution:

```java
public class DogTest {

    @Test
    public void eatDinnerTest() {
        Dog d = new Dog();
        d.setDogFood(Mockito.mock(DogFood.class));
        int returnVal = d.eatDinner();
        assertEquals(1, returnVal);
    }

}
```

This is better, but still not ideal, as extra statements are still required to set up the test.  If, instead, we just pass in `DogFood` as a parameter to the method, like so:

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
        assertEquals(1, returnVal);
    }

}
```

This will enable much easier and much more focused testing.  You'll also note the code has several other benefits aside from increased testability.  It's easier to read and understand, it's shorter, and there are fewer chances for errors.  In the original version, it would be very easy for a programmer to forget to set the `_df` variable to a `DogFood` object at some point, causing a null pointer exception whenever the dog tried to eat.  While still possible, this is less likely when you are passing in the object directly to the method.  We'll discuss more of the benefits of writing testable code---and why testable code is good code, and vice versa---in a later chapter.

## Stubs

If doubles are fake objects, stubs are fake methods.  In the above examples, we didn't care what calling `.eat()` on the `DogFood` object did; we just didn't want it to call an actual `DogFood` object.  In many cases, though, we expect a certain return value when a method is called.  Let's modify the `.eat()` method on `DogFood` so that it returns an integer indicating how tasty the dog food is:

```java
public class Dog {

    public int eatDinner(DogFood df) {
        int tastiness = df.eatDinner();
        return tastiness;
    }

}
```

If we were just using `df` as a normal test double, then there is no telling what `df.eat()` will return.  Specifically, the answer varies by test framework---some will always return a default value, some will call out the real object, some will throw an exception.  This should just be a piece of trivia, though---you shouldn't call methods on a double object unless you have stubbed them.  The whole reason for making a test double is so that you have an object that you have specified, instead of relying on external definitions.  So let's say that our doubled `DogFood` object has a (scientifically determined) tastiness level of 13.  We can specify that whenever the `.eat()` method is called on our doubled object, then just return a value of 13.  We have made a __stub__ of the method:

```java
public class DogTest {

    @Test
    public void testEatDinner() {
        Dog d = new Dog();
        DogFood mockedDogFood = Mockito.mock(DogFood.class);
        when(mockedDogFood.eat()).thenReturn(13);
        int returnVal = d.eatDinner(mockedDogFood);
        assertEquals(13, returnVal);
    }

}
```

Now when the `mockedDogFood` object has its `.eat()` method called, it will return the value 13.  Once again, we have quarantined the other methods by re-creating fakes of them that act as we think they should.  In some languages, although not Java, we can even stub methods that don't exist.  This allows us to not only test classes that have errors, but even ones that don't have all of their methods written yet.

## Mocks and Verification

"Yes, yes, this is all fine," I can hear you saying, "but you didn't answer the original question!  We are still dependent on asserting on a value that is returned from a method, and thus won't be able to test methods without a return value!"  Remember the impure method `goToCatCafe` that we wanted to test from in the last chapter:

```java
public void goToCatCafe(CatCafe catCafe) {
    System.out.println("Petting cats at a Cat Café!");
    catCafe.haveFun();
}
```

There is no return value and thus nothing on which to assert.  The only way to test this method is to ensure that the `.haveFun()` method on the `duckPond` object was called.  We can do this using a special kind of test double called a __mock__.  A mock object will allow you to assert that a particular method was called on the mocked object.  In the above instance, instead of asserting that a particular value is returned (since no value is ever returned), you instead can make a "meta-assertion" that `.haveFun()` is called.  This is called __verification__ since you are _verifying_ that a method has been called.  Note that this kind of verification has nothing to do with the kind of verification that means "checking that the software is right".  They are two different concepts that happen to share the same word.

```java
public void testHaveFunAtDuckPond() {

    Person p = new Person();
    DuckPond dp = Mockito.mock(DuckPond.class);
    p.haveFunAtDuckPond(dp);
    verify(dp.haveFun(), times(1));

}
```

Note, in this case, that there is no traditional assertion.  The test case ends with the execution steps, viz., calling `.haveFunAtDuckPond()`.  The assertion is actually set when you verify that `.haveFun()` will be called one time on the mocked `DuckPond` object.

## Fakes

Sometimes, you need to want to have a test which depends on an object, and will require complex or non-performant behavior.  In this case, you can use a __fake__.  A fake is a special kind of test double which acts similarly to the regular object.  However, it is written to be a part of the test, meaning that it runs faster and simpler.  For example, you may remove any parts of the code which write to disk (always a slowdown when writing tests).  You may have it perform a simpler calculation than a very time-consuming one.  You may reduce that object's dependencies on other objects.

Fakes require more work to create than a simple test double, since they are a "lite" version of an object instead of simply specifying its external behavior.  However, this allows you to perform more intricate tests than the relatively simple ones possible with test doubles.  Suppose your DuckPond class is as follows:

```java
public class DuckPond extends Pond {

   private int _funLevel = 0;

   public void haveFun() {
      _funLevel++;
   }

   public void haveUltraFun() {
      int funMultiplier = super.retrieveUltraLevelFromDatabase();
      _funLevel += funMultiplier * _funLevel;
   }

   public int getFunLevel() {
      return _funLevel;
   }
   
}
```

Let's set aside any issues one might have about how this code is written, and how it might be refactored to make it more testable.  In this case, calling haveUltraFun() requires querying the database, which is called from a method from DuckPond's superclass.  However, this also modifies the `_funLevel` variable, based on the value it received from the `retrieveUltraLevelFromDatabase()` call.  The value of the variable `_funLevel` is going to depend both on that call to the database as well as how many times `haveFun()` and `haveUltraFun()` have been called.  While it would be possible to just make a test double that returns specific values, adding in this behavior for a test which called multiple methods on a DuckPond object might add up to lots of extra work.  Even worse, this work might have to be replicated in multiple tests.

Using DuckPond as-is also means that every call to `haveUltraFun()` will result in a dramatic slowdown in tests.  Remember that calls to the disk or network, since they make take an order of magnitude or longer in time than the test would take otherwise, are discouraged in unit testing.  

To get around this performance issue, let's create a fake version of the object, which we can use in our tests later.  This fake version will be a "stripped-down" version of DuckPond, but will keep the general behavior.

```java
public class FakeDuckPond extends Pond {

   private int _funLevel = 0;

   public void haveFun() {
      _funLevel++;
   }

   public void haveUltraFun() {
      // REMOVED DATABASE CALL
      _funLevel += 5 * _funLevel;
   }

   public int getFunLevel() {
      return _funLevel;
   }
   
}
```

We are now assuming that the `funMultiplier` variable retrieved from the database will always be 5.  This will both speed up tests (by removing the database read) and simplify calculations (as well as help us understand what the expected behavior should be).  However, unlike with a traditional test double or mock, we don't have to specify what the behavior should be externally.  The class itself will determine the (simplified) behavior.

## Setup and Teardown

Oftentimes, there are particular things that you want the tests for a particular class to do before each of the tests start, and after each of the tests end.  For example, you may set up a mocked database connection, and want to use it as a precondition for all of the tests, instead of repeating the same line of code to create it in each individual test case.  JUnit provides annotations for creating `setUp()` and `tearDown()` methods for cases such as this.  Specifically, you can add an `@Before` annotation for any methods you want to have run before each individual test case, and an `@After` annotation for any methods you want to run after.  Although you could theoretically name these methods something other than `setUp()` and `tearDown()`, these are common terms that you will see very often.

Here is an example of using `setUp()` and `tearDown()` methods:

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
        assertEquals(1, b.fluffinessLevel);
    }

    // Tests that a newly created bird is pretty
    @Test
    public void testPrettyBird() {
        Bird b = new Bird(_dbc);
        assertTrue(b.isPretty());
    }

}
```

Note that `@Before` and `@After` methods are called before _each_ test case, not one time before all of them and one time after all of them.  In the above instance, `setUp` will be called twice and `tearDown` will be called twice.

Also note that while there is nothing stopping you from prefacing multiple methods with `@Before` or `@After` annotations, it's usually not necessary and will just make it more difficult to follow the code.  If you do make multiple methods with these annotations, they will run in a deterministic order (that is, if you run it again, they will run in the same order).  However, since tests should not depend upon each other, this ordering is not specified and is liable to change whenever you update your code and/or JUnit version.  Thus, it's generally a good idea to just have one `@Before` and one `@After`, maximum, per test file.

Getting back to setup and teardown procedures specifically, if you have complicated ones, using multiple annotations is usually unnecessary. You can still use a single method, but call out to other methods as helpers, instead of annotating numerous methods.

## Testing System Output

One particular use case that `@After` and `@Before` annotations can help you with is checking for system output.  Console output is a very common item to check for, but testing for it in Java is non-intuitive.  Although it's possible to just pass in a System object with each method, which you could then mock and stub, this is not idiomatic Java code and will add lots of additional code (and most likely complexity) to your codebase.  The best solution that I have seen for checking it involves using the `setOut()` methods of `System` to put the output of `System.out` and `System.err` in `ByteArrayOutputStream`.[^disclosure]

[^disclosure]: Full disclosure: I saw this on Stack Overflow at [http://stackoverflow.com/q/1119385](http://stackoverflow.com/q/1119385).  (Yes, even authors of books go online to look for answers sometimes.)

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

Those who argue that private methods should _never_ be tested say that any calls from the rest of the program (i.e., the rest of the world from the class's standpoint) will have to come in through the public methods of the class.  Those public methods will themselves access private methods; if they don't, then what's the point of having those private methods?  By testing only the public interfaces of classes, you're minimizing the number of tests that you have and focusing on the tests and code that matter.

Another reason for not testing private methods is that inhibits you from refactoring the code later.  If you have already written tests for private methods, it is going to be more work to make changes to anything "behind the scenes".  In a sense, testing private methods means going against one of the key tenets of object-oriented programming, namely data hiding.  The system is going to be less flexible and more difficult to modify going forward.

Those who argue that private methods should _always_ be tested point to the fact that private methods are still code, even if they're not called directly from outside the class.  Unit testing is supposed to test functionality at the lowest levels possible, which is usually the method or function call.  If you're going to start testing higher up the abstraction ladder, then why not just do systems-level testing?

My opinion is that, like most engineering questions, the correct answer depends on what you're trying to do and what the current codebase is like.  As a side note, with most technical questions, saying "it depends" is a great way to be right, no matter what.  Let's take a look at a few examples.

Imagine that we're adding a service to Rent-A-Cat to automate taking pictures of cats:

```java
public class Picture {

    private void setupCamera() {
        // ...
    }

    private void turnOffCamera() {
        // ...
    }

    private Image captureImage() {
        // ...
    }

    public Image takePicture() {
        setupCamera();
        Image i = captureImage();
        turnOffCamera();
        return i;
    }

}
```

This code is relatively simple, and it's easy to see that all of the private methods will be called and tested by the public methods.  From the standpoint of a user of this class, it's easy to test that things work correctly---if a valid image is returned, then the method worked.

Now let's imagine in a different part of this service, we have an image transformation library that we also want to test:

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
            // Imagine lots more if...then...else if statements here
            // You get the idea
        }
    }

    // Lots of private methods here

}
```

Think of all the complicated tests that would be needed for this one method!  Even then, you're not focusing on the actual image transformations.  Many of your tests would just be focused on ensuring that the right method was called!

One could argue that this isn't a well-designed piece of code and should be refactored, ideally with the private methods put into, say, an `ImageTransformer` class, where the methods would be public and could easily be unit tested.  I wouldn't disagree.  However, the fact of the matter is that in the real world, there is often code like this lying around, and the tester is not always in a position to tell management that the company needs to spend a few months burning off technical debt instead of adding new features.  If your goal is to test the software, and test it well, you'll probably have to test the occasional private method.  For specific information on how to do this in Java, please see the supplemental chapter on Using Reflection to Test Private Methods in Java at the end of this book.

## Unit Test Structure

### Basic Outline

Unit tests in Java are usually grouped initially by class and further by method; they mirror the structure of the program.  Since unit tests are white-box tests which interact closely with the code, this makes finding errors in the codebase based upon the particular failing test case much easier than with integration or manual tests.

### What to Test?

Exactly what you test will vary based upon the domain of software you are testing and the amount of time you have for testing, as well as organizational standards and other external factors.  Of course, stating this doesn't give you any direction at all, and similar caveats could probably be put in front of every paragraph of this book.  There are some heuristics to follow, many of which directly mirror some of the items discussed when developing a test plan.

Ideally, you should look at the method and think of the various success and failure cases, determine the equivalence classes, and think about what some good boundary and interior values might be to test from those equivalence classes.  You want to also focus on testing common use cases over use cases which rarely occur, at least at first.  If you are writing more safety-critical software, often it makes sense to focus on testing for failure before checking the happy path.  Personally, I often work on a base case first, and then think of possible failure cases after that.  Oftentimes, I will go back, sometimes with a profiler, and see what code is executed is most often and add extra test cases for that.  I may try to construct a mental model of what is called often instead of using a profiler.  I will definitely think of from where the inputs to the method are coming.  If they are from a system that I have no control over (including users, the ultimate example of systems I have no control over) and may be sending unanticipated values, I will definitely spend more time thinking of possible failure cases and checking for edge cases.

You don't want to create a test suite that takes so long to run that people don't run it often, but a well-designed unit test suite with appropriate doubles, mocks, stubs, and the like should run very fast even when there are many tests.  I would err on the side of creating too many tests rather than too few, at first.  As you determine how many tests are necessary for the particular piece of software you're working on, you can start making trade-offs between the amount of time for development and for testing.

### Assert Less, Name Directly

When unit tests fail, they should point you exactly what went wrong, and where.  They should not be large "one size fits all" tests.  It is so fast to run a properly-written unit test (rarely taking more than a few tens of milliseconds) that the extra execution time and work involved in writing more tests is absolutely dwarfed by the amount of time that you will save when unit tests tell you exactly what went wrong when it failed.  A unit test with lots of assertions shows a lack of thought towards what exactly the unit test was supposed to check for.  Consider the following example:

```java
public class CatTest {

    @Test
    public void testCatStuff() {
        Cat c = new Cat();
        c.setDefaults();
        assertTrue(c.isAGoodKitty());
        assertEquals(0, c.numKittens());
        assertFalse(c.isUgly());
        assertNull(c.owner());
    }

}
```

When this test fails, what is wrong?  It could be that a newly created cat is not a good kitty, as it should be.  It could be that there's an error with the number of kittens that a newly created cat has.  It could be that the cat was erroneously assigned an owner.  You'd have to track down the particular assertion in the test code, because seeing that "cat stuff" has failed does not tell you anything.  If the name of the test case failing does not let you know what the failure is and preferably give you a very good pointer to where the failure lies, you are probably testing too much in each individual test case.  There are numerous cases where I've gone in and fixed code just based on seeing the name of the test case that failed, without ever looking at the actual test code.  This is a common consequence of well-specified, directed tests.

### Unit Tests Should Be Independent

Unit tests should not be dependent on run order.  That is, Test 2 should not depend on any side effects or result from Test 1.  JUnit, and most other unit testing frameworks, will not run individual test cases in a predetermined order.  By avoiding dependencies on each other and allowing each test to run independently, failures are localized to a particular test.  Imagine a testing framework that is just like JUnit but runs tests sequentially.  Now imagine writing the following code:

```java
public class CatTest {

    int _whiskersLength = 5;

    @Test
    public void testGrowWhiskers() {
        Cat c = new Cat(5);
        _whiskersLength = c.growWhiskers();
        assertEquals(6, _whiskersLength);
    }

    @Test
    public void testWhiskersLength() {
        Cat c = new Cat(5);
        assertEquals(_whiskersLength, c.getWhiskersLength());
    }

}
```

If we run this with JUnit, which executes test cases in a random order, sometimes the second test would pass, and sometimes it would fail!

Note that it in JUnit, it _is_ possible to specify the order that tests will run in, by using the `@FixMethodOrder` annotation.  However, you want to avoid doing that if at all possible.  It is all too easy to allow your tests to fall into the trap of depending on each other by ensuring that they always run in the same order.

There is yet another benefit to creating tests which have no dependencies on other tests.  Independent tests can be run in parallel, possibly decreasing execution time dramatically.  On a modern multi-core machine, you may find yourself running tests many times more quickly if they can be run independently.

### Try to Make Tests Better Every Time You Touch Them

Oftentimes, you will need to work with legacy code, including code which was poorly written or does not have good test coverage.  Although it may be tempting to simply continue working in the same manner that the code was originally written, you should try to do what you can to make the testing of the code better.  Keep your code easily testable, and if necessary, build wrappers around code that is difficult to test.

There is more discussion on writing testable code in the chapter Writing Testable Code (with a title like that, what else did you expect to find in it?)

## Code Coverage

Code coverage tells you how much of the codebase is actually executed when running the test suite code.  Since defining exactly what is meant by "how much of the codebase" can be complex, there are numerous kinds of code coverage.  The simplest form of code coverage is method coverage; this measures what percentage of methods have any tests that call into them.  For example, imagine a class `Turtle` with two methods, `crawl()` and `eat()`:

```java
public class Turtle {

    public void crawl() { ... }

    public void eat() { ... }

}
```

If we have a test calling `crawl()`, but none calling `eat()`, then we have 50% code coverage (one of two methods was tested).  Even if we had a hundred tests calling `crawl()`, checking out all of the various different ways that a turtle can crawl, but none testing `eat()`, we still have 50% code coverage.  Only once we add at least one test checking `eat()` will we have 100% code coverage.

A more detailed form of code coverage is statement coverage.  This measures what percentage of programming statements were actually executed by at least one test.  A programming statement is the smallest "chunk" of code that still makes sense as an individual unit of code; in Java, these are generally the pieces of code separated by semicolons.  Some examples of statements include:

1. `int j = 7;`
2. `System.out.println("Bark!");`
3. `foo--;`

Note that "statement" does not mean "a line of code"!  For example, in Java, you may have a line such as:

```java
doSomething(k); k++;
```

This is actually multiple statements, they just happen to be on the same line.

When developers discuss "code coverage", they usually mean specifically "statement coverage".  Compared to method coverage, using statement coverage provides vastly more granularity in knowing what parts of the codebase have actually been tested.  For example, let's add some detail to our `Turtle` class so that we can see what the code is like inside the various methods:

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

Using method coverage, having just one test for `eat()` or just one for `crawl()` would show 50% code coverage.  This hides the fact that `crawl()` is much more complicated than `eat()` and any single test would miss several different possible outcomes, whereas `eat()` can be tested very well with just one test.  It also would not let us know what particular lines were not tested---we would have to examine the test code to determine whether it was testing that the turtle could move on dirt, grass, or whatever else.  Statement coverage output can tell us exactly which lines were never executed during a test run, so we know exactly what kinds of tests to add to ensure that we are checking that each line has been tested at least once.

There are other variants of code coverage such as branch coverage, which measures which percentage of conditionals (if statements, case statements, etc.) have been tested.  However, these other kinds of code coverage are usually only used for much more specialized testing.  Chances are that you will deal with the statement and method coverage much more often.

Having a statement or method covered by tests does not mean that all defects in that unit of code have been found by the test!  It's easy to imagine defects slipping through method coverage.  In our `Turtle` example, if there was some sort of problem when the turtle was on grass, but our test only checked the case that the turtle was on dirt, we can see how method coverage would say that `crawl()` has been checked but defects can still creep through.  At a lower level of abstraction, statement coverage does not check for all variations in the way a particular statement could be executed.  Let's consider the following single-method class and its associated test case:

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
        assertEquals(5, mooTimes);
    }

}
```

From a code coverage perspective, we have 100% code coverage and 100% method coverage---the only method in the class is called from a test, and every statement in the method is executed.  However, calling `moo()` with a `mooLevel` parameter of 0 will cause a `DivideByZeroException` to be thrown.  This defect will not be discovered by the test case, despite every statement being executed.  Although not foolproof, ensuring that you've checked every equivalence class will help to ameliorate situations like this.

In fact, code coverage metrics can be even more misleading than this example.  As long as a statement is _executed_ by the test, that code is considered "covered".  Nothing verifies that the unit tests actually test anything, though.  Consider this case:

```java
public class CowTest {

    @Test
    public void mooTest() {
        Cow c = new Cow();
        int mooTimes = c.moo(1);
        assertTrue(true);
    }

}
```

This test results in 100% code coverage of the method, but tells you almost nothing about the code.  The only information you can get from this test passing is that calling `c.moo(1)` does not crash the program.

Code coverage is a powerful tool, but like anything else in software development, it is not a silver bullet.  It's an excellent way to see what areas of the codebase might need more testing, but it should not assure you that any code that has been covered is guaranteed free of defects.  It should not even assure you that a particular piece of code has truly been tested.

## Unit Testing as Part of a Complete Testing Plan

Just like your favorite nutritionally-suspect cereals, unit testing should not be your complete breakfast nor your entire test plan.  Unit testing is great for checking individual methods and low-level functionality, but it is not good at seeing how everything fits together.  It's even worse when trying to determine what the end product will look like; all of the individual methods may work, but together they form something which doesn't meet any of the requirements.

When you are testing, you should remember to perform some manual testing, integration testing, and depending upon your needs, other kinds of testing such as security testing or performance testing.  Relying heavily on one particular kind of testing is a recipe for missing important defects.
