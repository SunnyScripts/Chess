/**
 * Created by Ryan Berg on 11/2/15. rberg2@hotmail.com
 *
 */

package com.turingweb.data_structures;

public class HashTable
{
    protected int arraySize = 32;
    protected LinkedList[] hashArray = new LinkedList[arraySize];
    protected int numberOfEntries = 0;

    HashTable()
    {
        for(int i = 0; i < arraySize; i++)
        {
            hashArray[i] = new LinkedList();
        }
    }

    protected int hash(byte[] key)
    {
        int hashValue = 0;
        for(int i = 0; i < key.length; i++)
        {
            hashValue = (127 * hashValue + key[i]) % 16908799;
        }
        hashValue %= arraySize;

        return hashValue;
    }
    protected void rehash(int newArraySize)
    {

    }
    void insert(byte[] key, Object value)
    {
        hashArray[hash(key)].addElement(key, value);
        numberOfEntries++;

        if(numberOfEntries/arraySize > .75)
        {
            rehash(arraySize * 2);
        }
        else if(arraySize > 512 && numberOfEntries/arraySize < .25)
        {
            rehash(arraySize/2);
        }
    }
//    Object find(String key)
//    {
//        return hashArray[hash(key)].find(key);
//    }
}
