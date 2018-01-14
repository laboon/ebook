# Testing Basics

Before we get deep into actually writing tests, it behooves us to make sure that we're all on the same page with the theory and terminology of testing.  This chapter will provide us with the same vocabulary and the theoretical foundation for discussing testing.

## Equivalence Classes and Behavior

Imagine that you are in charge of testing a new display for a car tire air pressure sensor.  The air pressure reading comes in from an external sensor, and it is guaranteed that the air pressure value will be passed in to our display as a 32-bit, signed integer.  If the air pressure is greater than 35 pounds per square inch (PSI), the `OVERPRESSURE` light should turn on and all other lights should be off.  If the air pressure is between 0 and 20 PSI, the `UNDERPRESSURE` light should turn on, and all other lights should be off.  If the air pressure reading comes in as a negative number, the `ERROR` light should come on and all other lights should be off.

This should be a relatively simple test.  There's only one input, the type is known and all possible input and output values are known.  We are ignoring exogenous factors, of course---a hardware tester would want to know what happens if, say, the wire between the sensor and display is cut, or if overvoltage occurs, or... well, use your imagination.

Where does one start when testing this?  You will need to develop some inputs and expected outputs (e.g., "send in 15 PSI &rarr; see the `UNDERPRESSURE` light come on and all other lights go out").  You can then execute the test and see if what you see happening lines up with what you expected to see.  This is the core concept of testing---checking __expected behavior__ against __observed behavior__.  That is, ensuring that the software does what you expect it to under certain circumstances.  There will be lots of adjustments, wrinkles, and caveats to that, but the root of all testing is comparing expected behavior with observed behavior.

Your manager would like this tested as quickly as possible, and asks you to create four tests.  Armed with the information that you should check expected versus observed behavior, you decide to send in -1, -111, -900, and -5 to see if the `ERROR` light comes on in each case, and none of the other lights do.  Excited to have written your first tests, you show your manager, who frowns and says, "You're only testing one equivalence class!"

An __equivalence class__ (also called an equivalence partition) is one set of input values that maps to an output value.  You can think of them as different "groups" of input values that do something similar.  This enables testers to create tests which cover all parts of functionality, and avoid over-testing just one part (like in our example above, where the `ERROR` equivalence class was tested four times, while others weren't tested at all).

What are the other equivalence classes here?  In order to answer this, think of all of the different kinds of output you would expect:

1. The `ERROR` light comes on for PSIs of -1 or less
2. The `UNDERPRESSURE` light comes on for PSIs between 0 and 20 (inclusive)
3. No light comes on for PSIs between 21 and 35 (inclusive) - normal operating conditions
4. The `OVERPRESSURE` light comes on for PSIs of 36 or greater

Mathematically, one could think of this as a mapping between a group of input values and expected output conditions:

1. [MININT, MININT + 1, ... -2, -1] &rarr; `ERROR` light only
2. [0, 1, ... 19, 20] &rarr; `UNDERPRESSURE` light only
3. [21, 22, ... 34, 35] &rarr; No lights
4. [36, 37, ... MAXINT - 1, MAXINT] &rarr; `OVERPRESSURE` light only

(where MAXINT and MININT are the maximum and minimum 32-bit integers, respectively.)

We have now __partitioned__ our equivalence classes.  Equivalence class partitioning is the act of determining our equivalence classes and ensuring that they do not overlap at all, but do cover all possible input values.  In other words, they must maintain a __strict partitioning__.  For example, let's say that, due to bad or misread requirements, we had generated an equivalence class partitioning such as the following:

1. [-2, -1, 0, 1, 2] &rarr; `ERROR` light only
2. [3, 4, ... 21, 22] &rarr; `UNDERPRESSURE` light only
3. [20, 21, ... 34, 35] &rarr; No light
4. [36, 37, ... 49, 50] &rarr; `OVERPRESSURE` light only

There are two problems here.  The first is that all values less than -2 and greater than 50 are not mapped to an equivalence class.  What is the expected behavior if the sensor sends a value of 51?  Is that also considered an error?  Is it considered `OVERPRESSURE`?  In this case, it is __undefined__.  There is often undefined behavior in a reasonably complex software system under test, but the software tester should help find these gaps in coverage and find out what should (or, at least, does) happen in these gaps.

The second, and much worse, problem is that a contradiction has arisen for values 20, 21, and 22.  These belong to both the "`UNDERPRESSURE`" and "No lights" equivalence classes.  What is the expected behavior for an input value of 21?  Depending on which equivalence class you look at, it could be no lights or an `UNDERPRESSURE` light.  This is a violation of strict partitioning, and you can easily see how problematic it can be.

It is important to note that equivalence classes do not have to be composed of cases which yield the exact same output value!  For example, let's say that you are testing an e-commerce site.  Sales of less than $100.00 get 10% off, and sales of $100.01 or greater get 20% off the final sale price.  Even though there are going to be a wide range of output values, the output behavior will be similar for all values of $100.00 or less and for all values of $100.01 or more.  Those would be the two equivalence classes; there won't be a separate equivalence class for each individual output value (e.g. $10.00 &rarr; $9.00, $10.10 &rarr; $9.01, etc.).

Now that our equivalence classes have been determined, it's possible to write tests that cover all of the functionality of this display.  We may decide to send in a value of -2 to test the `ERROR` equivalence class, a value of 10 to test the `UNDERPRESSURE` equivalence class, a value of 30 to test the "No lights" equivalence class, and a value of 45 for the `OVERPRESSURE` equivalence class.  Of course, the values were picked rather arbitrarily.  In the next section, we'll see how one can choose specific values in order to maximize the chances of finding defects.

## Interior and Boundary Values

There's an axiom in testing that defects are more likely to be found near the boundaries of two equivalence classes.  These values---the "last" of one equivalence class and the "first" of a new equivalence class---are called __boundary values__.  Values which are not boundary values are called __interior values__.  For example, let's take a very simple mathematical function, the absolute value of an integer.  This has two equivalence classes:

1. [MININT, MININT + 1, ... -2, -1] &rarr; For input x, outputs -(x)
2. [0, 1, ... MAXINT - 1, MAXINT] &rarr; For input x, outputs x

The boundary values are -1 and 0; they are the dividing line between the two equivalence classes.  Every other value (e.g., 7, 62, -190) is an interior value; it is in the "middle" of an equivalence class.

Now that we understand what boundary and interior values are, one might ask, why is it the case that the boundaries are more likely to be defective?  The reason is that it is much more likely for code to have an error near a boundary because equivalence classes are so close.  Let's consider an example of the absolute value function:

```java
public static int absoluteValue (int x) {
    if ( x > 1 ) {
        return x;
    } else {
        return -x;
    }
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

Much better.  However, think how difficult it would be to rewrite this method so that it failed only when you pass in 57---not 56, not 58, but only 57.  It's possible, of course, but much less likely.  Since it's very rare to be able to exhaustively test every possible input value for a program (or even a single function), it makes sense to focus on values which are more likely to uncover defects.

Getting back to our pressure sensor display, our test manager says that we have time to test a few more values.  We want to ensure that at a minimum, we test all of the boundary values, and hopefully a good sampling of the interior values.  First, we'll calculate all of the boundary values, and then generate a test plan which tests all of the boundary values and some of the interior values.

Boundary values:

1. -1, 0 (Boundary between `ERROR` and `UNDERPRESSURE`)
2. 20, 21 (Boundary between `UNDERPRESSURE` and `NORMAL`)
3. 35, 36 (Boundary between `NORMAL` and `OVERPRESSURE`)

Values to Test:

1. Interior values, `ERROR`: -3, -100
2. Boundary values, `ERROR` / `UNDERPRESSURE`: -1, 0
3. Interior values, `UNDERPRESSURE`: 5, 11
4. Boundary values, `UNDERPRESSURE` / `NORMAL`: 20, 21
5. Interior values, `NORMAL`: 25, 31
6. Boundary values, `NORMAL` / `OVERPRESSURE`: 35, 36
7. Interior values, `OVERPRESSURE`: 40, 95

One could also consider __implicit boundary values__.  In contrast to __explicit boundary values__, which are a natural outgrowth of requirements (such as the ones calculated above), implicit values grow out of the system under test or the environment under which the system operates.  For example, MAXINT and MININT would both be implicit boundary values; adding one to MAXINT would cause an integer overflow and set the value to MININT, and decrementing one from MININT would lead to the value being MAXINT.  In each case, the equivalence class would change.

Implicit boundary values can also be runtime-dependent.  Suppose that we have a system with 2 gigabytes of memory free, and are running an in-memory database system.  The equivalence classes for testing a function which inserts a number of rows may be as follows:

1. Negative number of rows &rarr; Error condition
2. 0 rows or table does not exist &rarr; Returns NULL
3. One or more rows &rarr; Returns number of rows inserted

There's an implicit boundary between the number of rows which fit into memory and that which don't.  Whoever wrote the requirements may not have thought about this, but as a tester, you should keep implicit boundary values in mind.

## Base Cases, Edge Cases, Corner Cases

Let us continue our exploration of the pressure sensor display.  Going over our various test cases, we can see that they vary in how common they will be.  The vast majority of the time, the pressure will be normal, or slightly over-pressure or under-pressure.  Each of these is considered a __base case__---the system is operating within expected parameters for normal use.

When input values are outside normal operating parameters or are approaching the limits of what the system can handle, this is called an __edge case__.  An edge case may be the tire popping and air pressure dropping to zero.  Another case would be someone forgetting that they had the air hose attached to the tire, and pumping in air up to a pressure of 200 PSI, the absolute limit to which the tire is rated.

__Corner cases__ (also called __pathological cases__) refer to situations where multiple things go wrong at the same time, or where a value is, to put it bluntly, ridiculously out of range from what is expected.  An example would be a tire pressure sensor receiving a value of 2,000,000,000 (2 billion) PSI, which is quite a bit higher than the pressure in the core of the Earth.  Another example would be the tire popping at the same time that the sensor fails and attempts to send an error code.

Although I have been using a simple function with relatively well-defined inputs and outputs, base cases, edge cases, and corner cases can also be specified and thought about in other kinds of operations.  Consider an e-commerce site.  Some base cases for testing the shopping cart might be:

1. Add an item to an empty shopping cart
2. Add an item to a shopping cart which already contains an item
3. Remove an item from a shopping cart which already contains an item

These are all on the __happy path__ - a case where with valid, usual input and no problems occur.  There are no errors or exceptions generated, a user is likely to do them, the system is operating well, etc.  Now let us consider some edge cases:

1. The user attempts to add 1,000 items to the shopping cart at the same time
2. The user attempts to hit "remove" on a shopping cart with no items in it
3. The user opens and closes the shopping cart numerous times without doing anything
4. The user attempts to add an item which is no longer in stock

These are all things that certainly may happen, but are not "normal".  They may require special error-handling (such as attempting to remove items from a shopping cart with no items, or adding an item not in stock), deal with large numbers (such as adding 1,000 items), or tax the system in an odd way (opening and closing the shopping cart repeatedly).

Finally, corner cases are cases where major disasters are occurring, or obviously bad data is sent in.  A few examples would be:

1. An item that was in stock when the page loads is out of stock by the time the user clicks the "Add To Cart" button
2. The system receives a request to add 10^80 items (approximately equal to the number of atoms in the universe) to the shopping cart
3. The memory in which the shopping cart is stored has been corrupted

Corner cases often involve a catastrophic failure of some kind (loss of network connectivity, a key subsystem crashing), entirely invalid data being sent in, or multiple failures occurring at once.

## Success Cases and Failure Cases

When discussing test cases, there are two kinds of output that one would expect from a given test.  First, there may be a __success case__ (also called a __positive test case__); that is, the case returns an expected result given the input given to it.  In general, tests following the happy path of what a user would normally do should be success cases.

__Failure cases__ (also called __negative test cases__) are cases in which we expect the system to "fail" for some reason, such as attempting to write to a read-only disk, getting the square root of a negative number (in systems that don't work with imaginary/complex numbers), or attempting to add an invalid username to a system.  In failure cases, instead of returning a correct result, the system will do... something else.  What this "something else" is will vary from test to test, and with what kind of functionality is being tested.  Some examples might be returning an error code or default value, throwing an exception, shutting the system down, or simply logging the error to a log file or `stderr`.

## Black-, White-, and Grey-Box Testing

There are various ways of testing a system, each of which has benefits and drawbacks.  We'll explore three different kinds of testing here, and go into detail on various ways to test using these three paradigms in following chapters.

Perhaps the easiest kind of testing to understand is __black-box testing__.  In black-box testing, the tester has no knowledge of the internal workings of the system, and accesses the system as a user would.  In other words, the tester does not know about what database is in use, what classes exist, or even what language the program is written in.  Instead, testing occurs as if the tester were an ordinary user of the software.

Consider a desktop email application.  Tasked with testing this, a black-box tester would test whether or not it could retrieve and send email, whether the spell check worked, whether files could be saved, etc.  The tester would not check that a particular method on a class was called, or what objects are loaded into memory, or the actual calls to particular functions.  If the tester wanted to ensure that emails could be properly sorted alphabetically by sender, for instance, a proper black-box test would be to click on the "Sort Alphabetically by Sender" button or menu option.  A black-box tester would not know that the program was written in Java or Haskell, or whether merge sort, quicksort, or bubble sort was used.  The tester _would_ care about what results from those decisions, though. The black-box tester focuses on whether or not the system under test operates as expected from the user's point of view, and is free from user-facing defects.

Aspects of the system, such as what algorithm was used or what kind of memory allocation scheme is used, can be inferred by the black-box tester, but their concern is focused on the results of the system running.  For example, a black-box tester might notice that the system gets extremely slow when sorting a user's inbox when the user has thousands of messages.  The black-box tester may be able to infer that an O(n^2) algorithm is used for sorting, and file it as a defect.  However, they will not know about which algorithm was used, or any other features of the code causing the slowdown.

In regards to knowledge of the system under test, __white-box testing__ is the opposite of black-box testing.  In white-box testing, the tester has intimate knowledge of the codebase and directly tests the code itself.  White-box tests can test individual functions of the code, often looking at much more granular aspects of the system than black-box tests.

Continuing the example of a desktop email application, white-box tests might check the actual `sort(EmailEntry[] emails)` function, sending in various values to see what the function returns or does.  White-box testers would care about what happened specifically if a zero-length array or null reference were passed in, whereas a black-box tester would only care about what happens if they attempt to sort an empty list of emails in the application itself.  White-box tests access the code as code---checking that return values from functions are correct, ensuring that objects are instantiated properly, etc.---instead of looking at the system from a user's perspective.

__Grey-box testing__, as its name implies, is a hybrid approach between white and black-box testing.  Grey-box testing involves accessing the system as a user (as a black-box tester would do), but with knowledge of the codebase and system (as a white-box tester would have).  Using this knowledge, the grey-box tester can write more focused black-box tests.

Let us assume that our grey-box tester is looking at testing the email sorting functionality of the mail application.  Looking at the code, the tester realizes that the system uses insertion sort.  Insertion sort is known to have a worst case scenario of O(n^2) performance when the list is in reverse-sorted order.  Thus, the grey-box tester may add a test to check that the system is still able to handle sorting a list of emails which are in reversed order.  Another example would be noticing that there is no null check in the function to do a search, and checking if just hitting "enter" instead of typing something in the search bar causes a null pointer dereference, searching for "" and finding every single email, or some other unexpected behavior.

## Static and Dynamic Testing

Another way of categorizing tests is to group them into __static tests__ and __dynamic tests__.  In dynamic tests, the system under test is actually running; the code is executed.  Virtually every test we have discussed so far has been a dynamic test.  Even if we don't see the code itself, the computer is running it, doing something with the input provided, and eventually providing some output.

A static test, by contrast, does not execute the code.  Rather, it attempts to test aspects of the system without actually running the system.  Examples of static testing would be running a __linter__ (which flags "code smells" such as trying to use a variable before any value is assigned to it), or having somebody review the code manually without actually running it.

At first glance, the benefit of static testing may not be obvious.  After all, how can testing software without executing it provide any additional benefits?  It's as though you're deliberately removing yourself from the direct effects and looking at it from "one step away".  However, since static analysis looks directly at the code, instead of at the results of the code executing, it can help to find issues of quality in the code itself.

As an example, let's consider the following two methods, both of which accept a string `toChirp` and append the string `CHIRP!` to the end of it.  For example, passing in the value `foo` will return `fooCHIRP!` for each method:

```java
public String chirpify(String toChirp) {
    return toChirp + "CHIRP!";
}
```

```java
public String chirpify(String toChirp) {
       char[] blub = toChirp.toCharArray();
       char[] blub2 = new char[6];
       blub2[0] = (char) 0x43;
       blub2[1] = (char) 0110;
       blub2[2] = (char) 73;
       blub2[3] = (char) (0123 - 1);
       blub2[4] = (char) (40 * 2);
       blub2[5] = '!';
       String boxer99 = new String(blub).concat(new String(blub2));
       return boxer99;
}
```

Both of these will return the same result for a given input.  The output of both can be observed via dynamic tests, and checked against expected values.  However, would anyone argue that the second method is superior?  It's overly complex; it's difficult to understand what it does; the variable names are meaningless.  Finding all that is wrong with the code is left as an exercise for the reader.  With dynamic tests, it may be difficult or impossible to determine any difference between the two methods.  However, using static testing methods such as code review, it's trivial to find issues in the second method.
