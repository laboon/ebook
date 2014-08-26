## Testing Basics

Before we get deep into actually writing tests, it behooves us to make sure that we're all on the same page with the theory and terminology of testing.  This chapter will provide us with both the same vocabulary and the same theoretical underpinnings when discussing testing.

As a caveat, I'd like to state that I have seen conflicting terminology during my career in testing.  Different workplaces and testing teams may call certain things differently, or use the same term in different ways.  I have tried to use the most common expressions, but you may see slightly different terminology in industry.

#### Equivalence Classes and Expected vs Observed Behavior

Imagine that you are in charge of testing a new display for a car tire air pressure sensor.  The air pressure reading comes in from an external sensor, and we can assume that the air pressure value will be passed in to our display as a 32-bit, signed integer.  If the air pressure is greater than 35 pounds per square inch (PSI), the OVERPRESSURE light should turn on and all other lights should be off.  If the air pressure is between 0 and 20 PSI, the UNDERPRESSURE light should turn on, and all other lights should be off.  If the air pressure reading comes in as a negative number, the ERROR light should come on and all other lights should be off; if the air pressure in the tires is lower than the air pressure outside, there's either a sensor error or the sensor has somehow ended up on the surface of Venus.

This should be a relatively simple test - there's one input, the type is known and all possible input and output values are known.  We are ignoring exogenous factors, of course - a hardcore tester would want to know what happens if, say, the wire between the sensor and display is cut, or if overvoltage occurs, or... well, use your imagination.  

However, where does one start when testing it?  You will need to develop some inputs and expected outputs (e.g., "send in 15 PSI -> see the UNDERPRESSURE light come on and all other lights go out").  You can then execute the test and see if what you see happening lines up with what you expected to see.  This is the core concept of testing - checking __expected behavior__ with __observed behavior__.  That is, ensuring that the software does what you expect it to under certain circumstances.  There will be lots of twists, adjustments, wrinkles, and caveats to that, but the root of all testing is comparing expected behavior with observed behavior.

Your manager would like this tested as quickly as possible, and asks you to create four tests.  Armed with the information that you should check expected vs observed behavior, you decide to send in -111, -900, and -5 to see if the ERROR light comes on in each case, and none of the other lights do.  Excited to have written your first tests, you show your manager, who slowly shakes her head and says "You're only testing one equivalence class!"

An equivalence class (also called an __equivalence partition__) is one set of input values that maps to an output value.  You can think of them as different "groups" of input values that do something similar.  This enables testers to create tests which cover all parts of functionality, and avoid overtesting just one part (like in our example above, where the ERROR equivalence class was tested thrice and the other three, not at all).

What other three, you may ask?  In order to answer this, think of all of the different kinds of output you would expect:

1. The ERROR light comes on for PSIs of -1 or less
2. The UNDERPRESSURE light comes for PSIs between 0 and 20
3. No light comes on for PSIs between 21 and 35 (normal operating conditions)
4. The OVERPRESSURE light comes on for PSIs of 36 or greater

One could think of this more mathematically as a mapping between a group of input values and expected output conditions.

1. [-MAXINT, -MAXINT + 1, ... -2, -1] -> ERROR light only
2. [0, 1, ... 19, 20] -> UNDERPRESSURE light only
3. [21, 22, ... 34, 35] -> No lights
4. [36, 37, ... MAXINT -1, MAXINT] -> OVERPRESSURE light only

(where MAXINT and -MAXINT are the maximum and minimum 32-bit integers)

We have now __partitioned__ our equivalence classes.  Equivalence class partitioning is the act of determining our equivalence classes and ensuring that they do not overlap at all, and should cover all possible input values.  In other words, they must maintain a __strict partitioning__.  For example, let's say that, due to bad or misread requirements, we had generated an equivalence class partitioning such as the following:

[-2, -1, 0, 1, 2] -> ERROR light only
[3, 4, ... 21, 22] -> UNDERPRESSURE light only
[20, 21, ... 34, 35] -> No light
[36, 37, ... 49, 50] -> OVERPRESSURE light only

There are two problems here.  The first is that all values less than -2 and greater than 50 are not mapped to an equivalence class.  What is the expected behavior if the sensor sends a value of 51?  Is that also considered an error?  Is it considered OVERPRESSURE?  In this case, it is __undefined__.  There is often undefined behavior in a reasonably complex software system under test, but the software tester should help find these gaps in coverage and find out what should (or, at least, does) happen in these gaps.

The second, and much worse, problem is that a contradiction has arisen for values 20, 21, and 22.  These belong to both the "UNDERPRESSURE" and "No lights" equivalence classes.  What is the expected behavior for an input value of 21?  Depending on which equivalence class you look at it, it could be no lights or an UNDERPRESSURE light.  This is a violation of strict partitioning, and you can easily see how problematic it can be.

It is important to note that equivance classes do not have to be the exact same output value!  For example, let's say that you are testing an e-commerce site.  Sales of less than $100.00 get 10% off, and sales of $100.01 or greater get 20% off the final sale price.  Even though there are going to be a wide range of output values, the output behavior will be similar for all values of $100.00 or less and for all values of $100.01 or more.  Those would be the two equivalence classes; there won't be a separate equivalence class for each individual output value (e.g. $10.00 -> $9.00, $10.10 -> $9.01, etc.).

Now that our equivalence classes have been determined, it's possible to write tests that cover all of the functionality of this display.  We may decide to send in a value of -2 to test the ERROR equivalence class, a value of 10 to test the UNDERPRESSURE equivalence class, a value of 30 to test the "No lights" equivalence class, and a value of 45 for the OVERPRESSURE equivalence class.  Of course, the values were picked rather arbitrarily.  In the next section, we'll see how one can choose specific values in order to maximize the chances of finding any latent defects.

### Interior and Boundary Values

#### Base cases, Edge Cases

#### Success cases, Failure Cases

#### White / Black / Grey Box Testing