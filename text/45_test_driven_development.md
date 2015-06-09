## Test-Driven Development

Although we have covered how to write unit tests from a broad perspective, the question remains - how do we integrate test writing into the software development process?  In the old days, software testing was often an entirely separate process from writing the code, but today, software quality is the job of developers, as well.  Conversely, software testing has acquired many aspects of development; it is the rare tester who never has to write any code.  Even testers who mostly do manual testing will often write integration scripts or other tools.

Test-Driven Development (TDD) is one methodology for writing quality software  with a well-thought-out testing suite.  By following the tenets of TDD, developers will know what to test, in what order, and a way of balancing unit testing and coding the application under test.  It's not a panacea, of course.  In the software world, as Frederick Brooks reminds us, there is no silver bullet.  However, by using TDD properly, the werewolf of software development can at least be tamed a bit.

### What is TDD?

Test-Driven Development is a software development methodology that comprises several key points:

1. __Writing tests first, before code__ : Before you even start thinking of _how_ to do something, this forces you to think of _what_ is to be done.  Since your code has not been written, you can check that the test initially fails (in order to avoid tautological tests which always pass), and have a specific goal that you are reaching toward (getting the test you just wrote to pass).

2. __Writing only code that makes the test pass__ : This ensures that you focus on writing relevant code, instead of spending time working on possibly superfluous frameworking or other code.  One of the key benefits of TDD is that allows you to focus, by training you to keep your eye on the one goal of the cycle instead of thinking of all the other possible bits of code that you could be writing.

3. __Writing only tests that test the code__ : It's tempting to write tests for the sake of writing them; this will help to keep tests only being written for the functionality being written.

4. __A short turnaround cycle__ : TDD emphasizes quick cycles, which work to keep the developer on track and focused on a short, specific goal.

5. __Refactoring early and often__ : Refactoring is often held off until later in the development process, when it is much more difficult to do, especially if there has not been much time spent on doing it earlier.  That becomes all the more reason not to do it later.  By refactoring often, it becomes a part of the development process and a habit, instead of something that will be done "if there's enough time" (note: there is never enough time).

#### The Red-Green-Refactor Loop

We work within these constraints by using the "red-green-refactor" loop.  A single cycle in TDD involves the following three steps:

1. __Red__ :  TDD is a form of test-first development (TFD), so the first thing to do is write a test.  The developer writes a failing test for a new piece of piece of functionality or an edge case that should be checked.  The newly written test - and only that test - should fail.  If the newly written test does not fail, that means that the code has already been written for that functionality.  If other tests fail, this means that there is a problem with the test suite - perhaps an intermittent or non-deterministic failure of a test - that should be fixed before moving on.  This phase is called "red" because many unit testing frameworks will show failing tests as a red color.  Since red-green colorblindness affects a good portion of the human population, and humans are by far the most likely animal to program, this may not be the best color selection.  However, we will follow this standardized naming convention.

2. __Green__ : The developer now writes code to make this test pass, and only work on making this test pass, while not causing any other tests to fail.  At this point, some "ugly" code is to be expected; the focus is on making it work as opposed to making it pretty.  If other tests fail, then the developer has inadvertently caused a regression and should work on fixing that.  At the end of this phase, all tests should be passing ("green").

3. __Refactor__ : Once the tests pass, the developer should examine the code that was just written and refactor it.  There may be small problems like magic numbers (i.e. "naked" values in a program that are not described or represented by a constant such as `if (mph > 65) { ... }` instead of `if (mph > SPEED_LIMIT) { ... }`) or large problems such as a poorly-chosen algorithm.  These can be fixed during this phase, since in the previous phase the focus was on getting the results correct.  An easy way to remember this is the mnemonic "first make it green, then make it clean".  Since there is always a fall-back position of properly functionining (even if poorly written) code, the developer can try multiple approaches without worrying about getting to a point where the code doesn't work at all.  In the worst case scenario, the code can be reverted back to where it was at the end of the "green" phase and a different path can be chosen for rewriting.

After each red-green-refactor cycle, the developer should think what the next piece of functionality that he or she wants to add, and then loop back.  This will continue until the software has done, a path of testing, coding, and refactoring that will eventually culiminate not only in a complete product, but a solid test suite that should be directly relevant to all the functionality of the program.

This could all be re-written as a very simple algorithm.  By doing so, we can see how this helps focus the attention of the person writing the software; there is always a well-defined next step.

1. Write a test for functionality which has not been written yet.
2. Run test suite - only the newly written test should fail.  If not, first figure out why other tests are failing and fix that problem.
3. Write enough code to make that test pass, without causing other tests to fail
4. Run test suite - if any test fails, go back to step 3.  If all tests pass, continue.
5. Refactor code that you have written, and/or any associated code.
6. Run test suite - if any tests fail, go back to step 5.  If all tests pass, continue.
7. If there is any more functionality to add, go to step 1.  If there is no more functionality to add, the application is complete!

#### Concepts

There are several principles to keep in mind when writing in a TDD manner.

__YAGNI (You Ain't Gonna Need It)__

__KISS (Keep It Simple, Stupid)__

__Fake It 'Til You Make It__

__Avoid Test Interdependencies__

__Avoid Slow Running Tests__

__Remember That These Are Principles, Not Laws__

#### Example

#### Benefits of TDD

(automatically create a test suite)

(testing becomes part of workflow)

(when it's easy to write tests, more tests are written)

(tests are relevant)

(forces developer to think about what program should do, not just specifics of code)

(ensures small steps)

(code is testable)

(code is extensible)

(helps avoid defects)

(100% or close to it test coverage)

(confidence in codebase)

(structured methodology / checkbox manifesto)

#### Drawbacks of TDD

(focus on unit testing)

(extra time)

(perhaps less time spent on design/architecture)

(not appropriate for all development, e.g. safety-critical, prototype)

(architecture modifications may be difficult)

(tests become part of the overhead of a project)

(could fall into trap of overtesting)

(may be difficult to implement on legacy systems with other paradigms)



#### Example: Building FizzBuzz via TDD
