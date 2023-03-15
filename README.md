****************
* Double-Linked List
* CS 221 - 3
* 11/6/2022
* Bassel Abdulsabour
**************** 


OVERVIEW:

 This program implements double-linked list based on the IndexedUnsortedList 
 interface and tests it to confirm it is valid and working.


INCLUDED FILES:

 IUDoubleLinkedList.java - source file
 IndexedUnsortedList.java - source file
 DoubleNode.java - source file
 ListTester.java - source file
 README.txt - this file


COMPILING AND RUNNING:

 From the directory containing all source files, compile the test
 class (and all dependent classes) with the command: 
 $ javac *.java

 Run the compiled SetTester class with the command:
 $ java ListTester

 Console output will report which tests IUDoubleLinkedList passed or failed.


PROGRAM DESIGN AND IMPORTANT CONCEPTS:

 IUDoubleLinkedList implements IndexedUnsortedList interface which is 
 an interface for an iterable, indexed, unsorted list ADT. IUDoubleLinkedList
 uses DoubleNode class which provides the structure of a node of a 
 double-linked list (element, next, previous). The double-linked list
 is just like a single-linked list plus a  refernce to the previous node 
 in addition to ListIterator which has more methods than the normal
  Iterator due to the additional ability to traverse the list from tail to head.

 ListTester confirms correct operation of any data structure that implements
 IndexedUnsortedList. In this case, it works on IUDoubleLinkedList and tests 
 all its methods. 


TESTING:

 ListeTester was the main testing mechanism of this program. ListTester's 
 test cases include all methods that can make a change to the list, 
 and it tests the result of each change scenario by running tests of all methods 
 on the resultant list. All test cases have the end result of 
 one- or two- or three-element lists.
 
 Both tests and test cases include Iterator and ListIterator.
 
 ListTester was written before IUDoubleLinkedList, so test-driven 
 development helped ensure that all functionality was being tested 
 from the start. I used LitTester while writing the code for 
 IUDoubleLinkedList.


DISCUSSION:
 
 This project was a bit harder than the other previous ones because
 it is huge, and it made me put everything I learned so far together.
 The number of test cases that I had to add for the ListIterator was 
 also huge, but it was easier than I thought becaue it is mostly just a 
 matter of copying and pasting with minor modifications.

 Moreover, I was a bit confused as to whether the cause of a test failure 
 is the change scenario or the the method being tested. However, I learned 
 that if many tests fail within the same scenario, that probably means that the 
 method of the change itself is wrong. On the other hand, if
 most tests of a change scenario are passing while a couple are 
 failing, then the issue is with these methods that the tests check.

 Now, I realize the imortance of test-driven development and the ease of
 building a data structure with the presence of a test class.


