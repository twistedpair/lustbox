package com.github.twistedpair.datastructures;

import java.util.*;

/**
 * Class for creating a custom hashtable with integer keys and values.
 * The current hash function uses mod.
 * Collisions are handled through chaining.
 */
public class IntHashTable {

    /**
     * Internal array for hash table
     */
    private IntNode arr[];

    /** 
     * Size of array and used for mod function
     * Should be a large prime number 
     * Should not be a power of 2 but can be close to a power of 2
     */
    private int size;

    /**
     * Stores current number of collisions
     */
    private int collisionCnt;

    public IntHashTable(int size) {
        this.size = size;
        this.arr = new IntNode[size];
        this.collisionCnt = 0;
    }

    /**
     * Puts key, value pair in array
     * Updates key if key already exists
     * Increments collision counter if collision
     */
    public void put(int key, int value) {
        int insPos = hashFunction(key);
        
        // check if already in array
        IntNode intNode = arr[insPos];

        while (intNode != null) {

            // if in array update value, return
            if (intNode.key == key) {
                intNode.value = value;
                return;
            }
            intNode = intNode.next;
        }

        // create a new IntNode
        intNode = new IntNode(key, value);
        if (arr[insPos] != null) {
            intNode.next = arr[insPos];
            this.collisionCnt++;
        }

        // put at normal position, no collision
        arr[insPos] = intNode;
    }

    /**
     * Removes key from hash table
     * throws NoSuchElementException if it does exist
     * Decrements collision counter
     */
    public void remove(int key) {
        int pos = hashFunction(key);
        IntNode node = arr[pos];

        if (node == null) throw new NoSuchElementException();

        // Handle head of list being equal to key
        if (node.key == key) {
            arr[pos] = node.next;
            if (node.next != null)
                this.collisionCnt--;
            node = null;
            return;
        }

        IntNode nextNode = node.next;

        // Check key of nextNode in loop
        while (nextNode != null) {
            if (nextNode.key == key) {
                this.collisionCnt--;
                node.next = nextNode.next;
                nextNode = null;
                return;
            }
            node = nextNode;
            nextNode = nextNode.next;
        }

        // If no element matches key, throw exception
        throw new NoSuchElementException();
    }

    public boolean hasKey(int key) {
        int pos = hashFunction(key);
        IntNode node = arr[pos];
        while(node != null) {
            if (node.key == key)
                return true;
            node = node.next;
        }
        return false;
    }

    /**
     * Returns value if exists
     * Return null if not exists
     */
    public Integer get(int key) {
        int pos = hashFunction(key);
        IntNode node = arr[pos];
        while(node != null) {
            if (node.key == key)
                return node.value;
            node = node.next;
        }
        return null;
    }

    public void printTable() {
        System.out.println("Table:");
        for (int i = 0; i < size; i++) {
            IntNode node = arr[i];
            while (node != null) {
                System.out.println(node.key + " " + node.value);
                node = node.next;
            }
        }
        System.out.println("Number of collisions: " + this.collisionCnt);
    }

    public int getNumCollisions() {
        return this.collisionCnt;
    }

    private int hashFunction(int key) {
        return key % size;
    }

    /**
     * Internal helper class
     */
    private class IntNode{
        public int key, value;
        public IntNode next;
        public IntNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }
}
