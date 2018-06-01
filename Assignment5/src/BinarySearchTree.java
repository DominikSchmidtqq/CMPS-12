public class BinarySearchTree implements BSTInterface {

	protected BSTNode root;
	public BinarySearchTree() {
		//constructor for BinarySearchTree, initializes the root to null
		this.root = null;
	}

	protected class BSTNode {
		//inner class BSTNode, has an item field, and a left and a right BSTNode field
		protected String item;
		protected BSTNode left, right;
		public BSTNode(String item) {
			//constructor for BSTNode, initializes all fields
			this.item = item;
			this.left = null;
			this.right = null;
		}
	}

	//returns whether or not the BST is empty
	public boolean isEmpty() {
		return (root == null);
	}

	//clears the BST by setting the root to null
	public void makeEmpty() {
		this.root = null;
	}

	//returns the result of an in order traversal
	public MyQueue inOrder() {
		MyQueue StringsInOrder = new MyQueue();
		inOrderRecursive(root, StringsInOrder);
		return StringsInOrder;
	}

	//returns the result of a pre order traversal
	public MyQueue preOrder() {
		MyQueue StringPreOrder = new MyQueue();
		preOrderRecursive(root, StringPreOrder);
		return StringPreOrder;
	}

	//returns the result of a post order traversal
	public MyQueue postOrder() {
		MyQueue StringPostOrder = new MyQueue();
		postOrderRecursive(root, StringPostOrder);
		return StringPostOrder;
	}

	//returns whether or not the BST contains a String
	public boolean contains(String s) {
		//returns whether or not the recursive helper function found the String
		return recursiveSearch(root, s);
	}

	//adds a String s into the BST
	public void put(String s) {
		//sets the root to the subtree returned by the recursive helper
		root = recursiveInsert(root, s);
	}

	//deletes the string s from the BST
	public void delete(String s) {
		//sets the root to the subtree returned by the recursive helper
		root = recursiveRemove(root, s);
	}

	// TODO: Fill this in and call it from contains()
	//recursive helper for contains, recursively searches for a string
	protected boolean recursiveSearch(BSTNode node, String s) {
		if (node == null) {
			//if the node is null returns false
			return false;
		} else if (node.item.equals(s)) {
			//if the node is the same as the string returns true
			return true;
		} else if (s.compareTo(node.item) < 0) {
			//if the string is smaller than the current node, goes left in the tree
			return recursiveSearch(node.left, s);
		} else {
			//if the string is bigger than the current node, goes right in the tree
			return recursiveSearch(node.right, s);
		}
	}

	// TODO: Fill this in and call it from put()
	protected BSTNode recursiveInsert(BSTNode node, String s){
		if (node == null) {
			//if the current node is null, makes a new node with the passed string and returns that node
			return new BSTNode(s);
		} else if (node != null) {
			//if the current node is not null
			if (s.compareTo(node.item) < 0) {
				//if the string is smaller than the current item goes left
				node.left = recursiveInsert(node.left, s);
			} else if (s.compareTo(node.item) > 0) {
				//if the string is bigger than the current item, goes right
				node.right = recursiveInsert(node.right, s);
			}
		}

		return node;
	}

	// TODO: Fill this in and call it from delete()
	//recursive helper for delete
	protected BSTNode recursiveRemove(BSTNode node, String s) {
		if (node != null) {
			//if the node is not null
			if (s.compareTo(node.item) < 0) {
				//if the string is smaller than the current item, goes left
				node.left = recursiveRemove(node.left, s);
			} else if (s.compareTo(node.item) > 0) {
				//if the string is bigger than the current item, goes right
				node.right = recursiveRemove(node.right, s);
			} else {
				//if the string is the same as the current item, deletes the node
				node = deleteNode(node);
			}
		}

		return node;
	}
	
	// TODO: Fill this in and call it from recursiveRemove()
	//deletes a node
	protected BSTNode deleteNode(BSTNode node) {
		if ((node.right == null) && (node.left == null)) {
			//if node has no children, sets node to null
			node = null;
		} else if ((node.left != null) && (node.right == null)) {
			//if node has only a left child, promotes the left child
			node = node.left;
		} else if ((node.left == null) && (node.right != null)) {
			//if node has only a right child, promotes the right child
			node = node.right;
		} else {
			//if node has two children, gets the smallest child of the right child
			//promotes that child
			node.item = getSmallest(node.right);
			node.right = recursiveRemove(node.right, node.item);
		}

		return node;
	}

	// TODO: Fill this in and call it from deleteNode()
	//gets the smallest item of a subtree
	protected String getSmallest(BSTNode node) {
		String smallest = node.item;
		while (node.left != null) {
			//keeps going left until null
			smallest = node.left.item;
			node = node.left;
		}

		return smallest;
	}

	// TODO: Fill this in and call it from inOrder()
	//recursive helper for inOrder
	protected void inOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			inOrderRecursive(node.left, queue);
			queue.enqueue(node.item);
			inOrderRecursive(node.right, queue);
		}
	}

	// TODO: Fill this in and call it from preOrder()
	//recursive helper for preOrder()
	protected void preOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			queue.enqueue(node.item);
			preOrderRecursive(node.left, queue);
			preOrderRecursive(node.right, queue);
		}
	}

	// TODO: Fill this in and call it from postOrder()
	//recursive helper for postOrder()
	protected void postOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			postOrderRecursive(node.left, queue);
			postOrderRecursive(node.right, queue);
			queue.enqueue(node.item);
		}
	}

	// Prints out the tree structure, using indenting to show the different levels of the tree
	public void printTreeStructure() { 
		printTree(0, root);
	}

	// Recursive helper for printTreeStructure()
	protected void printTree(int depth, BSTNode node) {
		indent(depth);
		if (node != null) {
	    	System.out.println(node.item);
	    	printTree(depth + 1, node.left);
	    	printTree(depth + 1, node.right);
	 	} 
	 	else {
	  		System.out.println("null");
	  	}
	}

	// Indents with with spaces 
	protected void indent(int depth) {
		for(int i = 0; i < depth; i++)
			System.out.print("  "); // Indents two spaces for each unit of depth
	}


	// Extra Credit 

    public void balanceBST() {
	    MyQueue inOrder = inOrder();
	    MyQueue temp = new MyQueue();

	    int itemsInQueue = 0;
	    while (!inOrder.isEmpty()) {
	        String currentItem = (String)inOrder.dequeue();
	        temp.enqueue(currentItem);
	        itemsInQueue++;
        }

        String[] items = new String[itemsInQueue];

	    for (int i = 0; i < itemsInQueue ; i++) {
	        items[i] = (String)temp.dequeue();
        }

        makeEmpty();
        root = balanceRecursive(items, 0, itemsInQueue - 1);
    }
	// TODO: If doing the extra credit, fill this in and call it from balanceBST()
	 protected BSTNode balanceRecursive(String[] array, int first, int last) {
        if (first > last) {
            return null;
        }

	    BSTNode node = new BSTNode(array[(first + last) / 2]);
        node.left = balanceRecursive(array, 0, (first + last) / 2 - 1);
        node.right = balanceRecursive(array, (first + last) / 2 + 1, last);
        return node;
	}
}