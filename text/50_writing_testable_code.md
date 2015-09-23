# Writing Testable Code

Although we have been looking at testing from the perspective of a tester, often it can be useful to view it from the perspective of someone writing the code.  Just as a tester can make the life of a developer easier by determining defects and the level of quality of a program, the programmer can make life easier for a tester by ensuring that the code they write is easily able to be tested.  In today's programming environment, the tester and developer are often the same person, especially when it comes to unit tests.  Thus, it makes perfect sense (from a game theory perspective) to make the code as testable as possible.

By writing the code in a testable way, not only will you make it easier to write tests now, but there are a host of other benefits.  You will tend to write good, modular code.  Future changes will be easier to make, since you will have more tests and can write better tests.  The code will be easier to understand, and there will be fewer problems since you're more easily able to test all of the edge cases.  Although your goal may be to write testable code, you'll end up writing code that is better in all sorts of other ways.

## What Do We Mean By "Testable Code"?

In some sense, virtually all code is testable.  As long as you can control the inputs and observe the outputs, you can test any piece of software.  By __testable code__, we mean code that is easy to test in an automated fashion at multiple levels of abstraction.  This means that writing black-box systems tests is simple, that writing unit tests (for classes, methods, or other units of code) is not unreasonably complex, and that more advanced testing such as performance and security testing are possible.  Not all testable code is good code---it's possible to write a horrible mess of non-performant spaghetti code which is nevertheless easy to test.  However, all good code is testable code.  Being able to write tests for a particular piece of code is an integral part of the code being well-written.

Let's imagine that we're testing the following piece of code.  It's part of a video game which simulates a bird moving across a landscape.  Pressing a button makes the bird fly and updates its height and location on the screen.

```java
public class Bird {

    public int _height = 0;

    public int _location = 0;

    public void fly() {
        Random r = new Random();
        _height += r.nextInt(10) + 1;
        _location += r.nextInt(10) + 1;
        Screen.updateWithNewValues(_height, _location);
    }

}
```

While this may be a straightforward method, it is going to be very difficult to test via unit test.  After all, there are no return values to assert against.  The results will have be checked by looking at class-level variables.  There's no way to tell a specific "right" answer, since there's dependence on a random number generator which you have no way of overriding.  There's no way to check that the Screen was updated without having an actual Screen object available and running.  All of these mean that this code will be difficult to test in isolation, and thus very difficult to unit test.  Throughout the rest of this chapter, we will look at different strategies to ensure that our code does not end up like this.

## The Basic Strategies for Testable Code

The two key concepts to keep in mind when writing code that is testable at the unit test level are:

1. Ensure code is segmented
2. Ensure that events are repeatable

If a particular method relies on only a few other parts (classes, methods, external programs, etc.) of the system to work---and ideally, if its results are affected only by the values passed in as parameters---then it will be relatively easy to test.  However, if it relies on a variety of other parts, then it can get very difficult, very quickly.  You not only have to make sure that the part of the system is working, but any parts that it is dependent upon.  You may be able to provide test doubles, but if the code is not written with them in mind, it may not be possible.  Let's examine some code which is not segmented well, and will be extremely difficult to test:

```java
public int getNumGiraffesInZoo() {
    String animalToGet = "Giraffe";
    DatabaseWorker dbw = DatabaseConnectionPool.getWorker();
    NetworkConnection nwc = NetworkConnectionFactory.getConnection();
    dbw.setNetworkConnection(nwc);
    String sql = SqlGenerator.generate("numberQuery", animalToGet);
    int numGiraffes = 0;
    try {
        numGiraffes = (int) dbw.runSql(sql);
    } catch (DatabaseException dbex) {
        numGiraffes = -1;
    }
    return numGiraffes;
}
```

At first glance, this looks easy to test---after all, you just need to assert that the number of giraffes is the number you expect---but this code is not well-segmented.  Not only does it depend on the `DatabaseWorker`, `NetworkConnection`, `DatabaseConnectionPool`, `NetworkConnectionFactory`, and `SqlGenerator` classes to work correctly, along with all of their assorted methods, there is no way to double them since they are all constructed inside of the method.  A problem in any of these will cause your test to fail, and it can be difficult to know why the test failed.  Was it in the actual method you are testing, or one of the numerous dependencies?

Let's restructure this so that the method is well-segmented:

```java
public int getNumGiraffesInZoo(DatabaseWorker dbw, SqlGenerator sqlg) {
    int numGiraffes = 0;
    String sql = sqlg.generate("numberQuery", "giraffe");
    try {
        numGiraffes = (int) dbw.runSql(sql);
    } catch (DatabaseException dbex) {
        numGiraffes = -1;
    }
    return numGiraffes;
}
```

While this is still suboptimal, it is at least possible to override all of the dependencies with doubles.  There's less concern in the method on items that are unrelated to the method itself (e.g., connecting network connections to the database worker, which probably belongs in the `DatabaseConnectionPool` or the `DatabaseWorker` class itself, certainly not in the `getNumGiraffesInZoo` method.  We could go a bit further and move all of the database workings to their own class, wrapping them all up so that only the important parts are visible to this method:

```java
public int getNumGiraffesInZoo(AnimalDatabaseWorker adbw) {
    int numGiraffes = 0;
    try {
        numGiraffes = adbw.getNumAnimals("giraffe");
    } catch (DatabaseException dbex) {
        numGiraffes = -1;
    }
    return numGiraffes;
}
```

We have now reduced the number of dependencies to that single class `AnimalDatabaseWorker`, and only call one method on it.

The second concept is to ensure that everything you do is repeatable.  What you don't want is a test which works fine sometimes.  If there is a failure, you should know about it immediately.  If there is not a failure, you do not want to have to have false alarms.

You can make a test repeatable by ensuring that all of the values that it depends on are able to be replicated.  This is one of the (many, many, many) reasons that global variables are, in general, a Bad Idea.  Let's consider testing the following method:

```java
public int[] sortList() {
    if (_sortingAlgorithm == "MergeSort") {
        return mergeSort(_list);
    } else if (_sortingAlgorithm == "QuickSort") {
        return quickSort(_list);
    } else {
        if (getRandomNumber() % 2 == 0) {
            return bubbleSort(_list);
        } else {
            return bogoSort(_list);
        }
    }
}
```

What happens when we run this?  The execution flow first depends on two variables that were not passed in, so we'd need to add additional checks to make sure that they're the right value before testing if we want to make sure the test is repeatable.  Who knows what other code has modified `_list` and `_sortingAlgorithm`?  Even if we ensure ahead of time that all the variables are set correctly, how can we test what happens when `_sortingAlgorithm` is set to something other than "MergeSort" or "QuickSort"?  The method will call either `bubbleSort()` or `bogoSort()`, and there is no (reasonable) way for us to tell which one will be called ahead of time.  If there is an error with `bubbleSort()` but not `bogoSort()`, then the test might randomly fail.

In order to test effectively, code should be segmented, and written in such a way that ensuring that the exact same code with the exact same values can be run multiple times.  If this is the case, then the exact output should occur each time, and tests will not randomly fail.  Write your code in such a way as to discourage random test failures, not encourage them.

## Provide a Scriptable Interface

While the previous section described how to ensure code is testable from a white-box perspective, creating a program that is easily testable from a black-box, systems-level perspective can be even more difficult.  Methods, in general, are meant to be called---there's a built-in interface to do it, and the parameters for each method are generally specified. Entire systems, however, may do many things on their own with only occasional outside influence.

Some interfaces are "automatically" scriptable.  If you are writing a web app, for example, you can use a web testing framework such as Selenium or Capybara to access the various pages, click buttons, enter text, and do all of the other things you can do to a web page.  Text-based interfaces are also relatively easy to script, since they simply accept text and output it.  Output can be redirected to a file or otherwise checked for accuracy.

Programs which do not provide a method of scripting by virtue of their interface, such as native GUI applications, will ideally have some sort of scripting built into them.  This can be done via __test hooks__, or "hidden" methods which provide a way to input data or receive information about the program.  These are externally accessible, perhaps with a key or other security measure, but usually not publicly advertised.

There are several downsides to adding hooks to your program, or any sort of scriptable interface.  It's a security risk, for one thing---if someone discovers how to access the test hooks, they may be able to determine hidden information, overwrite data, or perform other malicious actions (or simply be curious and make a mistake).  Adding a scriptable interface will require additional complexity in the program, as well as additional program length and size.  The interface may be a drag on performance.

Worse, scripts may give you a false sense of security.  Adding a separate way to access user-facing functionality means that you will need to have (at least) two parallel ways to use the system.  In some cases, functionality that works perfectly fine via the scriptable interface will not work when accessing it the "normal" way.

Time spent working on a scripting interface also means less time spent writing other features of the software, or improving its quality.  This trade-off may be worthwhile, but it should be considered on a project-by-project basis.

You can test graphical and other non-text interfaces without test hooks, but it will tend to be much more difficult.  Writing code to directly interface with something is often the easiest and most direct route.  There are programs which allow you to directly manipulate the cursor, take screenshots of the result, and perform other interface interaction which is not scripted.  However, these tools are often finicky and require some degree of manual verification.

## Write Tests Up-Front

Ideally, you should be using the TDD paradigm, or something similar.  Even if you are not using TDD, you should be writing lots of tests at approximately the same time as you are writing code.  You will quickly realize when the code you have written cannot be tested, and you won't continue going down long pathways writing code which you will have trouble testing "later".  Note that "later" often means "never".

The longer you go on writing code without writing tests for it, the more likely you are to make code which is difficult to test.  Even if you mean to make it testable, you often won't realize that what you wrote will be difficult to test for one reason or another.  Actually writing the tests gives you confirmation that you are going down the right path.

## DRYing Up Code

The acronym __DRY__ stands for "Don't Repeat Yourself", and it is a key tenet to making your code not only more testable, but better all around.  The trivial case of failing to keep code DRY is simply copy-and-pasting, often with a slightly different method name:

```java
public int[] sortAllTheNumbers(int[] numsToSort) {
    return quickSort(numsToSort);
}

public int[] sortThemThereNumbers(int[] numsToSort) {
    return quickSort(numsToSort);
}
```

In case you think this is a ludicrous contrived example, I have personally seen---and fixed---code like this in the wild.  Although it seems obvious when they're right next to each other, duplicate code loves to hide in little nooks and crannies.  When you have a multi-thousand-line class, things like this happen.  A programmer needs a method and quickly adds one before searching if another exists, someone writes a "test" version of the method, then forgets that there was an original one, or someone just started copying and pasting from some code they found online.  If everything continues to work fine, then there is very little impetus to look for issues like this, let alone fix them.  Even if someone does notice it, modifying it may be seen as out of scope for whatever they are working on.  It would almost certainly require refactoring other code that depends on it.  However, not having your code DRY means more tests, more danger when refactoring, and just plain redundancy.  Deciding on one method to use instead of having multiple copies will save you time and energy in the long run, as you reduce the ancillary associated costs of extra unit tests and confusion on the part of future maintainers.

What happens when you decide that you are no longer going to use the `quickSort()` method, or decide to support floating-point numbers in addition to integers?  You will have to make changes in two (or more... there's practically no limit to how many times code can be copied and pasted) places, which can be easy to forget to do.  That's double the room for error, or for doing it slightly differently in one place than the other.  Testing for these kind of things can be difficult.  Remove duplicate code sooner rather than later.

While the duplicated method above is a simple example, you may have more complex cases of code duplication.  Any time you find yourself with repeated code, even if it's in the middle of a statement, it may be a good idea to put it into its own method.  Consider the following SQL code which must be called by each of these methods in order to determine how many of a particular kind of breed exists in the database:

```java
public int getNumberOfCats(String catBreed) {
    int breedId = DatabaseInterface.execute(
      "SELECT BreedID FROM CatBreeds WHERE BreedName = " + catBreed);
    int numCats = DatabaseInterface.execute(
      "SELECT COUNT(*) FROM Cats WHERE BreedID = " + breedId);
    return numCats;
}

public int getNumberOfPigeons(String pigeonBreed) {
    int breedId = DatabaseInterface.execute(
      "SELECT BreedID FROM PigeonBreeds WHERE BreedName = " + pigeonBreed);
    int numPigeons = DatabaseInterface.execute(
      "SELECT COUNT(*) FROM Pigeons WHERE BreedID = " + breedId);
    return numPigeons;
}
```

While the statements aren't exactly the same, they are similar enough that they are a target for DRYing up the code.  After all, we can always pass in parameters to tweak the behavior to exactly what we want:

```java
public int getNumAnimals(String animalType, String breed) {
    int breedId = DatabaseInterface.execute(
      "SELECT BreedID FROM " + animalType + "Breeds WHERE BreedName = " + breed);
    int numAnimals = DatabaseInterface.execute(
      "SELECT COUNT(*) FROM " + animalType + "s WHERE BreedID = " + breedId);
    return numAnimals;
}

public int getNumberOfCats(String catBreed) {
    return getNumAnimals("Cat", catBreed);
}

public int getNumberOfPigeons(String pigeonBreed) {
    return getNumAnimals("Pigeon", pigeonBreed);
}
```

The code is now going to be much more flexible and maintainable.  Supporting additional animals will only require ensuring that we add methods that call `getNumAnimals()` with the appropriate parameters.  Directly testing the database, which can be challenging, is now restricted to one particular method.  More effort and energy can be focused on testing the more abstracted method instead of spreading out the testing on multiple methods with broader responsibilities.

## Dependency Injection

As we've seen, having dependencies hard-coded into your methods can make them difficult to test.  Let's assume you have a class-level reference to a `Duck` object that is created by a `Pond` object.  The `Pond` class has a `sayHi()` method which will say "Hi!" to all of the animals in the pond:

```java
public class Pond {

    Duck _d = new Duck();

    Otter _o = new Otter();

    public void sayHi() {
        _d.say("Hi!");
        _o.say("Hi!");
    }

}
```

How can you test this?  There's no easy way to verify that the duck got the "Hi" message.  __Dependency injection__ allows you to avoid this problem.  Although this is an excellent term to use to sound knowledgeable about testing, it's actually a very simple concept that you have probably seen in practice already.  In a nutshell, dependency injection means passing the dependencies in as parameters to a method, as opposed to having hard-coded references to them.  This will make it much easier to pass in test doubles or mocks.

Let's re-write the above `Pond` class allowing for dependency injection:

```java
public class Pond {

    Duck _d = null;

    Otter _o = null;

    public Pond(Duck d, Otter o) {
        _d = d;
        _o = o;
    }

    public void sayHi() {
        _d.say("Hi!");
        _o.say("Hi!");
    }

}
```

Note that you are passing in the Duck and the Otter in the constructor, thus moving the responsibility for creating them outside of the Pond class.  Because of this, you don't have to send in actual ducks and otters. Instead you can send in mocks, which can then verify that the `say()` method was called with the parameter "Hi!"  Dependency injection will allow you to test your methods much more easily than having dependencies automatically created.

## TUFs and TUCs

TUFs are __test-unfriendly features__, whereas TUCs are __test-unfriendly constructs__.  TUFs are software features that are by their nature difficult to test.  For example, writing to a database requires either some well-thought-out test doubles or dependence on many external factors (database drivers, the database itself, disk usage, etc.)  Other test-unfriendly features would be communicating over the network, using a windowing library, or writing to disk.  In all of these cases, if you do not provide appropriate doubles, it will be very difficult to test these features.  If you do provide doubles, then it only becomes "moderately difficult".

Test-unfriendly constructs are constructs in code where testing is by nature difficult.  In Java, this would include private methods (which require the Reflection API to directly access), constructors, or final methods.  It can be difficult to override the appropriate methods or provide test doubles easily in many TUCs.  This is an additional layer of complexity which, when combined with a test-unfriendly feature, can make your tests convoluted and difficult to reason about.

One method for keeping the complexity of your tests to a reasonable level is to not put TUFs inside of TUCs.  Move them to their own methods, or otherwise segment and design the code so that TUCs contain only minimal and easily-tested code.

## Dealing with Legacy Code

Not everybody has had the opportunity to read an excellent book with a chapter on writing testable code.  Many existing codebases were written by people who were not familiar with modern software engineering techniques, either through ignorance or because the techniques were not common when the code was written.  It would be foolishly optimistic to expect that people writing some FORTRAN IV code in 1966 would be using testing techniques which did not become common until the '90s.  Code that is already existing in production, but which does not follow modern software engineering best practices and often has substandard---or even no---automated test coverage, is known as __legacy code__.

Long story short---working with legacy code is difficult.  There is no getting around it.  You will be missing many of the benefits of a good testing suite; you may not be able to interact with the original developers if there are issues, ambiguities, or undocumented code; and it may be difficult to understand outdated code written in an older style.  However, it is often necessary, especially when you are working for a company which has been writing code for a long time.  It wouldn't make sense to rewrite millions of lines of code every time you want to add a new feature to the software suite you are already using.

When you find yourself having to work with legacy code, one of the most important things you can do is create some __pinning tests__.  Pinning tests are automated tests, usually unit tests, which check for the existing behavior of the system.  Note that the existing behavior is not always the expected, or correct, behavior.  The goal of a pinning test is to simply see how the program reacts before you make any changes to it.  Oftentimes, those weird, unexpected edge cases are actually used by users of the system.  You do not want to unintentionally modify them when adding a feature, unless it is something that you have explicitly set out to do.  Making unintentional changes to a program can be dangerous for yourself and for users of the program.  Note that this does not mean that you simply ignore the fact that the program is not working correctly.  However, fixing it should be seen as a different process than creating the pinning test.

When working with legacy code, you want to be very explicit about the features you are introducing and defects you are fixing.  It's very easy to start fixing every error you see, but this can easily get you into trouble.  You start forgetting what you came in to fix in the first place, you aren't focused on one thing, and you make changes in massive clumps instead of the "one step at a time" pace you should be going.

Personally, I like to keep a little text file open which has changes that I would like to make in the future, but are beyond the scope of what I am fixing at the moment.  For example, I may be editing a class to add a new method.  I note that there are numerous magic numbers in the file, which are not defined anywhere.  I may make a note to refactor this class later.  This doesn't mean that I won't use appropriate, well-named constants in the method that I have added.  However, if I tried to fix everything in the file as I was going along, I may spend three times as long than if I just did the minimum of what I needed to do.  This may not be a good use of my time.

Doing the minimum isn't normally considered a great way to go through life, but oftentimes when writing software it is.  You want to make small changes to the codebase, little incremental steps, because large changes are fraught with hard-to-find errors.  If you have a 10,000-line code change, and something goes wrong, trying to look through all of that will be a nightmare.  However, let's say you make a thousand 10-line code changes.  At each point, you can run all of the unit tests and see if the problem manifests itself.  Tracking problems down via tiny steps is much easier than tracking it down in one giant commit.

Additionally, doing the minimum can ensure that you are using your time wisely.  It may not be worth your time to add documentation to every method in the class you're working on, especially if you don't think it will be modified again anytime in the near future.  It's not that it's not a good idea, but perhaps not a good prioritization of your time.  Remember that you have a limited amount of time available not just on this Earth, but for completing a project.  Spend too much time on the wrong things, and it doesn't get done.  While it's an honorable drive to want to fix all of the problems you see in the codebase, the dirty little secret of the software industry is that there is all sorts of ugly code running behind the scenes, and most of the time, it works.  Your favorite bank has thousands of `GOTO` statements in its transfer code.  Your favorite three-letter government agency has hundreds of thousands of lines of code that no unit test has ever laid eyes (or mocks or stubs) on.  It is okay to cry about the state of the world, but sometimes you just need to let it be.

If possible, you want to start your search for code to modify by looking for __seams__.  Seams are locations in code where you can modify _behavior_ without modifying _code_.  This is probably easiest to see with examples.  In this first method, there is no seam---there is no way to modify how the program behaves without modifying some code in the method:

```java
public void createDatabaseTable() {
    DatabaseConnection db = new DatabaseConnection(DEFAULT_DB);
    int status = db.executeSql("CREATE TABLE Cats "
        + "(CatID int, Name varchar(255), Breed varChar(255));");
}
```

If you want to change the database this code will update, you will need to modify the constant `DEFAULT_DB` or otherwise change the line of code.  There's no way to pass in a test double for it, or otherwise use a fake database in your test.  If you want to add a column, you will need to edit the string that is inside the method.  Now let's compare this to a method which is a seam:

```java
public int executeSql(DatabaseConnection db, String sqlString) {
    return db.executeSql(sqlString);
}
```

If you want to use a doubled database connection, just pass it in when calling the method.  If you want to add a new column, you can just modify the string.  You can check for edge or corner cases, like seeing if an exception is thrown or a particular error status is returned, even if the original documentation is missing.  Since you can explore these cases without directly modifying any code, it will be infinitely easier to determine that you haven't broken anything when writing your tests.  After all, you haven't modified anything!  If you had to manually edit the code to observe the behavior, as in the first example, any original results you got from pinning tests would be suspect.  Code modification would usually not be as straightforward as the example above.  You may have to edit multiple methods, and you would never be entirely sure if the results you see were because that is the way the code executed originally, or if it was a consequence of the edits you made while trying to test it.

Note that having a seam does not mean that the code is good in other ways.  After all, being able to pass in arbitrary SQL to be executed is a pretty big security risk if it hasn't been sanitized elsewhere.  Writing code with too many seams may be overkill, and will certainly make the codebase larger.  Seams are simply where you can start figuring out how the system currently responds to inputs.  Finding seams will allow you to easily start writing comprehensive pinning tests, to ensure that you are catching a variety of edge cases.

Perhaps most importantly, it is important from a psychological point of view not to "give in" to the codebase and sink to its level.  Just because there are no unit tests for a feature does not mean that it's fine to make some modifications without adding unit tests.  If the code for a particular class is uncommented, that shouldn't give you _carte blanche_ to not comment the code that you add.  As it evolves, code tends to sink towards the lowest common denominator.  You need to actively fight that de-evolution.  Write unit tests, fix things up as you find them, and document code and features appropriately.

If you are interested in more details about how to work with legacy code, there are (at least) two great books on the topic.  The first is _Refactoring: Improving the Design of Existing Code_ by Martin Fowler, and the other is _Working Effectively with Legacy Code_ by Michael Feathers.  The latter is an especially valuable resource for working with code that does not already have a comprehensive test suite.

One of the things I find so interesting about software engineering is that it is a field which is changing extremely quickly.  Many of the techniques that I discuss will probably seem as quaint as `GOTO` statements and line numbers do at the time of the writing of this book.  If this is the case, then please be gentle in your criticism half a century hence.

## Conclusion

Software testing is not only about writing the tests, but writing code which will enable the tests to be written easily and well.  In today's world of software engineering, writing code and writing tests are not mutually exclusive.  Many---I would daresay venture to say "most"---developers write their own unit tests along with their code, and many testers will have to write code to automate their tests or develop testing tools.  Even if you do not write code in your role as a tester, you may be asked to review it or to determine how to have more comprehensive automated testing for a software system.

