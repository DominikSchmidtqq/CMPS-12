
public class MyLinkedList implements ListInterface {

	/* TODO: Write a LinkedList implementation for all the methods specified in ListInterface */ 
	public int size;
	Node head = null;
	Node tail = null;

	public MyLinkedList() {
	    this.size = 0;
    }

    public int size() {
	    return this.size;
    }

    public boolean isEmpty() {
	    if (this.size == 0) {
	        return true;
        } else {
	        return false;
        }
    }

    public void add(int index, Object value) {
        if (index > 0 || index > this.size) {
            throw new ListIndexOutOfBoundsException("Index" + index + " is out of range");
        } else {
            Node nextNode = new Node();
            nextNode.data = value;

            if (this.size == 0) {
                nextNode.link = null;
                this.head = nextNode;
                this.tail = nextNode;
            } else if (index == 0) {
                nextNode.link = head;
                head = nextNode;
            } else if (index == this.size) {
                Node current = head;

                for (int i = 0; i < this.size - 1; i++) {
                    current = current.link;
                }

                current.link = nextNode;
                nextNode.link = null;
                tail = nextNode;
            } else {
                Node current = head;

                for (int i = 0; i < index - 1; i++) {
                    current = current.link;
                }

                Node previous = current;
                current = current.link;
                previous.link = nextNode;
                nextNode.link = current;
            }
            this.size++;
        }
    }

    public void remove(int index) {
	    if (this.isEmpty()) {
	        throw new ListIndexOutOfBoundsException("List is empty");
        } else if (index < 0) {
	        throw new ListIndexOutOfBoundsException("Invalid index");
        } else {
	        if (index == 0) {
	            Node current = head;
	            head = head.link;
	            current.link = null;
            } else if (index == this.size -1) {
	            Node previous = head;

	            for (int i = 0; i < this.size - 2; i++) {
	                previous = previous.link;
                }

                previous.link = null;
            }
            this.size--;
        }
    }

    public Object get(int index) {
        if (index < 0 || index >= this.size) {
            throw new ListIndexOutOfBoundsException("Cannot get value at Index: "+index+" because it is out of range");
        } else {
            Node current = head;

            for (int i = 0; i < index; i++) {
                current = current.link;
            }

            return current.data;
        }
    }

    public int find(Object o) {
	    Node current = head;

	    for (int i = 0; i < this.size; i++) {
	        current = current.link;

	        if (current.equals(o)) {
	            return i;
            }
        }

        return -1;
    }

    public void removeAll() {
        int previousSize = this.size;
	    this = new MyLinkedList();
        this.size = previousSize;
    }
}
