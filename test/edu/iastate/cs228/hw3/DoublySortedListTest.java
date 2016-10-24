package edu.iastate.cs228.hw3;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas John Wesolowski : wojoinc@iastate.edu on 10/16/2016.
 * Created under Github user WojoInc
 */
public class DoublySortedListTest {
    DoublySortedList dsl;
    @Before
    public void setUp() throws Exception {
       // dsl = new DoublySortedList("C:\\Users\\97wes\\IdeaProjects\\COMS228\\src\\edu\\iastate\\cs228\\hw3\\test.txt");
       // System.out.println(dsl.toString());
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
        System.out.println(dsl.toString());
    }

    @Test
    public void restock() throws Exception {
        dsl = new DoublySortedList();
        dsl.add("apple",10);
        dsl.add("apple",10);
        dsl.add("peach",10);
        assertTrue(dsl.inquire("apple")==20);
        System.out.println(dsl.toString());
        dsl.restock("C:\\Users\\97wes\\IdeaProjects\\COMS228\\src\\edu\\iastate\\cs228\\hw3\\test.txt");
        System.out.println(dsl.toString());

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
        dsl = new DoublySortedList();
        dsl.add("apple",25);
        dsl.add("kiwi",35);
        dsl.add("banana",20005);
        dsl.add("orange",31);
        dsl.add("grape",25);
        System.out.println(dsl.toString());
        dsl.bulkSell("C:\\Users\\97wes\\IdeaProjects\\COMS228\\src\\edu\\iastate\\cs228\\hw3\\test.txt");
        System.out.println(dsl.toString());
        assertTrue(dsl.inquire("apple")==15);
        assertTrue(dsl.inquire("kiwi")==20);
        assertTrue(dsl.inquire("banana")==19000);
        assertTrue(dsl.inquire("orange")==30);
        assertTrue(dsl.inquire("grape")==0);
    }

    @Test
    public void printInventoryN() throws Exception {
        dsl.add("Apple",10);
        dsl.add("Orange",16);
        dsl.add("Banana",12);
        dsl.add("Peach",16);
        dsl.add("Grape",19);
        dsl.add("Lime",16);
        dsl.remove("Apple");
        dsl.remove("Peach");
        dsl.remove("Grape");
        dsl.compactStorage();
        dsl.add("Kiwi",59);
        System.out.println(dsl.printInventoryN());
    }

    @Test
    public void printInventoryB() throws Exception {
        dsl.add("Apple",10);
        dsl.add("Orange",16);
        dsl.add("Banana",12);
        dsl.add("Peach",16);
        dsl.add("Grape",19);
        dsl.add("Lime",16);
        dsl.remove("Apple");
        dsl.remove("Peach");
        dsl.remove("Grape");
        dsl.compactStorage();
        dsl.add("Kiwi",59);
        System.out.println(dsl.printInventoryB());
    }

    @Test
    public void toString1() throws Exception {
        dsl.add("Apple",10);
        dsl.add("Orange",16);
        dsl.add("Banana",12);
        dsl.add("Peach",16);
        dsl.add("Grape",19);
        dsl.add("Lime",16);
        dsl.remove("Apple");
        dsl.remove("Peach");
        dsl.remove("Grape");
        dsl.compactStorage();
        dsl.add("Kiwi",59);
        System.out.println(dsl.toString());
    }

    @Test
    public void compactStorage() throws Exception {

        dsl.add("Apple",10);
        dsl.add("Orange",16);
        dsl.add("Banana",12);
        dsl.add("Peach",16);
        dsl.add("Grape",19);
        dsl.add("Lime",16);
        dsl.remove("Apple");
        dsl.remove("Peach");
        dsl.remove("Grape");
        dsl.compactStorage();
        dsl.add("Kiwi",59);
    }

    @Test
    public void clearStorage() throws Exception {

    }

    @Test
    public void split() throws Exception {
        dsl = new DoublySortedList("C:\\Users\\97wes\\IdeaProjects\\COMS228\\src\\edu\\iastate\\cs228\\hw3\\insertionsorttest.txt");
        Pair p = dsl.split();
        System.out.println(((DoublySortedList)p.getFirst()).printInventoryN());
        System.out.println(((DoublySortedList)p.getSecond()).toString());
    }

    @Test
    public void insertionSort() throws Exception {
        dsl = new DoublySortedList("C:\\Users\\97wes\\IdeaProjects\\COMS228\\src\\edu\\iastate\\cs228\\hw3\\insertionsorttest.txt");
        System.out.println(dsl.printInventoryN());
        System.out.println(dsl.printInventoryB());
    }

    @Test
    public void quickSort() throws Exception {

    }

}