# Glossary of Terms

__9's__: See __n nines__.

__Acceptance testing__: Testing by an end user, customer, or other independent personnel to verify that the system can be accepted for use.

__Accessibility error__: An error resulting from a user employing a non-standard input or output device, where the system is not usable for those who do not access the system using "standard" devices.

__Active attack__: In __security testing__, an attack on a system which causes some changes to the system, such as adding a program or modifying data in a database.

__All-pairs testing__: Another term for __pairwise testing__.

__Alpha testing__: "Real-world" testing by a small group of test engineers or some other small group of technically proficient personnel.  Often succeeded by beta testing.

__Application under test__: The system which the tester is testing.

__Assertion__: In a unit test, a statement which states that a certain condition must hold.  If the condition does not hold, the test is considered to have failed.  For example, `assertEquals(2, Math.sqrt(4));`.

__Availability__: In __performance testing__, what percentage of the time the system is available to the user (not in a failure mode, unresponsive, etc.)  In __security testing__, one of the elements of the __InfoSec Triad__, an attribute that refers to the ability of authorized users to access a system.

__Bacteria__: A kind of __malware__ which consumes an excess amount of system resources, perhaps taking up all file descriptors or disk space.

__Bad data error__: An error resulting from the system receiving malformed, corrupt, or otherwise invalid data.

__Base case__: A test case for the basic expected functionality of a system, or an interior value in an equivalence class.  For example, when testing a calculator, a base case might be a user adding 2 and 2 together.

__Baseline test__: A kind of __load test__ where a bare minimum amount of events, perhaps even none, are processed, to provide a "baseline" to show what minimal load on the system looks like.

__Bathtub curve__: A generalized function of failure rates, which start out high (as poor components fail soon after being implemented), stay low throughout much of the life of the system, then start increasing as the system nears it end of life.  So named because it looks like the outline of a bathtub as viewed from the side.

__Beta testing__: "Real-world" testing by a subset of the actual user base prior to release of the system.  Often preceded by alpha testing.

__Black-box testing__: Testing the code as a user would, with no knowledge of the codebase.  Most manual tests are black-box tests.

__Blocked__: A status for a test case in a test run.  It indicates that the test case cannot be executed at this time, for reasons outside the tester's control.  For example, the functionality it tests is not yet at a testable state, perhaps because it has not yet been developed.

__Blocker__: A defect of the highest severity, where the system cannot reasonably be released without fixing it or providing a __workaround__.

__Bot network__: A collection of __zombies__ controlled by a master.

__Boundary value__: A value which is "on the boundary" between equivalence classes.  For example, a system that has two equivalence classes, between 0 and 19, and 20 or higher, would have boundary values at 19 and 20.

__Bounds checking__: Run-time checking that data is not being written outside of a properly allocated array.

__Branch coverage__: What percentage of branches in the code are tested, usually by unit tests.

__Brownfield development__: Writing software which must interact with already-existing software in production, thus limiting potential design, solutions, architecture, etc.

__Buffer overrun__: A __vulnerability__ where more data can be written than has been allocated for it.  This can cause system crashes or unauthorized access.

__Bug__: Another term for __defect__.

__Chaos Monkey__: A __stochastic testing__ tool developed by Netflix which tests distributed systems by shutting off random servers in the system.

__CIA Triad__: Another term for the __InfoSec Triad__.

__Code coverage__: How much of the codebase is actually tested, usually via unit tests.  Although there are different kinds of code coverage, the majority of the time that non-specialists use it, they are referring to __statement coverage__.

__Combinatorial testing__: Testing in such a way so as to ensure that various combinations of variables will work as expected.

__Complete (requirements)__: The property of having the requirements specify the entirety of the system.

__Confidentiality__: An attribute of a system, that only authorized users may read data.  An element of the __InfoSec Triad__.

__Configuration error__: An error resulting from a misconfiguration of the system, as opposed to an error in the code which comprises the system itself.

__Consistent (requirements)__: The property of having requirements which can all be followed without a paradox (e.g., "the system shall display the message 'Hello' upon startup" and "the system shall not display any message upon startup" are not consistent).

__Corner case__: A test case for functionality of a system which is unlikely to occur, or is beyond the realm where a user will likely reproduce it.  By analogy with __edge case__ (a corner is where multiple edges intersect).

__Covering array__: An array which covers all possible combinations of variable values.

__Cracker__: An unauthorized person attempting to access and/or modify a system or data using underhanded techniques.

__Critical__: A defect of the second-highest level of severity, which severely impacts how a user could use the system.

__Defect__: A flaw in a system which causes it to behave in an unexpected or incorrect manner, or does not meet the requirements of the system.  Much of software testing is involved in finding defects in a system.

__Denial of Service__: A method of attacking __availability__ by sending so many unauthorized packets or other events to a computing resource that no authorized users have access to it.

__Deterministic__: Something for which the causal behaviors are entirely known and reproducible.

__Display error__: An error where the correct value was computed, but it was not displayed correctly.

__Distributed Denial of Service__: A __denial of service__ attack which consists of many different sources of the unauthorized packets, so as to increase the number of events the system must process as well as help disguise the ultimate source.

__Distributed system error__: An error arising as a consequence of the system being distributed, as opposed to running entirely on one computer.

__Dogfood, eating your own__: Another term for __dogfooding__.

__Dogfooding__: Using your own software while developing it.  For example, running the operating system that you are developing on your own computer.

__DoS__: An acronym for __denial of service__.

__DoS tools__: Tools which enable denial of service attacks.

__DRY__: Don't Repeat Yourself.  A tenet of writing good, testable code which states that code should not be repeated, for example by having two different methods which do the same thing, or copy/pasting code from one part of the codebase to another instead of making it into a callable method.

__Dynamic testing__: Testing the system by executing it.  Examples would be unit testing or black-box testing.

__Edge case__: A test case for functionality of a system which can be expected to happen, but will be rare and may require special work to handle appropriately from a development point of view.  For example, when testing a calculator, an edge case might be ensuring that trying to divide by zero provides a correct error message.

__Efficiency-based performance__: Performance on metrics related to the efficiency of use of the computational resources available to the system.

__Enhancement__: A requested modification or additional functionality which was not originally specified in the requirements.

__Equivalence class__: A group of input values which provide the same, or similar type, of output.

__Equivalence class partitioning__: Separating a specific functionality into distinct equivalence classes based on input values.

__Error__: A status for a test case in a test run.  It indicates that there is an issue with the test case itself, and the test cannot be run.  For example, the preconditions indicate an impossible condition.

__Error of assumption__: An error resulting from a developer or other person making an incorrect assumption about how the system should work.

__Evil monkey__: A __stochastic testing__ method whereby malicious code or data is sent in to a system.  Simulates an attacker trying to gain access or cause damage to a system.

__Execution steps__: The actual steps that the test will execute after ensuring that all preconditions hold.

__Expected behavior__: What the system is expected to do under certain circumstances.  For example, after typing in "`2 + 2 =`" on a calculator, the expected behavior is that the system will display "`4`" on the screen.

__Explicit boundary value__: A __boundary value__ explicitly called out by the system requirements.  For example, requirements for an automated thermometer may state the system will turn on the DANGER light when the registered temperature is 102 degrees or over.  The explicit boundary values would be 101 and 102. Contrast with __implicit boundary value__.

__Exploit__: A program or piece of data which takes advantage of a vulnerability.  A vulnerability is "strictly theoretical" until somebody develops a way to exploit it.

__Externally consistent (requirements)__: The property of having the system be consistent with requirements of other systems or of the universe.  For example, having a system which mandates that the system will be able to communicate with a base on Pluto instantaneously would require faster-than-light communication, and would thus be inconsistent with the laws of this universe.

__Fabrication__:  In __security testing__, an attack on __integrity__, which deliberately adds data, such as an attack which allows a user to create an entirely new bank account.

__Failed__: A status for a test case in a test run.  It indicates that, while the test itself was executed without error, the system has not met at least one postcondition or expected output value, or some other unexpected behavior has taken place.  In other words, the observed behavior was not the expected behavior.

__Failure case__: A kind of test where the expected behavior of the system is to fail in a certain way.  For example, sending in a negative number to a square root function which does not support complex numbers may be expected to throw an exception. Compare to __success case__.

__Falsifying the invariant__: Showing an example where an __invariant__ does not hold, such as an invariant for arithmetic method that adding two positive integers should always result in a number greater than one of the numbers, and showing that 1 + 1 = 0.

__Feasible__: In regard to requirements, possible to test with a realistic timeframe and allocation of resources.

__Field testing__: Testing that a system works while actually operating with real users.

__Fishing__: Catching marine animals for food or enjoyment.  Has nothing to do with __security testing__.  If you think it did, you are probably thinking of __phishing__.

__Floating-point error__: An error caused by a floating-point number being rounded or being inaccurate due to the incomplete mapping between actual decimal numbers and floating-point values.

__Fork bomb__: A special kind of __bacteria__ which continually forks itself, causing all CPU resources to be used up creating more copies of the fork bomb.

__Fragile__: A description of a test case or suite which is easily broken by small modifications to the codebase or environment.

__Functional requirement__: A requirement that specifies exactly what a system should do under certain circumstances.  For example, "the system shall display ERROR on the console if any parameter is negative."  Contrast with __non-functional requirement__.

__Fuzz testing__: A form of __stochastic testing__ whereby random but possible data is passed in to a system to see how it responds.

__Greenfield development__: Writing software from scratch, and thus being able to design the entire system without worrying about previous solutions.

__Grey-box testing__: Testing the code as a user would, but with knowledge of the codebase in order to understand where errors might be hiding.  A mixture of __white-box testing__ and __black-box testing__.

__Hacker__: According to the Jargon File, "[a] person who enjoys exploring the details of programmable systems and how to stretch their capabilities".  Often used in modern times to mean the same as __cracker__.

__Happy path__: The easiest path a user will take through the system, when the system is operating properly, without attempting to perform anything that is an __edge case__ or __corner case__.

__Identifier__: A number or string which uniquely identifies a test case, defect, or anything else.

__-ility requirement__: Another term for __non-functional requirement__, so named because many of these requirements use words ending in "-ility" to describe them (e.g., usability, scalability, maintainability).

__Impact__: When reporting defects, how the defect will affect users of the system.

__Implicit boundary value__: A __boundary value__ that is not explicitly called out by the system requirements, but may have an impact on the system's operation.  For example, code written using 32-bit signed integers (such as `int` in Java) has an implicit boundary at 2,147,483,647, the maximum integer size.  Adding one to this will cause the number to overflow.

__Impure__: The opposite of __pure__, a method or function which produces at least one __side effect__.

__Information Security__: The field of ensuring that computer systems successfully exhibit all three aspects of the __InfoSec Triad__.

__InfoSec Triad__: The three criteria that indicate a secure system---__confidentiality__, __integrity__, and __availability__.

__Injection attack__: A kind of attack where the malicious user tries to get the victim's computer to execute arbitrary code.

__Injection error__: An error where the system accidentally allows arbitrary code to be executed.  Leaves the system vulnerable to an __injection attack__.

__Input value__: A particular value which will be passed in to a test case.  The distinction between this and precondition can be hazy in manual or other black-box testing; in white-box testing, values that you are passing in to the method under test (e.g. as arguments or parameters) are input values.

__Integration__: Connecting multiple systems or subsystems to work together.

__Integration error__: A type of error resulting from incompatibilities or other problems at the boundary between different systems or subsystems.

__Interface error__: An error where the interface to another system is defined incorrectly, or the system accessing it does not do so correctly.

__Integrity__: An attribute of a system, that only authorized users may write data.  An element of the __InfoSec Triad__.

__Interception__: In __security testing__, an attack on __confidentiality__, such as eavesdropping on a network with a __packet analyzer__ or on a computer with a __keylogger__.

__Interior value__: A value which is not a boundary value in its equivalence class.

__Intermittent failure__: A test failure which does not occur all of the time, but only on certain runs.  Often, the reason for the intermittent failure is unknown, or it would have been fixed already.

__Internally consistent (requirements)__: The property of having no requirements contradict each other.  For example, a requirements specification which has a requirement which states "The system shall always leave the red light on" and another requirement which states that "The system shall turn off the red light if `SWITCH1` is enabled" would not be internally consistent.

__Interruption__: In __security testing__, an attack on __availability__, such as a __DDoS attack__ or pulling the plug from a network switch.

__Invariant__: In __property-based testing__, a property that should always hold for a function or method.  For example, a sorting method which accepts an unsorted array should always return an array with the same number of elements as the unsorted original.

__Keylogger__: Software which stores all keys that were pressed by the user, usually to be transmitted to, or retrieved by, an attacker.

__Key Performance Indicator__: A __performance indicator__ that is considered of primary importance in the development of the system.

__KPI__: Abbreviation for __Key Performance Indicator__.

__Legacy code__: Code which is running in production, and was written without using modern software engineering techniques and/or has substandard automated test coverage.

__Linter__: A static analysis tool which informs the user of potential issues with code.

__Load testing__: Running a full system with a specified amount of demand (e.g., a certain number of users or events) in order to determine how the system operates under realistic conditions.

__Logic bomb__: Code within a program which executes an unauthorized function, such as deleting all data on the first day of the month.

__Logic error__: An error in a program due to faulty logic being programmed.

__Major__: A defect of the third-highest level of severity, which indicates a severe problem but still allows the user to use the system.

__Malware__: Software which has pernicious and deliberate effects to the user of the software, such as a computer virus or key logger.

__Mean time between failures__: In __availability testing__, the mean (average) amount of time between failures on a system.  Often abbreviated as __MTBF__.

__Mean time to repair__: In __availability testing__, the mean (average) amount of time it takes to repair a failure.  Often abbreviated as __MTTR__.

__Media testing__: Ensuring that the media that the system is stored on (e.g., a CD-ROM or a server's hard drive) is operating correctly and has all of the data in the correct place.

__Minor__: A defect of a lower level of severity than __normal__, which causes only a very small problem for use of the system.

__Missing data error__: An error resulting from the system not receiving necessary data.

__Mock__: A particular kind of __test double__ which keeps track of which methods on it have been called.

__Modification__: In __security testing__, an attack on __integrity__, which deliberately modifies data, such as an attack which allows a user to arbitrarily change the balance on their bank account.

__Monkey testing__: Another term for __stochastic testing__.

__MTBF__: An acronym  for __mean time between failures__.

__MTTR__: An acronym for __mean time to repair__.

__n nines__: A way of showing what percentage of the time the system is available, based on the number of nines in that number, and assuming nines are the only significant digit.  For example, a system that is available 99.92% of the time has 3 nines' availability, while a system that is available 99.999% of the time has 5 nines' availability.

__Negative test case__: See __failure case__.

__Nines__: See __n nines__.

__Non-deterministic__: A test failure which does not occur all of the time, but only on certain runs, and for unknown reasons.

__Non-functional requirement__: A requirement that specifies how the system should operate, without specifying specific behavior.  For example, "the system shall be extensible, allowing for the addition of plug-ins" or "the system shall be usable by personnel with less than one hour of training".  Contrast with __functional requirement__.

__Normal__: A defect of a severity which is noticeable but does not strongly hamper the user's use of the system.

__Null pointer error__: An error resulting from the code trying to dereference a null pointer, or access a null object.

__Observed behavior__: What the system actually does under certain circumstances.  For example, if I type in "`2 + 2 =`" on a calculator, the __expected behavior__ may be that I see "`4`", but if I instead see "`WALLA WALLA`", then "`WALLA WALLA`" is the observed behavior.

__Off-by-one error__: A specific kind of __logic error__ where a program does something wrong because a value is off by one unit.

__Operational testing__: Testing that a system works under real-world conditions.

__Output value__: A particular value which will be output by a test case.  The distinction between these and postconditions in manual or other black-box testing can be hazy; in white-box testing, values that are directly returned from a method are output values.

__Packet analyzer__: A tool which allows you to view individual packets that are being transmitted over the network.

__Packet sniffing__: Using a __packet analyzer__ or similar software to view data being transmitted over a network.

__Pair programming__: Two people working at the same time on a problem on a single computer.  Can be a white-box tester and developer, looking at the code together.

__Pairwise testing__: A particular form of combinatorial testing where you are testing for all two-way interactions.

__Partitioning__: See __equivalence class partitioning__.

__Passed__: A status for a test case in a test run.  It indicates that the test was executed without error, and that the system has met all postconditions and/or expected output values---the observed behavior was equal to the expected behavior.

__Passive attack__: In __security testing__, an attack on a system which causes no changes to the system, such as eavesdropping on network traffic.

__Pathological case__: Another term for __corner case__.

__Paused__: A status for a test case in a test run.  It indicates that the tester has started to run the test, but it is temporarily on hold for external reasons (e.g., the tester went out to lunch).

__Penetration testing__: Testing the security of the system by attempting to compromise it as an unauthorized user would.

__Performance indicator__: A quantitative measure that indicates the level of performance of the system.  For example, response time or memory usage.

__Performance target__: In __performance testing__, the target value for a __performance indicator__.  If the indicator value meets or exceed the target, then the system has met the target.  Contrast with __performance threshold__.

__Performance testing__: Testing that a system meets the __performance indicators__ designated for it.

__Performance threshold__: In __performance testing__, an absolutely minimal value for a given performance indicator for the system to be considered releasable.  Contrast with __performance target__.

__Performant__: A program which provides a high level of performance, which is usually measured by it meeting or exceeding its __KPIs__.

__Phishing__: A common __attack__ which attempts to get personal or other sensitive information via email or other communications.

__Phone phreak__: A person who explores the telephone system, usually without the permission of relevant authorities.  Famous phone phreaks include John Draper and Joe Engressia.

__Phreaker__: Another term for __phone phreak__.

__Pinning test__: An automated test (usually a unit test) which checks for the current response of a system, in order to modify code without modifying existing behavior.  Note that pinning tests check for the current response, _not_ the correct response.  Often used when refactoring or adding to __legacy code__.

__Positive test case__: See __success case__.

__Postcondition__: All conditions which need to hold true after the execution steps in order for the test to pass.  For example, a postcondition after editing your user name may be that the Account Information page shows the new user name.

__Precondition__: All conditions which need to hold true before the execution steps of a test case can begin.  For example, when testing the Account Information of a website, a precondition may be that the user is already logged in.

__Principle of Least Privilege__: The principle that states that users should have the minimal amount of access to the system necessary to do their jobs.  For example, a developer should not (in general) have access to payroll data, and HR personnel should not have access to source code.

__Profiler__: A tool which allows you to measure the resource utilization and internal events (such as method calls or instantiation of objects) of a running program.

__Pure__: A method or function which does not produce any __side effects__, but simply returns the result of a computation.

__QA__: See __quality assurance__.

__Qualitative__: Not able to be expressed numerically, such as "the system shall be _extremely_ cool".  Contrast with __quantitative__.

__Quality assurance__: Ensuring the quality of software, by a variety of methods.  Software testing is an important, but not the only, part of quality assurance.

__Quality attribute__: Another (probably better) term for __non-functional requirement__.

__Quantitative__: Able to be expressed numerically, such as "the system shall respond within 500 milliseconds".  Contrast with __qualitative__.

__Ransomware__: A kind of __malware__ which performs an unwanted action (e.g., encrypting your hard drive) and asks for money or other compensation in order to undo it.

__Real time__: The actual amount of time (the same kind of time as measured by a clock) taken for a process to perform some task.  Also referred to as __wall clock time__.  Not to be confused with "real-time system".

__Reflection__: A way to determine class and method structure at run-time.

__Regression failure__: A failure of a previously-working piece of functionality that is caused by (seemingly) unrelated additional functionality or defect fixes.

__Regulation acceptance testing__: Testing that a system meets legal or other regulatory requirements.

__Reproduction steps__: The steps necessary for reproducing a defect.  Often included in defect reports so that readers of the defect will understand what causes the defect and how to reproduce it.

__Requirement__: A statement of what the system under development needs to do in order to be considered complete and correct.

__Requirements specification__: A list of requirements for a given system.  The system is expected to meet all of the requirements in the requirements specification.

__Response time__: In __performance testing__, how quickly a system responds after user input or other event.

__Rounding error__: An error in a program caused by the system rounding a number.

__Running__: A status for a test case in a test run.  It indicates that the test is currently being executed, but has not yet completed.

__Sanitization__: "Cleaning up" user input so that it does not contain code that would be executed by the running program or other content that would cause harm to the system.

__Scripted testing__: Testing with a rigid script, such as a test plan, where steps are well-defined and ordered. Contrast with __unscripted testing__.

__Seam__: A place in the codebase where you can modify behavior without modifying the code itself.

__"Seat of your pants" testing__: Testing where the expected behavior is known to the tester through experience with the software or its domain, instead of being formally specified.

__Security testing__: Testing that the system meets the criteria of the InfoSec Triad, that the system is safe from unauthorized tampering and/or access.

__Service-based performance__: Performance on metrics related to how the user will interact with the system.

__Service Level Agreement__: An agreement by a service provider, which often includes a guarantee of __availability__.

__Side effect__: Anything which is not strictly the returned result of a computation, such as displaying a message or setting a variable value.

__SLA__: An abbreviation for Service Level Agreement.

__Smart monkey__: A __stochastic testing__ method whereby the data passed in mimics how an actual user would use the system.  For example, a smart monkey test for a word processing system might type some letters, format them, and save the file (as an actual user might), as opposed to clicking buttons purely at random.

__Smoke test__: A small subset of tests which is used as a gateway for further testing.

__Soak test__: Another term for __stability test__.

__Social engineering__: Manipulating people to underhandedly cause them to perform actions that put security of a system at risk.  For example, an attacker calling an administrative assistant falsely claiming that they are from the IT department and need to know the user's password.

__Spear phishing__: A specific kind of __phishing__ which is aimed at highly targeted individuals and is customized for them.  For example, a regular phishing email may be "Dear user, please to reset your email password for linked here" whereas a spear phishing email would be "Dear Mr. Jones, please reset your SuperDuperEmail password here. Sincerely, Jane Smith, SuperDuperCo. Vice President of Security".

__Spyware__: A kind of __malware__  which surreptitiously monitors the actions of the user of the system.

__SQL injection attack__: A specific, common kind of __injection attack__ where the malicious user attempts to have the system execute arbitrary SQL commands.

__Stability test__: A kind of __load test__ where a small number of events are processed, but over a long period of time, in order to determine how stable the system is for non-trivial time periods.

__Stakeholder__: Any person who has a direct interest in the successful completion, execution, or release of the system, such as customers, developers, and project managers.

__Statement coverage__: What percentage of statements in the code are tested, usually by unit tests.

__Static testing__: Testing the system without executing any of its code.  Examples would be code analysis or modeling tools.

__Stochastic testing__: Testing a system through the use of randomized inputs.  These inputs do not have to be entirely random; for example, they can be a probability distribution of values or generated strings.  Also called __monkey testing__.

__Stress test__: A kind of __load test__ where a very high number of events are processed in a small amount of time, in order to determine how the system deals with periods of time where the system is "stressed".

__Strict partitioning__: Partitioning equivalence classes such that there is no overlap between the input values for any of them.

__Stub__: A "fake method" which can be used in unit testing to limit dependencies on other methods and focus testing on the method under test.

__Success case__: A kind of test case where the expected behavior of the system is to return the correct result or do the correct thing.  Compare to __failure case__.

__System testing__: Testing the system as a whole, as the user (as opposed to a developer) would interact with it.  Usually done in a black-box or grey-box manner.

__System time__: The amount of time that kernel code executes while a system performs a task.

__System under test__: The system that is actually being tested.

__Target__: In __performance testing__, another term for __performance target__.

__Tautological test case__: A test case which was written so that it will always pass, for example `boolean foo = true; assertTrue(foo);`.  This is usually done inadvertently.

__Test artifact__: A document or other byproduct of the testing process, such as test plans or results.

__Test case__: The smallest unit of a test plan, these are the individual tests which describe what is to be tested and a check for the expected behavior of a system.

__Test coverage__: A general term for which aspects or parts of the system are covered by tests.

__Test double__: A "fake object" which can be used in unit testing to limit dependencies on other classes, which may have problems of their own, not yet be developed, or simply be time- or resource-intensive to actually instantiate.

__Test fixture__: A procedure or program which puts a system into a state ready for testing.

__Test hook__: A "hidden" method which allows input to, or output from, the system in order to make it easier to test.

__Test plan__: A list of related test cases that are run together.

__Test run__: An actual iteration (run-through) of a test plan.

__Test suite__: A grouping of related test plans.

__Testability__: A quality of a system that specifies how easy it is to test---having well-designed and coherent methods, pure functions where possible, allowance for dependency injection, etc.

__Testable code__: Code which can be easily tested in an automated fashion at multiple levels of abstraction.

__Test-Driven Development (TDD)__: A particular software development methodology which embraces __test-first__ development along with several other tenets, such as continuous refactoring and expectation of change.

__Test-First Development__: Any software development methodology in which tests are written before the code that makes them pass.

__Test-Unfriendly Construct__: A part of the structure of the code which is difficult to test, such as a constructor, finalizer, or private method.

__Test-Unfriendly Function__: A functional aspect of a program which is difficult to test, such as communicating over a network or writing to disk.

__Threshold__: In __performance testing__, another term for __performance threshold__.

__Throughput__: In __performance testing__, the number of events or tasks the system can handle in a given time frame.

__Total time__: The total amount of time that code (user or kernel) executes, without taking into account other factors (such as time spent waiting for input).  Compare to __real time__.

__Traceability matrix__: A two-dimensional matrix displaying test cases and requirements and indicating which test cases test which requirements.

__Trapdoor__: A program or piece of a program which provides secret access to a system or application.

__Triage__: A meeting or other way (e.g. email discussion) of prioritizing defects.

__Trivial__: A defect of a severity which is not noticeable, or hardly noticeable, and only causes the smallest of issues for a user of the system.

__Trojan horse__: A kind of __malware__ which pretends to be another in order to trick users into installing and executing it.

__TUC__: See __Test-Unfriendly Construct__.

__TUF__: See __Test-Unfriendly Function__.

__Unambiguous (requirements)__: The property of having requirements which can be read and understood in one and only one way.

__Unit testing__: Testing the smallest individual units of code, such as methods or functions, in a white-box manner.

__Unscripted testing__: Testing without a rigid script, where the tester has broad latitude to use their own volition and knowledge to determine the quality of the system or find defects. Contrast with __scripted testing__.

__UPOD__: An acronym for "Under-Promise, Over-Deliver".  Refers to the fact that you should promise less than you think you can deliver, but then endeavor to deliver more.

__User acceptance testing__: A particular kind of __acceptance testing__ where the tester is the user of the software.  It ensures that the system under test is acceptable to the user by meeting their needs.

__User testing__: Having an actual user of the system attempt to perform tasks, often without instruction, in order to determine how users interact with the system.

__User time__: The amount of time that user code executes while a system performs a task.

__Utilization__: In __performance testing__, the relative or absolute amount of a given computing resource (e.g., RAM, processor instructions, disk space) is used under certain circumstances.

__Validation__: Ensuring that the system meets the needs of the customer.  Checking that you built the right _system_.

__Verification (category of testing)__: Ensuring that the system operates correctly, does not crash, provides correct answers, etc.  Checking that you build the system _right_.

__Verification (in unit testing)__: Checking that a particular method was called on a __mock__ object.

__Virus__: A kind of __malware__, often small, that replicates itself with human intervention.  This intervention could be something such as clicking on a link or running a program sent to you as an attachment.

__Vulnerability__: A potential defect that would allow a user to compromise or otherwise gain unauthorized access to a system.

__Wall clock time__: Another term for __real time__.

__White-box testing__: Testing the code directly and with full knowledge of the code under test.  __Unit testing__ is an example of white-box testing.

__Workaround__: A method for avoiding a known __defect__ while still being able to use the system.

__Worm__: A kind of __malware__, often small, that replicates itself without human intervention.

__YAGNI__: You Ain't Gonna Need It.  A tenet of __Test-Driven Development__ which states that you should not do work or add features which are not immediately necessary.

__Zombie__: A computer with software installed which allows unauthorized users access to it to perform unauthorized functionality.  For example, a system might have a mailer program built in which will allow other users to send spam from your machine, so that the original users cannot be tracked.
