/**
 * Created by Ryan Berg on 11/2/15. rberg2@hotmail.com
 *
 */

package com.turingweb.data_structures;


public class LinkedList
{
    protected int listLength = 0;
    protected LinkedListNode headReference = null;

    void addElement(Object key, Object value)
    {
        headReference = new LinkedListNode(key, value, headReference);
        listLength ++;
    }

    Object find(Object key)
    {
        LinkedListNode currentNode = headReference;

        for(int i = 0; i < listLength; i++)
        {
            if(currentNode.getKey(key) != null)
            {
                return currentNode.getKey(key);
            }
            currentNode = currentNode.getNextReference();
        }
        return "key not found";
    }

    //TODO public?
    public LinkedListNode getHeadReference() {
        return headReference;
    }

    public int getListLength() {
        return listLength;
    }
}
