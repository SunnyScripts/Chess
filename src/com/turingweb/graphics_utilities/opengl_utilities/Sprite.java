package com.turingweb.graphics_utilities.opengl_utilities;

import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by ryanberg on 12/20/15.
 */
public class Sprite
{
    private int shader;
    private int textureID;
    private int vertexArrayObject;

    public Sprite(int shaderProgram, int textureID)
    {
        this.shader = shaderProgram;
        this.textureID = textureID;
        initializeRenderData();
    }
    public void drawSprite(Vector2d position, Vector2d size, Vector2d rotation, Vector3d color)
    {
        // Prepare transformations
        glUseProgram(shader);
        Matrix4f model = new Matrix4f().identity();
        model = model.translate((float)position.x, (float)position.y, 0);

        model = model.translate(0.5f * (float)size.x, 0.5f * (float)size.y, 0.0f);
        model = model.rotateYXZ((float)rotation.x, (float)rotation.y, 0);
        model = model.translate(-0.5f * (float)size.x, -0.5f * (float)size.y, 0.0f);

        model = model.scale((float)size.x, (float)size.y, 1.0f);

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);

        model = model.identity();

        glUniformMatrix4fv(glGetUniformLocation(shader, "model"), false, model.get(floatBuffer));
        glUniform3f(glGetUniformLocation(shader, "spriteColor"), (float)color.x, (float)color.y, (float)color.z);

        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glBindVertexArray(vertexArrayObject);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        glBindVertexArray(0);
    }
    private void initializeRenderData()
    {
        int vertexBufferObject;

        FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(24);
        //          //Position   //Texture Coordinate
        floatBuffer.put(0).put(1).put(0).put(1);
        floatBuffer.put(1).put(0).put(1).put(0);
        floatBuffer.put(0).put(0).put(0).put(0);

        floatBuffer.put(0).put(1).put(0).put(1);
        floatBuffer.put(1).put(1).put(1).put(1);
        floatBuffer.put(1).put(0).put(1).put(0);
        floatBuffer.flip();


        vertexArrayObject = glGenVertexArrays();
        vertexBufferObject = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, floatBuffer, GL_STATIC_DRAW);

        glBindVertexArray(vertexArrayObject);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 4, Float.BYTES, false, 12 * Float.BYTES, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
}
