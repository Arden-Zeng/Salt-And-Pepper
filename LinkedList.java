
/**
 * Generic Linked List that only stores Meal objects.
 * @see Meal
 *
 */
public class LinkedList {
	private Node head;
	
	/**
	 * Linked List Constructor
	 */
	public LinkedList() {
		
	}
	
	/**
	 * Append a Meal object to the end of the Linked List.
	 * @param data the Meal object reference to be appended to the Linked List.
	 */
	public void add (Meal data) {
		if (head == null) {
			head = new Node(data);
		}
		else {
			Node current = head;
			while (current.next != null) {
				current = current.next;
			}
			current.next = new Node(data);
		}
	}
	
	/**
	 * Get the ith reference stored in the Linked List.
	 * @param index ith term. Indexing starts at 0.
	 * @return the ith Meal object.
	 */
	public Meal get (int index) {
		Node current = head;
		for (int i = 0; i < index; i ++) {
			current = current.next;
		}
		return current.data;
	}
	
		


	/**
	 * Getter method.
	 * @return the size of the Linked List. int value.
	 */
	public int size() {
		Node current = head;
		int size = 0;
		while (current!= null) {
			size ++;
			current = current.next;
		}
		return size;
	}
	
}
