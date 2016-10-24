package edu.iastate.cs228.hw2;

/**
 * ${FILE_NAME} part of project COMS228 in package edu.iastate.cs228.hw2
 * Created by Thomas Wesolowski on 10/24/2016.
 */
public class HeapSorter extends AbstractSorter {

    private HeapNode[] tree;

    private class HeapNode<T> {
        public HeapNode subNode0;
        public HeapNode subNode1;
        public Comparable<T> value;

        public int compareTo(HeapNode<T> n) {
            return value.compareTo((T)n.value);
        }
        }

        public HeapSorter(Point[] pts){
            super(pts);
            algorithm = "heapsort";
            outputFileName = "heap.txt";
        }

    @Override
    public void sort(int order) throws IllegalArgumentException {
        //TODO implement sorting algortihm
    }
}
