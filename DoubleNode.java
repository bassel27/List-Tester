/** Double-linked node for double-linked data structures 
 * @author Bassel Abdulsabour
*/
public class DoubleNode<E> {
    private DoubleNode<E> next;
    private DoubleNode<E> prev;
    private E element;

    /**
     * Creates an empty node.
     */
    public DoubleNode() {
        next = null;
        prev= null;
        element = null;
    }

    /**
     * Creates a node storing the specified element.
     *
     * @param elem the element to be stored within the new node
     */
    public DoubleNode(E elem) {
        next = null;
        prev = null;
        element = elem;
    }

    /**
     * Returns the node that follows this one.
     *
     * @return the node that follows the current one
     */
    public DoubleNode<E> getPrev() {
        return prev;
    }

    /**
     * Sets the node that follows this one.
     *
     * @param node
     *             the node to be set to follow the current one
     */
    public void setPrev(DoubleNode<E> node) {
        prev = node;
    }

    /**
     * Returns the node that follows this one.
     *
     * @return the node that follows the current one
     */
    public DoubleNode<E> getNext() {
        return next;
    }

    /**
     * Sets the node that follows this one.
     *
     * @param node
     *             the node to be set to follow the current one
     */
    public void setNext(DoubleNode<E> node) {
        next = node;
    }

    /**
     * Returns the element stored in this node.
     *
     * @return the element stored in this node
     */
    public E getElement() {
        return element;
    }

    /**
     * Sets the element stored in this node.
     *
     * @param elem
     *             the element to be stored in this node
     */
    public void setElement(E elem) {
        element = elem;
    }

    @Override
    public String toString() {
        return "Element: " + element.toString() + " Has next: " + (next != null);
    }

}
