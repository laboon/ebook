## Pairwise and Combinatorial Testing

Imagine that you're testing a word processor.  Specifically, you're testing the ability to add font effects, e.g., bold, italic, superscript, 3-D, etc.  Of course, in any word processor worth its salt, these effects can also be combined in one region of text, so that you may have a word which is both bold and italic, or a letter which is both subscripted and in 3-D, or even a sentence which is bold, italic, underlined, 3-D, and superscripted.  The number of possible combinations - ranging from absolutely no effects (and thus plain text) all the way to every single effect turned on - is 2 ^ n, where n is the number of effects.  Thus, if there are 10 different kinds of font effects available, the number of tests that you would have to run to fully test all of the possible combinations of font effects is 2 ^ 10, or 1,024.  This is a non-trivial number of tests to write.

If you want truly complete test coverage, you'll have to test each of these different combinations (e.g., only bold; bold and italic; bold and superscript; bold, superscripted, italic, strikethrough, 3-D; etc.).  Imagine if there is an issue that only shows up when a letter is italic, underlined, bold, subscripted, and struck-through.  You won't find that defect unless you take the time to go through a comprehensive test of all combinations!

However, it turns out that this situation is rarer than you might think.  You may not need to test all of these combinations in order to find a large percentage of the defects in the system.  In fact, according to a study by the National Institute of Standards and Technology, up to 90% of all defects in a software system can be found simply by testing all combinations of two variables.  In our example, if you just tested all of the possible pairs (bold and italic, bold and superscript, superscript and 3-D, etc.), you would have found many of the defects in the software but have used only a fraction of the testing tie.  Of all the software projects that they analyzed, the maximum number of variables that interacted to cause a defect that they found was six.  By keeping this in mind when constructing tests, you can find virtually all of the defects that a comprehensive test approach would find while using orders of magnitude less testing time and resources.

This technique is called __combinatorial testing__.  Testing all of the possible pairs of values is a kind of combinatorial testing, but has its own term, __pairwise testing__ or __all-pairs testing__.  At the beginning of this book, we discussed how comprehensively testing a non-trivial program was almost impossible.  By using the techniques in this chapter, however, we can relax our testing only slightly, but still find ourselves able to test extremely thoroughly.

## Example




