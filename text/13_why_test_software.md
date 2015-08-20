# Why Test Software?

Now that we know what software testing is, it makes sense to ask why one would want to do it.  After all, you're definitely adding some extra work, since you'll have to write the tests.  You'll also have to ensure that you write code that's testable, and possibly create testing frameworks around the system.  Depending on how you test it, you may have to learn other frameworks or even other programming languages.

## Let's Put the Decision in Your Hands

Let's imagine that you're put into the role of CEO of Rent-A-Cat.  A promising young project manager runs up to you in the halls, sweat dripping off of perfectly coiffed hair, clutching a printout of an Excel spreadsheet.

"Ma'am! (or sir)!" the project manager cries.  "I've discovered a way to reduce our project expenses by tens of thousands of dollars!  All we have to do is remove all testing resources from the team.  I've got some good software developers; there's no way they'd ever make a mistake.  This way, we can finally buy a gold-plated sink for the executive washroom!"

At this point, you have two options.

1. Cackle maniacally, and look forward to the feel of that distilled water on your manicured hands in the only sink regal enough for your presence.
2. Explain to the project manager the reasons for testing, and why it's important to test the software prior to release.

As you're already a couple chapters in to a book on software testing, I'll assume that you're going to choose option #2.  At first glance, though, you might think that it would make sense not to test your software.  From an organizational standpoint, a corporation exists in order to enrich its shareholders.  If you can reduce the cost of developing software by removing part of the software development process, then you may owe it to your shareholders to think long and hard about whether it would be a good idea to do so.

Remember that a CEO is in charge of an entire corporation, not just engineering or IT or sales or any one particular part.  There's an entire company to run, and even for a tech company, software testing will be a relatively minuscule part of that in most cases.  If a company doesn't come up with good ideas for products, it will fail.  If it can't market them appropriately, it will fail.  If it can't sell them at a reasonable profit, it will fail.  All that being said, there are numerous reasons for that CEO to turn down the project manager's plan.

## No Software Developer is Perfect

Assuming you've programmed before, Hands up if you've never written a piece of incorrect code before.  If you have your hand up, then you need to test your software.  If you don't, then please stop lying and raise your hand.  It's a good thing to remember that software development is one of the most intellectually complicated things that human beings do, stretching the very limits of what the human mind is capable of.  Thinking in those terms, that off-by-one error you made retrieving an index doesn't seem like so much of a big deal.

According to the paper _The Economic Impact of Inadequate Infrastructure for Software Testing_, written by the  National Institute of Standards and Technology in 2002, software defects cost the US economy almost $60 billion annually.  That's approximately 0.6% of gross domestic product.  As more and more software is written, and our daily lives become more and more connected with software, this figure has probably increased dramatically by now.  Even going with the low 2002 number, however, this means that software defects have caused problems worth about 1/3 of the entire US agricultural output.

## Catching Bugs Sooner Rather than Later

The Golden Rule of Testing is that you should find defects as early as you can.  If you find problems with the software early on, it is often trivial for a developer to add a fix, and nobody external to the team may ever know that for a while your application crashed if users entered their usernames as numbers.  A defect such as this one that a user stumbles across in production may be much more damaging - the company loses money since the application is down, users are upset, and it will be more difficult to fix when people are under stress.  

Software testing allows you to find these errors before users see them, when your team will do a better job of fixing them, with less impact to the bottom line.

## Ensuring Quality

Although testing software can provide you with many valuable benefits, it is not the sole way to improve the quality of your software.  Code reviews and inspections by independent developers have been shown to greatly increase the level of quality of software.  __Pair programming__, where two people work at the same time at one computer, has also been shown to have remarkable positive effects on software quality.  While it's easy for people to overlook their own mistakes, another person looking at the same code or text independently will often see something instantaneously.  Trust me, I have overlooked numerous embarrassing typos that were caught as soon as this book went out for review.

Allowing a proper amount of time for testing, development, and other aspects of the software development life cycle can also improve quality.  Very few pieces of software are written perfectly, and software written under a crushing deadline is even more rarely of acceptable quality.  By providing adequate time and resources for engineers to develop the software, the level of quality will generally improve.

The choice of language, framework, and design of the program can also make a big difference in the quality of the program.  While every language has its partisans, there is definitely a reason that most web applications are not written in Assembly language, or that most embedded real-time software is not written with Ruby.  Different languages, libraries, etc. have different benefits, and using one that is appropriate for the system you are designing will pay dividends in terms of quality.

While this book focuses on testing, it should be recognized that it is only one part of producing software quality.  Quality on a modern software project is the responsibility of everybody on the team, not just the testers.


