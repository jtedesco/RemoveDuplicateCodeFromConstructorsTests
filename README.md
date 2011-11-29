Remove Duplicate Code In Constructors Refactoring (Test Suite)
==============================================================

Summary
=======
The test suite for a Java refactoring to remove duplicate code in constructors by delegating work to other constructors in the class wherever possible.

Test Suite
==========

Our test suite thoroughly tests possible situations for our refactoring to handle. Our tests fall into two broad categories, namely invalid and valid tests, where we expect the refactoring to fail and succeed, respectively. Within each category, we have additional cateogires for tests.

1) Invalid Tests
	
   a) Test classes that contain no constructors
   b) Test classes that contain only one constructor
   c) Test classes that contain compile error in constructors
   d) Test classes that contain no duplicate code in constructors
   e) Test classes where assigned field contain dependencies
   f) Test classes where user parameters are invalid

2) Valid Tests

   a) Test classes that contain two constructors
   b) Test classes that contain three constructors
   c) Test classes where new constructor must be created
   d) Test classes where existing constructor must be used
   e) Test classes with user-provided parameters (helper method & custom access modifiers)