## Requirements

Remember that validating software is ensuring that we are building the right software; in other words, ensuring that what we are creating is what the users and/or customers want.  In order to validate software, we need to know what it is that users want the software to do, which can be a much more difficult task than you may think.  Users often aren't able to specify exactly what they are looking for.  They may think that they know exactly what they want, but when they see the implementation, immediately know that it is not.  Or they may have no idea - they just want your team to build something "like a social media site, but with buying and selling, and you know, like, hot dogs?  But for cats?"

One way of determining what software to build is to determine the __requirements__ of the software.  Requirements are statements specifying exactly what it is that a piece of software should do under certain conditions.  This is more or less popular depending on the domain and the kind of software being developed.  For example, when building software to monitor a nuclear reactor, there may be very specific and well-laid-out requirements.  If you are working for a social media startup (hopefully not one with buying and selling, and you know, like hot dogs... for cats, because there is apparently competition), then your "requirements may be something your CEO scribbled on a napkin after one too many bottles of Bordeaux and one too few meetings with actual investors.

Requirements ensure that the developers know what to build, and the testers know what to test.

In our tire air pressure example in the chapter on testing basics, we had some relatively simple requirements, although we did not mention at the time that that is what they were. 

1. The ERROR light comes on for PSIs of -1 or less
2. The UNDERPRESSURE light comes for PSIs between 0 and 20
3. No light comes on for PSIs between 21 and 35 (normal operating conditions)
4. The OVERPRESSURE light comes on for PSIs of 36 or greater

These "informal requirements" show what should happen to the system under certain input values.  The classic way of writing requirements is to say that the system "shall" do something.  When testing, you can mentally translate the "shall" to a "must".  That is, if the requirement says that the system shall do something, then the system must do it in order for you as a tester to say that the system has met the requirement.  Requirements are very exact and should avoid amiguity at almost all costs.  Let us translate these informal requirements to something a little more in line with how requirements are written in the real world.

REQ-1. If an air pressure value of -1 or lower is received by the display sensor, then the ERROR light __shall_ be enabled, and all other lights __shall__ be disabled.
REQ-2. If an air pressure value between 0 and 20 (inclusive) is received by the display sensor, then the UNDERPRESSURE light __shall__ be enabled, and all other lights __shall__ be disabled.
REQ-3. If an air pressure value between 21 and 35 (inclusive) is received by the display sensor, then all lights __shall__ be disabled.
REQ-4. If an air pressure value of 36 or greater is received by the display sensor, then the OVERPRESSURE light __shall__ be enabled, and all other lights __shall__ be disabled.

Note how much more specific this has become compared to our informal requirements listed above.  Requirements engineering is a true engineering discipline, and it can be very difficult to specify a large and/or complex system.  Note also how much denser and how much more text is created in an attempt to remove ambiguity.  It will definitely take much more time to write down formal requirements than to just create something based on a conversation.  Changes are also much more difficult to make.  The trade-off may or may not be worth it, depending on the domain and the system under test.  Ensuring that avionics software controls the plane correctly under all conditions would probably warrant a very thorough and detailed __requirements specification__ (a listing of all the requirements of a system), whereas our social media site mentioned above may not.  Rigidity can provide a very good definition of a system, but at the price of flexibility.

On a side note, if you ever wonder why lawyers are paid so much, this is similar to what they do all day.  One can think of the laws as a series of requirements of what a person should do to be law-abiding, what kinds of punishment to inflict in the case that a person breaks the law, how the laws should be created, etc.

1. If an offender jay-walks on a major thoroughfare, when not at a crosswalk, and when there is oncoming traffic, and at least one vehicle found it necessary to apply their brakes, the offender __shall__ be charged with a first-class misdemeanor of "Pedestrian Failure to Yield".
2. If a person jay-walks on a major thoroughfare, when the "Walk" signal is not displayed at a crosswalk, when there is no oncoming traffic, the offender __shall__ be charged with a second-class misdemeanor of "Pedestrian Disobeying a Traffic Signal".

In both the law and in software testing, it's possible to "go down a rabbit hole" trying to determine exactly what the text means.  The English language is full of ambiguities.  For example, let's take a perfectly reasonable requirement which can be read multiple ways:

REQ-1. The primary alarm system shall sound if it detects an intruder with a visual display unit.

Does this mean that the primary alarm should sound if the intruder is holding a visual display unit?  Or does it mean that if the intruder is detected by using the system's visual display unit, the primary alarm should sound?  Be sure to avoid as much ambiguity as possible when writing requirements.  It's difficult to do in English, but writing requirements in formal logic can be even more difficult (we'll discuss this a little bit in the chapter on formal verification, in case you want to skip ahead).

When writing requirements, it's important to keep in mind that requirements state what the system should do, not how it should do it.  In other words, it shoul not specify the implementation details, but only how that system or subsystem interacts and interfaces with the world around it.  This may be easier to understand with some examples from a brand-new interstellar spaceship:

GOOD REQUIREMENTS:

GOODREQ-1. When the user presses the "velocity" button, the current velocity of the spaceship shall be displayed on the main screen.
GOODREQ-2. The system shall be able to support a velocity of 0.8c (80% of the speed of light) for at least three hundred days (7,200 hours) without requiring maintenance.
GOODEQ-3. The system shall store the last 100 coordinates of locations that it has sampled.

BAD REQUIREMENTS:
BADREQ-1. When the user presses the "velocity" button, the system shall access memory location 0x0894BC40 and display it on the main screen. 
BADREQ-2. The system shall use a antimatter-matter reaction in order to propel it to 0.8c.
BADREQ-3. The system shall use a relational database to store the last 100 coordinates it has sampled.

Note that the bad requirements all state __how__ something should be done, not __what__ the system needs to do.  What happens if the memory address where the velocity value is stored is changed?  The entire requirement would have to be changed, as well as any that may have depended on it.  Why is it important to use an antimatter-matter reactor, specifically?  The key thing is that the spaceship can move at a specific velocity.  Finally, why is it important that a relational database is used to store coordinates?  From the user's perspective, all that they care about is that the coordinates are stored.

For complex or safety-critical systems, requirements may specify implementations.  In these cases, it is critical that a system not only does something, but does it in a proven and specified way.  For most systems, however, it is overkill and greatly limits flexibility as the software development process continues.

By requiring implementation details, however, you remove the possibility of black-box testing.  Without looking at the code, how can you be sure that the system is displaying the contents ofmemory location 0x0894BC40?  It's impossible (unless you have the incredibly specific superpower of being able to look at a RAM chip and somehow know what is being stored at and where).  All tests would have to be white box tests.

#### Testability

From a tester's perspective, one of the most important aspects of requirements is whether or not they are testable.  From a software development lifecycle perspective, "testable requirements" is actually just another term for "good requirements".  A requirement that cannot be tested cannot be shown to have been met.  Let's take an example of two requirements, and try to determine which is better.  Note that both semantically and syntactically valid, and contain that all-important "shall".

1. The system shall increment the PATRONS counter upon every time the TURNSTILE sensor is activated without error.
2. The system shall do the things with the counter every time someone walks through.

Note that the first requirement is very unambiguous; it states what should be done, what input to monitor, and what to expect.  Specifically, whenever the TURNSTILE sensor is activated, we expect the PATRONS counter (which may be a variable, a display, or something else - it should be specified elsewhere in a complete requirements specification) to be incremented by one.  The second requirement is ambiguous in several ways.  What shall it do with the counter?  How can it tell when someone walks through?  What is it referring to that someone can walk through?  It would be impossible to set up a test for this requirement.

Now that we've seen examples of testable and non-testable requirements, can we specify in detail what it means for a requirement to be testable?

In order for the requirements to be testable, they must meet five criteria, each of which we will cover individually.  They should be:

1. Complete
2. Consistent
3. Unambiguous
4. Quantitative
5. Feasible to test

Requirements should cover the entire operation of the system.  This is what we mean by saying that a requirements specification should be __complete__.  Anything that is not covered by the requirements is liable to be interpreted in different ways by developer, designers, testers, and users.  If something is important, it should be specified precisely.

#### More Tips on Writing Good Requirements

#### Functional vs Non-Functional Requirements

#### Functional Requirements

#### Non-Functional Requirements
