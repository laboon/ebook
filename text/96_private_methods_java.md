# Using Reflection to Test Private Methods in Java

In Java, there's no way to directly call private methods from a unit test, although this is definitely not the case in other languages (such as with Ruby's `.send(:method_name)` method, which bypasses the concept of "private" entirely).  However, using the reflection library, we can "reflect" what the structure of the class is at runtime.  The reflection library is built into the Java language, so you don't need to install anything else to use it.

Let's give an example---if you've never worked with reflection before, it can be a bit strange.  Say we want to write a class which tells the user what methods are available in that class.  Without reflection, this is impossible in Java; how can you know what other methods exist without hard-coding them into a String or something along those lines?  It's actually relatively simple to do using reflection:

```java
import java.lang.reflect.Method;

public class ReflectionFun {

    public void printQuack() {
        System.out.println("Quack!");
    }

    public static void main(String[] args) {
        Method[] methods = ReflectionFun.class.getMethods();

        // Get all methods from class and any from superclasses callable
        // on this class.
        System.out.println("All methods:");
        for (Method method : methods){
            System.out.println(method.getName());
        }
    }

}
```

When we run this program, we get the following output:

```
All methods:
main
printQuack
wait
wait
wait
equals
toString
hashCode
getClass
notify
notifyAll
```

We can then do things like check to see if a method exists before calling it, or let the programmer know what methods exist.  If you've ever used a language like Ruby, where you can quickly check what methods are available on an object, you can see how useful this can be.  If you're new to a codebase, and you know that you want to do something related to quacking, but you're not sure if the method you want to call is named `displayQuack()`, or `quackify()`, or `quackAlot()`, or whatever, you can do a quick method listing and see that the method you are looking for is `printQuack()`.

You may have noticed that there are many more methods here than are listed in the `ReflectionFun` class.  This is because the `getMethods()` method returns a list of _all_ methods callable on an object (that is, public methods, we will see how to get private methods soon).  Since all objects in Java descend from the `Object` class, any of the public methods on the `Object` class will also appear here.

You'll also note that there are three different `wait` methods listed.  This is simply because Java considers methods with the same name but different argument lists as different methods.  Reviewing the Java API, we can see that the following three methods exist:

```java
public void wait();
public void wait(long timeout);
public void wait(long timeout, int nanos);
```

Reviewing the code above, you can see that the `methods[]` array actually contains methods as objects!  This may not seem strange in a functional language, but if you are only used to straightforward Java programming, it may seem a bit weird.  If the concept of a method or function existing as a first-class object seems strange to you, my first recommendation is to learn Haskell or another functional programming language until it seems like second nature.  If you don't particularly have the time for that, just think of them as functions that you can pick up and carry around, then do something with them at a later date, instead of having them only be in one place.

Now that we have this list of methods, we can actually invoke them by name, by passing in the name of the method to which we'd like to have a reference to the `getMethod()` method:

```java
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionFun {

    public void printQuack() {
        System.out.println("Quack!");
    }

    public static void main(String[] args) {
        try {
            System.out.println("Call public method (printQuack):");
            Method method = ReflectionFun.class.getMethod("printQuack");
            ReflectionFun rf = new ReflectionFun();
            Object returnValue = method.invoke(rf);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
            System.err.println("Failure!");
        }
    }

}
```

This displays:

```
Call public method (printQuack):
Quack!
```

Using this, you could add a way to manually test and call methods, by having the user enter a string and trying to call a method by that name on the object.  We now have run-time control of what methods to call.  This is very useful for metaprogramming and programmer interfaces such as REPLs (read-eval-print-loop systems, which let you enter some code, see the results, and repeat).  Now that you understand reflection, it only takes some minor tweaks to our existing code for us to be able to easily access and test private methods.

You can't use the `getMethod()` or `getMethods()` methods, as they only return publicly available methods.  Instead, you need to use either the `getDeclaredMethod()` or `getDeclaredMethods()` methods.  These have two key differences from the `getMethod()`-style methods listed above:

1. They only return methods declared in that specific class.  They will not return methods defined in superclasses.
2. They return public, private, and protected methods.

Therefore, if we wanted a list of _all_ methods defined on `ReflectionFun`, we could use the `getDeclaredMethods()` method.  Just for fun, let's also add a private `printQuock()` method to go along with our public `printQuack()` method (my definition of "fun" may be slightly different than yours):

```java
import java.lang.reflect.Method;

public class ReflectionFun {

    public void printQuack() {
        System.out.println("Quack!");
    }

    private void printQuock() {
        System.out.println("Quock!");
    }

    public static void main(String[] args) {
        System.out.println("Declared methods:");
        Method[] methods = ReflectionFun.class.getDeclaredMethods();
        for(Method method : methods){
            System.out.println(method.getName());
        }
    }

}
```

The output of this program is:

```
Declared methods:
main
printQuack
printQuock
```

We once again have a list of Method objects, and we can now invoke them.  There's only one small snag---we first need to set that method to "accessible" before calling it, using the `setAccessible()` method.  It accepts a Boolean parameter to determine whether or not the method should be accessible outside the class:

```java
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionFun {

    public void printQuack() {
        System.out.println("Quack!");
    }

    private void printQuock() {
        System.out.println("Quock!");
    }

    public static void main(String[] args) {
        try {
            System.out.println("Call private method (printQuock):");
            ReflectionFun rf = new ReflectionFun();
            Method method2 = ReflectionFun.class.getDeclaredMethod("printQuock");
            method2.setAccessible(true);
            Object returnValue = method2.invoke(rf);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
            System.err.println("Failure!");
        }
    }

}
```

This will output:

```
Call private method (printQuack):
Quock!
```

We can combine this with the other unit testing we've learned earlier in the chapter to write a unit test for a private method:

```java
public class LaboonStuff {
    private int laboonify(int x) { return x; }
}

@Test
public void testPrivateLaboonify() {
    try {
        Method method = LaboonStuff.class.getDeclaredMethod("laboonify");
        method.setAccessible(true);
        LaboonStuff ls = new LaboonStuff();
        Object returnValue = method.invoke(ls, 4);
        int foo = ((Integer) returnValue).intValue();
        assertEquals(4, foo);
    } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException ex) {
        // The method does not exist
        fail();
    }
}
```

It is plain to see that testing even simple private methods can include quite a bit of boilerplate code.  In many cases, you will probably want to wrap the private method access code in a separate helper method.
