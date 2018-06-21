/**
 * Created by Ryan Berg on 11/2/15. rberg2@hotmail.com
 *
 */

package com.turingweb.data_structures;

class LinkedListNode extends Entity
{
    protected LinkedListNode nextReference;

    LinkedListNode(Object key, Object value, LinkedListNode nextReference)
    {
        put(key, value);
        this.nextReference = nextReference;
    }

    void setNextReference(LinkedListNode nextReference)
    {
        this.nextReference = nextReference;
    }
    LinkedListNode getNextReference()
    {
        return nextReference;
    }
}

