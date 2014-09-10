## Defects

#### What is a Defect?

#### Reporting a defect

#### A Standardized Defect Template

This is not an industry standard defect reporting template, but I have found it to be very useful.  It ensures that all of the major aspects of a defect, once found, have been reported.  By acting as a kind of checklist for what to put down for a defect, it helps to ensure that a tester will not forget anything (that big blank space after a line has a way of showing up in most people's visual fields).  Note that although there is no identifier specifically listed here, it will often be automatically added by whatever defect tracking software you are using.  If you are not using defect tracking software, and don't want to use defect  tracking software, then you can add an IDENTIFIER field.

Without further ado, here is the template.

```
SUMMARY:
DESCRIPTION:
REPRODUCTION STEPS:
EXPECTED BEHAVIOR:
OBSERVED BEHAVIOR:
IMPACT:
SEVERITY:
NOTES:
```

##### Summary

The __summary__ is a one sentence or so summary of the defect found.  It's useful when people are scrolling through long lists of defects, or to make sure that people understand the gist of the defect.  Some examples of good summaries would be:

1. Page background is red, should be blue
2. In-app calculator displays -1 for result of sqrt(-1)
3. System exits with "SEGFAULT" error when user selected
4. Adding three or more items to a cart at the same time clears out other items

All of these describe the problem succinctly, without going into much detail.  Remember that the summary will often be glanced at along with perhaps hundreds of other bug summaries, especially if you are presenting to management.  There is a reason why most books written today have snappy titles, as opposed to titles such as __The Egg, Or The Memoirs Of Gregory Giddy, Esq: With The Lucubrations Of Messrs. Francis Flimsy, Frederick Florid, And Ben Bombast. To Which Are Added, The Private Opinions Of Patty Pout, Lucy Luscious, And Priscilla Positive. Also The Memoirs Of A Right Honourable Puppy. Conceived By A Celebrated Hen, And Laid Before The Public By A Famous Cock-Feeder.__  Yes, that is a real book, written in 1772.

##### Description

In the description, the defect is described in more detail.

##### Reproduction Steps

Remember that once you have found a defect, that somebody is probably going to try to fix it.  It may be you, or it may be somebody else, but the first step to fixing something is being able to reproduce it.  The __reproduction steps__ are the specific steps that one must take in order for the defect to manifest itself.

When someone brings a problem to a developer to be fixed, the order of operations is as follows:

1. Developer attempts to replicate defect.
2. If developer cannot replicate defect, will write on defect report that "it works on my machine" and stop working on defect.
3. Otherwise, the developer will write some code hoping to fix it.
4. Developer replicates original behavior that caused defect.
5. If defect is fixed, write on defect report that it has been fixed.  Otherwise, go to step 3.

You will notice that there is a bit of a premature exit at line 2 if the developer cannot replicate the defect.  You will also note the first appearance of the bane of many testers, the excuse that "it works on my machine!"  Development and production machines are often very different - in installed programs, libraries, perhaps even operating systems.   A defect appearing in production or in a test environment but not a developer's system is a very real and very common problem.  Just as often, though, the tester has simply not specified the reproduction steps as precisely as possible.  As a tester, you can help minimize the problem of hearing "it works on my machine!" by writing down reproduction steps in a detailed and unambiguous way.

This includes writing down any 

##### Expected Behavior

The core

##### Observed Behavior

##### Impact

The tester should have some idea of what impact this will have on the user.

##### Severity

In 

##### Notes

I like to think of this field as the "miscellaneous" field.  It's where everything which might be useful for tracking down a bug, or that may or may not be releant, goes.  It is also a good place for putting data which is too long to fit in any of the other sections, which should be relatively short and easy to grasp for developers, managers, other testers, and anyone else who may have to look at this defect and try to understand it, but may not be as familiar with that section of the software as the original tester.

What exactly goes here will vary by the type of software you are testing, but it will usually be technical details of the program or its environment, or factors which may or may not be relevant.  Some examples include:

1. Stack traces
2. Copies of relevant sections of the program log
3. System or environment variables
4. Technical specifications of where it was discovered (e.g., operating system, CPU, amount of RAM, etc.)
5. Other applications running the computer at the same time
6. Particular settings, flags, or arguments passed into the program
7. Suspicious or notable behavior of other programs
8. Copied error message text

#### Tracking, triaging, and prioritizing defects
