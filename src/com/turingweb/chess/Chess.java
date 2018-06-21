/**
 * Created by ryanberg on 12/5/15. rberg2@hotmail.com
 */

//TODO: timer, with alpha and delta time
//TODO: cleanup on close


package com.turingweb.chess;

import com.turingweb.graphics_utilities.opengl_utilities.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwInit;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Chess
{
    protected Window window;

    protected int shaderProgram;

    protected final static byte darkPlayerValue = -1;
    protected final static byte lightPlayerValue = 1;
    final static byte pawnID = 1;
    final static byte kingID = 2;
    final static byte queenID = 3;
    final static byte bishopID = 4;
    final static byte knightID = 5;
    final static byte rookID = 6;

    Plane tileArray[] = new Plane[64];
    byte gameState[] = new byte[]{
             6,  5,  4,  2,  3,  4,  5,  6,
             1,  1,  1,  1,  1,  1,  1,  1,
             0,  0,  0,  0,  0,  0,  0,  0,
             0,  0,  0,  0,  0,  0,  0,  0,
             0,  0,  0,  0,  0,  0,  0,  0,
             0,  0,  0,  0,  0,  0,  0,  0,
            -1, -1, -1, -1, -1, -1, -1, -1,
            -6, -5, -4, -2, -3, -4, -5, -6
    };

    Plane gamePieces[] = new Plane[64];

    FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    protected GLFWErrorCallback errorCallback;
    protected GLFWKeyCallback keyCallback;

    public void start()
    {
        init();
//        run();
        stop();
    }
    public void stop()
    {
        window.close();
        errorCallback.release();
        //TODO: remove buffers
    }
    private void init()
    {
        glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
        if ( glfwInit() != GLFW_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");

        window = new Window(1000, 800, "Chess");

        //enable wireframe mode
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

         //Load Shader\\
        shaderProgram = ShaderLoader.shaderProgramFromFiles("Shaders/shader.vert", "Shaders/shader.frag");

        glUseProgram(shaderProgram);


        glClearColor(0.35f, 0.35f, 0.35f, 1.0f);

        //world Projection

        Matrix4f projectionMatrix = new Matrix4f();
//        projectionMatrix.perspective((float)Math.toRadians(45), 1.33333333f, .1f, 100);
        projectionMatrix.ortho(0, window.width(), window.height(), 0, -1f, 1);
        projectionMatrix.get(matrixBuffer);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "projection"), false, matrixBuffer);

        int scaleSize;
        if(window.height() < window.width())
        {
            scaleSize = (int)Math.floor(window.height()*.125f);
        }
        else
        {
            scaleSize = (int)Math.floor(window.width()*.125f);
        }

        buildBoard(scaleSize);

        Texture pawnWhiteTexture = new Texture("Images/pieces/pawnW.png");
        Texture pawnBlackTexture = new Texture("Images/pieces/pawnB.png");
//
//        Plane pawn = new Plane();
//        pawn.setTexture(pawnTexture);
//        pawn.scale(scaleSize, scaleSize, 1);
//        pawn.setPosition(tileArray[50].getPosition().x, tileArray[50].getPosition().y, .5f);



        for(int i = 0; i < gameState.length; i++)
        {
            switch (Math.abs(gameState[i]))
            {
                case pawnID:
                    Pawn pawn = new Pawn();
                    pawn.scale(scaleSize, scaleSize, 1);
                    pawn.setPosition(tileArray[i].getPosition().x, tileArray[i].getPosition().y, .5f);
                    if(gameState[i] <= darkPlayerValue)
                        pawn.setTexture(pawnBlackTexture);
                    else
                        pawn.setTexture(pawnWhiteTexture);
                    gamePieces[i] = pawn;

                    break;
                default:
                    gamePieces[i] = null;
            }

        }

        glfwSetKeyCallback(window.getWindowID(), keyCallback = new GLFWKeyCallback()
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_PRESS )
                {
                   gamePieces[9].setPosition(tileArray[25].getPosition().x, tileArray[25].getPosition().y, .5f);
                    //TODO: update game state
                }
            }
        });


        while(!window.isClosing())
        {
            updateLogic();
            renderFrame();
        }

    }

    private void renderFrame()
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for(Plane tile : tileArray)
        {
//            tile.rotate(0, 0, 0.01f);
            tile.draw(shaderProgram);
        }

        for(Plane plane : gamePieces)
        {
            if(plane != null)
            {
                plane.draw(shaderProgram);
            }
        }

        window.swapBuffers(); // swap the color buffers
    }
    private void updateLogic()
    {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }

    //TODO: should rebuild on window resize
    private void buildBoard(int scaleSize)
    {

        int xPosition = (window.width() - scaleSize * 8) / 2;
        int yPosition = (window.height() - scaleSize * 8) / 2;

        Texture whiteTile = new Texture("Images/tileW.png");
        Texture blackTile = new Texture("Images/tileB.png");

        Texture currentTexture;

        boolean isTileBlack = false;

        for(int i = 0; i < tileArray.length; i++)
        {
            Plane tile = new Plane();

            if(isTileBlack)
            {
                currentTexture = blackTile;
                if((i + 2) % 8 != 1)
                    isTileBlack = false;
            }

            else
            {
                currentTexture = whiteTile;
                if((i + 2) % 8 != 1)
                    isTileBlack = true;
            }

            tile.setTexture(currentTexture);

            tile.scale(scaleSize, scaleSize, 1);

            tile.setPosition((i%8)*scaleSize+xPosition+(scaleSize*.5f), yPosition+(scaleSize*.5f), 0);

            tileArray[i] = tile;
//            System.out.println(i+1 + " " +isTileBlack);

            if((i + 2) % 8 == 1)
                yPosition+=scaleSize;
        }
    }
}
