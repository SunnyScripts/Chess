package com.turingweb.chess;

import java.awt.Point;

/**
 * Created by ryanberg on 11/23/15.
 */
public interface GamePiece
{
    public byte[] getLegalMoves(byte[] gameState, int position, byte player);
}
