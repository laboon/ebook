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

1. [-2, -1, 0, 1, 2] -> ERROR light only
2. [3, 4, ... 21, 22] -> UNDERPRESSURE light only
3. [20, 21, ... 34, 35] -> No light
4. [36, 37, ... 49, 50] -> OVERPRESSURE light only

There are two problems here.  The first is that all values less than -2 and greater than 50 are not mapped to an equivalence class.  What is the expected behavior if the sensor sends a value of 51?  Is that also considered an error?  Is it considered OVERPRESSURE?  In this case, it is __undefined__.  There is often undefined behavior in a reasonably complex software system under test, but the software tester should help find these gaps in coverage and find out what should (or, at least, does) happen in these gaps.

The second, and much worse, problem is that a contradiction has arisen for values 20, 21, and 22.  These belong to both the "UNDERPRESSURE" and "No lights" equivalence classes.  What is the expected behavior for an input value of 21?  Depending on which equivalence class you look at it, it could be no lights or an UNDERPRESSURE light.  This is a violation of strict partitioning, and you can easily see how problematic it can be.

It is important to note that equivance classes do not have to be the exact same output value!  For example, let's say that you are testing an e-commerce site.  Sales of less than $100.00 get 10% off, and sales of $100.01 or greater get 20% off the final sale price.  Even though there are going to be a wide range of output values, the output behavior will be similar for all values of $100.00 or less and for all values of $100.01 or more.  Those would be the two equivalence classes; there won't be a separate equivalence class for each individual output value (e.g. $10.00 -> $9.00, $10.10 -> $9.01, etc.).

Now that our equivalence classes have been determined, it's possible to write tests that cover all of the functionality of this display.  We may decide to send in a value of -2 to test the ERROR equivalence class, a value of 10 to test the UNDERPRESSURE equivalence class, a value of 30 to test the "No lights" equivalence class, and a value of 45 for the OVERPRESSURE equivalence class.  Of course, the values were picked rather arbitrarily.  In the next section, we'll see how one can choose specific values in order to maximize the chances of finding any latent defects.

#### Interior and Boundary Values
There's an axiom in testing that defects are more likely to be found near the boundaries of two equivalence classes.  These values - the "last" of one equivalence class and the "first" of a new equivlence class - are called __boundary values__.  Values which are not boundary values are called __interior values__.  For example, let's take a very simple mathematical function, the absolute value of an integer.  This has two equivalence classes:

1. [-MAXINT, -MAXINT + 1, ... -2, -1, 0] -> For input x, outputs -(x)
2. [1, 2, ... MAXINT - 1, MAXINT] -> For input x, outputs x

The boundary values are 0 and 1; they are the dividing line between the two equivalence classes.  Every other value (e.g., 7, 62, -190) is an interior value; it is in the "middle" of an equivalence class.

Now that we understand what boundary and interior values are, one might ask, why is it the case that the boundaries are more likely to be defective?  The reason is that it is much more likely for code to have an error near a boundary because equivalence classes are so close.  Let's consider an example of the absolute value function:

```java
public static int absoluteValue (int x) {
    if ( x > 1 ) {
       return x;
    } else {
      return -x;
}
```

Did you see the coding error?  There's a simple off-by-one error in the first line of the method.  Since it's checking if the argument x is greater than one, sending in the input value 1 will return -1.  Since boundary values are often explicitly mentioned in code, that's more reason for them to potentially fail or be put into the "wrong" equivalence class.  Let's rewrite it correctly, now that we have found the error:


```java
public static int absoluteValue (int x) {
    if ( x >= 1 ) {
       return x;
    } else {
      return -x;
}
```

Much better.  However, think how difficult it would be to rewrite this method so that it failed only when you pass in 57 - not 56, not 58, but only 57.  It's possible, of course, but much less likely.  Since it's very rare to be able to exhaustively test every possible input value for a program (or even a function!), it makes sense to focus on values which are more likely to uncover defects.

Getting back to our pressure sensor display, our stingy test manager says that we have time to test more values.  We want to ensure that at a minimum, we test all of the boundary values, and hopefully a good sampling of the interior values.  First, we'll calculate all of the boundary values, and then generate a test plan which tests all of the boundary values and some of the interior values.  

Boundary values:
1. -1, 0 (Boundary between ERROR and UNDERPRESSURE)
2. 20, 21 (Boundary between UNDERPRESSURE and NORMAL)
3. 35, 36 (Boundary between NORMAL and OVERPRESSURE)

Values to Test:
1. Interior values, ERROR - -3, -100
2. Boundary values, ERROR / UNDERPRESSURE - -1, 0
3. Interior values, UNDERPRESSURE - 5, 11
4. Boundary values, UNDERPRESSURE / NORMAL - 20, 21
5. Interior values, NORMAL - 25, 31
6. Boundary values, NORMAL / OVERPRESSURE - 35, 36
7. Interior values, OVERPRESSURE - 40, 95

One could also consider __implicit boundary values__.  In contrast to __explicit boundary values__, which are a natural outgrowth of requirements (such as the ones calculated above), implicit values grow out of the system under test or the environment under which the system operates.  For example, MAXINT and -MAXINT would both be implicit boundary values; adding one to MAXINT would cause an integer overflow and set the value to -MAXINT, and decrementing one from -MAXINT would lead to the value being MAXINT.  In each case, the equivalence class would change.

Implicit boundary values can also be runtime-dependent.  Let us assume that we have a system with 2 gigabytes of memory free, and are running a database system.  Furthermore, let us assume that the system is an in-memory database; it cannot write any information to the disk (before you say that that's a ridiculous database design, check out Redis).  The equivalence classes for testing a function which inserts a number of rows may be as follows:

1. Negative number of rows -> Error condition
2. 0 rows or tables does not exist -> Returns NULL
3. One or more rows -> Returns number of rows inserted

There's an implicit boundary between the number of rows which fit into memory and that which don't.  Whoever wrote the requirements may not have thought about this, but as a tester, you shuld keep implicit boundary values in mind!

#### Base cases, Edge Cases, Corner Cases
Let us continue our exploration of the pressure sensor display.  Going over our various test cases, we can see that they vary in how common they will be.  We can assume that the vast majority of the time, the pressure will be normal, or slightly overpressure or underpressure.  Each of these is considered a __base case - the system is operating within expected parameters for normal use.

When input values are outside normal operating parameter or are approaching the limits of what the system can handle, this is called an __edge case__.  An edge case may be the tire popping and air pressure dropping to zero.  Another case would be someone forgetting that they had the air hose attached to the tire, and pumping in air up to a pressure of 200 PSI, the absolute limit to which the tire is rated.  This also might cover error cases which might not be expected in normal operation.

__Corner cases__ (often also called __pathological cases__) refer to situations where multiple things go wrong at the same time, or where a value is, to put it bluntly, ridiculously out of range from what is expected.  An example would be a tire pressure sensor receiving a value of 2,000,000,000 (2 billion) PSI, which is quite a bit higher than pressure in the core of the Earth.  Another example would be the tire popping at the same time that the sensor fails and attempts to send an error code.

Although I have been using a simple function with relatively well-defined inputs and outputs, base cases, edge cases, and corner cases can also be specified and thought about in other kinds of operations.  Consider an e-commerce site.  Some base cases for testing the shopping cart might be:

1. Add an item to an empty shopping cart
2. Add an item to a shopping cart which already contains an item
3. Remove an item from a shopping cart which already contains an item

These are all on the "happy path" - there are no errors, a user is likely to do them, the system is operating well, etc.  Now let us consider some edge cases:

1. The user attempts to add 1,000 items to the shopping cart at the same time
2. The user attempts to hit "remove" on a shopping cart with no items in it
3. The user opens and closes the shopping cart numerous times without doing anything
4. The user attempts to add an item which is no longer in stock

These are all things that certainly may happen, but are not "normal".  They may require special error-handling (such as attempting to remove items from a shopping cart with no items, or adding an item not in stock), deal with large numbers (such as adding 1,000 items), or tax the system in an odd way (opening and closing the shopping cart repeatedly).

Finally, corner cases are cases where major disasters are occurring, or obviously bad data is sent in.  A few examples would be:

1. The database goes down immediately after a user adds an item
2. The system receives a request to add 10^80 items (approximately equal to the number of atoms in the universe) to the shopping cart
3. The memory in which the shopping cart is stored has been corrupted

Corner cases often involve a catastrophic failure of some kind (loss of network connectivity, a key subsystem crashing), entirely invalid data being sent in, or multiple failures occurring at once.

#### Success cases, Failure Cases

#### White / Black / Grey Box Testing