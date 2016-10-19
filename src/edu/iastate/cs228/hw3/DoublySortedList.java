package edu.iastate.cs228.hw3;

import com.sun.istack.internal.NotNull;

import javax.swing.text.html.HTMLDocument;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author Thomas John Wesolowski : wojoinc@iastate.edu
 * Created under Github user WojoInc
 *
 */

/**
 * IMPORTANT: In the case of any minor discrepancy between the comments before a method
 * and its description in the file proj3.pdf, use the version from the file. 
 *
 */

public class DoublySortedList
{
	 private int size;     			// number of different kinds of fruits	 
	 private Node headN; 			// dummy node as the head of the sorted linked list by fruit name 
	 private Node headB; 			// dummy node as the head of the sorted linked list by bin number
	 private NameComparator ncomp = new NameComparator();
	 private BinComparator bcomp = new BinComparator();

	 /**
	  *  Default constructor constructs an empty list. Initialize size. Set the fields nextN and 
	  *  previousN of headN to the node itself. Similarly, set the fields nextB and previousB of 
	  *  headB. 
	  */
	 public DoublySortedList()
	 {
		 size=0;
		 headN = new Node();
		 headB = new Node();
		 headN.previousN=headN.nextN=headN;
		 headB.previousB=headB.nextB=headB;
		 headN.previousB=headN.nextB=headB;
		 headB.previousN=headB.nextN=headN;
	 }
	 
	 
	 /**
	  * Constructor over an inventory file consists of lines in the following format  
	  * 
	  * fruit  quantity  bin
	  * 
	  * Throws an exception if the file is not found. 
	  * 
	  * You are asked to carry out the following operations: 
	  * 
	  *     1. Scan line by line to construct one Node object for each fruit.  
	  *     2. Create the two doubly-linked lists, by name and by bin number, respectively,
	  *        on the fly as the scan proceeds. 
	  *     3. Perform insertion sort on the two lists. Use the provided BinComparator and 
	  *        NameComparator classes to generate comparator objects for the sort.
	  *        
	  * @param inventoryFile    name of the input file
	  * @throws FileNotFoundException if file does not exist
	  */
	 public DoublySortedList(String inventoryFile) throws FileNotFoundException
	 {
		 File f = new File(inventoryFile);
		 Scanner s = new Scanner(f);
		 headN = new Node(s.next(),s.nextInt(),s.nextInt(),null,null,null,null);
		 headB = new Node(headN.fruit,headN.quantity,headN.bin,null,null,null,null);
		 Node walkerN;
		 while (s.hasNextLine()){

		 }
	 }
	 
	 
	 /**
	  * Called by split() and also used for testing.  
	  * 
	  * Precondition: The doubly sorted list has already been created. 
	  * 
	  * @param size
	  * @param headN
	  * @param headB
	  */
	 public DoublySortedList(int size, Node headN, Node headB)
	 {
		 this.size = size; 
		 this.headN = headN;
		 this.headB = headB;
	 }

	 
	 public int size()
	 {
		 return size; 
	 }

	 
	 /**
	  * Add one type of fruits in given quantity (n). 
	  * 
	  *     1. Search for the fruit. 
	  *     2. If already stored in some node, simply increase the quantity by n
	  *     3. Otherwise, create a new node to store the fruit at the first available bin.
	  *        add it to both linked lists by calling the helper methods insertN() and insertB(). 
	  *        
	  * The case n == 0 should result in no operation.  The case n < 0 results in an 
	  * exception thrown. 
	  * 
	  * @param fruit                      name of the fruit to be added 
	  * @param n	                      quantity of the fruit
	  * @throws IllegalArgumentException  if n < 0 
	  */
	 public void add(String fruit, int n) throws IllegalArgumentException
	 {
		 if(n==0)return;
		 else if(n<0) throw new IllegalArgumentException("Cannot add a negative quantity!");

		 //search the N list for the fruit, until we reach the head node, meaning we have wrapped around.
		 Node node = queryN(fruit);
		 if(node!=null)node.quantity+=n;


		 else{
			 Node insert = new Node();
			 insert.fruit=fruit;
			 insert.quantity = n;
			 insertB(insert,null,null);
			 insertN(insert,null,null);
			 size++;
		 }

	 }
	 
	 
	 /**
	  * The fruit list is not sorted.  For efficiency, you need to sort by name using quickSort. 
	  * Maintain two arrays fruitName[] and fruitQuant[].  
	  * 
	  * After sorting, add the fruits to the doubly-sorted list (see project description) using 
	  * the algorithm described in Section 3.2 of the project description. 
	  * 
	  * Ignore a fruit if its quantity in fruitFile is zero.  
	  * 
	  * @param fruitFile                  list of fruits with quantities. one type of fruit 
	  *                                   followed by its quantity per line.
	  * @throws FileNotFoundException
	  * @throws IllegalArgumentException  if the quantity specified for some fruit in fruitFile 
	  *                                   is negative.
	  */
	 public void restock(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	 {
		 // TODO
	 }

	 
	 /**
	  * Remove a fruit from the inventory. 
	  *  
	  *     1. Search for the fruit on the N-list.  
	  *     2. If no existence, make no change. 
	  *     3. Otherwise, call the private method remove() on the node that stores 
	  *        the fruit to remove it. 
	  * 
	  * @param fruit
	  */
	 public void remove(String fruit)
	 {
		 Node n = queryN(fruit);
		 if(n!=null)remove(n);
	 }
	 
	 
	 /**
	  * Remove all fruits stored in the bin.  Essentially, remove the node.  The steps are 
	  * as follows: 
	  * 
	  *     1. Search for the node with the bin in the B-list.
	  *     2. No change if it is not found.
	  *     3. Otherwise, call remove() on the found node. 
	  * 
	  * @param bin   
	  * @throws IllegalArgumentException  if bin < 1
	  */
	 public void remove(int bin) throws IllegalArgumentException
	 {
		 if(bin<1)throw new IllegalArgumentException("Bin number is less than 1!");
		 Node n = queryB(bin);
		 if(n!=null)remove(n);
	 }
	 
	 
	 /**
	  * Sell n units of a fruit. 
	  * 
	  * Search the N-list for the fruit.  Return in the case no fruit is found.  Otherwise, 
	  * a Node node is located.  Perform the following: 
	  * 
	  *     1. if n >= node.quantity, call remove(node). 
	  *     2. else, decrease node.quantity by n. 
	  *     
	  * @param fruit
	  * @param n	
	  * @throws IllegalArgumentException  if n < 0
	  */
	 public void sell(String fruit, int n) throws IllegalArgumentException 
	 {
		 if(n<0)throw new IllegalArgumentException("Cannot sell negative amount of fruit!");

		 Node node = queryN(fruit);
		 if(node!=null){
			 if(node.quantity<=n)remove(node);
			 else node.quantity -= n;
		 }
	 }

	/**
	 * Traverses the N-list for a specific fruit and returns the node in the N-list representing the fruit
	 * @param fruit the name of the fruit to search for
	 * @return the Node representing the fruit, null if fruit is not found in N-list
	 */
	 private Node queryN(@NotNull String fruit) {

		 for (Node n = headN.nextN; n != headN; n = n.nextN) {
			 if (n.fruit.compareTo(fruit) == 0) return n;
		 }
		 return null;
	 }

	/**
	 * Traverses the B-list for a specific bin and returns the appropriate node if found
	 * @param bin the bin to search for
	 * @return the node representing the bin, or null if the bin does not exist in the B-list
	 */
	 private Node queryB(@NotNull int bin){
		 for (Node b = headB.nextB; b != headB; b = b.nextB) {
			 if (b.bin == bin) return b;
		 }
		 return null;
	 }
	 
	 /** 
	  * Process an order for multiple fruits as follows.  
	  * 
	  *     1. Sort the ordered fruits and their quantities by fruit name using the private method 
	  *        quickSort(). 
	  *     2. Traverse the N-list. Whenever a node with the next needed fruit is encountered, 
	  *        let m be the ordered quantity of this fruit, and do the following: 
	  *        a) if m < 0, throw an IllegalArgumentException; 
	  *        b) if m == 0, ignore. 
	  *        c) if 0 < m < node.quantity, decrease node.quantity by n. 
	  *        d) if m >= node.quanity, call remove(node).
	  * 
	  * @param fruitFile
	  */
	 public void bulkSell(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	 {
		 // TODO 
	 }
	 
	 
	 /**
	  * 
	  * @param fruit
	  * @return quantity of the fruit (zero if not on stock)
	  */
     public int inquire(String fruit)
     {
		 Node n = queryN(fruit);
		 return n==null ? 0:n.quantity;
      }
     
	 
	 /**
	  * Output a string that gets printed out as the inventory of fruits by names.  
	  * The exact format is given in Section 5.1.  Here is a sample:   
	  *
	  *  
	  * fruit   	quantity    bin
	  * ---------------------------
	  * apple  			50  	5
	  * banana    		20      9
	  * grape     		100     8
	  * pear      		40      3 
	 */
	 public String printInventoryN()
	 {	 
		 // TODO
		 
		 return null; 
	 }
	 
	 /**
	  * Output a string that gets printed out as the inventory of fruits by storage bin. 
	  * Use the same formatting rules for printInventoryN().  Below is a sample:  
	  * 
	  * bin 	fruit     	quantity      
	  * ----------------------------
	  * 3		pear      	40             
	  * 5		apple     	50            
	  * 8		grape     	100           
	  * 9		banana    	20  
	  *           	 
	  */
	 public String printInventoryB()
	 {
		 // TODO 
		 
		 return null; 
	 }
	 
	 
	 @Override
	 /**
	  *  The returned string should be printed out according to the format in Section 5.1, 
	  *  exactly the same required for printInventoryN(). 
	  */
	 public String toString()
	 {
		 // TODO 
		 
		 return null; 
	 }
	 
	 
	 /**
	  *  Relocate fruits to consecutive bins starting at bin 1.  Need only operate on the
	  *  B-list. 
	  */
	 // 
	 public void compactStorage()
	 {
		 // TODO 
	 }
	 
	 
	 /**
	  *  Remove all nodes storing fruits from the N-list and the B-list.
	  */
	 public void clearStorage()
	 {
		 for(Node n = headN.nextN; n!=headN; n=n.nextN)remove(n);
	 }
	 
	 
	 /** 
	  * Split the list into two doubly-sorted lists DST1 and DST2.  Let N be the total number of 
	  * fruit types. Then DST1 will contain the first N/2 types fruits in the alphabetical order.  
	  * DST2 will contain the remaining fruit types.  The algorithm works as follows. 
	  * 
	  *     1. Traverse the N-list to find the median of the fruits by name. 
	  *     2. Split at the median into two lists: DST1 and DST2.  
	  *     3. Traverse the B-list.  For every node encountered add it to the B-list of DST1 or 
	  *        DST2 by comparing node.fruit with the name of the median fruit. 
	  *   
	  * DST1 and DST2 must reuse the nodes from this list. You cannot make a copy of each node 
	  * from this list, and arrange these copy nodes into DST1 and DST2. 
	  * 
	  * @return   two doubly-sorted lists DST1 and DST2 as a pair. 
	  */
	 public Pair<DoublySortedList> split()
	 {
		 // TODO 
		 
		 return null; 
	 }
	 
	 
	 /**
	  * Perform insertion sort on the doubly linked list with head node using a comparator 
	  * object, which is of either the NameComparator or the Bincomparator class. 
	  * 
	  * Made a public method for testing by TA. 
	  * 
	  * @param sortNList   sort the N-list if true and the B-list otherwise. 
	  * @param comp
	  */
	 public void insertionSort(boolean sortNList, Comparator<Node> comp)
	 {
		 // TODO 
	 }
	 

	 /**
	  * Sort an array of fruit names using quicksort.  After sorting, quant[i] is the 
	  * quantity of the fruit with name[i].  
	  * 
	  * Made a public method for testing by TA. 
	  * 
	  * @param size		number of fruit names 
	  * @param fruit   	array of fruit names 
	  * @param quant	array of fruit quantities 
	  */
	 public void quickSort(String fruit[], Integer quant[], int size)
	 {
		 // TODO 
	 }
	 
	 
	 // --------------
	 // helper methods 
	 // --------------
	 
	 /**
	  * Add a node between two nodes prev and next in the N-list.   
	  * 
	  * @param node
	  * @param prev  preceding node after addition 
	  * @param next  succeeding node 
	  */
	 private void insertN(@NotNull Node node, @NotNull Node prev, @NotNull Node next)
	 {
		 for(Node n =headN.nextN; n!=headN; n=n.nextN) {
			 if(node.fruit.compareTo(n.fruit)>0 && (n.nextN==headN||node.fruit.compareTo(n.nextN.fruit)<0)){
				 node.previousN = n;
				 node.nextN = n.nextN;
				 n.nextN = n.nextN.previousN = node;
				 next = node.nextN;
				 prev = node.previousN;
				 return;
			 }
		 }
		 node.previousN = headN;
		 node.nextN = headN;
		 headN.nextN = headN.previousN = node;
		 next = node.nextN;
		 prev = node.previousN;
	 }
	
	 
	 /**
	  * Add a node between two nodes prev and next in the B-list.  
	  * 
	  * @param node
	  * @param prev  preceding node 
	  * @param next  succeeding node 
	  */
	 private void insertB(@NotNull Node node, @NotNull Node prev, @NotNull Node next)
	 {
		 for(Node b = headB.nextB; b!=headB; b=b.nextB) {
			 if(b.bin>1)break;
			 if(b.nextB.bin-b.bin>=2 || b.nextB==headB){
				 node.previousB = b;
				 node.nextB = b.nextB;
				 b.nextB = b.nextB.previousB = node;
				 node.bin = b.bin + 1;
				 next = node.nextB;
				 prev = node.previousB;
				 return;
			 }
		 }
		 node.previousB = headB;
		 node.nextB = headB.nextB;
		 if(size==0) headB.nextB = headB.previousB = node;
		 else headB.nextB = headB.previousB.previousB = node;
		 node.bin=1;
		 next = node.nextB;
		 prev = node.previousB;
	 }
	 
	 
	 /**
	  * Remove node from both linked lists. 
	  * 
	  * @param node
	  */
	 private void remove(Node node)
	 {
		 //bridges gap between next and previous nodes
		 node.previousN.nextN=node.nextN;
		 node.nextN.previousN=node.previousN;
		 node.previousB.nextB = node.nextB;
		 node.nextB.previousB = node.previousB;
		 size--;
		 // sets node = null and relies on garbage collector to handle freeing memory
		 node =null;
	 }
	  
	 
	 /**
	  * 
	  * @param name		name[first, last] is the subarray of fruit names 
	  * @param bin		bin[first, last] is the subarray of bins storing the fruits.
	  * @param first
	  * @param last
	  */
	 
	 /**
	  * 
	  * @param fruit    array of fruit names 
	  * @param quant	array of fruit quantities corresponding to fruit[]
	  * @param first
	  * @param last
	  * @return
	  */
	 private int partition(String fruit[], Integer quant[], int first, int last)
	 {
		 // TODO 
		 
		 return 0; 
	 }

}
