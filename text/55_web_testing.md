# Automated Web Testing

So far, all of the systems under test that we've discussed have used a simple, text-based interface.  It turns out that in the years since 1965, many users have decided that they want graphical, touch-based, or other ways of interacting with a system.  Testing these, especially in an automated fashion, can often be more difficult than a traditional text interface.

We can do automated systems-level tests using a variety of tools.  In this chapter, we'll discuss testing web sites using a tool called Selenium.  The point of this chapter is not to make you an expert in using Selenium, though.  It's to give you the necessary foundation to write automated tests for any application with a more complicated interface than text.  Many of the techniques we will discuss 

## Theory of Testing Graphical Interfaces

The key idea to keep in mind is that we are still testing, even if the interface is different than the ones we have encountered so far.  That is, we are still checking to see that the expected behavior of the system under certain circumstances is the same as the observed behavior.  This expected behavior may be more complicated to explain or to check, or we may discuss it in a more abstract manner, but there are always ways to determine to define the expected behavior and to observe if it actually occurs.

For example, for a text-based interface, one can always copy and paste the expected and observed text.  Comparisons are simple - you can check visually or, for more lengthy output, use a tool like Unix's `diff` or write a simple program to verify that the text is correct.  This becomes difficult to do when interacting with graphical programs, web pages, and the like.  The expected behavior might be something such as "an error message will appear in the center of the screen, with a picture of a bug with a slash through it".  How do we programmatically check such a complicated explanation of the expected behavior?

One possibility is to simply examine the HTML code of the page and ensure that it matches the known HTML which will display the picture of the bug, that centers it properly, and all of the other criteria.  That is, imagine some JUnit pseudocode like this:

```java
@Test
public void testCenteredBugMessage() {
    String
    String expectedHtml = “<!DOCTYPE html><html lang="en-US"><head><title>No Bug Page</title></head><body><div><img src=\"bugslash.jpg\" alt="Bug with Slash Through It" height="50" width="50"><strong>No bugs allowed here!</strong></div></body>"; 
    // Assume the getHtml() method is a helper method to get the 
    // raw HTML returned from a URL
    String pageHtml = getHtml("http://example.com"); 
    assertEquals(expectedHtml, pageText); 
}
```

This will check that the page has the right HTML, and assuming we wrote it correctly, then if the test passes, the page under test should be displayed correctly.  There are a few problems with testing complex interfaces this way, though.

_It's fragile_ - If anything on the page changes, the entire test is invalid.  For example, let's add a space between the `<img>` tag and the `<strong>` tag.  From an HTML-rendering perspective, we have not changed how the page is displayed at all.  Our test will now fail, however, since the actual string has changed.  Any change to the actual HTML, no matter how trivial, will show up as a failure.  Adding additional functionality to the page, such as a menu bar,  will show up as a failure.  Unless we have a very, very good idea of what the final page should look like, our test will probably need to be re-written on a regular basis.

_It's at the wrong level of abstraction_ - If I were talking to another person about what I wanted on a web page, I certainly wouldn't start with "well, first I want a `<` sign, and then a `!`, and then the word DOCTYPE, and then a space..."  I wouldn't even say "I want a `<strong>` tag, and within it should be the words 'No bugs allowed'."  I would discuss the web page at the proper level of abstraction, that is, from the level of abstraction that a user would see it.  For example, I might say "I want to see the text 'No bugs allowed' in  bold, and it should be next to a picture of a bug with a slash in it."  By focusing on the lower level of abstraction, we are missing the forest for the trees - focusing too much on the implementation details of how the page should be created as opposed to 

An analogy would be programming in JVM bytecode instead of in Java.  The following bytecode encodes a simple method - can you tell what it does by quickly scanning it?

```
  public static boolean foo(int);
    Code:
       0: iload_0
       1: iconst_2
       2: irem
       3: ifne          10
       6: iconst_1
       7: goto          11
      10: iconst_0
      11: ireturn
```

The bytecode above is absolutely equivalent to the Java code below (in fact, I generated the bytecode from this method using the `javap -c` command).

```java
    public static boolean foo(int x) {
	return x % 2 == 0;
    }
```

In this case, the purpose of the method is manifest - it returns true if the argument `x` is even, false otherwise.  It is much easier to understand the exact same method written in Java (even if you are familiar with JVM bytecode), because you are viewing the problem from a higher level of abstraction.   

_There's no semantic understanding_ - Testing a long string of HTML does not give you an understanding of what should be there.  This means that modifying a test for a page means determining what kind of HTML should exist, as opposed to what the system is supposed to do (e.g., display a textbox or a link).  Remember from the chapter on requirements that we should let the system know what to do, not how to do it.  With web pages, the HTML is simply the "how" - we should be testing the "what"; that is, what is finally displayed to the user.

_What about JavaScript and CSS?_ - This is mostly specific to web pages and not all graphical display formats, but JavaScript and CSS can dynamically modify HTML or how it is displayed.  In order to check that the correct JavaScript and CSS have been added to the page, we would be reduced to copying and pasting the code from the code to the checked string.  This is almost (although not quite) a tautological test - "verify that the code I wrote over there is placed on the page here".  It does not tell us anything about the correctness of the additional code, only that it was in fact put on the page.  Who knows what the code will do?  

For all of the reasons listed above, when testing a web page, we will seldom look at the underlying HTML code directly.  Instead, we can use tools and libraries to inspect and interact with the web page at the proper level of abstraction.  Instead of simply determining which HTML code is downloaded, we will be able to state our tests in terms of "check that the title of this page is 'New User Login'" or "click on this link and verify that it takes the user to the 'Search' page".  

## Selenium

The particular tool we will cover for web testing is called __Selenium__.  This is far from the only tool for performing web testing out there, of course.  However, there are a variety of reasons to use this tool.

First, as of this writing, it is commonly used in industry.  It's battle-hardened, having been around for well over a decade.  Although we will show how it can be used with Java, it supports numerous other languages including Python and Ruby.  Thanks to its use of an intermediary language, __Selenese__, to communicate test commands, several test frameworks have built interfaces to it, including JUnit. It comes with its own IDE that allows users that are new to Selenium to quickly create their own tests and scripts without learning yet another language.  However, since we can interface with it using many popular languages, you can do anything which is computationally possible.  Finally, it works with a variety of modern web browsers - you will not be restricted to a particular buggy or only partially implemented web browser.  

Selenium is actually an entire framework, not just one simple tool.  The __Selenium WebDriver__ API allows you to control a web browser just as a user would, but in an automated fashion.   In this book, we will only scratch the surface.  

As a side note, if you're wondering why the tool is called Selenium, an early competitor of theirs was called Mercury.  Since mercury poisoning can be cured by ingestion of selenium, they decided to take a cue from chemistry in the naming of their product.  

## Using Selenium With The IDE

