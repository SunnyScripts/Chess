package com.turingweb.data_structures;

/**
 * Created by ryanberg on 11/19/15.
 */
public class Queue extends LinkedList
{
    public void enqueue(Object value)
    {
//        System.out.println("add to queue "+value);
        addElement(null, value);
    }
    public Object dequeue()
    {
        LinkedListNode firstObject = headReference;
        if(firstObject == null)
        {
            return null;
        }
        headReference = headReference.nextReference;
        listLength--;
        return firstObject.getValue();
    }
}
