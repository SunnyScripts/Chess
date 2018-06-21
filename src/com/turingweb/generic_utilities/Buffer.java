package com.turingweb.generic_utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by ryanberg on 12/6/15. rberg2@hotmail.com
 */
public class Buffer
{
    private Buffer(){}

    public static ByteBuffer arrayToBuffer(byte[] byteArray)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(byteArray.length).order(ByteOrder.nativeOrder());
        byteBuffer.put(byteArray).flip();
        return byteBuffer;
    }
}
