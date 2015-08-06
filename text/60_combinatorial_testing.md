# Pairwise and Combinatorial Testing

Imagine that you're testing a word processor.  Specifically, you're testing the ability to add font effects, e.g., bold, italic, superscript, 3-D, etc.  Of course, in any word processor worth its salt, these effects can also be combined in one region of text, so that you may have a word which is both bold and italic, or a letter which is both subscripted and in 3-D, or even a sentence which is bold, italic, underlined, 3-D, and superscripted.  The number of possible combinations - ranging from absolutely no effects (and thus plain text) all the way to every single effect turned on - is 2 ^ n, where n is the number of effects.  Thus, if there are 10 different kinds of font effects available, the number of tests that you would have to run to fully test all of the possible combinations of font effects is 2 ^ 10, or 1,024.  This is a non-trivial number of tests to write.

If you want truly complete test coverage, you'll have to test each of these different combinations (e.g., only bold; bold and italic; bold and superscript; bold, superscripted, italic, strikethrough, 3-D; etc.).  Imagine if there is an issue that only shows up when a letter is italic, underlined, bold, subscripted, and struck-through.  You won't find that defect unless you take the time to go through a comprehensive test of all combinations!

However, it turns out that this situation is rarer than you might think.  You may not need to test all of these combinations in order to find a large percentage of the defects in the system.  In fact, according to a study by the National Institute of Standards and Technology, up to 90% of all defects in a software system can be found simply by testing all combinations of two variables.  In our example, if you just tested all of the possible pairs (bold and italic, bold and superscript, superscript and 3-D, etc.), you would have found many of the defects in the software but have used only a fraction of the testing tie.  Of all the software projects that they analyzed, the maximum number of variables that interacted to cause a defect that they found was six.  By keeping this in mind when constructing tests, you can find virtually all of the defects that a comprehensive test approach would find while using orders of magnitude less testing time and resources.

This technique is called __combinatorial testing__.  Testing all of the possible pairs of values is a kind of combinatorial testing, but has its own term, __pairwise testing__ or __all-pairs testing__.  At the beginning of this book, we discussed how comprehensively testing a non-trivial program was almost impossible.  By using the techniques in this chapter, however, we can reduce the number of tests dramatically (in some cases, by many orders of magnitude), but still find ourselves able to test extremely thoroughly.

## An Example Of Pairwise Testing

Let's take a very simple example, a program which has only three variables for a character's formatting: bold, italic, and underline.  These can be combined, so that, for example, a character can be just italic; or italic and underlined; or bold, italic and underlined.  Since each of these variables is a Boolean (they can only be true or false - you can't have a "half-italic" character) The list of possibilities for a character can be expressed as a truth table.

```
     BOLD       | ITALIC    | UNDERLINE 
    ------------+-----------+------------
1.   false      | false     | false
2.   false      | false     | true 
3.   false      | true      | false
4.   false      | true      | true 
5.   true       | false     | false
6.   true       | false     | true 
7.   true       | true      | false
8.   true       | true      | true 
```

In order to exhaustively test the program, we will need to run the eight tests above.  Let's assume that our manager is extremely strict, however, and insists that we only have time to run six tests.  How can we reduce the number of tests that we run while still maintaining the highest likelihood of finding defects?

As explained above, checking that all pairs of variables are checked can help us find a large percentage of defects.  There are three possible pairs of varibles in this example: bold and italic, bold and underline, and italic and underline.  For each of these pairs, we want to have an entire truth table of the two elements covering all possibilities.  For example, to ensure that we test all of the possible combinations of bold and italic, we can make the following truth table:

```
 BOLD       | ITALIC    
------------+-----------
 false      | false
 false      | true
 true       | false
 true       | true
```

Now we need to ensure that our tests cover every possibility of bold and italic.  We can see that test 1 provides false/false, test 4 provides false/true, test 5 provides true/false, and test 8 provides true/true.

```
     BOLD       | ITALIC    | UNDERLINE 
    ------------+-----------+------------
1.   false      | false     | false       <--
2.   false      | false     | true 
3.   false      | true      | false
4.   false      | true      | true        <--
5.   true       | false     | false       <--
6.   true       | false     | true 
7.   true       | true      | false
8.   true       | true      | true        <--
```

However, note that when we test these tests, we're also checking other combinations and pairs at the same time!  For example, running test 1 also gives us the false/false combination for bold/underline, and the false/false combination for italic/underline.  We're literally getting multiple tests for the price of one, so feel free to throw that in people's faces whenever they say that there's no such thing as a free lunch.

The next step is to ensure that the other variable pairs have all their combinations covered, using a similar technique.  However, as opposed to randomly selecting any test case which met our criteria as we did in the first selection, we want to check that we're not already testing that combination in a test that we're already performing.  This is to avoid duplicating effort; if test 1 is already checking for false/false for bold/underline, why add test 3 as well?  Let's check for bold/underline at this point, keeping in mind that we want to use already-existing tests if at all possible.  Test 1 gives us the false/false combination; test 4 gives us false/true; test 5 gives us true/false, and test 8 gives us true/true.  Wow!  All of the combinations were already covered by test cases we were going to do anyway.  We've now tested all bold/italic and bold/underline combinations with only four tests!

Finally, we should check the last variable combination, italic/underline.  Test 1 gives us false/false and test 4 gives us true/true.  Unfortunately, though, false/true and true/false are not yet covered by any tests that we're already planning on using, so we need to add some more.  In fact, since there are two different combinations, and these combinations are mutually exclusive, we will need to add two tests.  Let's add test 2 for false/true and test 3 for true/false.  Our final test plan is the following:

```
     BOLD       | ITALIC    | UNDERLINE 
    ------------+-----------+------------
1.   false      | false     | false
2.   false      | false     | true
3.   false      | true      | false
4.   false      | true      | true
5.   true       | false     | false
8.   true       | true      | true
```

We have met our strict manager's demand and reduced the number of tests by 25%.  We have used a __covering array__ to determine a smaller way to check all 2-way (pairwise) interactions.  This is actually not the optimal configuration for testing all combinations, as we used a naive algorithm and did not check other possibilities (perhaps by selecting different tests whenever we had a choice, we could have covered even more cases).  Although the particular algorithms used are beyond the scope of this book, there are tools that will generate these covering arrays quickly and automatically, using more advanced algorithms and heuristics.  These combinatorial test generators - such as the free Advanced Combinatorial Testing System from NIST - are invaluable when attempting to generate covering arrays for a non-trivial number of variables.

Using NIST ACTS with the IPOG algorithm, I was able to generate the folowing optimal covering array.  With only four tests, it would literally be impossible to be more efficient, since each pairwise truth table will themselves require four different tests.  Even more importantly, we have now exceeded the manager's expectations, cutting the tests down by 50% instead of the 25% that he demanded, yet still testing all pairwise interactions.

```
     BOLD       | ITALIC    | UNDERLINE 
    ------------+-----------+------------
1.   false      | false     | false
4.   false      | true      | true 
5.   true       | false     | false
7.   true       | true      | false
```

Although this example was done with Boolean variables, any kind of variable can be used as long as it's finite.  If it's a variable with infinite possibilities - say, an arbitrary length string - you can map to a certain number of possibilities (e.g., "a", "abcde", and "abcdefghijklmnop").  When doing so, you should first think of the different equivalence classes, if any, and ensure that you have a value from each equivalence class.  You should also try to check several different edge cases, especially those which may cause problems when interacting in combination with other variables.  Suppose in our previous example that instead of checking a character, we wanted to check a word.  We may add different possibilities for the word to be tested.  Let's start with "a" (a single character) and "bird", a simple word.  Our generated covering array will look similar to the previous one, just using "a" and "bird" as the values for the word variable, instead of true and false as for all of the other variables.

```
        WORD   | BOLD       | ITALIC    | UNDERLINE 
       --------+------------+-----------+------------
1.      "a"    | true       | false     | false
2.      "a"    | false      | true      | true
3.      "bird" | true       | true      | false
4.      "bird" | false      | false     | true
5.      "bird" | true       | false     | true
6.      "a"      | false      | false     | false
```

Note that we are still checking all pairs.  Let's check the word/bold pair to verify that that is the case.  In test 1, we check for the word "a" with bold set to true.  In test 2, we check the word "a" with bold set to false.  In test 3 we check the word "bird" with bold set to true, and in test 4 we check the word "bird" with bold set to false.  However, what if we're worried that special characters may cause an issue when formatting?  We'd want to add a third possibility for the word variable, which is certainly allowed even though all of our possibilities so far have used only two possible values.

```
        WORD   | BOLD       | ITALIC    | UNDERLINE 
       --------+------------+-----------+------------
1.      "a"    | true       | false     | false
2       "a"    | false      | true      | true
3       "bird" | true       | true      | false
4       "bird" | false      | false     | true
5       "!@#$" | true       | true      | true
6       "!@#$" | false      | false     | false
```

As a side note, notice that we are now testing even more combinations and yet there are still only six tests.  This is merely a foreshadowing of how much time can be saved with combinatorial testing.

## _n_-Way Interactions

Although most errors will be found by ensuring that all pairwise interactions are tested, often we would like to go even further and check for errors in three-way, four-way, or even more interactions.  The same theory holds for doing this: tests should be generated that cover the entire truth table for each 3-way variable interaction.  Just like we checked that all four true/false value combinations were tested for each two-way interaction in the first example, we will check that all eight value combinations for each three-way interaction are tested.

Let's expand the number of formatting variables in our system, adding SUPERSCRIPT and STRIKETHROUGH as possibilities.  In order to exhaustively test this, we will need 2 ^ 5, or 32, tests.  Generating a covering array for all pairwise interactions creates a six case test plan.  Once again, notice how we are testing even more variables than in the last case, but we are using the same number of tests.  In this case, we are running only 18.75% of the tests we would need for exhaustive testing, yet still testing all pairwise interactions.

```
        BOLD       | ITALIC    | UNDERLINE | SUPERSCRIPT | STRIKETHROUGH
       ------------+-----------+-----------+-------------+--------------
1.      true       | true      | false     | false       | false
2.      true       | false     | true      | true        | true
3.      false      | true      | true      | false       | true
4.      false      | false     | false     | true        | false
5.      false      | true      | false     | true        | true
6.      false      | false     | true      | false       | false
```

Checking for every possible three-way interaction is going to involve more tests.  If you think about it, this is logically necessary.  Since there are eight possible combinations for each three-way interaction, it would be impossible to cover any combination using only six tests.

```
        BOLD       | ITALIC    | UNDERLINE | SUPERSCRIPT | STRIKETHROUGH
       ------------+-----------+-----------+-------------+--------------
1.      true       | true      | true      | true        | true
2.      true       | true      | false     | false       | false
3.      true       | false     | true      | false       | true
4.      true       | false     | false     | true        | false
5.      false      | true      | true      | false       | false
6.      false      | true      | false     | true        | true
7.      false      | false     | true      | true        | false
8.      false      | false     | false     | false       | true
9.      false      | false     | true      | true        | true
10.     true       | true      | false     | false       | true
11.     true       | true      | true      | true        | false
12.     false      | false     | false     | false       | false
```

Let's examine a given three-way interaction, bold/italic/underline, and double-check that we are testing all of the possibilities.  False/false/false is covered by test 12; false/false/true is covered by test 9; false/true/false is covered by test 6; false/true/true is covered by test 5; true/false/false is covered by test 4; true/false/true is covered by test 3; true/true/false is covered by test 10; and true/true/true is covered by test 1.  Note that there are eight possible combinations and ten tests, so there is some repetition (for example, true/true/true is covered by both tests 1 and 11).  

The number of interactions can be tuned upwards as high as you would like, although if you are planning on testing n-way interactions where n is the number of variables you have, you are just doing exhaustive testing.  According to empirical studies done by NIST, the maximum number of interactions that caused an error was six, so checking for more than that would be overtesting in many situations.

## Working With Larger Variable Sets

Combinatorial testing seems to work well with relatively small data sets, saving us large percentages of time by reducing the number of tests necessary.  However, going from 32 to 12 tests is not that impressive; after all, 32 tests could probably still be run in a reasonable amount of time.  How well does combinatorial testing work for larger numbers of variables or possible values?

The answer is, incredibly well.  Instead of five Boolean variables, let's assume that we have fifty.  In order to exhaustively test all possible combinations, you'd need to run 2 ^ 50 (1,125,899,906,842,624) tests.  That's over a quintillion tests - you could do a test per second for the rest of your life and not make a dent.  However, if you're content with just checking each 2-way interaction, you can reduce that to fourteen tests!  That's a savings in tests that is really comprehensible using percentages, you're talking many, many orders of magnitude.  What once looked to be daunting is now easily attainable.  Increasing the number of interactions does not increase the amount of tests that greatly, either: testing all three-way interactions requires forty tests, and testing all four-way interactions only requires one hundred tests.  Even better, NIST ACTS was able to generate these test plans, even on my underpowered laptop, in less than a few seconds.

You can see that not only the number of tests you need grow sublinearly, the more variables you have, the higher percentage of time you will be saving by using combinatorial testing.  In the beginning of this book, we talked about how exhaustive testing was, for many programs, essentially impossible.  Using combinatorial testing is one way to ameliorate that problem.  With only a small percentage of the effort needed for exhaustive testing, we can find the vast majority of defects that would have been caught by it.


