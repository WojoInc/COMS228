package edu.iastate.cs228.hw5;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;

/**
 * @author
 */
public class ABTreeSet<E extends Comparable<? super E>> extends AbstractSet<E> {

	final class ABTreeIterator implements Iterator<E> {
		// TODO add private fields here

        Node root;
        Node cursor;

		ABTreeIterator() {


		}

		@Override
		public boolean hasNext() {
			// TODO

			return false;
		}

		@Override
		public E next() {
			// TODO

			return null;
		}

		@Override
		public void remove() {
			// TODO

		}
	}

	final class Node implements BSTNode<E> {
		int count;
		public E data;
		public BSTNode<E> left;
		public BSTNode<E> right;
		public BSTNode<E> parent;

		// TODO add private fields here

		Node(E data, BSTNode<E> parent) {
			this.data = data;
            this.parent = parent;

		}

		@Override
		public int count() {
			return count;
		}

		public void updateCount(){
            count++;
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

	// TODO add private fields here

    private Node root;
	private int size;
    private static final int _TOP = 2;
    private static final int _BOTTOM = 3;

	/**
	 * Default constructor. Builds a non-self-balancing tree.
	 */
	public ABTreeSet() {
		// TODO

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
		// TODO

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

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException
	 *             if e is null.
	 */
	@Override
	public boolean add(E e) {
		// TODO
        if(e==null) throw new NullPointerException("Cannot add null element.");
        if(root==null){
            root = new Node(e,null);
            root.updateCount();
            return true;
        }
        Node curr = root;
        int res;
        while (true){
            res = curr.data().compareTo(e);
            if(res==0) return false; //already in set
            else if(res > 0){
                if(curr.left!=null) curr = (Node)curr.left();
                else{
                    curr.left = new Node(e,curr);
                    curr.left.updateCount();
                    return true;
                }
            }
            else{
                if(curr.right!=null) curr = (Node)curr.right();
                else{
                    curr.right = new Node(e,curr);
                    curr.right.updateCount();
                    return true;
                }
            }
        }
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
		// TODO

		return null;
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
		// TODO

		return null;
	}

	/**
	 * Performs a re-balance operation on the subtree rooted at the given node.
	 * 
	 * @param bstNode
	 */
	public void rebalance(BSTNode<E> bstNode) {
		// TODO

	}

	@Override
	public boolean remove(Object o) {
		// TODO
		BSTNode node = getBSTNode((E) o);
		if(node==null) return false;
		unlinkNode(node);
		return true;
	}

	/**
	 * Returns the root of the tree.
	 * 
	 * @return the root of the tree.
	 */
	public BSTNode<E> root() {
		// TODO

		return null;
	}

	public void setSelfBalance(boolean isSelfBalance) {
		// TODO

	}

	@Override
	public int size() {
		// TODO

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
		// TODO

		return null;
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
