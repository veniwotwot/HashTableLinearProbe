
public class HashTableLinearProbe<K, V> {
	// hashtable is an array of HashEntry, with default size three.
	// I recognize that having a private int size is unnecessary, and that
	// hashtable.size() would have done the job just as well. Just personal
	// preference.
	private HashEntry<K, V>[] hashtable = new HashEntry[3];
	private int size = 3;

	// Insert method is the bread and butter of this problem. It inserts K,V based
	// on the value of K, whether it is duplicated, null, or deleted.
	public Boolean insert(K key, V value) {
		if (key == null) // Null key case
			throw new IllegalArgumentException("Error in insert: Key is invalid or null.");
		else if (getIndex(key) != -1) // Duplicate key case
			return false;
		else {
			int hashVal = (key.hashCode() % size); // mod for hash value, also support negative keys
			hashVal = Math.abs(hashVal);
			for (int i = 0; i < size; i++) {
				if ((hashVal + i) >= size) { // to solve bug due to overflow
					hashVal = hashVal - size;
				}
				if (hashtable[hashVal + i] == null) { // if space exists, insert
					hashtable[hashVal + i] = new HashEntry(key, value);
					return true;
				}
			}
			// if at this point, then the table is full and needs to be rehashed
			rehash();
			insert(key, value);
			return false;
		}
	}

	// The most used private method in this entire project, used to determine if
	// an index already exists or not. Covers logically deleted key case.
	private int getIndex(K key) {
		int hashVal = (key.hashCode() % size); // Start searching from original key pos, covers collision
		hashVal = Math.abs(hashVal);
		for (int i = 0; i < size; i++) {
			int pos = hashVal + i;
			if (pos >= size)
				pos = pos - size;
			if (hashtable[pos] == null) // When the position keyVal is null, we can stop searching
				return -1;
			if (hashtable[pos].getKey() == key && hashtable[pos].deleted == false) // Found && Not Deleted
				return pos;
		}
		// All else, full table and not found or deleted.
		return -1;
	}

	// Basic find method, utilizes private getIndex method
	public V find(K key) throws IllegalArgumentException{
		int index = getIndex(key);
		if (index == -1)
			//return null;
			throw new IllegalArgumentException("Error in find: Key is invalid or null.");
		return hashtable[index].getValue();
	}

	// Basic delete method, utilizes private getIndex method and private delete
	// method
	public boolean delete(K key) {
		int index = getIndex(key);
		if (index == -1)
			return false;
		hashtable[index].delete();
		return true;
	}

	// Basic getHashValue method, utilizes private getIndex method
	public int getHashValue(K key) {
		int index = getIndex(key);
		if (index == -1)
			return -1;
		return index;
	}

	// Rehash method, checks if table is full first and then rehashes
	// using a temp hashtable and public insert method
	public void rehash() {
		if (checkFull() == false) // Case where table is not full, no need to rehash
			return;
		HashEntry<K, V>[] temp = hashtable;
		hashtable = new HashEntry[size * 2];
		size = size * 2;
		for (int i = 0; i < size / 2; i++) {
			if (temp[i].deleted == false)
				insert(temp[i].getKey(), temp[i].getValue());
		}
	}

	// private method for rehash function, checks to see if table is full
	private boolean checkFull() {
		for (int i = 0; i < size; i++) {
			if (hashtable[i].getKey() == null)
				return false;
		}
		return true;
	}
	
	public static void main(String args[]) {
		HashTableLinearProbe<Integer, Integer> systemInt;
		systemInt = new HashTableLinearProbe();
		HashTableLinearProbe<String,String> systemString;
		systemString = new HashTableLinearProbe();
		boolean output;
		
		output = systemInt.insert(0, 2);
		System.out.println("Case1: insert normal int ....."+output);
		
		output = systemInt.insert(0, 2);
		System.out.println("Case2: insert duplcate key (int) ....."+output);
		
		output = systemInt.insert(-1, 2);
		System.out.println("Case3: insert negative key (int) ....."+output);
		
		output = systemInt.delete(0);
		System.out.println("Case4: delete existing key (int) ....."+output);
		
		output = systemInt.delete(5);
		System.out.println("Case5: delete nonexistent key (int) ....."+output);
		
		int output2;
		output2 = systemInt.find(-1);
		System.out.println("Case6: find existing key (int) ....."+output2);
		
		//int output3;
		//output3 = systemInt.find(100);
		//System.out.println("Case6: find existing key (int) ....."+output2);
		
		output = systemString.insert(new String("a"),new String("b"));
		System.out.println("Case7: insert normal string ....."+output);

		
	}

	// private class for HashEntries, utilizes generics
	private static class HashEntry<K, V> {
		boolean deleted = false;
		private K key = (K) null;
		private V value = (V) null;

		public HashEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void delete() {
			deleted = true;
		}

	}
}
