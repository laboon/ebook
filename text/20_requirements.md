# Requirements

Remember that validating software is ensuring that we are building the right software; in other words, ensuring that what we are creating is what the users and/or customers want.  In order to validate software, we need to know what it is that users want the software to do, which can be a much more difficult task than you may think.  Users often aren't able to specify exactly what they are looking for.  They may think that they know exactly what they want, but when they see the implementation, immediately know that it is not.  Or they may have no idea---they just want your team to build something "like a social media site, but with buying and selling, and you know, like, hot dogs?  But for cats?"

One way of determining what software to build is to determine the __requirements__ of the software.  Requirements are statements specifying exactly what it is that a piece of software should do under certain conditions.  This is more or less popular depending on the domain and the kind of software being developed.  For example, when building software to monitor a nuclear reactor, there may be very specific and well-laid-out requirements.  If you are working for a social media startup (hopefully not one with "buying and selling, and you know, like hot dogs... for cats", because apparently there is competition), then your "requirements" may be something your CEO scribbled on a napkin after one too many bottles of wine and one too few meetings with actual customers.

Requirements ensure that the developers know what to build, and the testers know what to test.  While requirements are important, they are not inviolable!  Common sense should be used when interpreting requirements, and requirements are often subject to change.  Note that there other methods of determining what software to build aside from traditional requirements, such as user stories.

In our tire air pressure example in the chapter on testing basics, we had some relatively simple requirements, although we did not mention at the time that that is what they were:

1. The `ERROR` light comes on for PSIs of -1 or less
2. The `UNDERPRESSURE` light comes for PSIs between 0 and 20
3. No light comes on for PSIs between 21 and 35 (normal operating conditions)
4. The `OVERPRESSURE` light comes on for PSIs of 36 or greater

These "informal requirements" show what should happen to the system under certain input values.  The classic way of writing requirements is to say that the system "shall" do something.  When testing, you can mentally translate the "shall" to a "must".  That is, if the requirement says that the system shall do something, then the system must do it in order for you as a tester to say that the system has met the requirement.  Requirements are written to be exact and should avoid ambiguity at almost all costs.  Let us translate these informal requirements to something a little more in line with how requirements are written in the real world.

* _REQ-1._ If an air pressure value of -1 or lower is received by the display sensor, then the `ERROR` light _shall_ be enabled, and all other lights _shall_ be disabled.
* _REQ-2._ If an air pressure value between 0 and 20 (inclusive) is received by the display sensor, then the `UNDERPRESSURE` light _shall_ be enabled, and all other lights _shall_ be disabled.
* _REQ-3._ If an air pressure value between 21 and 35 (inclusive) is received by the display sensor, then all lights _shall_ be disabled.
* _REQ-4._ If an air pressure value of 36 or greater is received by the display sensor, then the `OVERPRESSURE` light _shall_ be enabled, and all other lights _shall_ be disabled.

Note how much more specific this has become compared to our informal requirements listed above.  Requirements engineering is a true engineering discipline, and it can be very difficult to specify a large and/or complex system.  Note also how much denser and how much more text is created in an attempt to remove ambiguity.  It will definitely take much more time to write down formal requirements than to scribble down a general outline of the program on a napkin.  Changes are also much more difficult to make.  The trade-off may or may not be worth it, depending on the domain and the system under test.  Ensuring that avionics software controls the plane correctly under all conditions would probably warrant a very thorough and detailed __requirements specification__ (a listing of all the requirements of a system), whereas our social media site mentioned above may not.  Rigidity can provide a very good definition of a system, but at the price of flexibility.

On a side note, if you ever wonder why lawyers are paid so much, this is similar to what they do all day.  One can think of the laws as a series of requirements of what a person should do to be law-abiding, what kinds of punishment to inflict in the case that a person breaks the law, how the laws should be created, etc.:

1. If an offender jay-walks on a major thoroughfare, when not at a crosswalk, and when there is oncoming traffic, and at least one vehicle found it necessary to apply their brakes, the offender _shall_ be charged with a first-class misdemeanor of "Pedestrian Failure to Yield".
2. If a person jay-walks on a major thoroughfare, when the "Walk" signal is not displayed at a crosswalk, when there is no oncoming traffic, the offender _shall_ be charged with a second-class misdemeanor of "Pedestrian Disobeying a Traffic Signal".

In both the law and in software testing, it's possible to "go down a rabbit hole" trying to determine exactly what the text means.  The English language is full of ambiguities.  For example, let's take a perfectly reasonable requirement which can be read multiple ways:

* _UNCLEARREQ-1._ The primary alarm system shall sound if it detects an intruder with a visual display unit.

Does this mean that the primary alarm should sound if the intruder is holding a visual display unit?  Alternatively, does it mean that if the intruder is detected by using the system's visual display unit, the primary alarm should sound?  Be sure to avoid as much ambiguity as possible when writing requirements.  Although testers don't often write requirements themselves, they are often asked to review them.  Ensuring that you understand a requirement and that the requirement is clearly testable will save time and headaches later on in the development process.

When writing requirements, it's important to keep in mind that requirements state what the system should do, not how it should do it.  In other words, it should not specify the implementation details, but only how that system or subsystem interacts and interfaces with the world around it.  This may be easier to understand with some examples from a new interstellar spaceship game:

_GOOD REQUIREMENTS:_

* _GOOD-REQ-1._ When the user presses the "velocity" button, the current velocity of the spaceship shall be displayed on the main screen.
* _GOOD-REQ-2._ The system shall be able to support a velocity of 0.8c (80% of the speed of light) for at least three hundred days (7,200 hours) without requiring maintenance.
* _GOOD-REQ-3._ The system shall store the last 100 coordinates of locations that it has sampled.

_BAD REQUIREMENTS:_

* _BAD-REQ-1._ When the user presses the "velocity" button, the system shall access memory location 0x0894BC40 and display it on the main screen.
* _BAD-REQ-2._ The system shall use a simulated antimatter-matter reaction in order to propel it to 0.8c.
* _BAD-REQ-3._ The system shall use a relational database to store the last 100 coordinates it has sampled.

Note that the bad requirements all state _how_ something should be done, not _what_ the system needs to do.  What happens if the memory layout on the system is changed?  BAD-REQ-1 would have to be changed, as well as any other requirements that may have depended on data being in a specific memory location.  Why is it important to use an antimatter-matter reactor, specifically?  After all, the key thing is that the spaceship can move at a specific velocity.  Finally, why is it important that a relational database is used to store coordinates?  From the user's perspective, all that they care about is that the coordinates are stored.

For complex or safety-critical systems (such as an actual interstellar spaceship), requirements may specify implementations.  In these cases, it is not only critical that a system does something, but that it does so in a proven and specified way.  For most systems, however, such requirements are overkill and would greatly limit flexibility as the software development process continues.  It can also make it more difficult to test these requirements, since not only does the tester need to determine whether the expected behavior matched the observed behavior, but they also need to determine how the observed behavior occurred.

By requiring implementation details, you remove the possibility of black-box testing.  Without looking at the code, how can you be sure that the system is displaying the contents of memory location 0x0894BC40?  It's impossible (unless you have the incredibly specific superpower of being able to look at a RAM chip and know what is being stored and where).  All tests would have to be white-box tests.

## Testability

From a tester's perspective, one of the most important aspects of requirements is whether or not they are testable.  From a software development life cycle perspective, "testable requirements" is another term for "good requirements".  A requirement that cannot be tested cannot be shown to have been met.  Let's take an example of two requirements, and try to determine which is better.  Note that both are semantically and syntactically valid, and contain that all-important "shall":

1. The system shall increment the PATRONS counter by one every time the TURNSTILE sensor is activated without error.
2. The system shall do the things with the counter every time someone walks through.

Note that the first requirement is very unambiguous; it states what should be done, what input to monitor, and what behavior to expect.  Specifically, whenever the TURNSTILE sensor is activated, we expect the PATRONS counter (which may be a variable, a display, or something else---it should be specified elsewhere in a complete requirements specification) to be incremented by one.  The second requirement is ambiguous in several ways.  What shall it do with the counter?  How can it tell when someone walks through?  What is it referring to that someone can walk through?  It would be impossible to set up a test for this requirement.

Now that we've seen examples of testable and non-testable requirements, can we specify in detail what it means for a requirement to be testable?

In order for the requirements to be testable, they must meet five criteria, each of which we will cover individually.  They should be:

1. Complete
2. Consistent
3. Unambiguous
4. Quantitative
5. Feasible to test

Requirements should cover the entire operation of the system.  This is what we mean by saying that a requirements specification should be __complete__.  Anything that is not covered by the requirements is liable to be interpreted in different ways by developer, designers, testers, and users.  If something is important, it should be specified precisely.

The requirements should be __consistent__.  That is, they should not contradict each other or the laws of the universe (or whatever domain that you are operating in).  Requirements which do not contradict each other are __internally consistent__; requirements which do not contradict the world outside the system are __externally consistent__.

Here is an example of a group of requirements that is not internally consistent:

* _INCONSISTENT-REQ-1._ The system shall display "WARNING: `OVERPRESSURE`" on the console whenever the pressure is 100 PSI or greater.
* _INCONSISTENT-REQ-2._ The system shall turn off the console and display no further information as long as the pressure is less than 200 PSI.

What should the system do if the pressure is between 100 and 200 PSI?  You can use equivalence class partitioning here to determine that the requirements are not internally consistent.

The requirements should be __unambiguous__.  That is, they should specify things as precisely as possible for working in the particular domain the software is for.  The acceptable level of ambiguity will vary dramatically depending on what kind of software you are developing.  For example, if you are writing software for a children's game, it may be sufficient to state that a particular square should be "red".  If you are describing the requirements for an interface to a nuclear reactor, the exact PANTONE® shade of red of the warning light may need to be specified.

That being said, be careful not to paint yourself into a corner with too-restrictive requirements.  If your requirements state that a particular page needs to be a certain number of pixels, you may have difficulties converting to mobile devices, for example.  However, requirements certainly should not be of the form:

* _AMBIGUOUS-REQ-1._ The system shall do all of the shutdown stuff whenever the "shutdown" button is pressed.

What is the "shutdown stuff"?  Speaking to our friends, or amongst co-workers, we may use such ambiguous terms because the human brain is remarkably good at filling in ambiguity.  However, ambiguous requirements can lead to different developers or other stakeholders interpreting them in different ways.  The classic example of this is the Mars Climate Orbiter mishap, where one group of software engineers used Imperial measures and another group used metric measures.  Both groups thought the correct way to return results was obvious, but they came up with different implementations.

If at all possible, the requirements should be __quantitative__ (as opposed to qualitative).  That is, if you can apply numbers to a requirement, you should.  You should avoid using any sort of subjective terms such as "fast", "responsive", "usable", or "scrumdiddlyumptious."  If you can specifically state what the system is required to do, then do so.  For example, the following requirement is qualitative, and not quantitative.

* _QUALITATIVE-REQ-1._ The system shall return results extremely quickly.

What do we mean by this?  It's impossible to test without defining what we mean by "extremely quickly", or what kind of results need to be returned quickly.

Finally, some common sense needs to be applied to the requirements writing.  It's possible to write a requirement which may be theoretically possible to test, but for various reasons, impossible to test in the real world.  A requirement like this is not __feasible__ to test.  Let's say that we have the following requirements for testing our air pressure sensor:

* _INFEASIBLE-REQ-1._ The system shall be able to endure pressures of up to 9.5 x 10^11 pounds per square inch.
* _INFEASIBLE-REQ-2._ Under normal operating procedures (defined elsewhere), the system shall remain usable for two hundred years of continuous use.

Both of these requirements are, in fact, possible to test.  In order to test the first, simply place the system at ground zero of a relatively powerful thermonuclear explosion, and determine if system quality degrades after detonation.  The second requirement is even easier to meet; simply drive around with the pressure sensor for two hundred years.  Since the human lifespan is noticeably shorter than that, you'll probably need a few generations of testers.  Whether you make "pressure sensor tester" a hereditary position, or use a "master/apprentice" style system, is entirely up to you.

When applied to physical phenomena, it is manifestly clear how silly it would be to test these requirements.  (If they don't seem silly to you, how many testers do you know who have thermonuclear weapons at their disposal?  Probably fewer than five.)  However, oftentimes, it's more difficult to determine that a requirement is not feasible to test when you are dealing with software.  Living in a physical world, the human brain often has difficulty with determining how feasible a software requirement (which deals in the virtual world) may be to test.

## Functional versus Non-Functional

__Functional requirements__ state what a system should _do_; __non-functional requirements__ state what a system should _be_.

The requirements that we have discussed so far are functional requirements; that is, they say that the software system shall do a particular action under specific circumstances.  For example:

1. The system shall display the error message "User Not Found" if a user attempts to log in and the user name entered does not exist in the system.
2. Upon retrieval of a record from the database, if any field is invalid, the system shall return the string "INVALID" for that field.
3. Upon startup, the system shall display the message "WELCOME TO THE SYSTEM" to the user's console.

Functional requirements are (relatively) simple to test; they say that a specific behavior should occur under certain circumstances.  There are obviously going to be complexities and variations involved in testing some of them, but the general idea is straightforward.  For example, for the second requirement, tests might check for each of the various fields of the database, and different kinds of invalid values.  This may be quite an involved process, but there is a plan that can be followed to develop the tests that directly grows out of the requirements.

Non-functional requirements describe overall characteristics of the system, as opposed to specific actions that are taken under specific circumstances.  Non-functional requirements are often called __quality attributes__, because they describe qualities of the system as opposed to what it should do specifically.  Some examples of non-functional attributes include:

1. The system shall be usable by an experienced computer user with less than three hours' training.
2. The system shall be able to support one hundred simultaneous users.
3. The system shall be reliable, having less than one hour of unanticipated downtime per month.

Non-functional requirements are often much more difficult to test than functional requirements, because the expected behavior is much more vague.  This is why it is especially important to define the requirement itself clearly.

One of the key ways to clearly define non-functional requirements is to quantify them.

## A Note on Naming Requirements

Back in the days of yore, when primitive engineers chiseled binary trees with stone axes, there was only one way to write requirements.  It was the way that their forefathers had written requirements, and their forefathers before them, unto the dawn of civilization.  This sacred method of requirements naming was as follows:

_REQUIREMENTS:_

1. The system shall do X.
2. The system shall do Y.
3. The system shall do Z whenever event A occurs.
...

This was simple enough for small projects.  As software projects became larger and more specified, however, some problems arose with this scheme.  For example, what happened if a requirement became irrelevant?  There are now "missing" requirements; the list of requirements may be 1, 2, 5, 7, 12, etc.  Inversely, what if software requirements needed to be added?  If the list of requirements is just a straight linear ordering, those new requirements need to be put in at the end, or squeezed in between existing requirements (Requirement-1.5).

Another problem was remembering what each requirement actually specified based solely on the number.  It is not very difficult for the average person to keep a list of a few requirements in their head, but this becomes untenable when there are hundreds, or even thousands, of software requirements.

Several methods were developed in order to ameliorate the issue.  One method was to group all of the requirements into different sections (e.g., "DATABASE-1", "DATABASE-2") and continue the traditional numbering scheme from there.  At least under this scheme, a new DATABASE requirement did not require being placed at the very end of the list of requirements, but rather placed in a location near other relevant requirements.  This also gave a hint as to what the requirement referred.

Another method is to name the requirements with abbreviations of what the requirement is actually supposed to do.  Prefixes and suffixes such as "FUN-" for functional and "NF-" for non-functional are commonplace.

All that being said, even more important is to use the same requirements naming convention as the rest of your team!
