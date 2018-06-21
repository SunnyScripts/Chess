package com.turingweb.graphics_utilities.image;

import java.io.BufferedReader;
import java.io.IOException;
import com.turingweb.data_structures.Queue;

/**
 * Created by ryanberg on 11/19/15.
 */
public class LargestBlob
{
    final static byte ASCII_0 = 48;

    byte[] binaryArray = {};

    int width = 0;
    int height = 0;

    NeighborFetcher neighborFetcher = new NeighborFetcher(1, true);
    Queue queue = new Queue();

    public int getLargestBlob(BufferedReader bufferedReader)
    {
        int largestBlob = 0;

        try {
            width = Integer.parseInt(bufferedReader.readLine());
            height = Integer.parseInt(bufferedReader.readLine());

            binaryArray = new byte[width * height];

            for (int i = 0; i < width * height; i++) {
                binaryArray[i] = (byte) (bufferedReader.read() - ASCII_0);
//                System.out.println(binaryArray[i]);
                bufferedReader.skip(1);
            }
        }
        catch (IOException exception)
        {
            System.err.println(exception.getMessage());
            exception.printStackTrace();
        }

        int currentBlobSize;

        for(int i = 0; i < binaryArray.length; i++)
        {
            if(binaryArray[i] == 1)
            {
                //create queue // should be able to use one queue
                //add self to queue
                queue.enqueue(i);
                //set self to 0
                binaryArray[i] = 0;
                //call findTrueNeighbors
                int counter = 0;
                while(queue.getListLength() > 0)
                {
                    counter += findTrueNeighbors();
                }
                currentBlobSize = counter;
                //if counter > largestBlob, counter = largestBlob
                if(currentBlobSize > largestBlob)
                {
                    largestBlob = currentBlobSize;
                }
            }
        }
//        System.out.println("bin array");
//        for(Byte value : binaryArray)
//        {
//            System.out.println(value);
//        }
        return largestBlob;
    }
    protected int findTrueNeighbors()
    {
        //TODO everything above if(index == null) gets called many times recursively, ie queue.dequeue, can this be optimized?
        int counter = 0;
        int[] neighborsArray;
        //queue.remove
        Integer index = (Integer)queue.dequeue();
//        System.out.println("first item pulled " +index);
        //if queue.empty return counter
        if(index == null)
        {
            return counter;
        }
        //increment counter
        counter++;
        //add true neighbors to queue
        neighborsArray = neighborFetcher.getNeighbors(index, binaryArray, width, height);
//        System.out.println("neighbors and indices");
//        for(Integer value : neighborsArray)
//        {
//            System.out.println(value);
//        }
        for(int i = 0; i < neighborsArray.length; i+=2)
        {
            if(neighborsArray[i] == 1)
            {
                queue.enqueue(neighborsArray[i+1]);
                //set their values to 0
//                System.out.println("NA "+neighborsArray[i]);
//                System.out.println("zeroing out " +neighborsArray[i+1]);
                binaryArray[neighborsArray[i+1]] = 0;
            }
        }
//        System.out.println("Bin Array");
//        for(Byte value : binaryArray)
//        {
//            System.out.println(value);
//        }

        return counter;
    }
}
