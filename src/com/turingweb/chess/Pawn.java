package com.turingweb.chess;

import com.turingweb.graphics_utilities.opengl_utilities.Plane;
import com.turingweb.graphics_utilities.opengl_utilities.Texture;

/**
 * Created by ryanberg on 11/23/15.
 */
public class Pawn extends Plane implements GamePiece
{
    int boardWidth = 8;

    private final static byte pieceValue = 2;

    public Pawn()
    {
        super();
    }

    //TODO first move can move two forward
    @Override
    public byte[] getLegalMoves(byte[] gameState, int position, byte player)
    {
        int forward = position + (boardWidth * player);
        int forwardTwo = position + (boardWidth * 2 * player);
        int left = position - forward;
        int right = position + forward;

        int x = position % boardWidth;
        int y = (position - x)/boardWidth;



        int[] possibleMovesArray = {forward, forwardTwo, left, right};
        byte[] legalMovesArray = new byte[possibleMovesArray.length];

        int i = 0;
        for(Integer value : possibleMovesArray)
        {
            if(y > -1 && y < 9 && x > -1 && x < 9)
            {
                if(gameState[value]/-gameState[value] != player)
                {
                    legalMovesArray[i] = value.byteValue();
                    i++;
                }
            }
        }
        
        return legalMovesArray;
    }

    public byte getPieceValue() {
        return pieceValue;
    }
}
