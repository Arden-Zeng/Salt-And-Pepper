
/**
 * Generic node object template. Used in LinkedList class.
 * @see LinkedList
 *
 */
public class Node {

	Node next;
	Meal data;
	
	/**
	 * Node object constructor.
	 * @param data Parameter field requesting a Meal object reference for the node to hold.
	 * @see Meal
	 */
	public Node (Meal data) {
		this.data = data;
	}
	
	
}
