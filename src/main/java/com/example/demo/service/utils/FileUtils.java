package com.example.demo.service.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

    public static String getSaveContents()
    {
        return "E:\\GitHub";
    }
    public static boolean writeBytesToFile(String fileName, byte[] dates)
    {
        if(fileName!=null && dates!=null)
        {
            try
            {
                File file = new File(fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(dates);
                fileOutputStream.close();
                return true;
            }
            catch (Exception e){
                return false;
            }

        }
        return false;
    }
    public static long getFileLength(String filePath)
    {
        File file = new File(filePath);
        return file.length();
    }

    public static byte[] getFileDates(String filePath)
    {
        byte[] ret = null;
        File file = new File(filePath);
        try
        {
            FileInputStream fileInputStream = new FileInputStream(file);
            long fileLength = getFileLength(filePath);
            if(fileLength>0) {
                ret = new byte[(int) fileLength];
                fileInputStream.read(ret);
            }

            fileInputStream.close();
        }
        catch (Exception e){
            return ret;
        }
        return ret;
    }
}
