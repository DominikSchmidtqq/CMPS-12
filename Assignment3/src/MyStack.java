public class MyStack implements StackInterface {
	/* 
	* TODO 1: Implement "MyStack"
	*/
	private MyLinkedList list;

    public MyStack() {
        list = new MyLinkedList();
    }

    public boolean isEmpty() {
        if (list.size == 0) {
            return true;
        } else {
            return false;
        }
    }

    public void push(Object o) {
        list.add(0, o);
    }

    public Object pop() {
        Object o = list.get(0);
        list.remove(0);
        return o;
    }

    public Object peek() {
        return list.get(0);
    }

    public void popAll() {
        list = new MyLinkedList();
    }
	/* 
	* TODO 1: Implement "MyStack"
	*/
}