package edu.iastate.cs228.hw2;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * ${FILE_NAME} part of project COMS228 in package edu.iastate.cs228.hw2
 * Created by Thomas Wesolowski on 10/26/2016.
 */
public class HeapSorterTest {
    @Test
    public void add() throws Exception {
        HeapSorter<Integer> heap = new HeapSorter<>(new Point[5]);
        for (int i = 0; i < 6; i++) {
            heap.add(i);
        }
    }

}