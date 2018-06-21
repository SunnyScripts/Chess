package com.turingweb.graphics_utilities.opengl_utilities;

import com.turingweb.generic_utilities.FileLoader;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindFragDataLocation;

/**
 * Created by ryanberg on 12/6/15.
 */
public class ShaderLoader
{
    private ShaderLoader(){}

    public static int shaderProgramFromFiles(String pathToVertexShader, String pathToFragmentShader) {
        return buildShaderProgram(FileLoader.fileToString(pathToVertexShader), FileLoader.fileToString(pathToFragmentShader));
    }
    public static int buildShaderProgram(String vertexShaderText, String fragmentShaderText)
    {
        int programID = glCreateProgram();
        int vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertexShaderID, vertexShaderText);
        glShaderSource(fragmentShaderID, fragmentShaderText);

        glCompileShader(vertexShaderID);
        if(glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("Failed to compile vertex shader.");
            System.err.println(glGetShaderInfoLog(vertexShaderID, 2048));
        }

        glCompileShader(fragmentShaderID);
        if(glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            System.err.println("Failed to compile fragment shader.");
            System.err.println(glGetShaderInfoLog(fragmentShaderID, 2048));
        }

        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);

        glBindFragDataLocation(programID, 0, "fragColor");
        glLinkProgram(programID);
        glValidateProgram(programID);
        return programID;
    }
}
