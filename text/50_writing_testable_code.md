# Writing Testable Code

Although we have been looking at testing from the perspective of a tester, often it can be useful to view it from the perspective of someone writing the code.  Just as a tester can make the life of a developer easier by determining defects and the level of quality of a program, the programmer can make life easier for a tester by ensuring that the code they write is easily able to be tested.  In today's programming environment, the tester and developer are often the same person, especially when it comes to unit tests.  Thus, it makes perfect sense (from a game theory perspective) to make the code as testable as possible.

By writing the code in a testable way, not only will you make it easier to write tests now, but there are a host of other benefits.  You will tend to write good, modular code.  Future changes will be easier to make, since you will have more tests and can write better tests.  The code will be easier to understand, and there will be fewer problems since you're more easily able to test all of the edge cases.  Although your goal may be to write testable code, you'll end up writing code that is better in all sorts of other ways.

## What Do We Mean By "Testable Code"?

In some sense, virtually all code is testable.  As long as you can control the inputs and observe the outputs, you can test any piece of software.  However, by __testable code__, we mean code that is easy to test in an automated fashion at multiple levels of abstraction.  This means that writing black-box systems tests is simple, that writing unit tests (for classes, methods, or other units of code) are simple, and that more advanced testing such as performance and security testing are possible.  Not all testable code is good code - it's possible to write a horrible mess of non-performant spaghetti code which is nonetheless easy to test.  However, all good code is testable code.  Being able to write tests for a particular piece of code is an integral part of the code being well-written.

Let's imagine that we're testing the following piece of code.  It's part of a video game which simulates a bird moving across a landscape.  Pressing a button makes the bird fly and updates its height and location on the screen.

```java
public class Bird {

    public int _height = 0;

    public int _location = 0;

    public void fly() {
        Random r = new Random();
        _height += r.nextInt(10) + 1;
	_location += r.nextInt(10) + 1;
	Screen.updateWithNewValues(_height, _location);
    }

}
```

While this may be a straightforward method, this is going to be very difficult to test via unit test.  After all, there are no return values to assert against.  The results will have be checked by looking at class-level variables.  There's no way to tell a specific "right" answer, since there's dependence on a random number generator which you have no way of overriding.  There's no way to check that the Screen was updated without having an actual Screen object available and running.  All of these mean that this code will be difficult to test in isolation, and thus very difficult to unit test.  What if we fixed it up a little bit to make it more testable?

```java
public class Bird {

    public int _height = 0;

    public int _location = 0;

    private Random _r = new Random();

    private 
        _height += r.nextInt(10) + 1;
	_location += r.nextInt(10) + 1;
    	Screen.updateWithNewValues(_height, _location);

    public fly() {
        int newHeight = updateHeigh(_r);
	int newLocation = updateLocation(_r);
	updateScreen(newHeight, newLocation);
    }

}
```




## The Basic Strategies for Testable Code

## DRYing Up Code

## Dependency Injection

## The SOLID Principles

## The Law of Demeter

## Dealing With Legacy Code

## Conclusion
