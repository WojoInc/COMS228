package edu.iastate.cs228.hw2;

/**
 * ${FILE_NAME} part of project COMS228 in package edu.iastate.cs228.hw2
 * Created by Thomas Wesolowski on 10/24/2016.
 */
public class HeapSorter extends AbstractSorter {

    HeapSorter(){
        super();
        algorithm = "heapsort";
        outputFileName = "heap.txt";
    }


    @Override
    public void sort(int order) throws IllegalArgumentException {
        //TODO implement sorting algortihm
        int lastBranch = (points.length-3)/2;

    }
}
