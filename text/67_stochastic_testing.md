# Stochastic and Property-Based Testing

The term "stochastic" comes from the Greek _stokhastikos_, which is a form of _stokhazesthai_, which means "aim at" or "guess".  It's a good etymology for __stochastic testing__, which uses random processes that can be analyzed using statistics, but not exactly predicted.  At first blush, it may seem ridiculous to use randomness in software testing; after all, isn't the fundamental concept of testing to determine what the observed behavior is and if it is equal to the expected behavior?  If you don't know what the input is, how would you know what the expected output is?

The answer is that there are expected behaviors and properties you expect from a system, no matter what the input.  For example, no matter what code is passed to a compiler, you will expect it not to crash.  It may generate an error message saying that the code is unparseable.  It may make an executable.  That executable may run, or it may not.  You do expect the system not to have a segmentation fault.  Thus, you can still run tests where the expected behavior for any input is "does not crash the system".

By providing a method for the system to use random data as an input, you also reduce the cost of testing.  No longer do you have to imagine lots of specific test cases and then painstakingly program all of them in or write them down as test cases.  Instead, you just tie together some sort of random number generator and some way to generate data based on it, and your computer can do all the work of generating test cases.  Even though the random number generator may not be as good as a dedicated testing professional in coming up with edge cases, varying equivalence classes, etc., it will often find many problems simply by the sheer number of tests it can generate and how quickly it can do it.

Stochastic testing is also referred to as __monkey testing__, by analogy with a monkey banging on computer keys.  However, "stochastic testing" sounds much more impressive if you are talking to your manager, or writing a book on software testing.

## Infinite Monkeys and Infinite Typewriters

There is an old parable about monkeys and typewriters, which normally would not seem like two things that go together well.  The parable states that given a million monkeys and infinite amount of time, the monkeys will eventually write the works of Shakespeare (the monkeys in this scenario are immortal).  Stochastic testing follows a similar principle---given a large enough set of random input (a million monkeys banging on keys) and a large enough period of time, defects will be found.  In this analogy, our tester is William Shakespeare (don't let the comparison go to your head).  The tester could certainly write the works of Shakespeare in less time than the million monkeys.  However, monkeys (like the computer's random number generator) are much cheaper than re-animating Zombie William Shakespeare.  Even if the random number generator isn't as good a writer of tests as you (or Shakespeare), by sheer dint of numbers, it's bound to hit on numerous interesting edge cases and perhaps find defects.

Since you---or more precisely, the stochastic testing system---may not know exactly what the expected behavior should be for a given input, you need to check for properties of the system.  At the unit testing level, where you are checking individual methods, this is called __property-based testing__.

## Property-Based Testing

Let's say once again that we are testing our sorting function, `billSort`.  As you'll recall, it's meant to be twenty times faster than any other sorting algorithm. However, there are questions about its correctness, so you have been tasked to test that it works in all cases.  What kind of input values would you test it with?  Assume the method signature looks like this:

```java
public int[] billSort(int[] arrToSort) {
    ...
}
```

We'd definitely want to pass in a wide variety of values that hit different base, edge, and corner cases.  Single-element arrays.  Zero-element arrays.  Arrays with negative integers.  Arrays that are already sorted.  Arrays that are sorted the opposite way as the sort works (ascending versus descending, or vice versa).  Very long arrays.  Arrays that contain multiple values, and arrays that consist of the same value repeated over and over again.  This doesn't even take into account the fact that Java is a statically typed language, so we don't have to worry about what happens when an array contains strings, or references to other arrays, or complex numbers, or a variety of other kinds of things.  If this sort were implemented in a dynamically typed language such as Ruby, we'd really have a lot to worry about.  Just considering the Java method above, though, let's think about some of the different inputs and their respective expected output values for this method:

```
[] => []
[1] => [1]
[1, 2, 3, 4, 5] => [1, 2, 3, 4, 5]
[5, 4, 3, 2, 1] => [1, 2, 3, 4, 5]
[0, 0, 0, 0] => [0, 0, 0, 0]
[9, 3, 1, 2] => [1, 2, 3, 9]
[-9, 9, -4, 4] => [-9, -4, 4, 9]
[3, 3, 3, 2, 1, 1, 1] => [1, 1, 1, 2, 3, 3, 3]
[-1, -2, -3] => [-3, -2, -1]
[1000000, 10000, 100, 10, 1] => [1, 10, 100, 10000, 1000000]
```

Even without any additional wrinkles, there's an absolutely huge number of possible combinations of numbers to test.  That was just a taste.  There's even a huge number of equivalence cases to test, ignoring the fact there could be a problem with, say, a specific number; maybe only sorts with the number 5 don't work, for example.  Writing tests for all of these various kinds of input would be extremely tedious and error-prone.  How can we avoid having to write such a large number of tests?

### Climbing the Abstraction Ladder

Why not hop up a rung on the abstraction ladder and instead of thinking about the specific values that you want as input and output, you think about the *properties* you'd expect of your input and output?  That way, you don't have to consider each individual test.  You can let the computer know that you expect all of the output to have certain properties, and what kind of values you expect as input, and let the computer write and execute the tests for you.

For example, what kinds of properties did all of the correct output values of the `billSort` method have, in relationship to the input values?  There are quite a few.  These properties should hold for all sorted lists.  Thus, they are called __invariants__.

Some invariants for a sort function would be:

1. The output array has the same number of elements as the input array
2. Every value in the output array corresponds to one in the input array
3. The value of each successive element in the output array is greater than or equal to the previous value
4. No element not in the input array is found in the output array
5. The function is __idempotent__; that is, no matter how many times the function is called, the same output should always be returned.  If I run the sort method on a list, and then run the sort again on the output of that first sort, the second sort call should produce the same output array as just running it once.  Note that it would be harder to test the opposite of an idempotent function (a __non-idempotent__ function), since in that case, the output may or may not be changed (for example, assume a function which increments a value and returns that value modulo 6; any time you call that function some factor of six times, it will return the same value).
6. The function is pure; running it two times on the same input array should always produce the same output array.  Any time I call sort on the list `[3, 2, 1]`, it will return `[1, 2, 3]`.  It does not matter if the moon is waxing gibbous or waning crescent, or what the value of any global variables are, it will always return the same value if given the same input array.

Now that we have some of the properties we expect from *any* output of the `billSort` method, we can let the computer do the grunt work of thinking up random arrays of data, passing them in to our method, and then checking that whatever output array is produced meets all of the properties that we set.  If an output array does not meet one of the invariants, we can then report the error to the tester.  Producing output that does not meet the specified invariant is called __falsifying the invariant__.

There are multiple libraries for Java which perform property-based testing but no standard.  Property-based testing is much more popular in the functional programming world, with programs like QuickCheck for Haskell being more used than standard unit tests.  In fact, the concept of automated property-based testing of this sort comes from the functional world and from the Haskell community in particular.  For more information, see the paper _QuickCheck: A Lightweight Tool for Random Testing of Haskell Programs_ by Koen Claessen and John Hughes.

## Smart, Dumb, Evil, and Chaos Monkeys

As mentioned above, stochastic testing is often called monkey testing.  What is not as well known is that there are different kinds of monkeys doing our testing work for us.

Let's imagine a simple program which accepts a string argument from the user, and attempts to read that string as an arithmetic expression and display its result.  For example, "`5 + 6`" would display "`11`" and "`5 - (2 * 1) + 10`" would display "`13`".  If the program cannot parse the string because it is not a valid arithmetic expression (e.g., "`$%--0`"), then the program will print "ERROR".

__Dumb monkey__ testing is sending in just any old input you can think of (or, as it happens more often, that your input generation program creates).  "`Mfdsjbkfd`", "`1 + 2`", and "`(*@()`" all seem like good inputs to the dumb monkey.  There is no rhyme or reason, just lots of different randomized input.  This can be helpful for catching edge cases, but it is not very focused.  Remember that the world of possible values is absolutely huge.  The chances of finding a specific defect might be minimal when using dumb monkey testing.  By chance, we happened to find at least one valid arithmetic expression, but what are the odds of that occurring on a regular basis?  The vast majority of randomly generated inputs will cause the same behavior: simply printing ERROR.  The chance of it catching well-known and easy-to-test-for errors such as integer overflow or underflow, or floating-point rounding issues, is minuscule.

As a more real-life example, let us assume that you have a defect where arbitrary JavaScript code can be executed by entering it into a text box on your web application.  However, if the JavaScript code is not syntactically correct, nothing bad happens.  Think of how long it would take for a dumb monkey to randomly generate a valid JavaScript string that would take advantage of this obvious vulnerability!  It may be years.  Even then, it may be some JavaScript code which does not cause any problems, or even any noticeable output, such as a comment or logging a message to the console.  Meanwhile, even a novice tester is going to try to enter some JavaScript on any text box they can find.

Dumb monkey testing has some very obvious drawbacks, chief among them that without direction, most inputs are invalid and uninteresting.  However, implementing it can be done very quickly, and a large number of tests can be performed very quickly.

__Smart monkey__ testing involves using input which a user might conceivably enter, as opposed to being strictly random.  For example, suppose you are testing a calculator program, which accepts a string of numbers and arithmetic operators and displays a result.  It is rational to assume that an ordinary user is much more likely to enter the following input:

```
> 1 + 2
3
> 4 + + 6
ERROR
> 4 + 6
10
```

than:

```
> jiwh0t34h803h8t32h8t3h8t23
ERROR
> aaaaaaaaaaaaa
ERROR
> 084_==wjw2933
ERROR
```

While this assumption may not hold when it comes to toddlers, in general it is most likely true.  Thus, in order to focus our testing resources on finding defects which are more likely to occur, we can use smart monkey testing to act as a user.  Since the smart monkey test is automated, however, it will be able to operate much more quickly and on many more possible inputs than manual testing by an actual user.

Creating a smart monkey test can be difficult, because not only do you have to first understand what users would likely do with the application, but then also develop a model and a generator for it.  However, the smart monkey is much more likely to discover a defect in the system under test.

__Evil monkey__ testing simulates a malicious user who is actively trying to hurt your system.  This can be through sending very long strings, potential injection attacks, malformed data, unsupported characters, or other inputs which are designed to cause havoc in your system.  In today's networked world, systems are almost always under attack if they are connected to the Internet for more than a few milliseconds.  It is much better to have an evil monkey under our control determine that the system is vulnerable than let some actual malicious user figure it out!

Let us assume that we are storing all of the entered data for our arithmetic program in a database.  An evil monkey test may check to see if it can cause the program to somehow overwrite or modify that data by passing in a malicious SQL command, or some characters which may be interpreted as nulls, or an extremely long string to try to overflow a buffer.

```
> '); DELETE FROM entries; --"
ERROR
> \000\000\000
ERROR
> Well, Prince, so Genoa and Lucca are now just family estates of the Buonapartes...
ERROR
```

For that final line, you may assume that the entire text of _War and Peace_ by Leo Tolstoy was actually entered by the evil monkey.  I considered adding the whole thing in to inflate the word count of my book, but decided against it to help reduce the weight of the printed version.  These are only a few examples of how evil monkey testing might look for defects in a system.  For more examples of testing the security of systems, see the chapter in this book entitled _Security Testing_.

Perhaps the best-named kind of monkey is the __Chaos Monkey__.  Chaos Monkey is a tool developed by Netflix which randomly shuts down servers that their system is running on, in order to simulate random outages.  For any large system, servers will go down on a regular basis, and at any given time some percentage of systems will be unavailable.  Chaos monkey testing ensures that the system as a whole will be able to operate effectively even when individual machines are not responding.

You do not have to use the official Chaos Monkey tool to do this kind of testing, however.  Think of all the things that can go wrong with a multiple-server system, and simulate them.  What happens when the network topography changes?  Does the system stay active when somebody pulls out some power or networking cables?  What happens if latency is increased to several seconds?  A distributed system is ripe for problems.  Testing that it can handle them now will allow you to prepare for when they happen in reality.  After all, the best way to avoid a problem is to induce it repeatedly; soon, you will have automated procedures to ameliorate it or ensure that it doesn't happen.

## Mutation Testing

Yet another usage of randomness in testing will allow us to test our tests!  In __mutation testing__, we modify the code itself in random ways (keeping a copy of the original code to which we will eventually revert back, of course).  We are thus __seeding__ the system under test with defects.  Seeding is deliberately adding defects to a system in order to determine whether or not our testing process is capable of catching them.  This can provide us a general idea of the quality of our tests.  For example, if ten different defects are deliberately added to a system, and the quality assurance team catches all ten of them, then their other assessments of quality are more likely to be accurate.  If the quality assurance team catches only one or none of the ten seeded defects, then we would be justified in thinking that there are many _actual_ defects which are not being caught.  This would certainly call into question any guarantees that the team has made regarding the quality of the system under test.

After each random modification of code, the test suite, or the subset of it associated with the code that was modified, is then run.  If our test suite has full coverage of the code that was modified, at least one test should fail.  If none fail, there is a very good chance that our test coverage is incomplete.  There exists the possibility that the mutation was completely benign; say, it modified the default value of a variable which gets immediately overwritten.  Another possibility is that the modification made a change that would never be seen in practice, such as 

Let's work through an example of mutation testing.  Assume we have a method which calculates what kind of animal something might be based on the length of its neck.

```java
public class Guess {
    public static String animalType(int neckLength) {
        String toReturn = "UNKNOWN";
        if (neckLength < 10) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } else {
            toReturn = "Giraffe";
        }
        return toReturn;
    }
}
```

Our test cases check one value from each of the equivalence classes.

```java
@Test
public void animalTypeRhinoceros() {
   assertEquals("Rhinoceros", Guess.animalType(5);
}

@Test
public void animalTypeHippopotamus() {
   assertEquals("Hippopotamus", Guess.animalType(15);
}

@Test
public void animalTypeGiraffe() {
   assertEquals("Giraffe", Guess.animalType(25);
}
```

Now, if you recall from our chapter on unit testing, this is not providing very good coverage.  Of course, it's also not the worst case of unit testing a method that I have seen.  Mutation testing can give us a better idea of the overall quality of our tests than a simple code coverage metric.  Let's see a few mutations which will cause a failure, showing us first the strengths of the unit tests and how they can be made to fail.

```java
public class Guess {
    public static String animalType(int neckLength) {
        String toReturn = "UNKNOWN";
        // Value was changed from 10 to 1
        if (neckLength < 1) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } else {
            toReturn = "Giraffe";
        }
        return toReturn;
    }
}
```

In this case, we modified the conditional checking to see if `neckLength < 10` to `neckLength < 1`.  This will rightly cause the test `animalTypeRhinoceros` to fail, since our mutated version of the method will return "Hippopotamus" for a `neckLength` argument of 5.

```java
public class Guess {
    public static String animalType(int neckLength) {
        String toReturn = "UNKNOWN";
        // Less-than sign changed to greater-than sign
        if (neckLength > 10) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } else {
            toReturn = "Giraffe";
        }
        return toReturn;
    }
}
```

In this case, _all_ of the unit tests fail: the first will show that what should be a "Rhinoceros" is listed as a "Giraffe", and both of the other tests will return that the animal is a "Rhinoceros".  Good job, unit tests!

The more interesting cases which can help our codebase is when mutations do _not_ cause the tests to fail.  This generally means that our tests are not providing as much coverage of the method as we think.  Various errors in the method could slip through the test suite, now or in the future.  Remember, the failure of a unit test to catch a mutation does not mean there is a problem with the code now.  The code could have been implemented perfectly correctly.  It is a failure of the _unit tests_ to catch a possible issue.  We will just have no way of knowing, from a testing perspective, if the method fails under certain circumstances.

Let's take a look at an example of a mutation which will not cause any unit tests to fail, but has an impact on the execution of the code.

```java
public class Guess {
    public static String animalType(int neckLength) {
        String toReturn = "UNKNOWN";
        // Value was changed from 10 to 8
        if (neckLength < 8) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } else {
            toReturn = "Giraffe";
        }
        return toReturn;
    }
}
```

In this case, all of our unit tests will pass: an animal with a neck length of 5 will be seen as a rhinoceros, one with a neck length of 15 will be seen as a hippopotamus, and one with a neck length of 25 will be seen as a giraffe.   Sadly, though, the functionality of our method has definitely changed!  If somebody calls this mutated method with a `neckLength` argument of 9, that animal will be seen as a hippopotamus when it should be seen as a rhinoceros.  This shows us the importance of checking for boundary values between equivalence classes.  If we had checked for all of the boundaries, then one of our unit tests would have failed whenever these values were changed.

As we mentioned, some mutations may not cause any tests to fail, but are generally benign.  This often means that the code that it modified is superfluous, or that the code is implemented improperly but it does not matter during normal execution.  

```java
public class Guess {
    public static String animalType(int neckLength) {
        // String value changed from "UNKNOWN" to "AMBER"
        String toReturn = "AMBER";
        if (neckLength > 10) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } else {
            toReturn = "Giraffe";
        }
        return toReturn;
    }
}
```

This will not cause any failures in our unit tests, since the "UNKNOWN" value was just a placeholder and never actually used for anything.  It does tell us that perhaps we don't need to set a default value here, or that there is a better way to implement the method.  For example, this is a slightly more efficient way to implement the same method:

```java
public class Guess {
    public static String animalType(int neckLength) {
        // Default value is now a giraffe, no need for final else
        String toReturn = "Giraffe";
        if (neckLength > 10) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } 
        return toReturn;
    }
}
```

However, this could also be a code smell that we forgot to implement some functionality.  Perhaps we meant to return that default value if an animal with a negative-length neck was passed in, but never got around to actually coding that!

```java
public class Guess {
    public static String animalType(int neckLength) {
        String toReturn = "UNKNOWN";

        // If invalid neckLength value passed in, return default
        if (neckLength < 0) {
            return toReturn;
        }

        if (neckLength < 10) {
            toReturn = "Rhinoceros";
        } else if (neckLength < 20) {
            toReturn = "Hippopotamus";
        } else {
            toReturn = "Giraffe";
        }
        return toReturn;
    }
}
```

Mutation testing is a valuable way to determine how well your tests are testing your code.  It is very easy for a tester to miss an equivalence class, or a boundary value, or a failure mode.  When a mutation which cannot be caught by your tests shows up, though, you can see not only that there is a hole in your testing strategy, but also see how a coding mistake would cause it.
