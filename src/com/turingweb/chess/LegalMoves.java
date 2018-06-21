package com.turingweb.chess;

import java.util.ArrayList;

/**
 * Created by ryanberg on 11/29/15.
 */
public final class LegalMoves
{
    protected final static byte darkPlayer = -1;
    protected final static byte lightPlayer = 1;

    final static byte pawnID = 1;
    final static byte kingID = 2;
    final static byte queenID = 3;
    final static byte bishopID = 4;
    final static byte knightID = 5;
    final static byte rookID = 6;

    protected final static byte darkOnTop = -1;
    protected final static byte lightOnTop = 1;

    protected final static byte boardWidth = 8;
    protected final static byte boardHeight = 8;


    public Object[] getLegalMovesForGameState(byte[] gameState)
    {
        byte currentPlayer;
        byte currentGamePieceUID;
        byte currentRow;

        byte boardOrientationID = gameState[gameState.length-1];

        Object[] legalMoveArray = new Object[64];

        for(int position = 0; position < gameState.length -1; position++)
        {
            currentGamePieceUID = gameState[position];
            currentPlayer = (byte)(Math.signum(currentGamePieceUID));
            currentGamePieceUID *= currentPlayer;//absolute value
            currentRow = (byte)(position / boardWidth);
            ArrayList<Integer> pieceMovementArray = new ArrayList<Integer>();


            switch (currentGamePieceUID)
            {
                case pawnID:
                    //Todo forward off map, or to rank up not accounted for. Is space occupied
                    int forward = position+(boardWidth*currentPlayer*boardOrientationID);
                    pieceMovementArray.add(forward);
                    if(currentRow  * currentPlayer * boardOrientationID == 1 || currentRow  * currentPlayer * boardOrientationID == -6)
                    {
                        pieceMovementArray.add(position+(boardWidth*currentPlayer*boardOrientationID)*2);
                    }
                    if(position % 8 != 0)//not left edge
                    {

                        if(Math.signum(gameState[forward-1]) != currentPlayer && Math.signum(gameState[forward-1]) != 0)
                        {
                            pieceMovementArray.add(forward - 1);
                        }
                    }

                    if(position % 8 != 7)//not right edge
                    {
                        if(Math.signum(gameState[forward+1]) != currentPlayer && Math.signum(gameState[forward-1]) != 0)
                        {
                            pieceMovementArray.add(forward+1);
                        }
                    }
                    legalMoveArray[position] = pieceMovementArray;
                break;
                case kingID:
                    break;
                case queenID:
                    break;
                case bishopID:
                    break;
                case knightID:
                    break;
                case rookID:
                    break;
            }
        }

        return legalMoveArray;
    }

    private boolean isMoveOnBoard(int movePosition)
    {
        return false;
    }
}
