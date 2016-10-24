package edu.iastate.cs228.hw3;

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
	private String statsMask="                                    ";

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
		 //init head nodes
		 size=0;
		 headN = new Node();
		 headB = new Node();
		 headN.previousN=headN.nextN=headN;
		 headB.previousB=headB.nextB=headB;
		 headN.previousB=headN.nextB=headB;
		 headB.previousN=headB.nextN=headN;

		 File f = new File(inventoryFile);
		 Scanner s = new Scanner(f);
		 Node walkerN = headN;
		 Node walkerB = headB;
		 Node cursor;

		 while (s.hasNextLine()) {
			 //create blank cursor, get values from line of file
			 cursor = new Node();
			 cursor.fruit = s.next();
			 cursor.quantity = s.nextInt();
			 cursor.bin = s.nextInt();
			 //append to n-list
			 insertN(cursor, walkerN, walkerN.nextN);
			 //append to b-list
			 insertB(cursor, walkerB, walkerB.nextB);
			 //increment walkers
			 walkerB = walkerB.nextB;
			 walkerN = walkerN.nextN;
			 //increment size
			 size++;
		 }
		 //insertionSort(true,ncomp);
		 insertionSort(false,bcomp);
		 insertionSort(true,ncomp);
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
		 Node nextB = new Node(), prevB = new Node() , nextN, prevN;

		 //exception handling
		 if(n==0)return;
		 else if(n<0) throw new IllegalArgumentException("Cannot add a negative quantity!");

		 //search the N list for the fruit, until we reach the head node, meaning we have wrapped around.
		 Node node;
		 int res;
		 for(node =headN.nextN; node!=headN; node=node.nextN) {
			 res = node.fruit.compareTo(fruit);
			 if(res>=0){
				 //test if current node.fruit is exactly lexicographically equal to fruit
				 if(res==0){
					 node.quantity+=n; //increase quantity, return from method
					 return;
				 }
				 break;
			 }
		 }

		 //fruit is new in stock, set prev and next nodes for N list
		 nextN = node;
		 prevN = node.previousN;
		 //create place holder node for insertion
		 Node insert = new Node();
		 insert.fruit=fruit;
		 insert.quantity = n;

		 // search for appropriate location in the B-list
		 if(headB.nextB.bin>1){
			 nextB = headB.nextB;
			 prevB = headB;
			 insert.bin = 1;
		 }
		 else if(size==0){
			 nextB = headB;
			 prevB = headB;
			 insert.bin=1;
		 }
		 else {
			 for (Node b = headB.nextB; b != headB; b = b.nextB) {
				 if (b.nextB.bin - b.bin >= 2 || b.nextB == headB) {
					 insert.bin = b.bin + 1;
					 nextB = b.nextB;
					 prevB = b;
					 break;
				 }
			 }
		 }
		 // insert node into lists, increment size, then return
		 insertB(insert,prevB,nextB);
		 insertN(insert,prevN,nextN);
		 size++;

	 }

	/**
	 * Additional add method that allows for recursive calls, assuming that the fruits to be added occur
	 * in lexicographical order. This limits the length of both the N and B lists that is traversed for each call
	 * to this method, effectively causing the time complexity of subsequent calls of this method to converge to
	 * O(log n).
	 *
	 * @param fruit name of fruit to be added
	 * @param n quantity of the fruit
	 * @param lastN previous node in n list, for recursive calls, should be equal to the node returned by this method.
	 * @param lastB previous node in b list, for recursive calls, should be equal to the node returned by this method.
	 * @return the node that was added to the list, used for recursion
	 */
	public Node add(String fruit, int n, Node lastN, Node lastB)
	{
		Node nextB = new Node(), nextN, prevB = new Node(), prevN;

		//search the N list for the fruit, until we reach the head node, meaning we have wrapped around.
		Node node;
		int res;
		for(node = lastN.nextN; node!=headN; node=node.nextN) {
			res = node.fruit.compareTo(fruit);
			if(res>=0){
				//test if current node.fruit is exactly lexicographically equal to fruit
				if(res==0){
					node.quantity+=n; //increase quantity, return from method
					return node;
				}
				break;
			}
		}

		//fruit is new in stock, set prev and next nodes for N list
		nextN = node;
		prevN = node.previousN;
		//create place holder node for insertion
		Node insert = new Node();
		insert.fruit=fruit;
		insert.quantity = n;

		// search for appropriate location in the B-list
		if(headB.nextB.bin>1){
			nextB = headB.nextB;
			prevB = headB;
			insert.bin = 1;
		}
		else if(size==0){
			nextB = headB;
			prevB = headB;
			insert.bin=1;
		}
		else {
			if(lastB.nextB == headB){
				insert.bin = lastB.bin+1;
				prevB = lastB;
				nextB = lastB.nextB;
			}
			for (Node b = lastB.nextB; b != headB; b = b.nextB) {
				if (b.nextB.bin - b.bin >= 2 || b.nextB == headB) {
					insert.bin = b.bin + 1;
					nextB = b.nextB;
					prevB = b;
					break;
				}
			}

		}
		// insert node into lists, increment size, then return
		insertB(insert,prevB,nextB);
		insertN(insert,prevN,nextN);
		size++;
		return insert;
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
	  * @throws FileNotFoundException	  if file not found
	  * @throws IllegalArgumentException  if the quantity specified for some fruit in fruitFile 
	  *                                   is negative.
	  */
	 public void restock(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	 {
		 File f = new File(fruitFile);
		 Scanner s = new Scanner(f);
		 ArrayList fruit = new ArrayList<String>(), quant = new ArrayList<Integer>();
		 String sCursor;
		 Integer iCursor;
		 //get fruits from file, and create arrays.
		 while (s.hasNextLine()){
			 sCursor = s.next();
			 fruit.add(sCursor);
			 iCursor = s.nextInt();
			 if(iCursor>=0)quant.add(iCursor);
			 else throw new IllegalArgumentException("Negative quantity in fruitFile!");
		 }
		 String[] fruitName = new String[fruit.size()];
		 Integer[] fruitQuant = new	Integer[quant.size()];
		 fruit.toArray(fruitName);
		 quant.toArray(fruitQuant);

		 //perform recursive quicksort
		 quickSort(fruitName,fruitQuant,fruitName.length);

		 //add sorted fruits to n and b lists
		 //this for loop implies i time, where i= number of fruits to add
		 Node n = add(fruitName[0],fruitQuant[0],headN,headB);
		 for (int i =1; i<fruitName.length; i++) {
			 //this statement, when called recursively, carries log(i) time
			 n = add(fruitName[i],fruitQuant[i],n,n);
		 }
		 //total time = i log i
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
	  *     1. if n greater than or equal to node.quantity, call remove(node).
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
	 * Additional sell method that allows for recursion by specifying the lastN value to be the last Node
	 * returned by this method. This will resume traversal of the N-list at the point which the last traversal
	 * left off, reducing the time needed to traverse when using an array of fruits that has been pre-sorted.
	 *
	 * @param fruit the fruit to search for
	 * @param n the amount of fruit to remove, cannot be negative
	 * @param lastN the last node which the previous call to this method returned
	 * @throws IllegalArgumentException returns message stating that a negative integer for 'n' has been detected
	 * @return the node the traversal ended on
	 */
	public Node sell(String fruit, int n, Node lastN) throws IllegalArgumentException
	{
		if(n<0)throw new IllegalArgumentException("Cannot sell negative amount of fruit!");
		if(n==0) return lastN;

		Node nextN, prevN;

		//search the N list for the fruit, until we reach the head node, meaning we have wrapped around.
		Node node;
		int res;
		for(node = lastN.nextN; node!=headN; node=node.nextN) {
			res = node.fruit.compareTo(fruit);
			if(res>=0){
				//test if current node.fruit is exactly lexicographically equal to fruit
				if(res==0){

					/* if n is greater than quantity, remove node, and then return the preceding node
					 * to compensate for gap created by the removal of the current node during the next
					 * traversal
					 */
					if(n >= node.quantity){
						Node tmp = node.previousN;
						remove(node);
						return tmp;
					}
					//if n < node.quantity
					node.quantity-=n; //decrease quantity, return from method
					return node;
				}
				break;
			}
		}
		return node;
	}

	/**
	 * Traverses the N-list for a specific fruit and returns the node in the N-list representing the fruit
	 * @param fruit the name of the fruit to search for
	 * @return the Node representing the fruit, null if fruit is not found in N-list
	 */
	 private Node queryN(String fruit) {

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
	 private Node queryB(int bin){
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
	  * @param fruitFile file to read from
	  */
	 public void bulkSell(String fruitFile) throws FileNotFoundException, IllegalArgumentException
	 {
		 File f = new File(fruitFile);
		 Scanner s = new Scanner(f);
		 ArrayList fruit = new ArrayList<String>(), quant = new ArrayList<Integer>();
		 String sCursor;
		 Integer iCursor;
		 //get fruits from file, and create arrays.
		 while (s.hasNextLine()){
			 sCursor = s.next();
			 fruit.add(sCursor);
			 iCursor = s.nextInt();
			 quant.add(iCursor);
		 }
		 String[] fruitName = new String[fruit.size()];
		 Integer[] fruitQuant = new	Integer[quant.size()];
		 fruit.toArray(fruitName);
		 quant.toArray(fruitQuant);

		 //perform recursive quicksort
		 quickSort(fruitName,fruitQuant,fruitName.length);

		 //add sorted fruits to n and b lists
		 //this for loop implies i time, where i= number of fruits to add
		 Node n = sell(fruitName[0],fruitQuant[0],headN);
		 for (int i =1; i<fruitName.length; i++) {
			 //this statement, when called recursively, carries log(i) time
			 n = sell(fruitName[i],fruitQuant[i],n);
		 }
		 //total time = i log i
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
         String fruit ="fruit", qty="quantity", bin="bin";
         String header = fruit + statsMask.substring(0, 17 - fruit.length())+
                 qty + statsMask.substring(0, 17 - qty.length())+
                 bin+
                 "\n------------------------------------------\n";
		 for(Node n = headN.nextN; n!=headN; n = n.nextN) {
			 fruit = n.fruit + statsMask.substring(0, 17 - n.fruit.length());
			 qty = n.quantity + statsMask.substring(0, 17 - (new String("" + n.quantity).length()));
			 bin = n.bin + "\n";;
			 header = header + fruit + qty + bin;
		 }
		 return header;
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
		 String fruit ="fruit", qty="quantity", bin="bin";
		 String header = bin + statsMask.substring(0, 17 - bin.length())+
                 fruit + statsMask.substring(0, 17 - fruit.length())+
                 qty+
				 "\n------------------------------------------\n";

		 for(Node b = headB.nextB; b!=headB; b = b.nextB) {
			 bin = b.bin + statsMask.substring(0, 17 - (new String("" + b.bin).length()));
			 fruit = b.fruit + statsMask.substring(0, 17 - (b.fruit.length()));
			 qty = b.quantity + "\n";
			 header = header + bin + fruit + qty;
		 }
		 return header;
	 }
	 
	 
	 @Override
	 public String toString()
	 {
         String fruit ="fruit", qty="quantity", bin="bin";
         String header = fruit + statsMask.substring(0, 17 - fruit.length())+
                 qty + statsMask.substring(0, 17 - qty.length())+
                 bin+
                 "\n------------------------------------------\n";
         for(Node n = headN.nextN; n!=headN; n = n.nextN) {
             fruit = n.fruit + statsMask.substring(0, 17 - n.fruit.length());
             qty = n.quantity + statsMask.substring(0, 17 - (new String("" + n.quantity).length()));
             bin = n.bin + "\n";;
             header = header + fruit + qty + bin;
         }
         return header;
	 }
	 
	 
	 /**
	  *  Relocate fruits to consecutive bins starting at bin 1.  Need only operate on the
	  *  B-list. 
	  */
	 // 
	 public void compactStorage()
	 {
		 for(Node b=headB.nextB; b!=headB; b=b.nextB){
			 if(b.bin-b.previousB.bin>=2)b.bin=b.previousB.bin+1;
		 }
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
		 Node splitPoint = headN;
		 Node headN1 = new Node(),
				 headN2 = new Node(),
				 headB1 = new Node(),
				 headB2 = new Node();
		 DoublySortedList DST1, DST2;

		 //initialize new head nodes
		 headN1.previousN = headN1.nextN = headN1;
		 headN2.nextN = headN2.previousN = headN2;
		 headB1.previousB = headB1.nextB = headB1;
		 headB2.nextB = headB2.previousB = headB2;

		 //find midpoint of names, rounded down. for odd numbers, DST2 will contain the 1
		 //extra node.
		 for(int i = 0; i<= (size/2 -1); i++){
			 splitPoint = splitPoint.nextN;
		 }

		 //Tie up ends of each new N-list
		 splitPoint.nextN.previousN = headN2;
		 headN2.nextN=splitPoint.nextN;
		 headN2.previousN = headN.previousN;
		 headN.previousN.nextN = headN2;

		 headN1.nextN=headN.nextN;
		 headN.nextN.previousN = headN1;
		 headN1.previousN = splitPoint;
		 splitPoint.nextN = headN1;

         //create new DSLs
         DST1 = new DoublySortedList(size/2,headN1,headB1);
         DST2 = new DoublySortedList(size - size/2,headN2,headB2);

		 //begin adding correct nodes into array
		 Node walkerB1=headB1,walkerB2=headB2, tmp;
		 for(Node b = headB.nextB; b !=headB; b = tmp){
			 if(b.fruit!=null && b.fruit.compareTo(splitPoint.fruit)<=0){
                 tmp = b.nextB;     //keep track of current node in original b-list
				 insertB(b,walkerB1,walkerB1.nextB);    //reinsert node into b1 list
				 walkerB1 = walkerB1.nextB;     //walk down b1 list
			 }
			 else {
				 tmp = b.nextB;     //keep track of current node in original b-list
				 insertB(b,walkerB2,walkerB2.nextB);    //reinsert node into b2 list
				 walkerB2 = walkerB2.nextB;     //walk down b2 list
			 }
		 }
         return new Pair(DST1,DST2);
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
         boolean cursor = false;
		 boolean end;
		 Node walker,tmp;
		 if (sortNList){
			 for (Node n = headN.nextN; n !=headN; n = n.nextN) {
				 end=false;
				 /*
				  * uses walker node to walk backwards across the list when a node to sort has
				  * been found, and n to traverse forwards across the list
				  */
				 walker= n;
				 while(!end){
					 if(walker.previousN.fruit!=null && comp.compare(walker,walker.previousN)<0 ){
						 tmp = walker.previousN;
						 removeN(walker.previousN);
						 insertN(tmp,walker,walker.nextN);
                         /*
                          * keep track of current cursor position, so that when a node is moved more than
                          * one place to the left, the traversal will resume at the original location of n
                          * rather than at n-1, n-2, etc. Saves extra unneeded iterations.
                          */
                         if(!cursor) {
                             n=walker.nextN;
                             cursor = true;
                         }
					 }
					 else end=true;
					 if(walker.previousN==headN)end=true;
				 }
				 cursor=false;
			 }
		 }
		 else{
			 for (Node b = headB.nextB; b !=headB; b = b.nextB) {
				 end=false;
				 /*
				  * uses walker node to walk backwards across the list when a node to sort has
				  * been found, and n to traverse forwards across the list
				  */
				 walker= b;
				 while(!end){
					 if(comp.compare(walker,walker.previousB)<0 ){
						 tmp = walker.previousB;
						 removeB(walker.previousB);
						 insertB(tmp,walker,walker.nextB);
                         if(!cursor) {
                             b=walker.nextB;
                             cursor = true;
                         }
					 }
					 else end=true;
					 if(walker.previousB==headB)end=true;
				 }
				 cursor = false;
			 }
		 }
	 }

    /**
     * Remove a node from n-list only. Will not change size, will not
     * nullify node itself. Only for use inside insertionSort and remove(Node).
     *
     * @param nNode node to remove
     */
    private void removeN(Node nNode) {
        nNode.previousN.nextN = nNode.nextN;
        nNode.nextN.previousN = nNode.previousN;
    }

    /**
     * Remove a node from b-list only. Will not change size, will not
     * nullify node itself. Only for use inside insertionSort and remove(Node).
     *
     * @param bNode node to be removed
     */
    private void removeB(Node bNode) {
        bNode.previousB.nextB = bNode.nextB;
        bNode.nextB.previousB = bNode.previousB;
    }


    /**
	  * Add a node between two nodes prev and next in the N-list.   
	  * 
	  * @param node
	  * @param prev  preceding node after addition 
	  * @param next  succeeding node 
	  */
	 private void insertN(Node node, Node prev, Node next)
	 {
		 node.previousN = prev;
		 node.nextN = next;
		 prev.nextN = next.previousN = node;
	 }
	
	 
	 /**
	  * Add a node between two nodes prev and next in the B-list.  
	  * 
	  * @param node
	  * @param prev  preceding node 
	  * @param next  succeeding node 
	  */
	 private void insertB(Node node, Node prev, Node next)
	 {
		 node.nextB = next;
		 node.previousB = prev;
		 prev.nextB = next.previousB = node;
	 }
	 
	 
	 /**
	  * Remove node from both linked lists. 
	  * 
	  * @param node
	  */
	 private void remove(Node node)
	 {
		 //bridges gap between next and previous nodes
         removeN(node);
         removeB(node);
		 size--;
		 // sets node = null and relies on garbage collector to handle freeing memory
		 node =null;
	 }

	/**
	 * Sort an array of fruit names using quicksort.  After sorting, quant[i] is the
	 * quantity of the fruit with name[i].
	 *
	 * Made a public method for testing by TA.
	 *
	 * @param fruit array of fruit names
	 * @param quant	array of fruit quantities
	 */
	public void quickSort(String fruit[], Integer quant[], int size)
	{
		quickSortRec(fruit,quant,0,size-1);
	}

    /**
     * Recursive quicksort method which allows for specifying upper and lower bound in order to operate on
     * different sections of the same array.
     * @param fruit the fruit list
     * @param quant the fruit quantity list
     * @param first the lower bound
     * @param last the upper bound
     */
	private void quickSortRec(String fruit[], Integer quant[], int first, int last){
        if (last <= first) return;
        int j = partition(fruit,quant, first, last);
        quickSortRec(fruit, quant, first, j-1);
        quickSortRec(fruit, quant, j+1, last);
    }

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
		 int i = first;
		 int j = last + 1;
			while (true) {
				// find element greater than pivot
				while (fruit[++i].compareTo(fruit[first]) < 0)
					if (i == last) break;

				// find element less than pivot
				while (fruit[first].compareTo(fruit[--j]) < 0)
					if (j == first) break;

				// check if markers cross
				if (i >= j) break;

				swap(fruit,i, j);
				swap(quant,i, j);
			}

		 // swap pivot element with element at position where markers cross
		 swap(fruit,first, j);
		 swap(quant,first, j);

		 //return new midpoint by which to divide into sub-arrays
		 return j;
	 }

	/**
	 * Swap the two elements indexed at i and j respectively in the array objects[].
	 *
	 * @param i
	 * @param j
	 */
	private void swap(Integer[] objects, int i, int j)
	{
		Integer tmp = objects[i];
		objects[i] = objects[j];
		objects[j] = tmp;
	}

	/**
	 * Swap the two elements indexed at i and j respectively in the array objects[].
	 *
	 * @param i
	 * @param j
	 */
	private void swap(String[] objects, int i, int j)
	{
		String tmp = objects[i];
		objects[i] = objects[j];
		objects[j] = tmp;
	}

}
