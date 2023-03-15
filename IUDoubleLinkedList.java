import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// test case or test fail: toString() output
/**
 * Double-linked node-based implementation of IndexedUnsortedLIst
 * Fully supports basic iterator and ListIterator
 * 
 * @author Bassel Abdulsabour
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
    private DoubleNode<T> head, tail;
    private int size;
    private int modCount;

    public IUDoubleLinkedList() {
        head = tail = null;
        size = 0;
        modCount = 0;
    }

    @Override
    public void addToFront(T element) {
        ListIterator<T> lit = listIterator();
        listIterator().add(element);

    }

    @Override
    public void addToRear(T element) {
        ListIterator<T> iterator = listIterator(size);
        iterator.add(element);
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        int index = indexOf(target);
        if (index == -1) {
            throw new NoSuchElementException();
        }
        ListIterator<T> iterator = listIterator(index + 1);
        iterator.add(element);
    }

    @Override
    public void add(int index, T element) {
        ListIterator<T> lit = listIterator(index);
        lit.add(element);

    }

    @Override
    public T removeFirst() {
        ListIterator<T> lit = listIterator();
        T retVal = lit.next();
        lit.remove();
        return retVal;
    }

    @Override
    public T removeLast() {
        ListIterator<T> lit = listIterator(size);
        T retVal = lit.previous();
        lit.remove();
        return retVal;
    }

    @Override
    public T remove(T element) {
        ListIterator<T> lit = listIterator();
        T retVal = null;
        boolean foundIt = false;
        while (!foundIt) { // retVal.eqauls(element) if your object type is complex, it'd add a toll o
                           // fcheccking the condition
            retVal = lit.next();
            if (retVal.equals(element)) {
                lit.remove();
                foundIt = true;
            }
        }
        return retVal;
    }

    @Override
    public T remove(int index) {
        if (index >= size) { // because the listIterator checks if the index is negative
            throw new IndexOutOfBoundsException();
        }
        ListIterator<T> lit = listIterator(index);
        T retval = lit.next();
        lit.remove();
        return retval;
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<T> lit = listIterator(index);
        lit.next();
        lit.set(element);
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        ListIterator<T> lit = listIterator(index);
        return lit.next();
    }

    @Override
    public int indexOf(T element) {
        int index = -1;
        DoubleNode<T> currentNode = head;
        for (int i = 0; currentNode != null && index == -1; i++) {
            if (currentNode.getElement().equals(element)) {
                index = i;
            }
            currentNode = currentNode.getNext();
        }
        return index;
    }

    @Override
    public T first() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return head.getElement();
    }

    @Override
    public T last() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        boolean doesContain = false;
        DoubleNode<T> currentNode = head;
        for (int i = 0; currentNode != null && !doesContain; i++) {
            if (currentNode.getElement().equals(target)) {
                doesContain = true;
            }
            currentNode = currentNode.getNext();
        }
        return doesContain;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> iterator = iterator();
        stringBuilder.append("[");
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next() + ", ");
        }
        if (stringBuilder.length() > 2)
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public Iterator iterator() {
        return new DLLIterator(); // returns Iterator so the only methods that the user has access to are hasnext
                                  // next and remove
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DLLIterator();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        return new DLLIterator(startingIndex);
    }

    /**
     * This class implements ListIterator methods for a double linked list.
     */

    public class DLLIterator implements ListIterator<T> {
        private DoubleNode<T> nextNode; // next node also give you prevnode available
        private int iterModCount;
        private int nextIndex; // you don't need to have prevIndex if you keep track of nextIndex
        private DoubleNode<T> lastReturnedNode; // if your iterator's between two nodes, waht should you remove?

        /**
         * Initializes iterator that starts at index 0
         */
        public DLLIterator() {
            this(0); // reduces code duplication
        }

        /**
         * Initializes iterator before startingIndex
         * 
         * @param startingIndex index of the element that would be next
         * @throws IndexOutOfBoundsException if startingIndex <0 || startingIndex > size
         */
        public DLLIterator(int startingIndex) {
            if (startingIndex < 0 || startingIndex > size) {
                throw new IndexOutOfBoundsException();
            }
            if (startingIndex <= (size - 1) / 2) { // start from the beginning
                nextNode = head;
                nextIndex = 0;
                for (; nextIndex < startingIndex; nextIndex++) {
                    nextNode = nextNode.getNext();
                }
            } else {
                nextNode = tail;
                nextIndex = size - 1;
                if (startingIndex == size) {
                    nextIndex = size;
                    nextNode = tail.getNext();
                }
                for (; nextIndex > startingIndex; nextIndex--) {
                    nextNode = nextNode.getPrev();
                }
            }
            lastReturnedNode = null;
            iterModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextNode != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturnedNode = nextNode;
            T retVal = nextNode.getElement();
            nextNode = nextNode.getNext();
            nextIndex++;
            return retVal;
        }

        @Override
        public boolean hasPrevious() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextNode != head;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            if (nextNode == null) {
                nextNode = tail;
            } else {
                nextNode = nextNode.getPrev();
            }
            lastReturnedNode = nextNode;
            nextIndex--;
            return nextNode.getElement();
        }

        @Override
        public int nextIndex() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            if (iterModCount != modCount) { // this has highest priority and should be first because you ask if the
                                            // iterator is allowed to exist then check if it can do its job
                throw new ConcurrentModificationException();
            }

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }
            if (lastReturnedNode == head) {
                head = lastReturnedNode.getNext();
            } else { // executed if lastReturnedNode == tail
                lastReturnedNode.getPrev().setNext(lastReturnedNode.getNext());
            }
            if (lastReturnedNode == tail) {
                tail = lastReturnedNode.getPrev();
            } else { // executed if lastReturnedNode == head
                lastReturnedNode.getNext().setPrev(lastReturnedNode.getPrev());
            }
            if (lastReturnedNode == nextNode) { // last move was previous
                nextNode = nextNode.getNext();
            } else { // last move was next
                nextIndex--;
            }
            lastReturnedNode = null;
            size--;
            modCount++;
            iterModCount++;
        }

        @Override
        public void set(T newElement) {
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            if (lastReturnedNode == null) {
                throw new IllegalStateException();
            }
            lastReturnedNode.setElement(newElement);
            iterModCount++;
            modCount++;
        }

        @Override
        public void add(T e) { // should always work //adds to the left //always update newNode first //can't
                               // call remove after this
            if (iterModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            DoubleNode<T> newNode = new DoubleNode<T>(e);
            newNode.setNext(nextNode);
            if (nextNode != null) {
                newNode.setPrev(nextNode.getPrev());
                if (nextNode != head) {
                    nextNode.getPrev().setNext(newNode);
                } else {
                    head = newNode;
                }
                nextNode.setPrev(newNode);
            } else {
                newNode.setPrev(tail);
                if (tail != null) {
                    tail.setNext(newNode);
                } else {
                    head = newNode;
                }
                tail = newNode;
            }
            size++;
            modCount++;
            iterModCount++;
            nextIndex++;
            lastReturnedNode = null;

        } // no <T>

    }
}
