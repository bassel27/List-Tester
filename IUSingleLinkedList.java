import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author Bassel
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private Node<T> head, tail;
	private int size;
	private int modCount;

	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		Node<T> newNode = new Node<T>(element);
		newNode.setNext(head);
		if (size() == 0) {
			tail = newNode;
		}
		head = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		Node<T> newNode = new Node<T>(element);
		if (size == 0) {
			head = newNode;
		} else {
			tail.setNext(newNode);
		}
		newNode.setNext(null);
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);
	}

	@Override
	public void addAfter(T element, T target) {
		Node<T> targetNode = head;
		while (targetNode != null && !targetNode.getElement().equals(target)) {
			targetNode = targetNode.getNext();
		}
		if (targetNode == null) {
			throw new NoSuchElementException();
		}

		Node<T> newNode = new Node<T>(element);
		newNode.setNext(targetNode.getNext());
		targetNode.setNext(newNode);
		if (targetNode == tail) {
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> currentNode = head;
		Node<T> newNode = new Node<T>(element);
		for (int i = 0; i < index - 1; i++) {
			currentNode = currentNode.getNext();
		}
		if (index == 0) {
			newNode.setNext(head);
			head = newNode;
			if (tail == null) {
				tail = newNode;
			}
		} else {
			newNode.setNext(currentNode.getNext());
			currentNode.setNext(newNode);
			if (index == size) {
				tail = newNode;
			}
		}
		size++;
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		T retval = head.getElement();
		head = head.getNext();
		if (size == 1) {
			tail = head;
		}
		size--;
		modCount++;
		return retval;
	}

	@Override
	public T removeLast() { // can't be O(1)
		T retVal;
		if (size() == 0) {
			throw new NoSuchElementException();
		} else if (size() == 1) {
			retVal = head.getElement();
			head = tail = null;
		} else {
			Node<T> currentNode = head;
			while (currentNode.getNext().getNext() != null) {
				currentNode = currentNode.getNext();
			}
			retVal = currentNode.getNext().getElement();
			tail = currentNode;
			tail.setNext(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {

		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		boolean found = false;
		Node<T> previous = null;
		Node<T> current = head;

		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}

		if (!found) {
			throw new NoSuchElementException();
		}

		if (size() == 1) { // only node
			head = tail = null;
		} else if (current == head) { // first node
			head = current.getNext();
		} else if (current == tail) { // last node
			tail = previous;
			tail.setNext(null);
		} else { // somewhere in the middle
			previous.setNext(current.getNext());
		}

		size--;
		modCount++;

		return current.getElement();
	}

	@Override
	public T remove(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		T retVal;
		if (index == 0) {
			if (head == tail) {
				tail = null;
			}
			retVal = head.getElement();
			head = head.getNext();

		} else {
			Node<T> currentNode = head;
			for (int i = 0; i < index - 1; i++) {
				currentNode = currentNode.getNext();
			}
			retVal = currentNode.getNext().getElement();
			currentNode.setNext(currentNode.getNext().getNext());
			if (index == size() - 1) {
				tail = currentNode;
			}
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> currentNode = head;
		for (int i = 0; i < index; i++) {
			currentNode = currentNode.getNext();
		}
		currentNode.setElement(element);
		modCount++;
	}

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
	public T get(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		Node<T> currentNode = head;
		for (int i = 0; i < index; i++) {
			currentNode = currentNode.getNext();
		}
		return currentNode.getElement();
	}

	@Override
	public int indexOf(T element) {
		int index = -1;
		Node<T> currentNode = head;
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
		Node<T> currentNode = head;
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
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> { // no <T> for SLLIterator because you'll cause a shadow generic
														// // we want the same T that the list has
		private Node<T> nextNode;
		private int iterModCount;
		private boolean canRemove;

		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount) // check if this iterator is allowed to exist
				throw new ConcurrentModificationException();
			return nextNode != null;
		}

		@Override
		public T next() {
			if (!hasNext()) { // checks concurrentmodexcpetion
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextNode = nextNode.getNext();
			canRemove = true;
			return retVal;
		}

		@Override
		public void remove() {
			if (iterModCount != modCount) // check if this iterator is allowed to exist
				throw new ConcurrentModificationException();
			if (!canRemove)
				throw new IllegalStateException();
			canRemove = false;
			if (size() == 1) {
				head = tail = null;
			} else if (head.getNext() == nextNode) { // remove head
				if (tail == head) {
					tail = nextNode;
				}
				head = nextNode;
			} else { // general case
				Node<T> previousPreviousNode = head;
				while (previousPreviousNode.getNext().getNext() != nextNode) {
					previousPreviousNode = previousPreviousNode.getNext();
				}
				previousPreviousNode.setNext(nextNode);
				if (nextNode == null) { // removing tail
					tail = previousPreviousNode;
				}
			}
			size--;
			modCount++;
			iterModCount++;
		}
	}
}