package edu.iastate.cs228.hw5;

import java.util.*;

/**
 * @author
 */
public class ABTreeSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	final class ABTreeIterator implements Iterator<E> {

        //The node keeping track of the iterators place in the tree
        Node cursor;
        //The node whoose value is accessed by calls to next()
        Node current;

		ABTreeIterator() {
            cursor = root;
            if(cursor!=null){
                while(cursor.left!=null){
                    cursor = (Node)cursor.left;
                }
            }
		}

		@Override
		public boolean hasNext() {
			return cursor!=null;
		}

		@Override
		public E next() {
            if(!hasNext()) throw new NoSuchElementException("End of tree reached.");
            current = cursor;
            cursor = (Node)successor(cursor);
			return current.data;
		}

        public BSTNode<E> nextNode() {
            if(!hasNext()) throw new NoSuchElementException("End of tree reached.");
            current = cursor;
            cursor = (Node)successor(cursor);
            return current;
        }
		@Override
		public void remove() {
            /*
             * Disallow subsequent calls to remove()
             */
			if(current==null) throw new IllegalStateException("No element to remove, must call next() first");

            /*
             * Copy data from last node accessed by call to next() into the cursor node, then bridge
             * gaps between links by calling unlinkNode(). Set current node == null, such that
             * remove may not be run again without first calling next().
             */
            if(current.left!=null&&current.right!=null) cursor = current;
            current.updateCount(false);
            unlinkNode(current);
            current = null;

		}
	}

	final class Node implements BSTNode<E> {
		int count;
		public E data;
		public BSTNode<E> left;
		public BSTNode<E> right;
		public BSTNode<E> parent;

		Node(E data, BSTNode<E> parent) {
			this.data = data;
            this.parent = parent;

		}

		@Override
		public int count() {
			return count;
		}

		public void updateCount(boolean upDown){
		    if(upDown)count++;
		    else count--;
            if(this!=root) ((Node)parent).updateCount(upDown);
            else size = root.count;
        }

		@Override
		public E data() {
			return data;
		}

		@Override
		public BSTNode<E> left() {
			return left;
		}

		@Override
		public BSTNode<E> parent() {
			return parent;
		}

		@Override
		public BSTNode<E> right() {
			return right;
		}

		@Override
		public String toString() {
			return data.toString();
		}
	}

    private Node root;
	private int size;
	private boolean isRebalancing = false;
    private int _TOP = 2;
    private int _BOTTOM = 3;

	/**
	 * Default constructor. Builds a non-self-balancing tree.
	 */
	public ABTreeSet() {
		this(false);
	}

	/**
	 * If <code>isSelfBalancing</code> is <code>true</code> <br>
	 * builds a self-balancing tree with the default value alpha = 2/3.
	 * <p>
	 * If <code>isSelfBalancing</code> is <code>false</code> <br>
	 * builds a non-self-balancing tree.
	 * 
	 * @param isSelfBalancing
	 */
	public ABTreeSet(boolean isSelfBalancing) {
        root = null;
        size = 0;
        isRebalancing = isSelfBalancing;
	}

	/**
	 * If <code>isSelfBalancing</code> is <code>true</code> <br>
	 * builds a self-balancing tree with alpha = top / bottom.
	 * <p>
	 * If <code>isSelfBalancing</code> is <code>false</code> <br>
	 * builds a non-self-balancing tree (top and bottom are ignored).
	 * 
	 * @param isSelfBalancing
	 * @param top
	 * @param bottom
	 * @throws IllegalArgumentException
	 *             if (1/2 < alpha < 1) is false
	 */
	public ABTreeSet(boolean isSelfBalancing, int top, int bottom) {
		// TODO
        root = null;
        size = 0;
        _TOP = top;
        _BOTTOM = bottom;
        isRebalancing = isSelfBalancing;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException
	 *             if e is null.
	 */
	@Override
	public boolean add(E e) {
        if(e==null) throw new NullPointerException("Cannot add null element.");
        if(root==null){
            root = new Node(e,null);
            root.updateCount(true);
            return true;
        }
        boolean needsRebal = false; //detects if rebalance is needed.
        BSTNode<E> rebalNode = null; //node to rebalance from
        Node curr = root;
        BSTNode added = null;
        int res;

        while (added==null){
            res = curr.data().compareTo(e);
            if(res==0) return false; //already in set
            else if(res > 0){
                if(!needsRebal) {
                    needsRebal = checkWeight(curr, false);
                    rebalNode = curr;
                }
                if(curr.left!=null) curr = (Node)curr.left();
                else{
                    curr.left = new Node(e,curr);
                    added = curr.left;
                }
            }
            else{
                if(!needsRebal) {
                    needsRebal = checkWeight(curr, true);
                    rebalNode = curr;
                }
                if(curr.right!=null) curr = (Node)curr.right();
                else{
                    curr.right = new Node(e,curr);
                    added = curr.right;
                }
            }
        }

        if(isRebalancing&&needsRebal) rebalance(rebalNode);
        added.updateCount(true);
        return true;
	}

	private boolean checkWeight(BSTNode node, boolean tree){
	    //false = left, true = right
        int numLeft = 0, numRight = 0;
        if(tree) {
            numRight =1;
            numRight+=node.right().count();
        }
        else {
            numLeft =1;
            numLeft+=node.left().count();
        }
        if(((numLeft*_BOTTOM) > (root.count()*_TOP))||
                ((numRight*_BOTTOM) > (root.count()*_TOP)))
        return true;
        else return false;
    }

	@Override
	public boolean contains(Object o) {
        if(root==null)return false;
		if(o.getClass()!= root.data().getClass()) return false;
        return getBSTNode((E)o)!=null;
	}

	/**
	 * @param e
	 * @return BSTNode that contains e, null if e does not exist
	 */
	public BSTNode<E> getBSTNode(E e) {
		BSTNode<E> current = root();
        int res;
		while(current!=null){
            res = current.data().compareTo(e);
            if(res==0) return current;
            else if(res > 0) current = current.left();
            else current = current.right();
        }
		return null;
	}

	/**
	 * Returns an in-order list of all nodes in the given sub-tree.
	 * 
	 * @param root
	 * @return an in-order list of all nodes in the given sub-tree.
	 */
	public List<BSTNode<E>> inorderList(BSTNode<E> root) {
        ArrayList<BSTNode<E>> output = new ArrayList<>(size);
        ABTreeIterator iterator = new ABTreeIterator();
        while (iterator.hasNext()){
            output.add(iterator.nextNode());
        }
        return output;
	}

	@Override
	public Iterator<E> iterator() {
		return new ABTreeIterator();
	}

	/**
	 * Returns an pre-order list of all nodes in the given sub-tree.
	 * 
	 * @param root
	 * @return an pre-order list of all nodes in the given sub-tree.
	 */
	public List<BSTNode<E>> preorderList(BSTNode<E> root) {
        ArrayList<BSTNode<E>> output = new ArrayList<>(size);
        ABTreeIterator iterator = new ABTreeIterator();
        while (iterator.hasNext()){
            output.add(iterator.nextNode());
        }
        return output;
	}
	/**
	 * Performs a re-balance operation on the subtree rooted at the given node.
	 * 
	 * @param bstNode
	 */
	public void rebalance(BSTNode<E> bstNode) {

        ArrayList<BSTNode> list = (ArrayList) inorderList(bstNode);
        ArrayList<BSTNode> low = new ArrayList<>();
        ArrayList<BSTNode> high = new ArrayList<>();
        Node subRoot = (Node)splitList(list,low,high);
        if(subRoot.data.compareTo(bstNode.data())>0){
            subRoot.right = subRoot;
            subRoot.parent = bstNode;
        }
        else{
            subRoot.left = subRoot;
            subRoot.parent = bstNode;
        }
        subRoot.left = low.get(low.size()/2 -1);
        ((Node)subRoot.left).parent = subRoot;
        subRoot.right = high.get(high.size()/2 -1);
        ((Node)subRoot.right).parent = subRoot;
        if(low.size()>1){
            rebalanceRec(subRoot,low);
        }
        if(high.size()>1){
            rebalanceRec(subRoot,high);
        }

    }
	private void rebalanceRec(BSTNode<E> bstNode,ArrayList<BSTNode> toAdd){
        ArrayList<BSTNode> list = toAdd;
        ArrayList<BSTNode> low = new ArrayList<>();
        ArrayList<BSTNode> high = new ArrayList<>();
        Node subRoot = (Node)splitList(list,low,high);
        subRoot.left = low.get(low.size()/2 -1);
        ((Node)subRoot.left).parent = subRoot;
        subRoot.right = high.get(high.size()/2 -1);
        ((Node)subRoot.right).parent = subRoot;
        if(low.size()>1){
            rebalanceRec(subRoot,low);
        }
        if(high.size()>1){
            rebalanceRec(subRoot,high);
        }
    }
    private BSTNode<E> splitList(ArrayList<BSTNode> orig, ArrayList<BSTNode> low, ArrayList<BSTNode> high){
	    //ignoring unchecked casts as the method is private and I have only used arraylists, so cast
        // should be safe.
	    low = (ArrayList)orig.subList(0,orig.size()/2-1);
	    high = (ArrayList)orig.subList(orig.size()/2,orig.size());
	    return orig.get(orig.size()/2 -1);
    }
	@Override
	public boolean remove(Object o) {
		BSTNode node = getBSTNode((E) o);
		if(node==null) return false;
		node.updateCount(false);
		boolean needsRebalancing;
		needsRebalancing = checkWeight(node.parent(), true);
		needsRebalancing = checkWeight(node.parent(), false);
		unlinkNode(node);
		return true;
	}

	/**
	 * Returns the root of the tree.
	 * 
	 * @return the root of the tree.
	 */
	public BSTNode<E> root() {
		return root;
	}

	public void setSelfBalance(boolean isSelfBalance) {
		isRebalancing = isSelfBalance;
		rebalance(root);

	}

	@Override
	public int size() {
		return size;
	}

	public BSTNode<E> successor(BSTNode<E> node) {
        if(node==null) return null;
        else if(node.right()!=null){
            BSTNode<E> curr = node.right();
            while(curr.left()!=null) curr = curr.left();
            return curr;
        }
        /*
         * Iterate up the tree to find the node furthest down
         * that has a left child
         */
        else{
            BSTNode curr = node.parent();
            BSTNode child = node;
            while(curr!=null && curr.right() == child){
                child = curr;
                curr = curr.parent();
            }
            return curr;
        }
	}

	@Override
	public String toString() {
        if(size==0) return "ABSet is empty, nothing to return.";
        ABTreeIterator itr = (ABTreeIterator)iterator();
        String out = "";
        String offset = "                                   ";
        while(itr.hasNext()){
            BSTNode curr = null;
            BSTNode curr2 = itr.nextNode();
            int indent=0;
            while(curr!=root){
                curr=curr2.parent();
                indent++;
            }
            out = out + offset.substring(0,indent) + curr2.data()+"\n";
        }
        return out;
	}

	private void unlinkNode(BSTNode<E> node){
		//deal with case where there are two child nodes

		if(node.left() != null && node.right()!=null){
			BSTNode succ = successor(node);
			((Node)node).data = (E) succ.data();
			node = succ;
		}
		//node has only one child, left or right, not both
		//may also have no children, in which case replace is null
		Node replace = null;
		if(node.left()!=null)replace = (Node)node.left();
		else if(node.right()!=null) replace = (Node)node.right();

		//reattach seperated links

		if(node.parent()==null) root = replace;
		else{
			if (node==node.parent().left()) ((Node)node.parent()).left = replace;
			else ((Node)node.parent()).right = replace;
		}
		if(replace!=null){
			replace.parent = node.parent();
		}
		--size;
	}

}
