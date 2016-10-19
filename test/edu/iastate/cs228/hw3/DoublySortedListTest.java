package edu.iastate.cs228.hw3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas John Wesolowski : wojoinc@iastate.edu on 10/16/2016.
 * Created under Github user WojoInc : wojoinc@gmail.com
 */
public class DoublySortedListTest {
    DoublySortedList dsl;
    @Before
    public void setUp() throws Exception {
        dsl = new DoublySortedList();
    }

    @Test
    public void size() throws Exception {
        dsl = new DoublySortedList(10,null,null);
        System.out.println("DSL size: " + dsl.size());
        assertTrue(dsl.size()==10);
    }

    @Test
    public void add() throws Exception {
        dsl = new DoublySortedList();
        dsl.add("Apple",10);
        assertTrue(dsl.inquire("Apple")==10);
        dsl.add("Apple",10);
        dsl.add("Peach",10);
        assertTrue(dsl.inquire("Apple")==20);
    }

    @Test
    public void restock() throws Exception {


    }

    @Test
    public void remove() throws Exception {
        dsl.add("Apple",10);
        dsl.add("Orange",16);
        dsl.remove("Apple");
        assertTrue(dsl.inquire("Apple")==0);
        assertTrue(dsl.inquire("Orange")==16);
        dsl.add("Apple",10);
        dsl.remove(1);
        assertTrue(dsl.inquire("Apple")==0);
        System.out.println(dsl.inquire("Orange"));
    }

    @Test
    public void sell() throws Exception {
        dsl.add("Apple",10);
        dsl.sell("Apple",5);
        System.out.println(dsl.inquire("Apple"));
        assertTrue(dsl.inquire("Apple")==5);
    }

    @Test
    public void bulkSell() throws Exception {

    }

    @Test
    public void printInventoryN() throws Exception {

    }

    @Test
    public void printInventoryB() throws Exception {

    }

    @Test
    public void toString1() throws Exception {

    }

    @Test
    public void compactStorage() throws Exception {

    }

    @Test
    public void clearStorage() throws Exception {

    }

    @Test
    public void split() throws Exception {

    }

    @Test
    public void insertionSort() throws Exception {

    }

    @Test
    public void quickSort() throws Exception {

    }

}