/**
 * Created by Ryan Berg on 11/4/15. rberg2@hotmail.com
 *
 */

package com.turingweb.data_structures;

public class Entity
{
    protected Object key;
    protected Object value;

    public void put(Object key, Object value)
    {
        this.key = key;
        this.value = value;
    }
    public Object getKey(Object key)
    {
        if(key == this.key)
        {
            return value;
        }
        return null;
    }
    public Object getValue()
    {
        return this.value;
    }
}
