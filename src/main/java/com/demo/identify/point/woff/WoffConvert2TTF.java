package com.demo.identify.point.woff;

import com.demo.identify.point.utils.FileUtil;

import java.io.*;
import java.util.Base64;
import java.util.zip.DataFormatException;

/**
 * @author 冯宇明
 * @version 1.0
 * @date 2020/1/3
 * @desc
 */
public class WoffConvert2TTF {

    public static void base64ToTTF(String base64Str, String ttfPathName) throws IOException, DataFormatException {
        byte[] ss = Base64.getDecoder().decode(base64Str);
        WoffConverter w = new WoffConverter();
        InputStream is = new ByteArrayInputStream(ss);
        ss = w.convertToTTFByteArray(is);
        FileUtil.write(ttfPathName, ss);
        is.close();
    }

    public static void woffToTTF(String woffFilePath, String ttfPathName) throws IOException, DataFormatException {
        WoffConverter w = new WoffConverter();
        InputStream is = new FileInputStream(woffFilePath);
        byte[] ss = w.convertToTTFByteArray(is);
        FileUtil.write(ttfPathName, ss);
        is.close();
    }

    public static void woffToTTF(InputStream is, String ttfPathName) throws IOException, DataFormatException {
        WoffConverter w = new WoffConverter();
        byte[] ss = w.convertToTTFByteArray(is);
        FileUtil.write(ttfPathName, ss);
    }

    public static void woffToTTF(File file, String ttfPathName) throws IOException, DataFormatException {
        WoffConverter w = new WoffConverter();
        InputStream is = new FileInputStream(file);
        byte[] ss = w.convertToTTFByteArray(is);
        FileUtil.write(ttfPathName, ss);
    }



    public static InputStream base64ToTTF(String base64Str) throws IOException, DataFormatException {
        byte[] ss = Base64.getDecoder().decode(base64Str);
        WoffConverter w = new WoffConverter();
        InputStream is = new ByteArrayInputStream(ss);
        ss = w.convertToTTFByteArray(is);
        InputStream result = new ByteArrayInputStream(ss);
        is.close();
        return result;
    }

    public static InputStream woffToTTF(String woffFilePath) throws IOException, DataFormatException {
        WoffConverter w = new WoffConverter();
        InputStream is = new FileInputStream(woffFilePath);
        byte[] ss = w.convertToTTFByteArray(is);
        InputStream result = new ByteArrayInputStream(ss);
        is.close();
        return result;
    }

    public static InputStream woffToTTF(InputStream is) throws IOException, DataFormatException {
        WoffConverter w = new WoffConverter();
        byte[] ss = w.convertToTTFByteArray(is);
        InputStream result = new ByteArrayInputStream(ss);
        is.close();
        return result;
    }

    public static InputStream woffToTTF(File file) throws IOException, DataFormatException {
        WoffConverter w = new WoffConverter();
        InputStream is = new FileInputStream(file);
        byte[] ss = w.convertToTTFByteArray(is);
        InputStream result = new ByteArrayInputStream(ss);
        is.close();
        return result;
    }


}
