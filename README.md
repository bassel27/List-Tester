 # List-Tester

  This program implements double-linked list based on the IndexedUnsortedList 
  interface and tests it to confirm it is valid and working.


 ## Compiling and Running

  From the directory containing all source files, compile the test
  class (and all dependent classes) with the command: 
  `$ javac *.java`

  Run the compiled SetTester class with the command:
  `$ java ListTester`

  Console output will report which tests IUDoubleLinkedList passed or failed.


 ## Program Design and Important Concepts

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


 ## Test-Driven Development

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

