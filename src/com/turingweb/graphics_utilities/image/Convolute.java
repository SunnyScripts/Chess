package com.turingweb.graphics_utilities.image;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by Ryan Berg on 11/14/15 rberg2@hotmail.com
 */
public class Convolute
{
//    BufferedImage bufferedImage;

    //this program uses a double buffer, a read stream and a write stream
    //read and write reference an arbitrary left or right buffer
    //this enables the program to swap buffers each iteration of some function
    //which means the image that was just written to becomes the the image we read from on the next iteration
    //the point of this is to remove the need to allocate new memory or collect garbage
    //over many edits of the same image
    protected byte[] leftBuffer;
    protected byte[] rightBuffer;

    protected byte[] readBuffer;
    protected byte[] writeBuffer;

    protected int imageWidth;
    protected int imageHeight;

    protected int channelLength = 3;
    protected int channelOffset = 0;

    NeighborFetcher neighborFetcher;
    ColorModel colorModel;
    SampleModel sampleModel;

    public Convolute(BufferedImage bufferedImage)
    {
        imageWidth = bufferedImage.getWidth();
        imageHeight = bufferedImage.getHeight();

        leftBuffer = new byte[imageWidth*imageHeight*channelLength];

        rightBuffer = ((DataBufferByte)bufferedImage.getRaster().getDataBuffer()).getData();

        writeBuffer = leftBuffer;
        readBuffer = rightBuffer;

        colorModel = bufferedImage.getColorModel();
        sampleModel = bufferedImage.getSampleModel();

        if (bufferedImage.getAlphaRaster() != null)
        {
            channelLength = 4;
            channelOffset = 1;
        }
        neighborFetcher = new NeighborFetcher(channelLength, false);

    }

    public BufferedImage boxBlur(int numberOfIterations)
    {
        boolean bufferSwap = true;

        for(int i = 0; i < numberOfIterations; i++)
        {
            _boxBlur();
            System.out.println(i+1 + " of " + numberOfIterations + " iterations done.");

            if(bufferSwap)
            {
                readBuffer = writeBuffer;
                writeBuffer = rightBuffer;
                bufferSwap = false;
            }
            else
            {
                readBuffer = writeBuffer;
                writeBuffer = leftBuffer;
                bufferSwap = true;
            }
        }
        return dataToBufferedImage(readBuffer);
    }

    protected void _boxBlur()
    {
        int pixelIndex = 0;

        for(int i = 0; i < imageHeight; i++)
        {
            for(int j = 0; j < imageWidth; j++)
            {
                int[] neighboringPixelsArray = neighborFetcher.getNeighbors(j, i, readBuffer, imageWidth, imageHeight);

                int redAverage = readBuffer[pixelIndex] &0xff;
                int greenAverage = readBuffer[pixelIndex+1]&0xff;
                int blueAverage = readBuffer[pixelIndex+2]&0xff;

                for(int k = 0; k < neighboringPixelsArray.length; k+=3)
                {
                    redAverage += neighboringPixelsArray[k]&0xff;
                    greenAverage += neighboringPixelsArray[k+1]&0xff;
                    blueAverage += neighboringPixelsArray[k+2]&0xff;
                }

                redAverage/=((neighboringPixelsArray.length/channelLength)+1);
                greenAverage/=((neighboringPixelsArray.length/channelLength)+1);
                blueAverage/=((neighboringPixelsArray.length/channelLength)+1);


                writeBuffer[pixelIndex] = (byte)redAverage;
                writeBuffer[pixelIndex+1] = (byte)greenAverage;
                writeBuffer[pixelIndex+2] = (byte)blueAverage;

                pixelIndex+=channelLength;
            }
        }
    }



    public BufferedImage luminosityGrayScale()
    {
        System.out.println("converting image to gray scale");
        int pixelIndex = 0;

//        if(hasAlphaChannel)
//        {
//            BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_4BYTE_ABGR);
//        }

        for (int i = 0; i < imageHeight; i++)
        {
            for (int j = 0; j < imageWidth; j++)
            {
                //each index represents a signed byte for a given color
                //& 0xff converts the byte to unsigned (from 0 to 255)
                int colorSum = (int) ( (readBuffer[pixelIndex]) * .299 +  (readBuffer[pixelIndex+1]) * .587 +  (readBuffer[pixelIndex+2]) * .114);

                //TODO write to buffer, raster image
//                writeBuffer[pixelIndex, pixelIndex + 1, pixelIndex +2] = colorSum;

                pixelIndex += 3;
            }
        }

        return null;
    }

    protected BufferedImage dataToBufferedImage(byte[] buffer)
    {
        DataBufferByte dataBufferByte = new DataBufferByte(buffer, imageWidth * imageHeight);
        WritableRaster writableRaster = Raster.createWritableRaster(sampleModel, dataBufferByte, null);
        return new BufferedImage(colorModel, writableRaster, true, null);
    }
}
