## Glossary of Terms

__Stochastic Testing__: Testing a system through the use of randomized inputs.  These inputs do not have to be entirely random; for example, they can be a probability distribution of values or generated strings.  Also called __monkey testing__.

__Monkey Testing__: Another term for __stochastic testing__.

__Chaos Monkey__: A __stochastic testing__ tool developed by Netflix which tests distributed systems by shutting off random servers in the system.

__Evil Monkey__: A __stochastic testing__ method whereby malicious code or data is sent in to a system.  Simulates an attacker trying to gain access to a system.

__Smart Monkey__: A __stochastic testing__ method whereby 

__Fuzz Testing__:

__Dogfooding__: Using your own software while developing it.  For example, running the operating system that you are developing on your own computer.

__Dogfood, Eating Your Own__: Another term for __dogfooding__.

__Regulation Acceptance Testing__: Testing that a system meets legal or other regulatory requirements.

__Operational Testing__: Testing that a system works under real-world conditions.

__Field Testing__: Testing that a system works while actually operating with real users.

__User Acceptace Testing__:

__Alpha Testing__:

__Beta Testing__:

__Test Artifact__: A document or other byproduct of the testing process, such as test plans or results.

__Implicit Boundary Value__: A __boundary value__ that is not explicitly called out by the system requirements, but may have an impact on the system's operation.  For example, code written using 32-bit signed integers (such as `int` in Java) has an implicit boundary at 2,147,483,647, the maximum integer size.  Adding one to this will cause the number to overflow.

__Explicit Boundary Value__: A __boundary value__ explicitly called out by the system requirements.  For example, requirements for an automated thermometer may state the system will turn on the DANGER light when the registered temperature is 102 degrees or over.  The boundary values would be 101 and 102.

__White-Box Testing__: Testing the code directly and with full knowledge of the code under test.  __Unit testing__ is an example of white-box testing.

__Grey-Box Testing__: Testing the code as a user would, but with knowledge of the codebase in order to understand where errors might be hiding.  A mixture of __white-box testing__ and __black-box testing__.

__Black-Box Testing__: Testing the code as a user would, with no knowledge of the codebase.  Most manual tests are black-box tests.

__Static Testing__: Testing the system without executing any of its code.  Examples would be code analysis or modeling tools.

__Dynamic Testing__: Testing the system by executing it.  Examples would be unit testing or black-box testing.

__Requirement__: A statement of what the system under development needs to do in order to be considered complete and correct.

__Requirements Specification__: A list of requirements for a given system.  The system is expected to meet all of the requirements in the requirements specification.

__Testability__:

__Internally Consistent (Requirements)__: The property of having no requirements contradict each other.  For example, a requirements specification which has a requirement which states "The system shall always leave the red light on" and another requirement which states that "The system shall turn off the red light if SWITCH1 is enabled" would not be internally consistent.

__Externally Consistent (Requirements)__: The property of having the system be consistent with requirements of other systems or of the Universe.  For example, having a system which mandates that the system will be able to communicate with a base on Pluto instantaneously would require faster-than-light communication, and would thus be inconsistent with the laws of this Universe.

__Functional Requirement_: A requirement that specifies exactly what a system should do under certain circumstances.  For example, "the system shall display ERROR on the console if any parameter is negative."  Contrast with __non-functional requirement__.

__Non-Functional Requirement__: A requirement that specifies how the system should operate, without specifying specific behavior.  For example, "the system shall be extensible, allowing for the addition of plug-ins" or "the system shall be usable by personnel with less than one hour of training".  Contrast with __functional requirement__.

__Quality Attribute__: Another (probably better) term for __non-functional requirement__.

__-Ility Requirement__: Another term for __non-functional requirement__.  So named because many of these requirements use words ending in -ility to describe them (e.g., usability, scalability, maintainability).

__Intermittent Failure__: A test failure which does not occur all of the time, but only on certain runs.  Often, the reason for the intermittent failure is unknown, or it would have been fixed already.

__Non-deterministic__: A test failure which does not occur all of the time, but only on certain runs, and for unknown reasons.

__Deterministic__: Something for which the causal behaviors are entirely known and reproducible.

__Test Case__: The smallest unit of a test plan, which describes what is to be tested and what the expected behavior is.

__Identifier__: A number or string which uniquely identifies a test case, defect, or anything else.

__Precondition__: All conditions which need to hold true before the execution steps of a test case can begin.  For example, when testing the Account Information of a website, a precondition may be that the user is already logged in.

__Postcondition__: All conditions which need to hold true after the execution steps in order for the test to pass.  For example, a postcondition after editing your user name may be that the Account Information page shows the new user name.

__Input Value__:

__Output Value__:

__Execution Steps__: The actual steps that the test will execute after ensuring that all preconditions hold.  

__Reproduction Steps__: The steps necessary for reproducing a defect.  Often included in defect reports so that readers of the defect will understand what causes the defect and how to reproduce it.

__Passed__: A status for a test case in a test run.  It indicates that the system has met all postconditions and/or expected output values - the observed behavior was equal to the expected behavior.

__Failed__: A status for a test case in a test run.  It indicates that the system has not met at least one postcondition or expected output value, or some other unexpected behavior has taken place.  In other words, the observed behavior was not the expected behavior.

__Blocked__: A status for a test case in a test run.  It indicates that this particular test case cannot be run, for reasons outside the tester's control.  For example, the functionality is tests is not yet at a testable state.

__Error__: A status for a test case in a test run.  It indicates that there is an issue with the test case itself, and the test cannot be run.  For example, the preconditions indicate an impossible condition.

__Paused__: A status for a test case in a test run.  The tester has started to run the test, but it is temporarily on hold.

__Running__: A status for a test case in a test run.  The tester is currently running the test.

__Assertion__: In a unit test, a statement which states that a certain condition must hold.  If the condition does not hold, the test is considered to have failed.  For example, `assertEquals(Math.sqrt(4), 2);`.

__Validation__: Ensuring that the system meets the needs of the customer.  Checking that you built the right _system_.

__Verification__: Ensuring that the system operates correctly, does not crash, provides correct answers, etc.  Checking that you build the system _right_.

__Stub__: A "fake method" which can be used in unit testing to limit dependencies on other methods and focus testing on the method under test.

__Mock__: A particular kind of __test double__ which keeps track of which methods on it have been called.

__Test Double__: A "fake object" which can be used in unit testing to limit dependencies on other classes, which may have problems of their own, not yet developed, or simply be time- or resource-intensive to actually instantiate.

__Reflection__: A way to determine class and method structure at run-time.

__Test Coverage__: A general term for which aspects or parts of the system are covered by tests.

__Code Coverage__: How much of the codebase is actually tested, usually via unit tests.  Although there are different kinds of code coverage, the majority of time that non-specialists use it, they are referring to __statement coverage__.

__Statement Coverage__: What percentage of statements in the code are tested, usually by unit tests.

__Branch Coverage__: What percentage of branches in the code are tested usually by unit tests.

__Combinatorial Testing__: 

__Pairwise Testing__:

__All-Pairs Testing__:

__DRY__: Don't Repeat Yourself.  A tenet of writing good, testable code which states that code should not be repeated, for example by having two different methods which do the same thing, or copy/pasting code from one part of the codebase to another instead of making it into a callable method.

__YAGNI__: You Ain't Gonna Need It.  A tenet of __Test-Driven Development__ which states that you should not do work or add features which are not immediately necessary.

__Law of Demeter__:

__Tell, Don't Ask__: Another term for the __Law of Demeter__.

__Tautological Test Case__: A test case which was written so that it will always pass, for example `boolean foo = true; assertTrue(foo);`.  This is usually done inadvertently.

__Traceability Matrix__: A two-dimensional matrix displaying test cases and requirements and indicating which test cases test which requirements.

__Performance Testing__: Testing that a system meets the performance indicators designated for it.

__Efficiency-Based Performance__: Performance on metrics related to the efficiency of use of the computational resources available to the system.

__Service-Based Performance__: Performance on metrics related to how the user will interact with the system.

__Performance Indicator__: A quantitative measure that indicates the level of performance of the system.  For example, response time or memory usage.

__Key Performance Indicator__: A __performance indicator__ that is considered of primary importance in the development of the system.

__KPI__: Abbreviation for __Key Performance Indicator__.

__Security Testing__: Testing that the system meets the criteria of the InfoSec Triad, that the system is safe from unauthorized tampering and/or access.

__InfoSec Triad__: The three criteria that indicate a secure system - confidentiaity, integrity, and availability.

__CIA Triad__: Another term for the __InfoSec Triad__.

__Malware__: Software which 

__Vulnerability__: A potential defect that would allow a user to compromise or otherwise gain unauthorized access to a system.

__Exploit__: A program or piece of data which takes advantage of a vulnerability.  A vulnerability is "strictly theoretical" until somebody develops a way to exploit it.

__Sploit__: Another term for __exploit__.

__Hacker__:

__Cracker__:

__Equivalence Class__:

__Equivalence Class Partitioning__:

__Interior Value__: A value which is not a boundary value in its equivalence class.

__Boundary Value__: A value which is "one the boundary" between equivalence classes.  For example, a system that has two equivalance classes, between 0 and 19, and 20 or higher, would have boundary values at 19 and 20.

__Base Case__: 

__Edge Case__:

__Corner Case__:

__Pathological Case__: Another term for __corner case__.

__User Story__:

__Connextra Template__: A common template for defining user stories, which consists of the phrase "As a <role>, I want <function>, So That <reason>".  Also often referred to as the __Role-Function-Reason Template__.

__Role-Function-Reason Template__: Another term for the __Connextra Template__.

__Expected Behavior__: What the system is expected to do under certain circumstances.  For example, after typing in `2 + 2 =` on a calculator, the expected behavior is that the system will display `4` on the screen.

__Observed Behavior__: What the system actually does under certain circumstances.  For example, if I type in `2 + 2 =` on a calculator, the __expected behavior__ may be that I see 4, but if I instead see `WALLA WALLA`, then `WALLA WALLA` is the observed behavior.

__Success Case__: A kind of test case where the expected behavior of the system is to return the correct result or do the correct thing.  Compare to __failure case__.

__Failure Case__: A kind of test where the expected behavior of the system is to fail in a certain way.  For example, sending in a negative number to a square root function which does not support complex numbers may be expected to throw an exception.

__Happy Path__: The easiest path a user will take through the system, when the system is operating properly, without attempting to perform anything that is an __edge case__ or __corner case__.  

__Unit Testing__: Testing the smallest individual units of code, such as methods or functions, in a white-box manner.

__System Testing__: Testing the system as a whole, as the user (as opposed to a developer) would interact with it.  Usually done in a black-box or grey-box manner.

__System Under Test__: The system that is actually being tested.

__Application Under Test__: The system which the tester is testing.

__Acceptance Testing__:

__Smoke Test__: A small subset of tests which is used as a gateway for further testing.  

__Sanity Test__: A very small set of tests which checks a very minimal amount of functionality, to ensure, for example, that a system was installed correctly.

__Sanity Check__: Another term for __sanity test__.

__Behavior-Driven Development (BDD)__:

__Test Plan__: A list of related test cases that are run together.

__Test Run__: An actual iteration (run-through) of a test plan.

__Test Suite__: A grouping of related test plans.

__Defect__:

__Bug__: Another term for __defect__.

__Test-Driven Development (TDD)__:

__Regression Failure__: A failure of a previously-working piece of functionality that is caused by (seemingly) unrelated additional functionality or defect fixes.

__Scripted Testing__: Testing with a rigid script, such as a test plan, where steps are well-defined and ordered.

__Unscripted Testing__: Testing without a rigid script, where the tester has broad latitude to use their own volition and knowledge to determine the quality of the system or find defects.

__Media Testing__: Ensuring that the media that the system is stored on (e.g., a CD-ROM or a server's hard drive) is operating correctly and has all of the data in the correct place.