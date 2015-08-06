# Introduction

First off, let me say that I rarely ever read introductions to technical books, so I won't be offended if you don't read this one.

## The story so far...

A long time ago, in a company far, far away, I was a test lead.  One of my responsibilities was to interview potential test engineers for our team, and I found that many candidates did not have a background in testing.  People who did well often picked things up as they went, and every company did things a little differently.  It didn't help that many people who had degrees in computer science, software engineering, or a related field had never learned about software testing, or even known that it existed as a career.  Developers had to learn how to properly test their own code, often following the old "apprentice" model by asking more senior developers what needed to be tested.

I figured that I could complain about it, or do something about it.  I was able to convince the Computer Science department at the University of Pittsburgh to allow me to develop and teach a class on software testing, which has grown to cover many different aspects of software quality, and become a popular, ongoing course (CS 1632: Software Quality Assurance, if you're interested in taking it!).  I had to develop my own curriculum, as I couldn't find a good book or syllabus on the subject that struck the right balance between theory and application.  Once again, I figured I could either complain about it, or do something about it, and once again, I chose the latter.

Understanding how and why software fails, and detecting defects, is a big part of the software development process.  A developer who doesn't care about code quality is not a good developer.  This book is targeted towards those interested in software testing or writing tests as a developer.  If you're interested in quality assurance, or just want to write code with fewer bugs, this book is for you!

## What this book covers

This book is intended to provide a relatively comprehensive overview of software testing.  By the end, my expectation is that the reader will have all the skills necessary to enter the workforce as a quality analyst, although I feel that managers, developers, and others involved in the software development process will also find it very useful.

To that end, the book starts with a generalized overview of the field - what is software testing, anyway?  It's difficult to study something without understanding what it is!  We'll then move on to going over some of the theory and terminology used by those in the software testing industry.  I'll let you in on a little secret - this is probably the least interesting part of the book.  However, we really should be speaking the same language when we talk about concepts.  It would be difficult to explain concepts in plumbing if I were forced to avoid using the word "pipe" for fear of someone not understanding it.

After that, we'll move on to the basics of developing a manual test plan, and dealing with defects that are found in the process of executing it.  Manual tests are rarer now than they used to be; automated tests have relieved much of the tedium of executing them.  There are definite benefits to developing tests qua tests without worrying about a specific language syntax or tool chain, though.  We'll also go over how to properly record our testing and report defects that are found from our testing.

Once writing manual tests is old hat, we can move on to automated tests - systems-level and unit tests.  Automated tests allow you to very quickly execute tests, from either a very low level (like making sure a sorting algorithm works correctly) to a very high level (ensuring that you can add something to your cart on an e-commerce website).  If you've actually executed some of the manual test plans, you'll see why letting the computer execute all of the tests for you is such a time-saver (and perhaps more importantly, aggravation-saver... repeatedly running manual tests is a great way to drive a tester insane).

Finally, we get to the really interesting stuff!  This section of the book is where we'll get to read about specialized kinds of testing, like combinatorial and property-based tests, performance testing, and security testing.  The world of software testing is quite a large one - testing embedded software is very different from testing web application, and performance testing is very different from functional testing, and testing for a startup's first prototype product is very different from testing for a large company developing a medical device.

## What this book doesn't cover

This book is an introductory text to the world of software testing; there's definitely lots of room to explore and go into more detail on all of the topics here.  However, the purpose isn't to try to rival War and Peace in length, but to provide a general introduction to the field.  Spending lots of time on the intricacies of each of them might be interesting to me or to a small subset of the people reading the book, but my goals are to provide a good foundation of practical software testing knowledge, and give a taste of some of the various specialties and subfields in the industry.  Consider it a whirlwind tour of Europe instead of studying abroad for a year in Paris.

This book is meant to be an introduction to the topic for people not familiar with software testing.  If you're testing software which puts people's lives at risk, you will definitely want to go more into depth than this book offers.  However, it's a good start to understand the testing process as a whole.

## A note on language choice

The examples I give are in Java and using much of the Java tool chain (e.g., JUnit).  This is not due to some particular love of Java; in fact, as I'll often mention how much easier something is to do in Ruby, or how a particular kind of bug doesn't even exist in Haskell, or how much more elegant the code would be if it were written in Lisp.  However, Java serves as a kind of _lingua franca_ amongst developers nowadays.  It's a pretty standard, popular, ALGOL-derived programming language, so even if you're not familiar with it, you can probably reason out what's happening. 
