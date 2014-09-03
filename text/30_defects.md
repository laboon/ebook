## Defects

#### What is a Defect?

#### Reporting a defect

#### A Standardized Defect Template

This is not an industry standard defect reporting template, but I have found it to be very useful.  It ensures that all of the major aspects of a defect, once found, have been reported.  By acting as a kind of checklist for what to put down for a defect, it helps to ensure that a tester will not forget anything (that big blank space after a line has a way of showing up in most people's visual fields).  Note that although there is no identifier specifically listed here, it will often be automatically added by whatever defect tracking software you are using.  If you are not using defect tracking software, and don't want to use defect  tracking software, then you can add an IDENTIFIER field.

Without further ado, here is the template.

```
SUMMARY:
DESCRIPTION:
REPRODUCTION STEPS:
EXPECTED BEHAVIOR:
OBSERVED BEHAVIOR:
IMPACT:
SEVERITY:
NOTES:
```

##### Summary

The __summary__ is a one sentence or so summary of the defect found.  It's useful when people are scrolling through long lists of defects, or to make sure that people understand the gist of the defect.  Some examples of good summaries would be:

1. Page background is red, should be blue
2. In-app calculator displays -1 for result of sqrt(-1)
3. System exits with "SEGFAULT" error when user selected
4. Adding three or more items to a cart at the same time clears out other items

All of these describe the problem succinctly, without going into much detail.

##### Description



##### Reproduction Steps

##### Expected Behavior

##### Observed Behavior

##### Impact

##### Severity

##### Notes

I like to think of this field as the "miscellaneous" field.  It's where everything which might be useful for tracking down a bug, or that may or may not be releant, goes.  It is also a good place for putting data which is too long to fit in any of the other sections, which should be relatively short and easy to grasp for developers, managers, other testers, and anyone else who may have to look at this defect and try to understand it, but may not be as familiar with that section of the software as the original tester.

#### Tracking, triaging, and prioritizing defects
