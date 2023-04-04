import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * The MyHashSet API is similar to the Java Set interface. This
 * collection is backed by a hash table.
 * 
 * Class that imitates the implementation of the Hashset class in Java
 */
public class MyHashSet<E> implements Iterable<E> {

	/** The table will start as
	 * an ArrayList) of this size.*/
	private final static int DEFAULT_INITIAL_CAPACITY = 4; //the size of the arrayList is set to "4"

	/** When the ratio of size/capacity exceeds this
	 * value, the table will be expanded. */
	private final static double MAX_LOAD_FACTOR = 0.75; //Static constant representing the maximum load factor

	
	public ArrayList<Node<E>> hashTable; 
	//a currently empty arrayList filled with node references of type "E" for "element"

	private int size;  // number of elements in the table (a.k.a. number of nodes)


	/* Static nested inner class that initializes the elements/properties of the nodes */
	public static class Node<T> {
		private T data;
		public Node<T> next;  // STUDENTS: Leave this public, too!

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}
	// [ STUDENT IMPLEMENTATION OF CODE STARTS HERE ]

	/**
	 * CONSTRUCTOR that...
	 * Initializes an empty table with the specified capacity ("initialCapacity").  
	 *
	 * @param "initialCapacity" initial capacity (length) of the 
	 * underlying table
	 */
	// STUDENTS: Calling the ArrayList constructor that takes
	// an int argument doesn't do what we want here. 
	// You need to make an empty ArrayList, and then add a bunch 
	// of null values to it until the size reaches the 
	// initialCapacity requested.  
	public MyHashSet(int initialCapacity) {
		this.hashTable = new ArrayList<>(); //empty ArrayList
		for(int i = 0; i < initialCapacity; i++) {
			hashTable.add(null);
		}
		size = 0;
	}

	/** Default Constructor that...
	 * Initializes an empty table of length equal to 
	 * DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Returns the number of elements stored in the table.
	 * @return number of elements in the table
	 */
	public int size(){
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * @return length of the table (capacity)
	 */
	public int getCapacity(){
		return hashTable.size();
	}

	/** 
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(Object element) {
		// In a Hashtable, in order to figure out whether or not an element exists in the set, we must find the
		// corresponding Hashcode using the "hashCode()" method and using that hashcode, we need to find the index 
		//(See next method for private helper method that does that for me)
		int indexInArrayList = getElementIndex(element); //we've got the element's index in the ArrayList!!
		//This index will represent the "head" part of our makeshift LinkedList (but in Array format yk?)
		//so, we must then capture the head element of the list so that we can traverse through the list and 
		//check if that element exists (see previous project for more examples ;)
		Node<E> curr = hashTable.get(indexInArrayList); //we've captured our head element! :D yay~~
		// now to traverse through...
		for(/* starting from the head of the bucket */;curr != null; curr = curr.next) {
			//if the element exists...
			if(curr.data.equals(element)){ 
				//"curr" by itself is just a node so to access the element inside the node... use the "data"
				//we use "equals" to check if the objects are the same "==" checks their memory address
				return true;
			}
		}
		return false; //the hashTable does NOT have that element :C
	}
	/**
	 * private helper method ;)
	 * @param element
	 * @return
	 */
	private int getElementIndex(Object element) { //passing in the same element as before
		int elementCode = element.hashCode();
		int elementIndex = Math.abs(elementCode % hashTable.size());
		//Though, in theory, the remainder should never turn out negative,However,I'm going to make sure by using
		//"Math.abs()" to get the absolute value of the element code
		return elementIndex;
	}

	/** Adds the specified element to the collection, if it is not
	 * already present.  If the element is already in the collection, 
	 * then this method does nothing.
	 * 
	 * @param "element" the element to be added to the collection
	 */
	public void add(E element) {
		if (!(contains(element))){//if MyHashSet does NOT contain the element...
			//make a private helper method to add the element to the linkedList :) see previous project
			addElement(element);
		} else {//if it DOES contain the element...
			return; //nothing ;)
		}
		if(loadFactor()> MAX_LOAD_FACTOR) {
			//double the size of the table..."Re-hashing"
			rehash();
		}
	}
	//HELPER #1
	/*
	 * Helper method that helps me rehash the hashTable
	 */
	private void rehash() {
		ArrayList<Node<E>> formerHashTable = hashTable; //not necessary but I did for readability purposes :)
		int optimizedCapacity = formerHashTable.size() * 2; //"optimized" because we'll have less collisions w/ this
		ArrayList<Node<E>> newHashTable = new ArrayList<>(optimizedCapacity);
		//empty ArrayList
		for(int i = 0; i< optimizedCapacity; i++) {
			newHashTable.add(null);
		}
		//making our newHashTable our curr object hashtable
		hashTable = newHashTable;
		//resetting the size to "0" because we rehashed the list so we'll now be adding the elements from the old
		// list to this new list that I've created
		size = 0;
		//"for each" or "enhanced for" loop
		for (Node<E> element : formerHashTable) { //starting from the first element/or "head" of the list
			while(element != null) {
				//calling my "add" method"
				add(element.data); //works because my newHashTable is now the current object
				element = element.next; //moving onto the next element
			}
		}
	}
	//HELPER #2
	/**
	 * Helper method that is used to determine the loadFactor of my hashTable
	 * @return the loadFactor
	 */
	private double loadFactor() {
		//loadFactors tend to be of type double so we are casting the result
		// [num of elements in the table] / [the total size of the hashTable] (should return an int)
	    return (double) size / hashTable.size();
	}
	//HELPER #3
	/*
	 * Helper method that adds an element to the front of the bucketlist
	 */
	private void addElement(E element){// w/ hashTables you can simply add to the front :D
		//getting the index # of the element in the ArrayList
		int index = getElementIndex(element);
		//getting the newNode's index so that we have a head to start at
		//actually finding the index in the ArrayList and making that box the "head"
		Node<E> head = hashTable.get(index);
		//making a newNode to put my element in
		Node<E> newNode = new Node<>(element);		
		newNode.next = head; //pushes the "head" node down and allows node.next to be the new head now :)
		hashTable.set(index, newNode); //making my newNode the new head
		//increasing the size of the totalNumOfElements by "1"
		size++; //because we added a new element c;
	}
	
	

	/** Removes the specified element from the collection.  If the
	 * element is not present then this method should do nothing (and
	 * return false in this case).
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element 
	 * removed
	 */
	public boolean remove(Object element) {
		int index = getElementIndex(element);
		Node<E> head = hashTable.get(index);
		Node<E> previous = null;
		for (Node<E> curr = head; curr != null; previous = curr, curr = curr.next) {
			if(curr.data.equals(element)) {
				//if this is the first element...(a.k.a. the "head" of the bucket)
				if(previous == null) {
					hashTable.set(index, curr.next); //set the next node to be the new "head"
				}else { //if this isn't the first node then just set the current node to be the previous node
					previous.next = curr.next;					
				}
				size--;
				return true;
			}
		}
		return false;		
	}

	/** Returns an Iterator that can be used to iterate over
	 * all of the elements in the collection.
	 * 
	 * The order of the elements is unspecified.
	 */
	@Override
	public Iterator<E> iterator() {
	    return new Iterator<E>() {
	        int bucketIndex = -1; //starting at impossible values
	        Node<E> current = null;
	        Node<E> next = null;

	        @Override
	        public boolean hasNext() {
	            if (next != null) {
	                return true;
	            }
	            while (++bucketIndex < hashTable.size()) {
	            	//while the bucketIndex is less than the hashTable's capacity...
	                if (hashTable.get(bucketIndex) != null) {
	                	//if that index/bucket value is NOT null then...
	                    next = hashTable.get(bucketIndex);
	                    //make sure the "next"  variable is updated
	                    return true;
	                }
	            }
	            return false;
	        }

	        @Override
	        public E next() { //moving on to the next element
	            if (!hasNext()) { //if the list does not have a next element...
	                throw new NoSuchElementException();
	            }
	            current = next;
	            next = next.next;
	            return current.data;
	        }
	    };
	}
	
	



}
