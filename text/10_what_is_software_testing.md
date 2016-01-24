# What is Software Testing?

Let's start with a few things that it is not:

1. It's not finding every single defect.
2. It's not randomly pressing buttons, hoping that something will break.
3. It's not hoping that something will break, period.
4. It's not something you do after all the programming is complete.
5. It's really, REALLY not something you postpone until users start complaining.

## A Definition of Software Testing

At a high level, software testing is a way of providing an estimate of software quality to stakeholders (that is, people who have a direct interest in the system, such as customers, users, and managers).  While stakeholders may not directly care about the quality of software, they are interested in managing risk.  This risk can take many forms.  For customers, using software involves risks that they will lose data, risks that their operations will fail, risks that they will spend more money on the software than it will save them.  For internal stakeholders, risks may include not releasing a software system on time (or at all), losing customers by releasing a substandard product, or even facing a lawsuit.  By testing software, you can provide a better estimate of how much risk the stakeholders will have to take on.

Software testing provides an independent view of the software product.  Developers are notorious for---consciously or unconsciously---taking it easy on the code that they write.  I wish I could say that when I write code, I avoid this, what with my focus on software quality.  However, oftentimes my conversations with someone testing my software go like this:

> _Me:_ "I got the square root routine working!  It's twice as fast as it was before!"
>
> _Tester:_ "Hmm... when I pass in a letter instead of a number, the program crashes."
>
> _Me:_ "Oh, I'm sure nobody will type in a letter."
>
> _Tester:_ "When I give it a negative number, the program crashes."
>
> _Me:_ "Well, we don't really support imaginary numbers.  So I didn't check for that."
>
> _Tester:_ "I type in `2.0`, and it throws an error that it can't deal with decimals."
>
> _Me:_ "Yeah, it probably should, but right now it only works for integers for input.  But the user probably knows that."
>
> _Tester:_ "Okay, when I give it `2`, the screen just fills up with decimals..."
>
> _Me:_ "Well, obviously!  The square root of two is irrational, it will keep calculating until the universe ends in heat death!  Just put in the square of a positive integer."
>
> _Tester:_ "When I type in `25`, it gives me the answer 3."
>
> _Me:_ "OK, that's probably wrong.  I only ever tested it with an input of `9`, so it passed all of my tests!"

(...and so on.)

Keep in mind that I am someone who teaches a class on software testing.  Even I can't help being nice to the poor little functions that I write.  The job of the tester is to be the drill sergeant to my helicopter parent.  I can nurture the poor little function, but it's not going to know what it's capable of until it experiences some discipline.

## Verification and Validation

Software testing also involves ensuring that the right software was created.  Imagine the following conversation between a project manager and a customer:

> _Project Manager:_ "I've gone over the product with a fine-toothed comb.  This cryptography engine is absolutely bulletproof, incredibly fast, and uses 8,192-bit encryption---your secrets will be safe for a trillion years."
>
> _Customer:_ "Actually, I just wanted to play solitaire."

Would you say that the program has met the requirements of the customer?  Of course not.  Even though the software has met all of the requirements of the software, doesn't crash, provides the correct answers, etc., if the software doesn't meet the needs of the customer, it's not going to be successful.

This illustrates the difference between __verification__ and __validation__.  Verification is ensuring that you're building the software right; validation is ensuring that you're building the right software.  In other words, verification is ensuring that the system doesn't crash, that it meets the requirements, that it handles failures gracefully, etc.  Validation is ensuring that the requirements meet the actual needs of the customer: Does the software do what the user wants?  Are there any gaps in the requirements so that even if the software does meet all the requirements, the user will not be satisfied with the product?

Both verification and validation are part of the software testing process.  Although most testers will spend much of their time concerned with verification, a software tester does not blindly test that the software meets the requirements.  Testers can be thought of as defenders of the user experience, even pushing back against other internal stakeholders to develop software which meets the needs of users instead of simply meeting the bottom line.

Interestingly, as more time and resources are spent on fixing defects or improving quality in other ways, money may be saved in the long run.  Developing on a system which has fewer defects, or at least which has defects you already know about, is far easier than adding features to software which fails intermittently and seemingly randomly.  A system with a good automated test suite will allow you to make changes with the assurance that you have not created additional defects in so doing.  This is the paradox of software quality---you may end up spending _less_ money and time for a _better_ product by properly testing.

## A Preliminary Definition of "Defect"

It's important to keep in mind that not every problem found with a system is a defect.  A defect is an issue that either breaks the functionality of the system as it's currently understood, or does not meet the requirements of the program.  If a program operates normally and meets all of the requirements, then it does not have a defect.  If the program does not meet the requirements, or does not operate normally (e.g., crashes randomly, does not respond to user input, etc.), then a defect has been found.

For example, consider a company creating a brand-new Tic-Tac-Toe implementation.  The requirements are as follows:

1. The game board shall be three squares by three squares, for a total of nine contiguous squares.
2. The first player shall be allowed to mark an X in any one square.
3. The second player shall then be allowed to mark an O in any one open (that is, not already marked by an X or O) square, following the completion of the first move by the first player.
4. Players shall then take turns placing X's and O's (by the first and second player, respectively) in open squares, until there are no open squares and no row, column, or diagonal is filled in with the same marker, in which case the game is a draw; or until an entire row, column, or diagonal is filled in with the same marker, in which case the owner of that marker (X for first player, O for second player) shall be the winner and the other player shall be the loser.

This sums up the game of Tic-Tac-Toe pretty nicely.  Now let's consider an instance where the first player, who should only be able to mark squares with X's, can mark a square with an O.  This is a defect, because it violates Requirement 2.  Even if the game is perfectly playable (let's say that the second player's marks then become X's), it's still a defect because it violates a requirement.

Now let's say that after beta testing, a user says that the game is unfair, because it forces one player to use X's and that mark is ugly.  The user suggests changing the X's into W's, because W is a much more beautiful letter.  Is this a defect or enhancement?

It's an __enhancement__, because the system met all of the requirements and operates normally.  The fact that a user does not like it does not make it a defect!  It may be very important to make this change, perhaps even more important than fixing actual defects.  Enhancements are not bad, or useless, or a lesser class of complaint; they just involve modifying the existing requirements of the system.

Another example of a defect would be if the Tic-Tac-Toe board's display disappears after a player entered a mark in the center square.  There are no specific requirements against this happening, but there are varying "implicit requirements" to programs, such as not crashing, maintaining a display consistent with internal state, being responsive, etc.  These implicit requirements will vary based on the type of system; for example, a video game may need to be responsive 99% of the time, but a batch-processed weather forecasting program (where data is fed in, and the results returned 30 minutes later) may only need to be "responsive" in the sense that an answer will eventually come out.

There can be disagreement over whether an issue is a defect or an enhancement.  Much of this disagreement can arise due to these implicit requirements.  If a video game character always takes three seconds to respond after hitting a button, one might argue that this is too long, even if there isn't a specific performance requirement.  It's just not a good user experience to have a three second lag all the time.  However, at what point is the button response latency no longer an issue?  Two seconds?  One?  One hundred milliseconds?  Similarly, is it acceptable for a program to crash and lose data if the system runs out of memory?  For a simple application running on your phone, possibly; it may be considered a rare enough event, and with such small impact for the average user, that adding support for it would be an enhancement.  For a mainframe running banking transfer software, this would almost certainly be a defect; preventing loss of data, even if not explicitly called out in the requirements, is extremely important in this domain.  Being familiar with the __system under test__ and its domain can allow you to perform __"seat of your pants" testing__; that is, testing for behavior that is not formally specified, but based on your knowledge of the system and domain, is to be expected..

In some scenarios, the difference between a defect and an enhancement will be a very big deal.  If your company is writing avionics software for a new fighter jet, there will most likely be a very rigorous process for determining whether something is an enhancement or a defect.  There will be specified requirements, arbitrators to make decisions, and people whose entire job is to draft and interpret requirements.  If a company is contracted to create a program written "to the letter of the law", that company will fight to say that a request by the customer is not a defect, but something not covered by requirements, and thus an enhancement.

In other scenarios, the boundary between defects and enhancements is a bit blurry.  Let's assume that you are working for a startup without much software engineering overhead, and where the only real requirement is the unstated "do whatever the customer wants or we'll go bankrupt".  In that case, if the customer wants something, then it should be worked on (within reason).

While deciding which defects or enhancements should be worked on is almost always in the realm of project management instead of quality assurance, input from the QA team is often helpful to them.  Software testing allows you to determine the impact of found defects, as well as the potential risks of fixing defects, making enhancements, or simply providing workarounds.  Having this knowledge will allow project managers to make better-informed decisions about the direction of the product.

## A Real-Life Testing Example

Let's say that you are tasked with testing a new program, "Lowerify", which takes a string and returns a lower-case version.  The customer didn't give any more details, because it seemed self-explanatory---the input is text that may or may not be lowercase, the output is the same string, but any upper-case letters are turned to lowercase.  The method handling this in the program has the following method signature:

```java
public String lowerify(String s)
```

The customer insists that there is nothing else that you need to know to start testing.  If you were tasked with testing this, though, what kinds of questions would you ask in order to develop a testing plan?  In other words, what other kinds of requirements would you try to elicit from the customer?

1. What kind of character encoding will this be in---UTF-8, ASCII, EBCDIC, something else?
2. What's the expected maximum character length?  Something that works well for a few words may not work so well if it's fed in ten terabytes of text.
3. What is the expected behavior if the input text is in a language other than English?  Especially, what if it's a language that doesn't have the concept of uppercase and lowercase letters?
4. What should the program do if a Control-C or other cancel command occurs midway through?
5. Will this system be required to read data off the network?  If so, what should occur if there is a network failure---retry, shut down, show an error message, something else?

Ensuring that you have the correct answers to these questions is part of the validation of the program.  You want to test against what the user wants the program to do.

Once you've established what the user wants, however, there's still work to be done.  You will need to verify that the program works under normal conditions and with a wide variety of input.

For this example, a few ideas of possible input to test that it works under a variety of cases:

1. A string of all capitalized letters, e.g., "`ABCDEFG`"
2. A string of already lowercase letters, e.g., "`lmnop`"
3. A string of non-alphabetic characters, e.g., "`78 &^% 0() []`"
4. A string of mixed capital and lowercase letters, e.g., "`VwXyZ`"
5. A string of special characters such as carriage returns and nulls, e.g., "`\r\n\0`"
6. An empty string
7. A very long string; say, the text of a long book from Project Gutenberg
8. Executable code
9. Binary input
10. Strings with EOF markers buried inside

Can you think of any other possible inputs that might cause an error or an incorrect result?

External factors could also be relevant.  What happens when...

1. The system runs out of memory while processing the text?
2. The CPU is running numerous other processes, leaving the system unresponsive?
3. The network connection is lost midway through processing?

It is important to note that it would be literally impossible to exhaustively test every combination of input.  Even if we were just to test alphanumeric inputs of exactly 10 characters, and ignore all external factors, you are already looking at over 3 quadrillion test cases.  Since strings can be of arbitrary length (up to the storage limits of the computer), and there are plenty of external factors to consider, an exhaustive test plan for this function would take billions of years to execute!

It's easy to see, even from this very simple example, that testing can be a very involved process, full of ambiguity and hard decisions about what to focus on.  As a tester, not only will you have to resolve these ambiguities, but also determine how much effort and time should be spent on resolving them.  The more energy you put into any particular aspect of testing, the less time you will have for other aspects.  Keep this in mind when you are developing a testing strategy---the amount of time you have to complete the project may be flexible, but it is always finite, and there are always various priorities which you will have to juggle in order to produce quality software.

Remember that the reason behind testing software is to estimate---and if possible, reduce---the risk to stakeholders.  Understanding the possible risks can itself help to reduce risk.  After all, untested software which was never run could (theoretically, at least) be perfect, or it could not work at all.  Testing helps calculate where between those two extremes the software _actually_ is.  It can help us figure out whether there are issues with the software which are trivial, or problems which should stop the product from shipping because major functionality does not work.  By helping to determine the level of risk, software testers allow the other stakeholders involved to make appropriate decisions.
