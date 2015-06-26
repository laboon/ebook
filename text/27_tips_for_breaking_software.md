## Breaking Sofware

Thus far, we have focused on how to develop test plans, but there has been little discussion of what to test for, outside of ensuring that the software meets the requirements.  There are lots of 

The reason one tests is to find defects; in order to find defects, you may have to venture a bit from the happy path, where everybody enters data in the format you like, systems have infinite memory and CPU, networks have zero latency and are always connected.  You'll have to traverse through the dark woods where people try to enter "FORTY-SEVEN" instead of "47", where the program runs out memory midway through a computation, where a horror movie villain stands poised above the Ethernet cable with a sharpened axe.

Okay, maybe not that last one. However, you will have to think of ways to test for the operation of a system when things aren't quite perfect.  Consider this chapter a way of priming your testing brain for how to cause failures and find the cracks in the software under test.

### Errors To Look For

1. __Logic Errors__ - A logic error is an error in the logic of the program; the developer understood what needed to be done, but in the process of converting the system from its description to its implementation, something went wrong.  This could be as simple

2. __Off-By-One Errors__ Although technically a logic error, these are so common that they will be addressed separately.  An off-by-one error is when a program does something wrong because a value is off by just one unit.  This is the reason that there was a focus on determining boundary values in a previous chapter - boundary values are a user-focused way of looking for off-by-one errors.  Why are these so common?  Let's imagine a very simple method which returns whether or not a person is a minor or not.

```java
public boolean isMinor(int personAge) {
    if (personAge <= 18) {
        return true;
    } else {
        return false;
    }
}
```

Did you spot the error?  At least in the United States at the time of this writing, you are no longer considered a minor once you reach 18.  By using `<=` instead of `<`, the method will return that the person is still a minor when they turn 18.  This minor mistake can happen in all sorts of ways - thinking that an array is 1-indexed instead of 0-indexed, thinking a "greater than or equal to" should be a "greater than", using `++i` when you should `i++`, etc.  These are also often less visible than other errors, since they will only show up for specific values.

When testing, check the boundary values and you are likely to run across some off-by-one errors.

3. __Integration Errors__

4. __Errors of Assumption__ 

5. __Missing Data Errors__

6. __Bad Data Errors__

6. __Display Errors__ 

6. __Injection Errors__ A subset of Bad Data Errors, Injection Errors are when executable code or other instructions are passed in to a program.  If a progra can be tricked into executing them, the consequences can be dire, including loss or corruption of data, unauthorized access to a system, or simply causing the system to crash.

An especially common issue is not dealing with escapes or odd characters properly.  An escape code which is not caught by the interface that the data is entered in may be used by another subsystem that does recognize it, or vice-versa.  Characters may be used for different purposes by different subsystems or language.  For example, Java strings can have null characters inside of them, whereas a null character indicates "end of string" for C strings.

In order to test for these, determine what happens when code of various kinds of code are passed in to the program.  This doesn't have to be Java code itself, or whatever language the system is written in, but could be something like JavaScript (which is run by default whenever displayed by most browsers) or SQL (for database updates and queries).  It could be a language that a different system used but the data is passed in.  It could be assembly code at the end of a long string trying to take advantage of a buffer overflow.  One of the problems with checking all of these is that there is such a wide variety, and there are adversaries who stand to gain personally from you missing a single point of entry for their malicious programs.

7. __Network Errors__ 

8. __Disk I/O Errors__ 

8. __Interface Errors__

9. __Null Pointer Errors__ You may be lucky enough to be working in a language in which the concept of a null pointer does not exist, but if you are programming in Java you are probably more familiar with these than you care to admit.  Any time that an object might be null, there needs to be an explicit check to ensure that it's not before trying to access it.  In a talk on the history of the concept of nil, Franklin Chen noted that in languages like Java, any object is always of two possible types - the object itself, or a null version of itself.  You can't assume that if you have an Integer object, for example, that it's actually an Integer; it can always be a null object masquerading as integer.

This can be relatively easy to look for if you are doing white-box testing and can see the code itself.  If you're performing black-box testing, though, you can still think of cases where an object may not exist.  What happens when you try to search for an object in a database that doesn't exist?  What happens when you don't enter anything in a textbox?  What happens when you give an invalid ID?  For many programs, the behavior necessary to operate under these conditions must be explicitly programmed.  Any time that a common behavior must be checked for explicitly, there is a chance that the programmer forgets to do it or does it incorrectly.

10. __Distributed System Errors__

11. __Configuration Errors__ There are two different ways that configuration errors can manifest themselves.  First, the administrators of a system can configure a software system in many different ways.  For example, when setting up a Rails application, there are numerous configuration options in various YAML files that you can set.  Many applications have configuration settings, command-line switches, or other ways of modifying how they operate, and they sometimes override or interact with each other in strange ways.

Secondly, if a system is reachable online, their personal computers may be set up in many different ways.  There are many web browsers, for instance, from full-featured ones from major companies and organizations to text-based browsers.  These browsers have various levels of compatibility with standards.  Users will be running them on different operating systems, with different hardware, and different software and plug-ins.  Some users keep JavaScript enabled all the time, others don't; some users block third-party applications or advertisements; some users don't display images; some users send a "Do Not Track" request and some don't; the list goes on and on.  There always exists the possibility that a problem lies with a specific configuration.

Will your system perform properly under every possible configuration that users use to access it (e.g., with different browsers, with JavaScript disabled, with images disabled, etc.)?  Does it give proper information on the problem if it is configured incorrectly?  Does it provide sensible defaults (or warn the user, depending on the requirements and domain) if a configuration value is missing or invalid?  Do certain settings override others in ways that are non-obvious, or cause problems when set in certain combinations?

12. __Accessibility Errors__ Oftentimes, systems will work properly when used by a user using a standard configuration, but not when a user is using a non-standard input or output device.  These are often necessary for users who cannot access a system using the standard keyboard-mouse-monitor input/output setup; for example, blind users who use a braille terminal for reading output from their computer.  If your software does not work correctly with these systems, then people who depend on them may have no other way to use your software.

### ... And Oh So Many More Ways

This chapter is not a complete list of how to break programs and find defects.  It's not even a fairly comprehensive list.  The fact is, computers do exactly what you tell them to do, and one of the challenges of writing software is telling the computer exactly what do under all sorts of circumstances.  While many of these circumstances are relatively common across programs (such as dealing with missing files or network connectivity), many other errors will be specific to the domain you are working in, or the program you are writing.  As a tester, you will need to keep an open mind and constantly be thinking of ways in which defects can manifest themselves in the particular program under test.