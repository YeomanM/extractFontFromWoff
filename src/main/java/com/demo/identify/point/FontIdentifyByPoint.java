package com.demo.identify.point;

import com.demo.identify.FontIdentify;

import java.io.InputStream;
import java.util.Map;

/**
 * @author 冯宇明
 * @version 1.0
 * @date 2020/1/8
 * @desc
 */
public final class FontIdentifyByPoint implements FontIdentify {

    private Convert.ConvertHelper helper;

    public FontIdentifyByPoint(InputStream templateIs, InputStream ttfIs) {
        helper = new Convert.ConvertHelper(templateIs,ttfIs);
    }

    public FontIdentifyByPoint(String templatePath, String ttfPath) {
        helper = new Convert.ConvertHelper(templatePath, ttfPath);
    }

    public static Map<String, String> identify(InputStream templateIs, InputStream ttfIs) throws Exception {
        return new Convert.ConvertHelper(templateIs,ttfIs).get();
    }

    public static Map<String, String> identify(String templatePath, String ttfPath) throws Exception {
        return new Convert.ConvertHelper(templatePath,ttfPath).get();
    }

    @Override
    public Map<String, String> identify() throws Exception {
        return helper.get();
    }
}
