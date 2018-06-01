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

	public MyQueue inOrder() {
		MyQueue StringsInOrder = new MyQueue();
		inOrderRecursive(root, StringsInOrder);
		return StringsInOrder;
	}

	public MyQueue preOrder() {
		MyQueue StringPreOrder = new MyQueue();
		preOrderRecursive(root, StringPreOrder);
		return StringPreOrder;
	}

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
	protected boolean recursiveSearch(BSTNode node, String s) {
		if (node == null) {
			return false;
		} else if (node.item.equals(s)) {
			return true;
		} else if (s.compareTo(node.item) < 0) {
			return recursiveSearch(node.left, s);
		} else {
			return recursiveSearch(node.right, s);
		}
	}

	// TODO: Fill this in and call it from put()
	protected BSTNode recursiveInsert(BSTNode node, String s){
		if (node == null) {
			return new BSTNode(s);
		} else if (node != null) {
			if (s.compareTo(node.item) < 0) {
				node.left = recursiveInsert(node.left, s);
			} else if (s.compareTo(node.item) > 0) {
				node.right = recursiveInsert(node.right, s);
			}
		}

		return node;
	}

	// TODO: Fill this in and call it from delete()
	protected BSTNode recursiveRemove(BSTNode node, String s) {
		if (node != null) {
			if (s.compareTo(node.item) < 0) {
				node.left = recursiveRemove(node.left, s);
			} else if (s.compareTo(node.item) > 0) {
				node.right = recursiveRemove(node.right, s);
			} else {
				node = deleteNode(node);
			}
		}

		return node;
	}
	
	// TODO: Fill this in and call it from recursiveRemove()
	protected BSTNode deleteNode(BSTNode node) {
		if ((node.right == null) && (node.left == null)) {
			node = null;
		} else if ((node.left != null) && (node.right == null)) {
			node = node.left;
		} else if ((node.left == null) && (node.right != null)) {
			node = node.right;
		} else {
			node.item = getSmallest(node.right);
			node.right = recursiveRemove(node.right, node.item);
		}

		return node;
	}

	// TODO: Fill this in and call it from deleteNode()
	protected String getSmallest(BSTNode node) {
		String smallest = node.item;
		while (node.left != null) {
			smallest = node.left.item;
			node = node.left;
		}

		return smallest;
	}


	// TODO: Fill this in and call it from inOrder()
	protected void inOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			inOrderRecursive(node.left, queue);
			queue.enqueue(node.item);
			inOrderRecursive(node.right, queue);
		}
	}


	// TODO: Fill this in and call it from preOrder()
	protected void preOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null) {
			queue.enqueue(node.item);
			preOrderRecursive(node.left, queue);
			preOrderRecursive(node.right, queue);
		}
	}

	// TODO: Fill this in and call it from postOrder()
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

	// TODO: If doing the extra credit, fill this in and call it from balanceBST()
	/* protected BSTNode balanceRecursive(String[] array, int first, int last) {

	} */
}