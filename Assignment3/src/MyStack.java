public class MyStack implements StackInterface {
	/* 
	* TODO 1: Implement "MyStack"
	*/
	private MyLinkedList list;

	// Constructor, creates new Stack instance
    public MyStack() {
        list = new MyLinkedList();
    }

    //checks whether or not the stack is empty
    public boolean isEmpty() {
        if (list.size == 0) {
            return true;
        } else {
            return false;
        }
    }

    //adds an object to the queue, inserting it at the front of the list
    public void push(Object o) {
        list.add(0, o);
    }

    //if an object exists, return it and remove it from the list, otherwise throws an exception
    public Object pop() {
        if (this.isEmpty()) {
            throw new StackException("Stack is empty");
        } else {
            Object o = list.get(0);
            list.remove(0);
            return o;
        }
    }

    //returns the object at the first index in the list without removing it, should it exist, otherwise throws and exception
    public Object peek() {
        if (this.isEmpty()) {
            throw new StackException("Stack is empty");
        } else {
            return list.get(0);
        }
    }

    //pops all objects by creating a new list
    public void popAll() {
        list = new MyLinkedList();
    }

    //toString for printing out Stack Objects
    public String toString() {
        String stack = new String("(");
        for (int i = 0; i < list.size - 1; i++) {
            stack  += list.get(i) + ", ";
        }

        if (this.isEmpty()) {
            stack += ")";
        } else {
            stack += list.get(list.size - 1) + ")";
        }

        return stack;
    }
	/* 
	* TODO 1: Implement "MyStack"
	*/
}