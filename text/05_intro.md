# Introduction

_Let me say that I rarely ever read introductions to technical books; I won't be offended if you don't read this one._

## The Story So Far

A long time ago, in a company far, far away, I was a test lead.  One of my responsibilities was to interview potential test engineers for our team, and I found that many candidates did not have a background in testing.  Those who did well in their career often picked things up as they went.  Even those who had degrees in computer science (or a related field) had often not learned about software testing.  Developers had to learn how to properly test their own code, often following the old "apprentice" model by asking more senior developers what needed to be tested.

I figured that I could complain about it, or do something about it.  I was able to convince the Computer Science department at the University of Pittsburgh to allow me to develop and teach a class on software testing, which has grown to cover many different aspects of software quality, and become a popular, ongoing course (CS 1632: Software Quality Assurance, if you're interested in taking it!).  I developed my own curriculum, as I couldn't find a good book or syllabus on the subject that struck the right balance between theory and application.  Finding a good book to use was even more difficult than finding a good Quality Assurance (QA) Engineer to interview!  Once again, I figured I could either complain about it, or do something about it, and once again, I chose the latter.

Testing software is a big part of the software development process, and useful not just for those looking specifically for a career in QA.  A developer who doesn't care about software quality is not a good developer.  This book is targeted to those interested in software testing or writing tests as a developer.

## What This Book Covers

This book is intended to provide a relatively comprehensive overview of software testing.  By the end, my expectation is that the reader will have all the skills necessary to enter the workforce as a quality analyst, although I feel that managers, developers, and others involved in the software development process may also find it very useful.

To that end, the book starts with a generalized overview of the field - what is software testing, anyway?  It's difficult to study something without understanding what it is!  We'll then move on to going over some of the theory and terminology used by those in the software testing industry.  I'll let you in on a little secret - this is probably the least interesting part of the book.  However, we really should be speaking the same language when we talk about concepts.  It would be difficult to explain concepts in plumbing if I were forced to avoid using the word "pipe" for fear of someone not understanding it.

After that, we'll move on to the basics of developing a manual test plan, and dealing with defects that are found in the process of executing it.  Manual tests are rarer now than they used to be; automated tests have relieved much of the tedium of executing them.  There are definite benefits to developing tests _qua_ tests without worrying about a specific language syntax or tool chain, though.  We'll also go over how to properly record our testing and report defects that are found from our testing.

Once writing manual tests is old hat, we can move on to automated tests - system-level and unit tests.  Automated tests allow you to very quickly execute tests, from either a very low level (like making sure a sorting algorithm works correctly) to a very high level (ensuring that you can add something to your cart on an e-commerce website).  If you've actually executed some manual test plans, you'll see why letting the computer execute all of the tests for you is such a time-saver. Perhaps more importantly, it's an excellent aggravation-saver, as repeatedly running manual tests is one of the fastest ways to drive a tester insane.

Finally, we get to the really interesting stuff!  This section of the book is where we'll get to read about specialized kinds of testing, like combinatorial, performance, and security tests.  The world of software testing is quite a large one - testing embedded software is very different from testing web applications, performance testing is very different from functional testing, and testing for a startup's first prototype product is very different from testing for a large company developing a medical device.

## What This Book Does Not Cover

This book is an introductory text to the world of software testing; there's definitely lots of room to explore and go into more detail on all of the topics here.  However, the purpose isn't to try to rival War and Peace in length, but to provide a general introduction to the field.  Spending lots of time on the intricacies of each of them might be interesting to me or to a small subset of the people reading the book, but my goals are to provide a good foundation of practical software testing knowledge, and give a taste of some of the various specialties and subfields in the industry.  Consider it a whirlwind tour of Europe instead of studying abroad for a year in Paris.

## A Note On Language Choice

The examples I give are in Java and use much of the Java tool chain (e.g., JUnit).  This is not due to some particular love of Java.  I'll often mention how much easier something is to do in Ruby, or how a particular kind of defect doesn't even exist in Haskell, or other references along those lines.  Java does serve as a _lingua franca_ amongst developers nowadays.  It is a pretty standard, popular, ALGOL-derived programming language, so even if you're not familiar with it, you can probably reason out what's happening.
