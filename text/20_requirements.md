## Requirements

Remember that validating software is ensuring that we are building the right software; in other words, ensuring that what we are creating is what the users and/or customers want.  In order to validate software, we need to know what it is that users want the software to do, which can be a much more difficult task than you may think.  Users often aren't able to specify exactly what they are looking for.  They may think that they know exactly what they want, but when they see the implementation, immediately know that it is not.  Or they may have no idea - they just want your team to build something "like a social media site, but with buying and selling, and you know, like, hot dogs?  But for cats?"

One way of determining what software to build is to determine the __requirements__ of the software.  Requirements are statements specifying exactly what it is that a piece of software should do under certain conditions.  This is more or less popular depending on the domain and the kind of software being developed.  For example, when building software to monitor a nuclear reactor, there may be very specific and well-laid-out requirements.  If you are working for a social media startup (hopefully not one with buying and selling, and you know, like hot dogs... for cats, because there is apparently competition), then your "requirements may be something your CEO scribbled on a napkin after one too many bottles of Bordeaux and one too few meetings with actual investors.

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

Note how much more specific this has become compred to our informal requirements listed above.  Requirements engineering is a true engineering discipline, and it can be very difficult to specify a large and/or complex system.  Note also how much denser and how much more text is created in an attempt to remove ambiguity.  It will definitely take much more time to write down formal requirements than to just create something based on a conversation.  Changes are also much more difficult to make.  The trade-off may or may not be worth it, depending on the domain and the system under test.  Ensuring that avionics software controls the plane correctly under all conditions would probably warrant a very thorough and detailed __requirements specification__ (a listing of all the requirements of a system), whereas our social media site mentioned above may not.  Rigidity can provide a very good definition of a system, but at the price of flexibility.

On a side note, if you ever wonder why lawyers are paid so much, this is similar to what they do all day.  One can think of the laws as a series of requirements of what a person should do to be law-abiding, what kinds of punishment to inflict in the case that a person breaks the law, how the laws should be created, etc.

1. If an offender jay-walks on a major thoroughfare, when not at a crosswalk, and when there is oncoming traffic, and at least one vehicle found it necessary to apply their brakes, the offender __shall__ be charged with a first-class misdemeanor of "Pedestrian Failure to Yield".
2. If a person jay-walks on a major thoroughfare, when the "Walk" signal is not displayed at a crosswalk, when there is no oncoming traffic, the offender __shall__ be charged with a second-class misdemeanor of "Pedestrian Disobeying a Traffic Signal".



#### Testability

#### Functional vs Non-Functional Requirements

#### Functional Requirements

#### Non-Functional Requirements
