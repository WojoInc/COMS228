package edu.iastate.cs228.hw2;

/**
 * ${FILE_NAME} part of project COMS228 in package edu.iastate.cs228.hw2
 * Created by Thomas Wesolowski on 10/24/2016.
 */
public class HeapSorter<E extends Comparable> extends AbstractSorter {

    private HeapNode[] tree;
    private HeapNode head;
    //private HeapNode cursor;
    private HeapNode tail;

    private int size;

    private class HeapNode {
        public HeapNode parent;
        public HeapNode left;
        public HeapNode right;
        public E value;
        public int index;

        public int compareTo(HeapNode n) {
            return value.compareTo(n.value);
        }
        HeapNode(){
            //blank heap node
            parent = null;
            left = null;
            right = null;
            value = null;
            index = 0;
        }
        HeapNode(E value){
            this();
            this.value = value;
        }
        HeapNode(HeapNode parent,int index){
            this();
            this.parent = parent;
            this.index = index;
        }
        HeapNode(E value,HeapNode parent, int index){
            this.index = index;
            this.parent = parent;
            this.value = value;
            this.left = new HeapNode(this,this.index*2);
            this.right = new HeapNode(this,this.index*2 + 1);
        }
    }

    public HeapSorter(Point[] pts){
        super(pts);
        algorithm = "heapsort";
        outputFileName = "heap.txt";
        head = tail = new HeapNode();
        tail.parent = head;
    }

    int add(E value){
        if(size == 0){
            head = new HeapNode(value);
            head.index = ++size;
            head.left = tail;
            tail.index = 2;
            tail.parent = head;
            return head.index;
        }
        else{
            if(tail.index%2==0){
                tail.parent.left = new HeapNode(value,tail.parent,2*tail.parent.index);
                //TODO finish add method logic
                tail = tail.parent.right;

            }else{
                tail.parent.right = new HeapNode(value,tail.parent,2*tail.parent.index +1);
                if(tail.parent.parent.index == 2*tail.parent.index +1){
            }

        }
        return tail.index;
    }

    @Override
    public void sort(int order) throws IllegalArgumentException {
        //TODO implement sorting algortihm
    }
}
