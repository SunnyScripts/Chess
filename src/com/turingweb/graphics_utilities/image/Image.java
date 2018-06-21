package com.turingweb.graphics_utilities.image;

import com.turingweb.generic_utilities.Buffer;
import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by ryanberg on 12/7/15. rberg2@hotail.com
 */
public class Image
{
    private BufferedImage bufferedImage;

    public Image(String pathToImage)
    {
        bufferedImage = readFromFile(pathToImage);
    }

    //BufferedImage will usually be RBG or ARGB
    public ByteBuffer getByteBufferRGBA()
    {
        byte[] pixelArray = ((DataBufferByte)bufferedImage.getRaster().getDataBuffer()).getData();

        System.out.println("altering image data");

        ByteBuffer buffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getWidth() * 4);

        int numberOfChannels = 3;
        if(bufferedImage.getAlphaRaster() != null)
            numberOfChannels = 4;

        int imageSize = bufferedImage.getWidth() * bufferedImage.getHeight();

        for(int i = 0; i < imageSize; i++)
        {
            if(numberOfChannels == 3)
            {
                //red
                buffer.put(pixelArray[i]);
                //green
                buffer.put(pixelArray[i+1]);
                //blue
                buffer.put(pixelArray[i+2]);
                //alpha
                buffer.put((byte)0xFF);
            }
            else
            {
                //red
                buffer.put(pixelArray[i+1]);
                //green
                buffer.put(pixelArray[i+2]);
                //blue
                buffer.put(pixelArray[i+3]);
                //alpha
                buffer.put(pixelArray[i]);
            }
        }
        //convert buffer from write to read
        buffer.flip();

        return buffer;
    }

    public BufferedImage convertByteBufferToBufferedImage(ByteBuffer byteBuffer)
    {
        return null;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public static void save(BufferedImage bufferedImage)
    {

    }

    public int width()
    {
        return bufferedImage.getWidth();
    }
    public int height()
    {
        return bufferedImage.getHeight();
    }
    public BufferedImage readFromFile(String path)
    {
        BufferedImage bufferedImage = null;
        System.out.println("read image from file started");
        try
        {
            System.out.println("attempting read");

            bufferedImage = ImageIO.read(new File(path));
            System.out.println("anything happening here?");
        } catch (IOException e)
        {

            e.printStackTrace();
        }

        System.out.println("returning image data");

        return bufferedImage;
    }
    public void verticalFlip()
    {
        bufferedImage = verticalFlip(bufferedImage);
    }
    public static BufferedImage verticalFlip(BufferedImage bufferedImage)
    {
        AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
        affineTransform.translate(0, -bufferedImage.getHeight());
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return affineTransformOp.filter(bufferedImage, null);
    }
}


















