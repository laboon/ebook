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

This includes writing down any relevant steps and preconditions before the actual steps which directly caused the defect to occur.  For example, if a certain environment variable needs to be set before starting the program, or a particular garbage collection flag was passed in, or even that the system was running for six hours before the particular test steps occurred.  Determining what is and is not relevant is a difficult process.  As you gain experience testing projects in your particular domain, and understand the system under test more, you will be able to use your testing "sixth sense" to determine this.  Before you have hit that point, it usually makes sense to err on the side of "too much information" rather than too little.

It is imperative to write down specific values and steps, especially if there is more than one way to do things.  Never write that one should "Enter an invalid value"; instead, write that one should "Enter -6 once the > prompt appears."  Note that in the latter example, the tester is providing a specific instance of an invalid value, as well as saying where and when it should be entered.  Just like when writing test cases, you should imagine that the person executing these is an automaton with a general understanding of the system.  Tell that automaton exactly what to do and remove as much ambiguity as is possible.

##### Expected Behavior

The core concept behind testing, and one which we will refer to again and again throughout this book, is checking that the expected behavior of a system matches the observed behavior.  It makes little sense to test something if you don't know what should happen.  In the __expected behavior__ field, the tester should note what the system was expected to do after the execution steps are executed.

Once again, the more specific the behavior listed, the easier it will be for developers and other testers to reproduce the issue, and eventually fix it.  One should never write down that "the system should return the correct value"; instead, write that the "the system should return 6".  

Aside from saving people from calculating the correct value themselves, another benefit of writing down the expected behavior so precisely is that the tester or test case may not be expecting the correct behavior!  In this case, someone can view the defect and determine that it's actually invalid - the software is performing as intended.  This can happen when the requirements are ambiguous or the reporting tester simple makes a mistake.

##### Observed Behavior

Opposed to the expected behavior field is the __observed behavior__ field.  This describes what actually happened after the execution steps were executed.  Just as in all of the fields of the defect report template, this should be filled in as precisely as possible.

##### Impact

The tester should have some idea of what impact this will have on the user.  In the __Impact__ field, this can be specified.  

Be careful not to overgeneralize or editorialize in this section.  Let's say that a video game background color changes from blue to purple when the player jumps.  A factual way of describing the impact is that "User will see background color change, but gameplay is unaffected."  An editorialized version would be "User will hate this game and everybody who made it, because the stupid background becomes a stupid color when the stupid user does something stupid."  In general, if you find yourself using the word "stupid" or other derogatory words in your defect reports, there is probably a better way to express the issue.

##### Severity

Related to the Impact field is the __Severity__ field, which contains the defect reporter's initial impression of how severe the issue is.  This can either be explained qualitatively (e.g., "this is really, really, bad.  Really, really, really bad." or "This isn't much of an issue, to be honest") or a standardized scale can be used in order to be more quantitative.  The latter is far more popular, but will usually vary from organization to organization.  It may be something as simple as a numeric scale, with "1" being a very minor issue and "10" being an absolute showstopper.  However, oftentimes there will be gradations with descriptors.

An example rating system is explained below.  

1. __Blocker__ - This is a defect so severe that the system cannot reasonably be released without either fixing it or devising a workaround.  Examples of blocker bugs would be 

2. __Critical__ - Although 

3. __Major__ - 

4. __Normal__ -

5. __Minor__ - 

6. __Trivial__ - The defect would probably not even be noticed, unless someone is looking specifically for it.

7. __Enhancement__ - Sometimes a defect isn't really a defect; it's something that the users want, but is not specified by the requirements or is otherwise not in the scope of the current project.  In such a case, the defect can be filed as an enhancement.  Alternatively, a tester may think that a problem is a defect, but management may overrule him or her and mark it as an enhancement.

Grading the severity of bugs can be art, not a science, although there are some heuristics for determining how severe an issue is.  Depending on the domain of software in which the tester is working, as well as the organization's own rules for testing, the severity of a defect may be marked dramatically differently.  Even within organizations, there can be conflicts between engineers, managers, testers, and other stakeholders on exactly how severe a defect is.

Severity should not be mistaken for priority.  Severity is how severe the problem is, whereas priority marks what order the organization would like to see the problem fixed.  For example, a typo on the home page of a web application is of low severity, as it does not impact the functionality of the software.  However, it reflects poorly on the company that created it, and will require only a modicum of developer time to fix.  In such a case, its priority would be very high.  Alternatively, a defect could of low priority, but high severity.  An example would be a serious performance issue with database reads, where they are so slow that it makes the system completely unusable to the user after a half-hour of work.  This is a very severe problem.  However, if fixing it will be a very time-consuming process, and database optimization is already planned for later in the project, it may be of low priority.  With all else being equal, though, defects of a higher severity are usually of higher priority than ones of a lower severity.

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

##### Exceptions to the Template

For some defects, fields may be intentionally left blank.  For example, the defect may be relatively simple and not include any extra detail, so the Notes field may be marked "N/A".  In many cases where the expected behavior is obvious, this field can also be marked "N/A"; if the Observed Behavior is "System crashes", it would be obvious to most reviewers that the Expected Behavior is "System does not crash."  As a best practice, it's better to write "N/A", "None" or some other signifier, so that others who look at the defect report are not under the impression that the tester just forgot to fill in some fields.  

In other cases, the field may be marked by "Unknown" or a similar marker that indicates that the filer of the defect does not know what the field shoud be.  For example, if the system crashed randomly, with no error message or other output, and the tester is unable to reproduce it, the Reproduction Steps field may be marked "Unknown".  If requirements are ambiguous or contradictory, the expected behavior field may be marked "Unknown".  If the tester is not sure what impact the defect would have on the end user, e.g., because the problem is deep in the infrastructure of the system, the Impact field may be marked "Unknown".

Depending on how much focus is spent on testing in an organization, some of the fields may be omitted, or some added.  For example, in some organizations, testers may not have the domain knowledge to be able to determine the impact or severity of a defect.  At a minimum, any defect report should at a minimum include reproduction steps, the expected behavior, and the observed behavior.  Anything beyond that is the icing on the bug cake.

#### Defect Examples

In this section, several examples of defects will be provided.

Note that a defect error provides a description of the defect, not what should be done to fix it.  In the first example, there are numerous ways that the defect could be resolved - the default error message for a 500 screen may be changed to include a message such as "Press the back button to retry", or the a null check could be added to the code so that the exception is never thrown, or additional error handling could occur inside the exception handler.  From the point of view of someone filing a defect, it doesn't matter; the problem is that the defect exists, and it is the job of the filer to report it accurately, not to say how it should be fixed.  This is similar to writing requirements, which are supposed to say what should be done, not how to do it.

SUMMARY: Site returns 500 error when username is blank

DESCRIPTION: When a user attempts to log in and fills in a password, but does not fill in a username, the system returns a 500 error page (see NOTES section for text of page).

This was attempted with numerous passwords.  It does not occur if no password is filled in.

REPRODUCTION STEPS:
Preconditions - User is not logged in

1. Using any web browser, navigate to root page of app
2. In the textbox labeled "Password", type "foo"
3. Ensure that the textbox labeled "Username" is blank
4. Click the "Login" button

EXPECTED BEHAVIOR:

User sees error page with "Please fill in both username and password" message

OBSERVED BEHAVIOR:

User sees 500 error. 

IMPACT:

A user who forgets to type in their username, but does type in the password, will not see the expected error page.

SEVERITY:

Normal - This is an edge case, but the user may not know to hit the back button to retry logging in.

NOTES:

The text of the page shown is

```
500 Internal Server Error

We're sorry, something went wrong.  Please try again later.
```

The server logs report the following error:

```
Caught NullPointerException in LoginProcedure, Line 38
```

----

SUMMARY:
DESCRIPTION:
REPRODUCTION STEPS:
EXPECTED BEHAVIOR:
OBSERVED BEHAVIOR:
IMPACT:
SEVERITY:
NOTES:

----

SUMMARY:
DESCRIPTION:
REPRODUCTION STEPS:
EXPECTED BEHAVIOR:
OBSERVED BEHAVIOR:
IMPACT:
SEVERITY:
NOTES:



#### Tracking, triaging, and prioritizing defects

Once a defect has been found and filed, its journey has only just begun.

(invalid)