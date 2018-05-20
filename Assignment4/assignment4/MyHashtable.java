class MyHashtable implements DictionaryInterface {

	//fields for MyHashtable
	protected int tableSize;
	protected int size;
	protected MyLinkedList[] table;

	//inner class, creates an Entry object
	protected class Entry {
		String key;
		Object value;
		//constructor, initializes all fields
		public Entry(String key, Object value)  {
			this.key = key;
			this.value = value;
		}
	}

	//returns whether or not the table is empty
	public boolean isEmpty() {
		return this.size > 0;
	}

	//returns the size(number of key/value pairs stored in the hashtable
	public int size() {
		return this.size;
	}

	public Object put (String key, Object value) {
		Object previousValue = null;
		int index = calculateIndex(key);
		if (table[index] == null) {
			MyLinkedList list = new MyLinkedList();
			list.add(0, new Entry(key, value));
			table[index] = list;
			size++;
		} else {
			boolean bucketContainsKey = false;
			for (int i = 0; i < table[index].size(); i++) {
				Entry entry = (Entry)table[index].get(i);
				if (entry.key.equals(key)) {
					previousValue = entry.value;
					entry.key = key;
					bucketContainsKey = true;
				}
			}
			if (!bucketContainsKey) {
				table[index].add(0, new Entry(key, value));
				size++;
				return null;
			} else if (bucketContainsKey) {
				return previousValue;
			}
		}
	}

	//returns the Object at value key
	public Object get(String key) {
		//hashes the key
		int index = calculateIndex(key);
		if (table[index] == null) {
			return null;
		} else {
			MyLinkedList listAtIndex = table[index];
			for (int i = 0; i < listAtIndex.size(); i++) {
				//loop through list at index and return the value at key
				Entry entry = (Entry)listAtIndex.get(i);
				if (entry.key.equals(key)) {
					return ((Entry)listAtIndex.get(i)).value;
				}

			}
			return null;
		}
	}

	public void remove(String key) {

	}

	//clears the internal array of Linked Lists by creating a new one
	public void clear() {
		this.table = new MyLinkedList[0];
		this.size = 0;
	}

	public String[] getKeys() {

	}

	//calculates the index of a String in an array given a key String
	private int calculateIndex(String key) {
		//convert key to Hashcode and find the absolute value of the index
		//in the range of 0 to the size of the internal array of Linked Lists - 1
		int hashCode = key.hashCode();
		int indexInArray = Math.abs(hashCode) % tableSize;
		return indexInArray;
	}

	//constructor for MyHashTable, initializes all fields
	public MyHashtable(int tableSize) {
		this.tableSize = tableSize;
		this.table = new MyLinkedList[tableSize];
		this.size = 0;
	}



	// Returns the size of the biggest bucket (most collisions) in the hashtable. 
	public int biggestBucket() {
		int biggestBucket = 0; 
		for(int i = 0; i < table.length; i++) {
			// Loop through the table looking for non-null locations. 
			if (table[i] != null) {
				// If you find a non-null location, compare the bucket size against the largest
				// bucket size found so far. If the current bucket size is bigger, set biggestBucket
				// to this new size. 
				MyLinkedList bucket = table[i];
				if (biggestBucket < bucket.size())
					biggestBucket = bucket.size();
			}
		}
		return biggestBucket; // Return the size of the biggest bucket found. 
	}

	// Returns the average bucket length. Gives a sense of how many collisions are happening overall.
	public float averageBucket() {
		float bucketCount = 0; // Number of buckets (non-null table locations)
		float bucketSizeSum = 0; // Sum of the size of all buckets
		for(int i = 0; i < table.length; i++) {
			// Loop through the table 
			if (table[i] != null) {
				// For a non-null location, increment the bucketCount and add to the bucketSizeSum
				MyLinkedList bucket = table[i];
				bucketSizeSum += bucket.size();
				bucketCount++;
			}
		}

		// Divide bucketSizeSum by the number of buckets to get an average bucket length. 
		return bucketSizeSum/bucketCount; 
	}

	public String toString() {
		String s = "";
		for(int tableIndex = 0; tableIndex < tableSize; tableIndex++) {
			if (table[tableIndex] != null) {
				MyLinkedList bucket = table[tableIndex];
				for(int listIndex = 0; listIndex < bucket.size(); listIndex++) {
					Entry e = (Entry)bucket.get(listIndex);
					s = s + "key: " + e.key + ", value: " + e.value + "\n";
				}
			}
		}
		return s; 
	}
}