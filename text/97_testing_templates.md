# Testing Templates

This is a reference for various templates used in the book.

## Test Case Template

IDENTIFIER: _A unique identifier for this test case, which ideally will also serve as a simple way to remember what the test case is testing._  Example: VALID-PARAMETER-MESSAGE.

TEST CASE: _A short description of what the test case does._

PRECONDITIONS: _Any conditions which must be true before the test case is executed._

INPUT VALUES: _Any input values to be passed in as part of the execution steps._

EXECUTION STEPS: _The steps the tester should take to run the test._

OUTPUT VALUES: _Any specific output values expected after the execution steps._

POSTCONDITIONS: _Any conditions which must hold true after the execution steps have been completed.  If these conditions are not met, the test fails._

## Defect Reporting Template

SUMMARY: _A short (one sentence or less) summary of what the defect is._

DESCRIPTION: _A more in-depth (can be a paragraph or more) description of the defect._

REPRODUCTION STEPS: _The specific steps to reproduce the defect._

EXPECTED BEHAVIOR: _What is expected to occur after the reproduction steps have been executed._

OBSERVED BEHAVIOR: _What actually happened after the reproduction steps were executed._

IMPACT: _How this specifically impacts a user of the software._

SEVERITY: _How severe this problem is, from TRIVIAL to BLOCKER._

WORKAROUND: _How to avoid triggering this defect, if known or possible._

NOTES: _Any other notes that may be useful in fixing or tracking down this defect, such as system configuration, thread dumps, or logging files._

## Red / Yellow / Green Template

_The system should be divided into a reasonable number, usually between three and ten, subsystems or areas of functionality.  Each subsystem or area should then be given a "color rating" of red, yellow, or green, as well as a short description (a few sentences, maximum) of why that rating was given._

RED: _This aspect of the system has major problems, and should not be released in this state.  Substantial additional help in the form of resources, scope reduction, or schedule increase will be necessary in order to get it ready for release._

YELLOW: _There are a few problems with this aspect of the system, some of them substantial.  Some additional help may be necessary to get it to a reasonable level of quality._

GREEN: _There are no major problems with this aspect of the system, and no additional help is necessary to see it to completion._

## Daily Status Report

_This is a template for reporting your daily status to a manager or other supervisor._

DATE

* _What you worked on today, and its status.  This can be several different bullet points._
* _What you plan on working on tomorrow.  This can also be several bullet points._

BLOCKERS: _Anything that is preventing you from getting work done, be that resources, time, or knowledge._
