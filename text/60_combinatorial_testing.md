## Pairwise and Combinatorial Testing

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
