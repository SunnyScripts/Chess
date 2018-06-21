package com.turingweb.graphics_utilities.opengl_utilities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Ryan Berg on 2/13/16.
 */
public class GameObject
{
    protected Matrix4f modelMatrix = new Matrix4f();
    private Vector3f tempPosition = new Vector3f();

    public void setPosition(float x, float y, float z)
    {
        modelMatrix.setTranslation(x, y, z);
    }
    public void setRotation(float x, float y, float z)
    {
        modelMatrix.setRotationXYZ(x, y, z);
    }

//    public void translate(float x, float y, float z)
//    {
//        //
//    }
    public void rotate(float x, float y, float z)
    {
        modelMatrix.rotateXYZ(x, y, z);
    }
    public void scale(float x, float y, float z)
    {
        modelMatrix.scale(x, y, z);
    }

    public Vector3f getPosition()
    {
        modelMatrix.getTranslation(tempPosition);
        return tempPosition;
    }
}
