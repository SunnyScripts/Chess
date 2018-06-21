package com.turingweb.graphics_utilities.opengl_utilities;

import com.turingweb.graphics_utilities.image.Image;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;

/**
 * Created by Ryan Berg on 12/6/15. rberg2@hotmail.com
 */


public class Texture
{
    private final int textureID;
    private final int width;
    private final int height;

    public Texture(String texturePath)
    {
        textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Load Texture\\

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer components = BufferUtils.createIntBuffer(1);

        //move origin to bottom left
//        stbi_set_flip_vertically_on_load(1);

        ByteBuffer imageBuffer = stbi_load(texturePath, width, height, components, 4);
        //https://developer.apple.com/library/ios/documentation/3DDrawing/Conceptual/OpenGLES_ProgrammingGuide/TextureTool/TextureTool.html

        if(imageBuffer == null)
        {
            throw new RuntimeException("Failed to load image: " + stbi_failure_reason());
        }

        this.width = width.get();
        this.height = height.get();

        //store image on GPU
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);

        //unbind texture
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind()
    {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }
    public void delete()
    {
        glDeleteTextures(textureID);
    }


    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }
}
