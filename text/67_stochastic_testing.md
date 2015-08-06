# Stochastic and Property-Based Testing

The term "stochastic" comes from the Greek _stokhastikos_, which is a form of _stokhazesthai_, which means "aim at" or "guess".  It's a good etymology for __stochastic testing__, which uses random processes that can be analyzed using statistics, but not exactly predicted.  At first blush, it may seem ridiculous to use randomness in software testing; after all, isn't the fundamental concept of testing to determine what the observed behavior is and if it equal to the expected behavior?  If you don't know what the input is, how would you know what the expected output it?

The answer is that there are expected behaviors and properties you expect from a system, no matter what the input.  For example, no matter what code is passed to a compiler, you will expect it not to crash.  It may generate an error message saying that the code is unparseable.  It may make an executable.  That executable may run, or it may not.  You do expect the system not to have a segmentation fault.  Thus, you can still run tests where the expected behavior is something like "does not crash the system".

By providing a method for the system to use random data as an input, you also reduce the cost of testing.  No longer do you have to imagine lots of specific test cases, and then painstakingly program all of them in or write them down as test cases.  Instead, you just tie together some sort of random number generator and some way to generate data based on it, and your computer can do all the work of generating test cases.  Even though the random number generator probably won't be nearly as good as a dedicated testing professional to coming up with edge cases, varying equivalence classes, etc., it will often find many problems simply by the sheer number of tests it can generate and how quickly it can do it.

Stochastic testing is also referred to as __monkey testing__, by analogy with a monkey banging on computer keys.  However, "stochastic testing" sounds much more impressive if you are talking to your manager, or, say, writing a book on software testing.

## Infinite Monkeys and Infinite Typewriters

There is an old parable about monkeys and typewriters, which normally would not seem like two things that go together well.  The parable states that given a million monkeys and infinite amount of time, the monkeys will eventually write the works of Shakespeare (NB these are immortal monkeys in this scenario).  Stochastic testing follows a similar principle - given a large enough set of random input (a million monkeys banging on keys) and a large enough period of time, defects will be found.  In this analogy, our tester is William Shakespeare (don't let the comparison go to your head).  He could certainly write the works of Shakespeare in less time than the million monkeys.  However, monkeys (like the computer's random number generator) are much cheaper than re-animating Zombie William Shakespeare.  Similarly, even if the random number generator isn't as good a writer of tests as you (or Shakespeare), by sheer dint of numbers, it's bound to hit on numerous interesting edge cases and perhaps find defects.

Of course, since you - or more precisely, the stochastic testing system - may not know exactly what the expected behavior should be for a given input, you need to check for properties of the system.  At the unit testing level, where you are checking individual methods, this is called __property-based testing__.

## Property-Based Testing

Let's say that we are testing a new sort function, billSort.  It's twice as fast as any other sorting algorithm out there, but there are questions about its correctness, and so you have been tasked to test that it works in all cases.  What kind of input values would you test it with?  Assume the method signature looks like this:

```java
public int[] billSort(int[] arrToSort) {
  ...
}
```

We'd definitely want to pass in a wide variety of values that hit different base, edge, and corner cases.  Single-element arrays.  Zero-element arrays.  Arrays with negative integers.  Arrays that are already sorted.  Arrays that are sorted the opposite way as the sort works (ascending vs descending, or vice versa).  Very long arrays.  Arrays that contain multiple values, and arrays that consist of the same value repeated over and over again.  This doesn't even take into account the fact that Java is a statically typed language, so we don't have to worry about what happens when an array contains strings, or references to other arrays, or complex numbers, or a variety of other kinds of things.  If this sort were implemented in a dynamically typed language such as Ruby, we'd really have a lot to worry about.  Just considering the Java method above, though, let's think about some of the different inputs and their respective expected output values for this method:

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
[2, 8, ... (many ints) ... -3, 7] => [-900, -874, ... (many ints) ... 989, 991]
```

Even without any additional wrinkles, there's an absolutely huge number of possible combinations of numbers to test.  That was just a taste.  There's even a huge number of equivalence cases to test, ignoring the fact there could be a problem with, say, a specific number; maybe only sorts with the number 5 don't work, for example.  Writing tests for all of these various kinds of input would be extremely tedious and error-prone.  How can we avoid having to write such a large number of tests?

### Climbing The Abstraction Ladder

Why not hop up a rung on the abstraction ladder and instead of thinking about the specific values that you want as input and output, you think about the *properties* you'd expect of your input and output?  That way, you don't have to consider each individual test.  You can let the computer know that you expect all of the output to have certain properties, and what kind of values you expect as input, and let the computer write and execute the tests for you.

For example, what kinds of properties did all of the correct output values of the billSort method have, in relationship to the input values?  There are quite a few.  These properties should hold for all sorted lists.  Thus, they are called *invariants*.

Some invariants for a sort function would be:

1. The output array has the same number of elements as the input array
2. Every value in the output array corresponds to one in the input array
3. The value of each successive element in the output array is greater than or equal to the previous value
4. No element not in the input array is found in the output array
5. The function is idempotent; that is, running the sort method on a list, and then running the sort again on the output, should produce the same output array as just running it once
6. The function is pure; running it two times on the same input array should always produce the same output array

Now that we have some of the properties we expect from *any* output of the billSort method, we can let the computer do the grunt work of thinking up random arrays of data, passing them in to our method, and then checking that whatever output array is produced meets all of the properties that we set.  If an output array does not meet one of the invariants, we can then report the error to the tester.  Producing output that does not meet the specified invariant is called *falsifying the invariant*.

### QuickCheck

### Using QuickCheck for Checking A Java Method

### Benefits and Drawbacks

## Smart, Dumb, Evil, and Chaos Monkeys

At the systems level, the terms "stochastic testing" or "monkey testing" are often used instead of property-based testing, even though they are performing similar kinds of operations.
