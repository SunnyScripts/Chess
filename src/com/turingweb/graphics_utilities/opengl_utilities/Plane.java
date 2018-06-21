package com.turingweb.graphics_utilities.opengl_utilities;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by Ryan Berg on 2/13/16.
 */
public class Plane extends GameObject
{
    protected int vertexArrayObject;
    protected int vertexBufferObject;
    protected int elementArrayObject;

    protected Texture texture;

    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public Plane()
    {
         //Model Data\\
        //============\\

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(32);
        verticesBuffer.put(new float[]
                {
                        //Position           Color              ST Coordinates
                        0.5f,  0.5f, 0.0f,   1.0f, 0.0f, 0.0f,  1.0f, 1.0f, //Top Left
                        0.5f, -0.5f, 0.0f,   0.0f, 1.0f, 0.0f,  1.0f, 0.0f, //Top Right
                        -0.5f, -0.5f, 0.0f,  0.0f, 0.0f, 1.0f,  0.0f, 0.0f,//Bottom Right
                        -0.5f,  0.5f, 0.0f,  1.0f, 1.0f, 0.0f,  0.0f, 1.0f //Bottom Left
                });
        verticesBuffer.flip();

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(6);
        indicesBuffer.put(0).put(1).put(3);
        indicesBuffer.put(1).put(2).put(3);
        indicesBuffer.flip();

         //Build OpenGL Objects\\
        //======================\\

        vertexArrayObject = glGenVertexArrays();
        vertexBufferObject = glGenBuffers();
        elementArrayObject = glGenBuffers();

        glBindVertexArray(vertexArrayObject);

        glBindBuffer(GL_ARRAY_BUFFER,  vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementArrayObject);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

         //Vertex Attributes\\
        //===================\\
//  Definitions:
//      glVertexAttribPointer(
//                      location of attribute in shader,
//                      number of channels in attribute, //for example: in vec2 textureCoordinate; has 2
//                      data type,
//                      normalize the data,
//                      distance to the next attribute position, //0 to have openGL guess
//                      attribute starting position offset);
//
//      glEnableVertexAttribArray(
//                      location of shader attribute);//ie. layout(location = 0) would be 0
//                      To get the location by name use glGetAttribLocation();
//                      int color = glGetAttribLocation(shaderProgram, "color");

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 8 * Float.BYTES, 0);

        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 8 * Float.BYTES, 3 * Float.BYTES);

        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, 8 * Float.BYTES, 6 * Float.BYTES);

         //Unbind VBO and VAO\\
        //====================\\
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }


    public void setTexture(Texture texture)
    {
        this.texture = texture;
//        modelMatrix.scale(texture.getWidth(), texture.getHeight(), 1);
    }

    public void draw(int shaderProgram)
    {
        glBindVertexArray(vertexArrayObject);
        texture.bind();

        modelMatrix.get(matrixBuffer);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "model"), false, matrixBuffer);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindVertexArray(0);
    }
}
