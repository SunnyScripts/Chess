package com.turingweb.graphics_utilities.image;

/**
 * Created by Ryan Berg on 11/16/15. rberg2@hotmail.com
 */
//TODO take objects with only one channel!
//TODO add radius option
public class NeighborFetcher
{
    protected final byte HEIGHT_NOT_GIVEN = -1;
    protected final byte INDEX_NOT_GIVEN = -2;

    //the nine boundary states an index can be in.
    //
    //      9  1  3
    //      8  0  2
    //     12  4  6
    //
    protected final byte NO_BOUNDARY = 0;
    protected final byte TOP_BOUNDARY = 1;
    protected final byte RIGHT_BOUNDARY = 2;
    protected final byte BOTTOM_BOUNDARY = 4;
    protected final byte LEFT_BOUNDARY = 8;

    protected final byte TOP_LEFT_CORNER_BOUNDARY = 9;
    protected final byte BOTTOM_RIGHT_CORNER_BOUNDARY = 6;
    protected final byte BOTTOM_LEFT_CORNER_BOUNDARY = 12;
    protected final byte TOP_RIGHT_CORNER_BOUNDARY = 3;


    //TODO get rid of this ugly mess
    protected int TOP_NEIGHBOR_INDEX;
    protected int TOP_RIGHT_NEIGHBOR_INDEX;
    protected int RIGHT_NEIGHBOR_INDEX;
    protected int BOTTOM_RIGHT__NEIGHBOR_INDEX;
    protected int BOTTOM_NEIGHBOR_INDEX;
    protected int BOTTOM_LEFT_NEIGHBOR_INDEX;
    protected int LEFT_NEIGHBOR_INDEX;
    protected int TOP_LEFT_NEIGHBOR_INDEX;

    protected int arrayIndex = INDEX_NOT_GIVEN;
    protected int channelWidth = 1;
    //this value allows for returning the index position of the value as well as the value itself
    //the value is every even number, the position the odd index directly following
    // For example: array = { value, position, value, position, value, position};
    protected boolean isReturningPositionData = false;
    //this mechanism is implemented by setting the following value to
    // 1: FALSE
    // 2: TRUE
    protected byte returningPositionValue = 1;

    public NeighborFetcher()
    {
    }

    public NeighborFetcher(int channelWidth, boolean isReturningPositionData)
    {
        this.channelWidth = channelWidth;
        this.isReturningPositionData = isReturningPositionData;
    }

    int[] getNeighbors(int arrayIndex, byte[] array, int arrayWidth)
    {
        this.arrayIndex = arrayIndex;

        int x = arrayIndex % arrayWidth;
        int y = (arrayIndex - x)/arrayWidth;
        return getNeighbors(x, y, array, arrayWidth);
    }
    int[] getNeighbors(int arrayIndex, byte[] array, int arrayWidth, int arrayHeight)
    {
        this.arrayIndex = arrayIndex;

        int x = arrayIndex % arrayWidth;
        int y = (arrayIndex - x)/arrayWidth;
        return getNeighbors(x, y, array, arrayWidth, arrayHeight);
    }

    int[] getNeighbors(int x, int y, byte[] array, int arrayWidth)
    {
        return getNeighbors(x, y, array, arrayWidth, HEIGHT_NOT_GIVEN);
    }

    int[] getNeighbors(int x, int y, byte[] array, int arrayWidth, int arrayHeight)
    {
        if(isReturningPositionData)
        {
            returningPositionValue = 2;
        }
        if(arrayHeight == HEIGHT_NOT_GIVEN)
        {
            arrayHeight = array.length/arrayWidth;
        }
        if(arrayIndex == INDEX_NOT_GIVEN)
        {
            arrayIndex = y * arrayWidth + x;
        }


        //is index an edge or corner
        int pixelBoundaryState = NO_BOUNDARY;
        if(y == 0)
        {
            pixelBoundaryState += TOP_BOUNDARY;
        }
        else if(y == arrayHeight-1)
        {
            pixelBoundaryState += BOTTOM_BOUNDARY;
        }

        if(x == 0)
        {
            pixelBoundaryState += LEFT_BOUNDARY;
        }
        else if(x == arrayWidth-1)
        {
            pixelBoundaryState += RIGHT_BOUNDARY;
        }
//        System.out.println("boundary state "+pixelBoundaryState+"\narray index "+arrayIndex);

        //index location of every neighbor
        int[] neighborArray = null;
        TOP_NEIGHBOR_INDEX = arrayIndex - arrayWidth;
        TOP_LEFT_NEIGHBOR_INDEX = TOP_NEIGHBOR_INDEX - 1;
        TOP_RIGHT_NEIGHBOR_INDEX = TOP_NEIGHBOR_INDEX + 1;
        RIGHT_NEIGHBOR_INDEX = arrayIndex + 1;
        LEFT_NEIGHBOR_INDEX = arrayIndex - 1;
        BOTTOM_NEIGHBOR_INDEX = arrayIndex + arrayWidth;
        BOTTOM_RIGHT__NEIGHBOR_INDEX = BOTTOM_NEIGHBOR_INDEX + 1;
        BOTTOM_LEFT_NEIGHBOR_INDEX = BOTTOM_NEIGHBOR_INDEX - 1;

        //
        //each neighbor is a potential starting point for a boundary condition.
        //
        //taking the number of neighbors {"corner" = 3, "edge" = 5", "normal" = 8}
        //multiplied by the width of the channel {"rgb" = 3, "rgba" = 4, "default" = 1}
        //gives you the size of the array needed to hold all neighbors.
        //
        //to choose a valid starting point, start at the TOP neighbor and move clockwise to each neighbor.
        //
        //TODO this mess is not cool man
        int[] indexArray = {TOP_NEIGHBOR_INDEX*channelWidth, TOP_RIGHT_NEIGHBOR_INDEX*channelWidth, RIGHT_NEIGHBOR_INDEX*channelWidth, BOTTOM_RIGHT__NEIGHBOR_INDEX*channelWidth,
                BOTTOM_NEIGHBOR_INDEX*channelWidth, BOTTOM_LEFT_NEIGHBOR_INDEX*channelWidth, LEFT_NEIGHBOR_INDEX*channelWidth, TOP_LEFT_NEIGHBOR_INDEX*channelWidth,
                TOP_NEIGHBOR_INDEX*channelWidth, TOP_RIGHT_NEIGHBOR_INDEX*channelWidth, RIGHT_NEIGHBOR_INDEX*channelWidth};

//        System.out.println("Pixel boundary state " + pixelBoundaryState);

        //get neighbors based on input index location
        switch(pixelBoundaryState)
        {
            case NO_BOUNDARY:
                neighborArray = buildNeighborArray(8, 0, indexArray, array);
                break;

            case TOP_BOUNDARY:
                neighborArray = buildNeighborArray(5, 2, indexArray, array);
                break;

            case TOP_RIGHT_CORNER_BOUNDARY:
                neighborArray = buildNeighborArray(3, 4, indexArray, array);
                break;

            case RIGHT_BOUNDARY:
                neighborArray = buildNeighborArray(5, 4, indexArray, array);
                break;

            case BOTTOM_RIGHT_CORNER_BOUNDARY:
                neighborArray = buildNeighborArray(3, 6, indexArray, array);
                break;

            case BOTTOM_BOUNDARY:
                neighborArray = buildNeighborArray(5, 6, indexArray, array);
                break;

            case BOTTOM_LEFT_CORNER_BOUNDARY:
                neighborArray = buildNeighborArray(3, 0, indexArray, array);
                break;

            case LEFT_BOUNDARY:
                neighborArray = buildNeighborArray(5, 0, indexArray, array);
                break;

            case TOP_LEFT_CORNER_BOUNDARY:
                neighborArray = buildNeighborArray(3, 2, indexArray, array);
                break;
        }
        arrayIndex = INDEX_NOT_GIVEN;
        return neighborArray;
    }
    protected int[] buildNeighborArray(int numberOfNeighbors, int startingPoint, int[] indexArray, byte[] array)
    {
        int[] neighborArray = new int[numberOfNeighbors*channelWidth*returningPositionValue];

        for(int i = 0, j = 0; j < numberOfNeighbors; i+=channelWidth*returningPositionValue, j++)
        {
            switch (channelWidth*returningPositionValue)
            {
                case 1:
                    neighborArray[i] = array[indexArray[j+startingPoint]];
                    break;
                case 2:
//                    System.out.println("N "+neighborArray.length + "  " + i);
//                    System.out.println("I "+indexArray.length + "  " + j+ " "+"starting point "+startingPoint);
//                    System.out.println("A "+array.length + "  " + indexArray[j+startingPoint]);
                    neighborArray[i] = array[indexArray[j+startingPoint]];
                    neighborArray[i+1] = indexArray[j+startingPoint];
                    break;
                case 3:


                    neighborArray[i] = array[indexArray[j+startingPoint]];
                    neighborArray[i+1] = array[indexArray[j+startingPoint]+1];
                    neighborArray[i+2] = array[indexArray[j+startingPoint]+2];
                    break;
                case 6:
                    break;
                case 4:
                    neighborArray[i] = array[indexArray[j+startingPoint]];
                    neighborArray[i+1] = array[indexArray[j+startingPoint]+1];
                    neighborArray[i+2] = array[indexArray[j+startingPoint]+2];
                    neighborArray[i+3] = array[indexArray[j+startingPoint]+3];
                    break;
                case 8:
                    break;
            }
          }
        return neighborArray;
    }
}