# Why Test Software?

Now that we know what software testing is, it makes sense to ask why one would want to do it.  After all, you're definitely adding some extra work, since you'll have to write the tests.  You'll also have to ensure that you write code that's testable, and possibly create testing frameworks for the system.  Depending on how you test it, you may have to learn other frameworks or even other programming languages.

## To Test Or Not To Test

Let's imagine that you're put into the role of CEO of Rent-A-Cat.  A promising young project manager runs up to you in the halls, sweat dripping off of perfectly coiffed hair, clutching a printout of an Excel spreadsheet.

"Ma'am! (or sir)!" the project manager cries.  "I've discovered a way to reduce our project expenses by tens of thousands of dollars!  All we have to do is remove all testing resources from the team.  I've got some good software developers; there's no way they'd ever make a mistake.  This way, we can finally buy that gold-plated sink for the executive washroom!"

At this point, you have two options:

1. Cackle maniacally, and look forward to the feel of that distilled water on your manicured hands in the only sink regal enough for your presence.
2. Explain to the project manager the reasons for testing, and why it's important to test the software prior to release.

As you're already reading a book on software testing, I'll assume that you will choose Option 2.  At first glance, though, you might think that it would make sense not to test your software.  From an organizational standpoint, a corporation exists in order to enrich its shareholders.  If you can reduce the cost of developing software by removing part of the software development process, then you may owe it to your shareholders to think long and hard about whether it makes sense financially to keep the test team.

Remember that a CEO is in charge of an entire corporation, not just engineering, IT or any other particular part.  Even for a tech company, software testing will be a relatively minuscule part of the company in most cases.  There are many other areas asking for resources, often with good reason.  The marketing department is asking for more marketers, and a company that doesn't come up with good ideas for products will fail.  Operations is asking for more help desk personnel; if people can't figure out how to use the product, they will stop buying it and the company will fail.  All of these other parts of the company could probably use some of the financial resources currently being used to pay for software testing personnel.

All that being said, there are numerous reasons for you, as CEO, to turn down that project manager's plan.

## No Software Developer is Perfect

Hands up if you've never written a piece of incorrect code before.  If you don't have your hand up, then you should already see why you need to test your software.  (If your hand _is_ up, then I can assume that you've never written any code before.)  It's a good thing to remember that software development is one of the most intellectually complicated things that human beings do, stretching the very limits of what the human mind is capable of.  Thinking in those terms, that off-by-one error you made retrieving an index doesn't seem like so much of a big deal.

According to the National Institute of Standards and Technology, software defects cost the US economy almost $60 billion in 2002.[^nist2002]  That's approximately 0.6% of the country's gross domestic product.  As more and more software is written, and our daily lives become more and more connected with software, this figure has probably increased dramatically by now.  Even going with the low 2002 number, however, this means that software defects have caused problems worth the same as about one-third of all US agricultural output.

[^nist2002]: _The Economic Impact of Inadequate Infrastructure for Software Testing_, National Institute of Standards and Technology, 2002.  [http://www.nist.gov/director/planning/upload/report02-3.pdf](http://www.nist.gov/director/planning/upload/report02-3.pdf).

## Catching Defects Sooner Rather than Later

The Golden Rule of Testing is that you should find defects as early as you can.  If you find problems with the software early on, it is often trivial for a developer to add a fix, and nobody external to the team may ever know that for a while your application crashed if users entered numbers as their usernames.  A defect such as this one that a user stumbles across in production instead may be much more damaging---the company loses money since the application is down and users are upset.  In addition, it will be more difficult and costly to fix at that point.  Developers will most likely be stressed, and looking for the fastest "duct tape" solution as opposed to a solid fix.  Just like fixing your leaky pipes with duct tape, this solution may fall apart again at the worst possible time.

Software testing allows you to find these errors before users see them, when your team will do a better job of fixing them, with less impact to the bottom line.

## Stability

Developing software is a complex process.  At times, on some teams, it can even devolve into chaos.  A testing team can help alleviate this by providing stability.  By ensuring that developers continue to work on software that contains fewer defects, the software can stay on a more stable footing.  Additional functionality is less likely to be built on top of a shaky foundation.

A quality assurance team can also keep track of known existing defects, and allow prioritization to take place.  Instead of developers and managers dropping everything and working on every recently-found defect, or ignoring any new ones while they work on old ones, a test team can help determine what is worth working on at any given time.  This leads to a more rigorous and smoothly-flowing software development process.

## Customer Advocacy

Software developers, managers, and others working on a software project often have their own reasons for working on the project, aside from the fact that they are usually paid to work on it.  Programmers may want to try a different language; designers may want to try a new user interface paradigm; project managers may want to do whatever it takes to hit the deadline.  The history of software development is littered with projects that were technically interesting, or were released on time, but did not meet the needs of users.

QA engineers have a special role to play---they act as a representative of the customers and users of the software.  Their role is to ensure that the customer gets high-quality software that is what the customer wants.  In fact, in many organizations, software testers have the power to halt development or allocate resources in order to ensure that the customer receives the right software, built right.  Having somebody whose job is to act as an agent of the customer is a powerful force.  Used wisely, it can help to produce software which delights the user.

## An Independent, Whole-System Perspective

Developers are focused on very small parts of a system, with which they become intimately familiar.  Rare is the developer who has an understanding of the entire system under development.  Testers may not have depth of knowledge of any one piece of the software, but do tend to have a broad perspective of the system as a whole.  They are testing various aspects of the system, trying out new functionality, and installing it on different kinds of systems.

This whole-system perspective provides a valuable counterpoint to those who are heads-down on one area of the software.  Understanding how different subsystems work together, and how functionality works from a user perspective, lets QA personnel provide information on the status of the system _as a system_.  This also makes testers a valuable resource when new functionality is added or modified, as they will understand how this might impact the overall system.

While other stakeholders have a direct stake in the development of the software, the role of a tester is to provide an independent view of the software.  By viewing the system separately and without the biases (conscious and unconscious) of the developers and managers, software testers can provide a more realistic and objective status of the system.

## Ensuring Quality

Although testing software can provide you with many valuable benefits, it is not the sole way to improve the quality of your software.  One of the most well-regarded books on software engineering, _Code Complete_ by Steve McConnell, lists the defect detection rates of developers using different techniques.  Code reviews, formal design inspections, and software modeling have all been shown to increase the level of quality of software.  __Pair programming__, where two people work at the same time at one computer, has also been shown to have remarkable positive effects on software quality.  While it's easy for people to overlook their own mistakes, another person looking at the same code or text independently will often see something instantly.  I know of what I speak; I overlooked numerous embarrassing typos in this book that were caught as soon as it went out for review.

While quality does not only mean "reducing the number of defects", it is certainly an important part.  By finding defects, software testers directly improve the quality of software.  Users of the software will receive a better product

Allowing a proper amount of time for testing, development, and other aspects of the software development life cycle can also improve quality.  Very few pieces of software are written perfectly, and software written under a crushing deadline may not be of acceptable quality.  By providing adequate time and resources for engineers to develop the software, the level of quality will generally improve.

The choice of language, framework, and design of the program can also make a big difference in the quality of the program.  While every language has its partisans, there is definitely a reason that most web applications are not written in Assembly language, or that most embedded real-time software is not written with Ruby.  Different languages, libraries, etc. have different benefits, and using one that is appropriate for the system you are designing will pay dividends in terms of quality.

While this book focuses on testing, it should be recognized that it is only one part of producing quality software.  Quality on a modern software project is the responsibility of everybody on the team, not just the testers.

## Risk

The reason for testing software boils down to minimizing risk, for everybody involved: customers, users, developers, etc.  Independent testing of the software allow for objective analysis of the quality of the system.  This reduces risk by providing information on the status of the system, both at a high level (e.g., "the system is ready to be released") and a low level (e.g., "if a username contains the symbol `!`, the system will crash").  Software development is a complex and risky process.  If the CEO wants to help ensure that risk is at a minimum, it is essential that software testers are part of the team.
