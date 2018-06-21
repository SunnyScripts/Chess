package com.turingweb.graphics_utilities.opengl_utilities;

/**
 * Created by ryanberg on 12/6/15.
 */

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{

    private long windowID;
    private int width;
    private int height;

    private GLFWCursorPosCallback cursorPositionCallback;

    public Window(int width, int height, CharSequence title)
    {
        this.width = width;
        this.height = height;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
//        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        glfwWindowHint(GLFW_SAMPLES, 4);

        windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowID == NULL) {
            glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetCursorPosCallback(windowID, cursorPositionCallback);

        //TODO use frameBuffer to get height
        GLFWVidMode glfwVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
//        glfwSetWindowPos(windowID, (glfwVidMode.width() - width) / 2, (glfwVidMode.height() - height) / 2);
        glfwSetWindowPos(windowID, 0, 0);

        glfwMakeContextCurrent(windowID);
        glfwShowWindow(windowID);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LESS);

        glViewport(0, 0, width, height);
    }
    public boolean isClosing()
    {
        return glfwWindowShouldClose(windowID) == GLFW_TRUE;
    }
    public void close()
    {
        glfwDestroyWindow(windowID);
        glfwTerminate();
    }
    public void swapBuffers()
    {
        glfwSwapBuffers(windowID);
    }

    public int width()
    {
        return width;
    }
    public int height()
    {
        return height;
    }
    public long getWindowID(){return windowID;}

}
