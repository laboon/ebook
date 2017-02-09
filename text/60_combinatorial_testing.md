# Pairwise and Combinatorial Testing

Imagine that you're testing a word processor.  Specifically, you're testing the ability to add font effects, e.g., bold, italic, superscript, 3-D, etc.  Of course, in any word processor worth its salt, these effects can also be combined in one region of text, so that you may have a word which is both bold and italic, or a letter which is both subscripted and in 3-D, or even a sentence which is bold, italic, underlined, 3-D, and superscripted.  The number of possible combinations---ranging from absolutely no effects (and thus plain text) all the way to every single effect turned on---is 2^n, where _n_ is the number of effects.  Thus, if there are 10 different kinds of font effects available, the number of tests that you would have to run to fully test all of the possible combinations of font effects is 2^10, or 1,024.  This is a non-trivial number of tests to write.

If you want truly complete test coverage, you'll have to test each of these different combinations (e.g., only bold; bold and italic; bold and superscript; bold, superscripted, italic, strikethrough, 3-D; etc.).  Imagine if there is an issue that only shows up when a letter is italic, underlined, bold, subscripted, and struck-through.  You won't find that defect unless you take the time to go through a comprehensive test of all combinations!

However, it turns out that this situation is rarer than you might think.  You may not need to test all of these combinations in order to find a large percentage of the defects in the system.  In fact, according to the publication _Practical Combinatorial Testing_ by Rick Kuhn, up to 90% of all defects in a software system can be found simply by testing all combinations of two variables.  In our example, if you just tested all of the possible pairs (bold and italic, bold and superscript, superscript and 3-D, etc.), you would have found many of the defects in the software but have used only a fraction of the testing time.  Of all the software projects that they analyzed, the maximum number of variables that interacted to cause a defect that was found to be six.  By keeping this in mind when constructing tests, you can find virtually all of the defects that a comprehensive test approach would find while using orders of magnitude less testing time and resources.

This technique is called __combinatorial testing__.  Testing all possible pairs of values is a kind of combinatorial testing, but has its own term, __pairwise testing__ or __all-pairs testing__.  At the beginning of this book, we discussed how comprehensively testing a non-trivial program was almost impossible.  By using the techniques in this chapter, we can reduce the number of tests dramatically (in some cases, by many orders of magnitude), but still find ourselves able to thoroughly test a system.

Let's take a very simple example, a program which has only three variables for a character's formatting: bold, italic, and underline.  These can be combined, so that, for example, a character can be just italic; or italic and underlined; or bold, italic and underlined.  Since each of these variables is a Boolean (they can only be true or false---you can't have a "half-italic" character), the list of possibilities for a character can be expressed as a __truth table__.  A truth table will shows all possible values that a given group of variables may hold.

As an interesting side note, if you think of "false" as 0 and "true" as 1, constructing a truth table is analogous to counting in binary from 0 to the size of the table minus one.  In the below instance, we start at "000" (0), then "001" (1), "010" (2), all the way up to "111" (7).  This is a useful trick when creating these tables yourself.

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

In order to exhaustively test the program, we will need to run the eight tests above.  Let's assume that our manager is extremely strict, and insists that we only have time to run six tests.  How can we reduce the number of tests that we run while still maintaining the highest likelihood of finding defects?

As explained above, checking that all pairs of variables are checked can help us find a large percentage of defects.  There are three possible pairs of variables in this example: bold and italic, bold and underline, and italic and underline.  How do we know that there are only three possible pairs?  For a small number of variables, we can simply check that there are no other possibilities, but as the numbers get larger, we will want to be able to calculate how many are necessary.  This will require some mathematical explanation.

## Permutations and Combinations

You may be worried that the title of this book was misleading, and that the "friendly" introduction you were promised is going to be filled with dense mathematical operations.  Fear not, however - the math here is relatively easy to understand and the equations are kept to a minimum.

Before we discuss the basis of combinatorial testing, we should understand what a __permutation__ is.  A permutation is some possible arrangement of a __set__ of objects.  It will not have any elements removed or added, only their order has changed.  One way of thinking about this is shuffling a deck of cards; each time the cards are shuffled, a different permutation is created, as the order has changed but no cards have been added or removed.  As a specific numeric example, assume that the array X contains the following values: `[1, 12, 4]`.  The list of possible permutations is:

```
1, 12, 4
1, 4, 12
12, 1, 4
12, 4, 1
4, 1, 12
4, 12, 1
```

The number of possible permutations of a set is equal to the __factorial__ of _r_, or `r!` (pronounced "r factorial"), or `r * (r - 1) * (r -2 ) ... * 1`.  For example, the factorial of 3 is `3 * 2 * 1 = 6`, and the factorial of 5 is `5 * 4 * 3 * 2 * 1 = 120`.  

We may also be interested in how many different ordered subsets it is possible to make using 

Assuming that _r_ and _n_ are positive integers, the number of _r_-permutations of a given set of size _n_ is determined by the following formula:

```
P(n, r) = n! / ((n - r)!)
```

It may be helpful to walk through an example and see not only how this is calculated but also how it might be used in a real testing situation.  Let us assume that are testing a Turtle Volleyball Game which will match any 2 of 4 turtles with each other.  Our turtles' names are Alan, Bob, Charlene, and Darlene.  We want to ensure that for any given matchup, the game will work correctly, and for our purposes, the order matters.  That is, if Bob is playing Alan, Bob serves first, and thus it will be considered a different game than when Alan plays Bob and Alan serves first.

There are `P(4, 2)` possible matchups (i.e., 2-permutations).  Plugging these values into the equation above, we can see that we will need:

```
P(4, 2)
= 4! / ((4 - 2)!)
= 24 / 2!
= 24 / 2
= 12 matchups
```

1. Alan / Bob
2. Alan / Charlene
3. Alan / Darlene
4. Bob / Alan
5. Bob / Charlene
6. Bob/ Darlene
7. Charlene / Alan
8. Charlene / Bob
9. Charlene / Darlene
10. Darlene / Alan
11. Darlene / Bob
12. Darlene / Charlene

Remember, since we are looking at permutations, each turtle must be checked against every other turtle.

As you may have already guessed from the name, when we discuss combinatorial testing, we will be interested in checking __combinations__ of a set of elements.  A combination is some _unordered_ subset of a set.  Using similar nomenclature as with permutations, we can also refer to an __r-combination__ of a set to indicate combinations of subsets.  Ordinarily, we are interested in combinations of a smaller size than the original set, as an _r_-combination of a set where _r_ is the same size of the set is just the set itself.

The number of _r_-combinations of a given set of size _n_ is called the __binomial coefficient__.  It is determined by the following formula:

```
C(n, r) = n! / (r! * (n - r)!)
```

For example, the number of possible _3_-combinations of a set consisting of five elements is:

```
C(5, 3)
= 5! / (3! * (5 - 3)!)
= 120 / (6 * 2)
= 10 3-combinations
```

The function `C(n, r)` is often expressed as "n choose r".  For example, "10 choose 4" would refer to how many 4-combinations exist in a set of 10 elements (210).

Let us return to our four-turtle Turtle Volleyball game.  In this case, however, we don't care which turtle serves first - for the purposes of the game, Alan playing Bob is the same as Bob playing Alan.  In order to determine the number of tests necessary to test all of these, we need to determine the number of _2_-combinations of the a 4 element set, or `C(4, 2)`, or 6 tests.

1. Alan / Bob
2. Alan / Charlene
3. Alan / Darlene
4. Bob / Charlene
5. Bob / Darlene
6. Charlene / Darlene

The idea of the factorial becomes clear when viewing the possible results.  Note that our first turtle, Alan, must be shown interacting with three other turtles (all of the other turtles).  The second turtle, Bob, only needs to interact with two turtles, since we already have a pairing with Bob.  When we get to Charlene, we only need to interact with one other turtle, since Charlene has already interacted with Alan and Bob.  Finally, Darlene has already interacted with all of the other turtles, so no additional tests need to be added for her.

You will note that fewer tests are necessary for testing all of the possible combinations as opposed to permutations.  It turns out that the number of combinations necessary to fully test will grow relatively slowly with the size of n.  By taking advantage of the concepts of combinatorials, we will be able to drastically reduce the number of tests we will have to perform in order to have a good understanding of the quality of a particular program.

Permutations and combinations are part of the field known as "discrete mathematics", which has numerous applications to software development as a whole.  Most Computer Science majors will take at least one course on discrete mathematics during their undegraduate career.

## Pairwise Testing

Now that we understand the basic concepts  of combinatorics, let's put it into use in developing a pairwise testing plan.  For each of these pairs, we want to have an entire truth table of the two elements covering all possibilities.  For example, to ensure that we test all of the possible combinations of bold and italic, we can make the following truth table:

```
 BOLD       | ITALIC
------------+-----------
 false      | false
 false      | true
 true       | false
 true       | true
```

Now we need to ensure that our tests cover every possibility of bold and italic.  We can see that Test 1 provides false/false, Test 4 provides false/true, Test 5 provides true/false, and Test 8 provides true/true:

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

Note that when we test these tests, we're also checking other combinations and pairs at the same time!  For example, running Test 1 also gives us the false/false combination for bold/underline, and the false/false combination for italic/underline.  We're literally getting multiple tests for the price of one.  Feel free to throw that in people's faces when they say that there's no such thing as a free lunch.

The next step is to ensure that the other variable pairs have all their combinations covered, using a similar technique as the one we used for bold and italic.  As opposed to randomly selecting any test case which met our criteria as we did in the first selection, we will want to check that we're not already testing that combination in a test that we're already performing.  This is to avoid duplicating effort; if Test 1 is already checking for false/false for bold/underline, why add Test 3 as well?  Let's check for bold/underline at this point, keeping in mind that we want to use already-existing tests if at all possible.  Test 1 gives us the false/false combination; Test 4 gives us false/true; Test 5 gives us true/false, and Test 8 gives us true/true.  Wow!  All of the combinations were already covered by test cases we were going to do anyway.  We've now tested all bold/italic and bold/underline combinations with only four tests!

Finally, we should check the last variable combination, italic/underline.  Test 1 gives us false/false and Test 4 gives us true/true.  Unfortunately, though, false/true and true/false are not yet covered by any tests that we're already planning on using, so we need to add some more.  In fact, since there are two different combinations, and these combinations are mutually exclusive, we will need to add two tests.  Let's add Test 2 for false/true and Test 3 for true/false.  Our final test plan is the following:

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

We have met our strict manager's demand and reduced the number of tests by 25%.  We have used a __covering array__ to determine a smaller way to check all 2-way (pairwise) interactions.  A covering array is any array representing a combination of tests which will cover all possibilities of n-way interaction (in this case, 2-way interaction).  Note that since the original array which covered all possible truth table values also covers all possible combinations of those values, it is technically also a covering array.  In general usage, however, the term refers to a smaller version of the array which still contains all of the tests necessary to check the various n-way interactions.

The covering array that we have created is actually not the optimal configuration for testing all combinations, as we used a naïve algorithm and did not check other possibilities.  Perhaps by selecting different tests whenever we had a choice, we could have covered the same number of combinations with fewer test cases.  Creating these by hand is also extremely time-consuming, especially as the number of interactions and variables go up.  For these reasons, combinatorial testing patterns are often created using a software tool.  Although the particular algorithms and heuristics used are beyond the scope of this book, these tools will generate these covering arrays quickly and automatically.  These combinatorial test generators---such as the free Advanced Combinatorial Testing System from the National Institute of Standards and Technology (NIST)---are invaluable when attempting to generate covering arrays for a non-trivial number of variables.

Using NIST ACTS with the IPOG algorithm, I was able to generate the following optimal covering array.  With only four tests, it would literally be impossible to be more efficient, since each pairwise truth table will themselves require four different tests.  Even more importantly, we have now exceeded the manager's expectations, cutting the tests down by 50% instead of the 25% that he or she demanded, yet still testing all pairwise interactions:

```
     BOLD       | ITALIC    | UNDERLINE
    ------------+-----------+------------
1.   false      | false     | false
4.   false      | true      | true
6.   true       | false     | true
7.   true       | true      | false
```

Let's verify that all possible pairs exist in this test plan.

1. _BOLD/ITALIC_ : false/false is handled in test case 1, false/true in test case 4, true/false in test case 6, and true/true in test case 7.
2. _ITALIC/UNDERLINE_ : false/false is handled in test case 1, false/true in test case 6, true/false in test case 7, and true/true in test case 4.
3. _BOLD/UNDERLINE_ : false/false is handled in test case 1, false/true in test case 4, true/false in test case 7, and true/true in test case 6.

Although this example was done with Boolean variables, any kind of variable can be used as long as it's finite.  If it's a variable with infinite possibilities---say, an arbitrary length string---you can map to a certain number of possibilities (e.g., "`a`", "`abcde`", and "`abcdefghijklmnop`").  When doing so, you should first think of the different equivalence classes, if any, and ensure that you have a value from each equivalence class.  You should also try to check several different edge cases, especially those which may cause problems when interacting in combination with other variables.  Suppose in our previous example that instead of checking a character, we wanted to check a word.  We may add different possibilities for the word to be tested.  Let's start with "`a`" (a single character) and "`bird`", a simple word.  Our generated covering array will look similar to the previous one, just using "`a`" and "`bird`" as the values for the word variable, instead of true and false as for all of the other variables:

```
        WORD   | BOLD       | ITALIC    | UNDERLINE
       --------+------------+-----------+------------
1.      "a"    | true       | false     | false
2.      "a"    | false      | true      | true
3.      "bird" | true       | true      | false
4.      "bird" | false      | false     | true
5.      "bird" | true       | false     | true
6.      "a"    | false      | false     | false
```

Note that we are still checking all pairs.  Let's check the word/bold pair to verify that that is the case.  In Test 1, we check for the word "`a`" with bold set to true.  In Test 2, we check the word "`a`" with bold set to false.  In Test 3 we check the word "`bird`" with bold set to true, and in Test 4 we check the word "`bird`" with bold set to false.  What if we're worried that special characters may cause an issue when formatting?  We'd want to add a third possibility for the word variable, which is certainly allowed even though all of our possibilities so far have used only two possible values:

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

As a side note, notice that we are now testing even more pairwise combinations and yet there are still only six tests.  This is merely a foreshadowing of how much time can be saved with combinatorial testing.

## _n_-Way Interactions

Although many errors will be found by ensuring that all pairwise interactions are tested, often we would like to go even further and check for errors in three-way, four-way, or even more interactions.  The same theory holds for doing this: tests should be generated that cover the entire truth table for each 3-way variable interaction.  Just like we checked that all four true/false value combinations were tested for each two-way interaction in the first example, we will check that all eight value combinations for each three-way interaction are tested.

Let's expand the number of formatting variables in our system, adding SUPERSCRIPT and STRIKETHROUGH as possibilities.  In order to exhaustively test this, we will need 2^5, or 32, tests.  Generating a covering array for all pairwise interactions creates a six-case test plan.  Once again, notice how we are testing even more variables than in the last case, but we are using the same number of tests.  In this case, we are running only 18.75% of the tests we would need for exhaustive testing, yet we are still testing all pairwise interactions:

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

Checking for every possible three-way interaction is going to involve more tests.  If you think about it, this is logically necessary.  Since there are eight possible combinations for each three-way interaction, it would be impossible to cover any combination using only six tests:

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

Let's examine a given three-way interaction, bold/italic/underline, and double-check that we are testing all of the possibilities.  False/false/false is covered by Test 12; false/false/true is covered by Test 9; false/true/false is covered by Test 6; false/true/true is covered by Test 5; true/false/false is covered by Test 4; true/false/true is covered by Test 3; true/true/false is covered by Test 10; and true/true/true is covered by Test 1.  Note that there are eight possible combinations and ten tests, so there is some repetition (for example, true/true/true is covered by both Tests 1 and 11).

The number of interactions can be tuned upwards as high as you would like, although if you are planning on testing n-way interactions where _n_ is the number of variables you have, you are just doing exhaustive testing.  According to empirical studies done by NIST, the maximum number of interactions that caused an error was six, so checking for more than that would be over-testing in many situations.

## Working with Larger Variable Sets

Combinatorial testing seems to work well with relatively small data sets, saving us large percentages of time by reducing the number of tests necessary.  However, going from 32 to 12 tests is not that impressive; after all, 32 tests could probably still be run in a reasonable amount of time.  How well does combinatorial testing work for larger numbers of variables or possible values?

The answer is, incredibly well.  Instead of five Boolean variables, let's assume that we have fifty.  In order to exhaustively test all possible combinations, you'd need to run 2^50 (1,125,899,906,842,624) tests.  That's over a quintillion tests---you could do a test per second for the rest of your life and not make a dent.  However, if you're content with just checking each 2-way interaction, you can reduce that to 14 tests!  That's a savings in tests that is really incomprehensible using percentages; you're talking many, many orders of magnitude.  What once looked to be daunting is now easily attainable.  Increasing the number of interactions does not increase the amount of tests that greatly, either: testing all three-way interactions requires forty tests, and testing all four-way interactions only requires one hundred tests.  Even better, NIST ACTS was able to generate these test plans, even on my underpowered laptop, in less than a few seconds.

You can see that not only does the number of tests you need grow sublinearly; the more variables you have, the higher percentage of time you will be saving by using combinatorial testing.  In the beginning of this book, we talked about how exhaustive testing was, for many programs, essentially impossible.  Using combinatorial testing is one way to ameliorate that problem.  With only a small percentage of the effort needed for exhaustive testing, we can find the vast majority of defects that would have been caught by it.

