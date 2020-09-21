package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode(null);
		tail = new LLNode(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element The element to add
	 */
	public boolean add(E element) {
		if (element == null) {
			throw new NullPointerException("Element cannot be null");
		}

		LLNode<E> item = new LLNode(element);
		LLNode<E> currentLastItem = tail.prev;
		currentLastItem.next = item;
		item.next = tail;
		item.prev = currentLastItem;
		tail.prev = item;
		size++;
		print();
		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E get(int index) {
		LLNode<E> current = getItem(index);
		return current.data;
	}

	private LLNode getItem(int index) {
		if (size == 0 || index > size -1 || index < 0) {
			throw new IndexOutOfBoundsException("List is empty");
		}

		LLNode item = head;
		for (int i = 0; i < index + 1; i++) {
			item =  item.next;
			if (item.data == null) {
				throw new IndexOutOfBoundsException("Item not found at index " + index);
			}
		}
		
		return item;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The     index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element) {
		LLNode item = getItem(index);
		LLNode newItem =  new LLNode(element);
		LLNode prevItem = item.prev;
		prevItem.next = newItem;
		newItem.prev = prevItem;
		newItem.next = item;
		item.prev =  newItem;
		size++;
		
	}

	/** Return the size of the list */
	public int size() {
		// TODO: Implement this method
		return size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		LLNode item = getItem(index);
		LLNode prevItem = item.prev;
		LLNode nextItem  = item.next;
		prevItem.next  =  nextItem;
		nextItem.prev = prevItem;
		size--;
		return (E)item.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index   The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) {
		LLNode item = getItem(index);
		LLNode newItem =  new LLNode(element);
		newItem.next = item.next;
		newItem.prev = item.prev;
		item.next.prev = newItem;
		item.prev.next =  newItem;
		return (E)item.data;
	}
	
	public void print() {
		LLNode currentItem = head;
		for(int i=0; i< size; i++) {
			currentItem = currentItem.next;
		}
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	@Override
	public String toString() {
		return  "data=" + data + " prev:" + prev + " next:" + next;
	}

}
