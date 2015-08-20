# What is Software Testing, Anyway?

Let's start with a few things that it's not.

1. It's not finding every single bug.
2. It's not randomly pressing buttons, hoping that something will break.
3. It's not hoping that something will break, period.
4. It's not something you do after all the programming is complete.
5. It's really, REALLY not something you don't think about until users start complaining.

## So what is it?

It's actually a variety of things.  At a high level, it's a way of providing an estimate of software quality to stakeholders (that is, people who have a direct interest in the system, such as customers, users, and managers).  By testing software, you can find defects (bugs) before the users stumble across them.

Software testing provides an independent view of the software product.  Developers are notorious for - consciously or unconsciously - taking it easy on the code that they write.  I wish I could say that when I write code, I avoid this, what with my focus on software quality.  However, oftentimes my conversations with someone testing my software go like this:

_Me:_ "I got the square root routine working!  It's twice as fast as it was before!"

_Tester:_ "Hmm... when I pass in a letter instead of a number, the program crashes."

_Me:_ "Oh, I'm sure nobody will type in a letter."

_Tester:_ "When I give it a negative number, the program crashes."

_Me:_ "Well, we don't really support imaginary numbers.  So I didn't do that."

_Tester:_ "So I type in `2.0`, and it throws an error that it can't deal with decimals."

_Me:_ "Yeah, it probably should, but right now it only works for integers for input.  But the user probably knows that."

_Tester:_ "Okay, when I give it `2`, the screen just fills up with decimals..."

_Me:_ "Well, obviously!  The square root of two is irrational, it will keep calculating until the universe ends in heat death!  Just put in the square of a positive integer."

_Tester:_ "When I type in `25`, it gives me the answer 3."

_Me:_ "OK, that's probably wrong.  I only ever tested it with an input of `9`, so it passed all of my tests!"

(...and so on)

Keep in mind that I am someone who teaches a class on software testing.  Even I can't help being nice to the poor little functions that I write.  The job of the tester is to be the drill sergeant to my helicopter parent.  I can nurture the poor little function, but it's not going to get any stronger until someone tries to hit it with some discipline.


## What Software Testing Is Not

There's a common misconception out there that software testing means finding every single bug in a program.  This is similar to saying that the point of running a marathon is to go at the speed of light - after all, that's the fastest that one can go, so if one can do that, then that person is guaranteed to win!  It sounds ridiculous when you apply it to a human running, and to a disciplined software tester, it sounds almost as ridiculous.

An old joke goes, "All programs can be reduced by one instruction, and all programs contain at least one bug.  Thus, by induction, all programs can be reduced to one instruction, which will contain a bug."  There's quite a bit of wisdom in that statement.  Software programs are some of the most complicated things that humanity has created (I know, I know, *citation needed*, but follow me for a bit here before immediately dismissing that statement).

Let's take a simple calculator program.  It does everything a one dollar calculator you can buy at the drug store does - enter numbers up to eight digits, and then perform the four basic arithmetic operations (add, subtract, multiply, divide) on them.  Let's ignore decimals and negative numbers for now.  If you're the type of person who wants their hypothetical examples to be completely specified, let's say that while it can display negative and decimal numbers, you can't input them.

Now we would like to exhaustively test this to ensure that all of the calculations work correctly.  Well, we have 10 ^ 8 (100,000,000, or 100 million) possible values for the first input - anywhere from 0 to 99,999,999.  Now we have four operations which can be done on that first input, and we can enter another number, giving us another 100,000,000 possibilities.  Simple multiplication of `(10 ^ 8) * 4 * (10 ^ 8)` gives us 40,000,000,000,000,000, or 40 quadrillion tests.  If a human were to run these tests at a rate of one per second, that person would finish in around 1.2 billion years, well past the time that multicellular life is predicted to die out on Earth (around 800 million years from now, in case you had any plans after that).  None of this even takes into account the fact that you can chain operations together (e.g., `54,000 * 22 - 98,329 + 17 - 22,000,000`), which makes the number of possible inputs, for all intents and purposes, infinite.  Even if our human tester (who has now gone 1.2 billion years without a bathroom break) could test a trillion times faster than he or she has been doing, all baryonic matter in the Universe would have decayed before the tests are finished.

This is obviously a ridiculous testing plan, but there are definitely times when situations like this occur - the equivalent of only `43,743 * 67,129,003` returning an incorrect answer, despite all other numbers working correctly.  There are so many possibilities in even a moderately-sized program that it's virtually impossible to *prove* that a program will work without resorting to extreme measures.  If you're interested in those extreme measures, do some reading on a concept called "Formal Verification", which in my experience is not used much in industry.  This means that, more likely than not, bugs are hiding in those vast, unexplored regions... or at least there is no way to really prove that they are not.

Even a formally verified program (that is, a "mathematically proven" program) can't be guaranteed to provide the correct answer under all conditions.  A sudden loss of power to the machine on which it's running - perhaps the failure of a power supply - will certainly not allow the right answer to be displayed.  Corrupted memory or storage would give you the wrong answer.  Any program is reliant on the processor of the machine on which it's running, and there have been numerous cases of bugs in processors - see the `F00F` and `FDIV` bugs in various Intel Pentium chips.  It's difficult to prove that something will work when the entire mathematical groundwork of the "universe" the program lives in (viz., the processor) may not work!

Software testing can give you an idea of the quality of a piece of software, but it can never tell you that there are no defects, or that it will work under all circumstances.  If someone tries to promise you that, ask them how it would do running on the surface of Venus in a puddle of molten lead.

## Verification and Validation

Software testing also involves ensuring that the right software was created.  Imagine the following conversation between a tester and a customer:

_Tester:_ "I've gone over the product with a fine-toothed comb.  This cryptography engine is absolutely bulletproof, incredibly fast, and uses 8,192-bit encryption - your secrets will be safe for a trillion years."

_Customer:_ "Actually, I just wanted to play solitaire."

Would you say that the program has met the requirements of the customer?  Of course not.  Even though the software has met all of the requirements of the software, doesn't crash, provides the correct answers, etc., if the software doesn't meet the needs of the customer, it's not going to be successful.

This illustrates the difference between *verification* and *validation*.  Verification is ensuring that you're building the software right; validation is ensuring that you're building the right software.  In other words, __verification__ is ensuring that the system doesn't crash, that it meets the requirements, that it handles failures gracefully, etc.  __Validation__ is ensuring that the requirements are the right requirements - do they actually request what the user wants, and are there any gaps which mean that even if the software meets all the requirements, and all the requirements are correct, there are parts of the program which will not be what the user or customer expected?

It's more common than one would think to have gaps in requirements, or to have requirements which are ambiguous, incorrect, or even contradictory.  This is mostly due to the fact that if you were describing a program in sufficient detail, you would no longer be describing a program, you'd be *writing* a program.  In that case, you'd have all of the same problems as just writing the code in the first place.

## What is a bug, really?

It's important to keep in mind that not every problem found with a system is a defect.  A defect is an issue that either breaks the functionality of the system as it's currently understood, or does not meet the requirements of the program.  If a program operates normally and meets all of the requirements, then it does not have a defect.  If the program does not meet the requirements, or does not operate normally (e.g., crashes randomly, does not respond to normal system

For example, consider a company creating a brand-new Tic-Tac-Toe implementation.  The requirements are as follows:

1. The game board shall be three squares by three squares, for a total of nine contiguous squares.
2. The first player shall be allowed to mark an X in any square.
3. The second player shall then be allowed to mark an O in any open (that is, not already marked by an X or O) square, following the completion of the first move by the first player.
4. Players shall then take turns placing X's and O's (by the first and second player, respectively) in open squares, until there are no open squares (in which case the game is a draw) or an entire row, column, or diagonal are filled in with the same marker, in which case the owner of that marker (X for first player, O for second player) shall be the winner and the other player shall be the loser.

This sums up the game of Tic-Tac-Toe pretty nicely.  Now let's say that after beta testing, a user says that the game is unfair, because it forces one player to use X's and that mark is ugly.  The user suggests changing the X's into W's, because W's are a much more beautiful letter.  Is this a defect or enhancement?

It's an __enhancement__, because the system met all of the requirements and operates normally.  The fact that a user did not like it is irrelevant.  This isn't to say that enhancements are bad, or useless, or a lesser class of complaint, just that they're not defects.

Now let's consider an instance where the first player, who should only be able to mark squares with X's, can mark a square with an O.  This is a defect, because it violates Requirement #2.  Even if the game is perfectly playable (let's say that the second player's marks then become X's), it's still a defect because it violates a requirement.

Another example of a defect would be if the Tic-Tac-Toe board's display inverted after a player entered a mark in the center square.  There are no specific requirements against this happening, but there are varying "implicit requirements" to programs, such as not crashing, maintaining a display consistent with internal state, being responsive, etc.  These implicit requirements will vary based on the type of system; for example, a video game may need to be responsive 99% of the time, but a batch-processed weather forecasting program (where data is fed in, and the results returned 30 minutes later) may only need to be "responsive" in the sense that an answer will eventually come out.

There can be disagreement over whether an issue is a defect or an enhancement.  Much of this disagreement can arise due to these implicit requirements.  If a video game character always takes three seconds to respond after hitting a button, one might argue that this is too long, even if there isn't a specific performance problem.  It's just not a good user experience to have a three second lag all the time.  However, at what point the button response latency become no longer an issue?  Two seconds?  One?  One hundred milliseconds?  Similarly, is it acceptable for a program to crash if the system runs out of memory?  For a personal computer, possibly; for a mainframe running banking transfer software, probably not.

In some scenarios, the difference between a defect and an enhancement  will be a very big deal.  If your company is writing avionics software for a new fighter jet, there will most likely be a very rigorous process for determining whether something is an enhancement or a defect.  There will be specified requirements, arbitrators to make decisions, and people whose entire job is to draft and interpret requirements.  If a company is contracted to create a program written "to the letter of the law", that company will fight to say that a request by the customer is not a defect, but something not covered by requirements, and thus an enhancement.

In other scenarios, the boundary between defects and enhancements is a bit blurry.  Let's assume that you are working for a startup without much software engineering overhead, and where the only real requirement is the unstated "do whatever the customer wants or we'll go bankrupt".  In that case, if the customer wants something, then it should be worked on (within reason).

## A Real-Life Testing Example

Let's say that you are tasked with testing a new program, "Lowerify", which takes a string and returns a lower-case version.  The customer didn't give any more details, because it seemed self-explanatory - the input is text which may or may not be lowercase, the output is the same string, but any upper-case letters are turned to lowercase.  The method handling this in the program has the following method signature:

```java
public static String lowerify(String s)
```

The customer insists that there is nothing else that you need to know to start testing.  If you were tasked with testing this, though, what kinds of questions would you ask in order to develop a testing plan?  In other words, what other kinds of requirements would you try to elicit from the customer?

1. What kind of character encoding will this be in - Unicode, ASCII, EBCDIC, something else?
2. What's the expected maximum character length?  Something that works well for a few words may not work so well if it's fed in ten terabytes of text.
3. What is the expected behavior if the input text is in a language other than English?  Especially, what if it's a language that doesn't have the concept of uppercase and lowercase letters?
4. What should the program do if a Control-C or other cancel command occurs midway through?
5. Will this system be required to read data off the network?  If so, what should occur if there is a network failure - retry, shut down, show an error message, something else?

Ensuring that you have the correct answers to these questions is part of validation of the program.  You want to test against what the user wants the program to do.

Once you've established what the user wants, however, there's still work to be done.  You will need to verify that the program works under normal conditions and with a wide variety of input.

For this example, a few ideas of possible input to test that it works under a variety of cases -

1. A string of all capitalized letters, e.g., `ABCDEFG`
2. A string of already lowercase letters, e.g., `lmnop`
3. A string of non-alphabetic characters, e.g. `78 &^% 0() []`
4. A string of mixed capital and lowercase letters, e.g. `VwXyZ`
5. A string of special characters such a carriage returns and nulls, e.g. `\r\n\0`
6. An empty string
7. A very long string, say the text of a long book from Project Gutenberg
8. Executable code
9. Binary input
10. Strings with EOF markers buried inside

Can you think of any other possible inputs that might cause an error or an incorrect result?

External factors could also be a factor.  What happens when...

1. The system runs out of memory while processing the text?
2. The CPU is running numerous other processes, leaving the system unresponsive?
3. The network connection is lost midway through processing?

It's easy to see, even from this very simple example, that testing can be a very involved process, full of ambiguities.  As a tester, not only will you have to resolve these ambiguities, but also determine how much effort and time should be spent on resolving them.  The more energy you put into any particular aspect of testing, the less time you will have for other aspects.  Keep this in mind when you are developing a testing strategy - the amount of time you have to complete the project may be flexible, but it is always finite, and there are always various priorities which you will have to juggle in order to produce quality software.
