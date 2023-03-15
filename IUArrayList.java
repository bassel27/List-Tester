import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// TODO: exceptions
/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int NOT_FOUND = -1;

    private T[] array;
    private int rear;
    private int modCount; // for the iterator to check if array has changed

    /** Creates an empty list with default initial capacity */
    public IUArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates an empty list with the given initial capacity
     * 
     * @param initialCapacity
     */
    @SuppressWarnings("unchecked")
    public IUArrayList(int initialCapacity) {
        array = (T[]) (new Object[initialCapacity]);
        rear = 0;
        modCount = 0;
    }

    /** Double the capacity of array */
    private void expandCapacity() {
        array = Arrays.copyOf(array, array.length * 2);
    }

    @Override
    public void addToFront(T element) {
        if (!isAddable()) {
            expandCapacity();
        }
        for (int i = rear; i > 0; i--) {
            array[i] = array[i - 1];
        }
        array[0] = element;
        rear++;
        modCount++;
    }
    /**
     * Checks if the list has the suitable capacity to add a new element
     * @return true if the list has the suitable capacity to add a new element
     */
    public boolean isAddable() {
        if (rear >= array.length) {
            return false;
        }
        return true;
    }

    @Override
    public void addToRear(T element) {
        if (!isAddable()) {
            expandCapacity();
        }
        array[rear] = element;
        rear++;
        modCount++;
    }

    @Override
    public void add(T element) {
        addToRear(element);
    }

    @Override
    public void addAfter(T element, T target) {
        int targetIndex = indexOf(target);
        if (targetIndex == -1)
            throw new NoSuchElementException();
        for (int i = rear; i > targetIndex + 1; i--) {
            array[i] = array[i - 1];
        }
        array[targetIndex + 1] = element;
        rear++;
        modCount++;
    }

    @Override
    public void add(int index, T element) {
        if (index > rear || index < 0)
            throw new IndexOutOfBoundsException();
        if (!isAddable())
            expandCapacity();
        for (int i = rear; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = element;
        rear++;
        modCount++;
    }

    @Override
    public T removeFirst() {
        if (size() == 0)
            throw new NoSuchElementException();
        modCount++;
        return remove(0);
    }

    @Override
    public T removeLast() {
        if (size() == 0)
            throw new NoSuchElementException();
        T retVal = array[rear - 1];
        array[rear - 1] = null;
        rear--;
        modCount++;
        return retVal;
    }

    @Override
    public T remove(T element) {
        int index = indexOf(element);
        if (index == NOT_FOUND)
            throw new NoSuchElementException();

        T retVal = array[index];

        rear--;
        // shift elements
        for (int i = index; i < rear; i++) {
            array[i] = array[i + 1];
        }
        array[rear] = null;
        modCount++;
        return retVal;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= rear)
            throw new IndexOutOfBoundsException();
        T retVal = array[index];
        for (int i = index; i < rear - 1; i++) {
            array[i] = array[i + 1];

        }
        array[rear - 1] = null;
        rear--;
        modCount++;
        return retVal;
    }

    @Override
    public void set(int index, T element) {
        if (index < 0 || index >= rear)
            throw new IndexOutOfBoundsException();
        array[index] = element;
        modCount++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= rear)
            throw new IndexOutOfBoundsException();
        return array[index];
    }

    @Override
    public int indexOf(T element) {
        int index = NOT_FOUND;

        if (!isEmpty()) {
            int i = 0;
            while (index == NOT_FOUND && i < rear) {
                if (element.equals(array[i])) {
                    index = i;
                } else {
                    i++;
                }
            }
        }

        return index;
    }

    @Override
    public T first() {
        if (isEmpty())
            throw new NoSuchElementException();
        return array[0];
    }

    @Override
    public T last() {
        if (isEmpty())
            throw new NoSuchElementException();
        return array[rear - 1];
    }

    @Override
    public boolean contains(T target) {
        return (indexOf(target) != NOT_FOUND);
    }

    @Override
    public boolean isEmpty() {
        return rear == 0;
    }

    @Override
    public int size() {
        return rear;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> iterator = iterator();
        stringBuilder.append("[");
        while(iterator.hasNext()){
            stringBuilder.append(iterator.next() + ", ");
        }
        if(stringBuilder.length() > 2) stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new ALIterator();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int startingIndex) {
        throw new UnsupportedOperationException();
    }

    /** Iterator for IUArrayList */
    private class ALIterator implements Iterator<T> {
        private int nextIndex;
        private int iterModCount;
        private int prevNextIndex;

        public ALIterator() {
            nextIndex = 0;
            iterModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            if (iterModCount != modCount)
                throw new ConcurrentModificationException();
            return nextIndex != rear;
        }

        @Override
        public T next() {
            if (iterModCount != modCount)
                throw new ConcurrentModificationException();
            if (nextIndex == rear)
                throw new NoSuchElementException();
            T retVal = array[nextIndex];
            nextIndex++;
            return retVal;
        }

        @Override
        public void remove() {
            if (iterModCount != modCount)
                throw new ConcurrentModificationException();
            if (nextIndex == 0 || prevNextIndex == nextIndex)
                throw new IllegalStateException();
            // shift elements
            for (int i = nextIndex - 1; i < rear; i++) {
                array[i] = array[i + 1];
            }
            array[rear] = null;
            rear--;
            nextIndex--;
            prevNextIndex = nextIndex;
            modCount++;
            iterModCount++;
        }
    }
}