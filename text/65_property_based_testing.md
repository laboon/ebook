## Property-Based Testing

#### Code Examples

```haskell
Prelude> import Test.QuickCheck
Prelude Test.QuickCheck> import Data.List
Prelude Test.QuickCheck Data.List> let prop_sort_idempotent xs = (sort xs) == (sort (sort xs))
Prelude Test.QuickCheck Data.List> let prop_sort_length_equal xs = (length xs) == (length (sort xs))
Prelude Test.QuickCheck Data.List> let prop_sort_sum_bad xs = (sum (sort xs)) < 5
Prelude Test.QuickCheck Data.List> quickCheck prop_sort_idempotent
Loading package array-0.4.0.1 ... linking ... done.
Loading package deepseq-1.3.0.1 ... linking ... done.
Loading package old-locale-1.0.0.5 ... linking ... done.
Loading package time-1.4.0.1 ... linking ... done.
Loading package random-1.0.1.1 ... linking ... done.
Loading package containers-0.5.0.0 ... linking ... done.
Loading package pretty-1.1.1.0 ... linking ... done.
Loading package template-haskell ... linking ... done.
Loading package QuickCheck-2.6 ... linking ... done.
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