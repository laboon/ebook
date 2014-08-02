## Property-Based Testing

#### The Story So Far

  Throughout most of this book, I've discussed how the key concept of testing was checking expected behavior against observed behavior.  It didn't matter if we were performing manual tests, unit tests, or integration tests, the same concept remained the bedrock principle of testing.  The tester created some input data or performed some operations on the system, and then checked the output value or state against what was expected to happen.

  This chapter is going to throw that concept a bit on its head.

#### The Problem with Testing as We Know It So Far

  Let's say that we are testing a new sort function, billSort.  It's twice as fast as any other sorting algorithm out there, but there are questions about its correctness, and so you have been tasked to test that it works in all cases.  What kind of input values would you test it with?  Assume the method signature looks like this:

```java
public int[] billSort(int[] arrToSort) {
  ...
}
```

We'd definitely want to pass in a wide variety of values that hit different base, edge, and corner cases.  Single-element arrays.  Zero-element arrays.  Arrays with negative integers.  Arrays that are already sorted.  Arrays that are sorted the opposite way as the sort works (ascending vs descending, or vice versa).  Very long arrays.  Arrays that contain multiple values, and arrays that consist of the same value repeated over and over again.  This doesn't even take into account the fact that Java is a statically typed language, so we don't have to worry about what happens when an array contains strings, or references to other arrays, or complex numbers, or a variety of other kinds of things.  If this sort were implemented in a dynamically typed language such as Ruby, we'd really have a lot to worry about.  Just considering the Java method above, though, let's think about some of the different inputs and their respective expected output values for this method:

```
[] => []
[1] => [1]
[1, 2, 3, 4, 5] => [1, 2, 3, 4, 5]
[5, 4, 3, 2, 1] => [1, 2, 3, 4, 5]
[0, 0, 0, 0] => [0, 0, 0, 0]
[9, 3, 1, 2] => [1, 2, 3, 9]
[-9, 9, -4, 4] => [-9, -4, 4, 9]
[3, 3, 3, 2, 1, 1, 1] => [1, 1, 1, 2, 3, 3, 3]
[-1, -2, -3] => [-3, -2, -1]
[1000000, 10000, 100, 10, 1] => [1, 10, 100, 10000, 1000000]
[2, 8, ... (many ints) ... -3, 7] => [-900, -874, ... (many ints) ... 989, 991]
```

Even without any additional wrinkles, there's an absolutely huge number of possible combinations of numbers to test.  That was just a taste.  There's even a huge number of equivalence cases to test, ignoring the fact there could be a problem with, say, a specific number; maybe only sorts with the number 5 don't work, for example.  Writing tests for all of these various kinds of input would be extremely tedious and error-prone.  How can we avoid having to write such a large number of tests?

#### Climbing The Abstraction Ladder

Why not hop up a rung on the abstraction ladder and instead of thinking about the specific values that you want as input and output, you think about the *properties* you'd expect of your input and output?  That way, you don't have to consider each individual test.  You can let the computer know that you expect all of the output to have certain properties, and what kind of values you expect as input, and let the computer write and execute the tests for you.

For example, what kinds of properties did all of the correct output values of the billSort method have, in relationship to the input values?  There are quite a few.  These properties should hold for all sorted lists.  Thus, they are called *invariants*.

Some invariants for a sort function would be:

1. The output array has the same number of elements as the input array
2. Every value in the output array corresponds to one in the input array
3. The value of each successive element in the output array is greater than or equal to the previous value
4. No element not in the input array is found in the output array
5. The function is idempotent; that is, running the sort method on a list, and then running the sort again on the output, should produce the same output array as just running it once
6. The function is pure; running it two times on the same input array should always produce the same output array

Now that we have some of the properties we expect from *any* output of the billSort method, we can let the computer do the grunt work of thinking up random arrays of data, passing them in to our method, and then checking that whatever output array is produced meets all of the properties that we set.  If an output array does not meet one of the invariants, we can then report the error to the tester.  Producing output that does not meet the specified invariant is called *falsifying the invariant*.

#### Example


#### 


#### History of Property-Based Testing

#### Benefits and Drawbacks

#### Code Examples

```haskell
Prelude> import Test.QuickCheck
Prelude Test.QuickCheck> import Data.List
Prelude Test.QuickCheck Data.List> let prop_sort_idempotent xs = (sort xs) == (sort (sort xs))
Prelude Test.QuickCheck Data.List> let prop_sort_length_equal xs = (length xs) == (length (sort xs))
Prelude Test.QuickCheck Data.List> let prop_sort_sum_bad xs = (sum (sort xs)) < 5
Prelude Test.QuickCheck Data.List> quickCheck prop_sort_idempotent
+++ OK, passed 100 tests.
Prelude Test.QuickCheck Data.List> quickCheck prop_sort_length_equal
+++ OK, passed 100 tests.
```

```haskell
Prelude Test.QuickCheck Data.List> quickCheck prop_sort_sum_bad
*** Failed! Falsifiable (after 13 tests and 6 shrinks):    
[5]
```

```haskell
Passed:  
[]
Passed: 
[]
Passed:  
[-1]
Passed:  
[]
Passed:  
[2,-2,-3]
Passed:  
[-4,-2,-1]
Passed:  
[]
Passed:  
[2,-2]
Passed:  
[-6,6,6,-8,-6,-7]
Passed:  
[]
Passed:   
[0,-8]
Passed:   
[-6]
Failed:   
[7,-10,11,10,2,-11]
*** Failed! Passed:                        
[]
Passed:                                        
[10,2,-11]
Failed:                                        
[7,-10,11]
Passed:                                     
[]
Passed:                                        
[-10,11]
Failed:                                        
[7,11]
Passed:                                      
[]
Failed:                                        
[11]
Passed:                                      
[]
Passed:                                        
[0]
Failed:                                        
[6]
Passed:                                      
[]
Passed:                                        
[0]
Passed:                                        
[3]
Failed:                                        
[5]
Passed:                                      
[]
Passed:                                        
[0]
Passed:                                        
[3]
Passed:                                        
[4]
Falsifiable (after 13 tests and 5 shrinks):    
[5]
```