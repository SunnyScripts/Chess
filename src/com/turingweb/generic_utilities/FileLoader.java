package com.turingweb.generic_utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by ryanberg on 12/6/15. rberg2@hotmail.com
 */
public final class FileLoader
{
    private FileLoader(){}

    public static String fileToString(String filePath)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String string;
            while ((string = reader.readLine()) != null)
            {
                stringBuilder.append(string).append("\n");
            }
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}