public class MySortedLinkedList extends MyLinkedList {

	/* TODO 
	   define the method
	   public void add(Comparable c)
	   that, given a Comparable (an interface type for all Object subclasses that define a compareTo() method), adds it to the 
	   list in sorted order.
	*/
	public void add(Comparable c) {
		Node nextNode = new Node();
		nextNode.data = c;

		if (this.size == 0) {
			nextNode.link = null;
			this.head = nextNode;
			this.tail = nextNode;
		} else if (c.compareTo(this.get(0)) <  0) {
			nextNode.link = head;
			head = nextNode;
		} else if (c.compareTo(this.get(this.size - 1)) >= 0) {
			Node current = head;

			for (int i = 0; i < this.size - 1; i++) {
				current = current.link;
			}

			current.link = nextNode;
			nextNode.link = null;
			tail = nextNode;
		} else {
			Node current = head;
			int count = 0;
			
			for (int i = 0; i < this.size; i++) {
				current = current.link;
				if (c.compareTo(this.get(i)) < 0) {
					count = i;
					System.out.println(c +" "+ c.compareTo(this.get(i))+" "+ this.get(i));
					System.out.println("Inserting " + c + " at "+i);
					break;
				}
			}
			
			current = head;
			
			for (int i = 0; i < count - 1; i++) {
				current = current.link;
			}
			
			Node previous = current;
			current = current.link;
			previous.link = nextNode;
			nextNode.link = current;
		}
		size++;
	}
	
	/* TODO
	   override the method
	   void add(int index, Object o)
	   so that it throws an UnsupportedOperationException with the message "Do not call add(int, Object) on MySortedLinkedList".
	   Directly adding objects at an index would mess up the sorted order.
	*/

	public void add(int index, Object o) {
		throw new UnsupportedOperationException("Do not call add(int, Object) on MySortedLinkedList.");
	}
}