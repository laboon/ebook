## What is Software Testing, Anyway?

Let's start with what it's not.

1. It's not finding every single bug.
2. It's not randomly pressing buttons, hoping that something will break.
3. It's not hoping that something will break, period.
4. It's not something you do after all the programming is complete.
5. It's really, REALLY not something you don't think about until users start complaining.

#### So what is it?

  It's actually a variety of things.  At a high level, it's a way of providing an estimate of software quality to stakeholders (that is, people who have a direct interest in the system, such as customers, users, and managers).  By testing software, you can find defects (bugs) before the users stumble across them.

   Software testing provides an independent view of the software product.  Developers are notorious for - consciously or unconsciously - taking it easy on the code that they write.  I wish I could say that when I write code, I avoid this, what with my focus on software quality.  However, oftentimes my conversations with someone testing my software go like this:

Me: "I got the square root routine working!  It's twice as fast as it was before!"
Tester: "Hmm... when I pass in a letter instead of a number, the program crashes."
Me: "Oh, I'm sure nobody will type in a letter."
Tester: "When I give it a negative number, the program crashes."
Me: "Well, we don't really support imaginary numbers.  So I didn't do that."
Tester: "So I type in 2.0, and it throws an error that it can't deal with decimals."
Me: "Yeah, it probably should, but right now it only works for integers for input.  But the user probably knows that."
Tester: "Okay, when I give it 2, the screen just fills up with decimals..."
Me: "Well, obviously!  The square root of two is irrational, it will keep calculating until the universe ends in heat death!  Just put in a positive integer."
Tester: "When I type in 25, it gives me the answer 3."
Me: "Well, that's probably wrong.  I only ever tested it with an input of 9, so it passed all of my tests!"

(and so on)

   Keep in mind that I am someone who teaches a class on software testing.  Even I can't help being nice to the poor little functions that I write.  The job of the tester is to be the drill sergeant to my helicopter parent.  I can nurture the poor little function, but it's not going to get any stronger until someone tries to hit it with some discipline.



#### Verification and Validation

  Software testing also involves ensuring that the right software was created.  Imagine the following conversation between a tester and a customer:

Tester: "I've gone over the product with a fine-toothed comb.  This cryptography engine is absolutely bulletproof, incredibly fast, and uses 8,192-bit encryption - your secrets will be safe for a trillion years."
Customer: "Actually, I wanted to play solitaire."

  Would you say that the program has met the requirements of the customer?  Of course not.  Even though the software has met all of the requirements, doesn't crash, provides the correct answers, etc., if the software doesn't meet the needs of the customer, it's not going to be successful.

  This illustrates the difference between *verification* and *validation*.  Verification is building the software right; validation is building the right software.

#### Quality Assurance (QA) versus Quality Control (QC)

#### What is a bug, really?