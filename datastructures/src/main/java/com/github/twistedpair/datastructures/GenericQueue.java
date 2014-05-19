package com.github.twistedpair.datastructures;

import java.util.*;

public class GenericQueue<T> {

    public Vector<T> v;

    public GenericQueue(int s) {
        
        v = new Vector<T>();
       
    }

    public T get(int i) {
        return v.get(i);
    }

}
