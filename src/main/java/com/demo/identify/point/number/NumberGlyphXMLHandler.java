package com.demo.identify.point.number;

import org.apache.fontbox.ttf.GlyphRenderer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 冯宇明
 * @version 1.0
 * @date 2020/1/7
 * @desc
 */
final class NumberGlyphXMLHandler {


    private Element root;

    NumberGlyphXMLHandler(String path) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(path);
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    NumberGlyphXMLHandler(InputStream in) {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(in);
            root = document.getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    Map<String, String> getMap (String xpath, String keyName, String valueName){
        List<Element> elements = root.selectNodes(xpath);

        Map<String, String> map = new HashMap<>(24);
        elements.forEach(e -> {
            String key = e.attribute(keyName).getValue();
            String value = e.attribute(valueName).getValue();
            map.put(key, value);
        });
        return map;
    }

    Map<String, List<GlyphRenderer.Point>> getMap (String pxpath, String xName, String
        yName, String on){
        List<Element> elements = root.selectNodes(pxpath);

        Map<String, List<GlyphRenderer.Point>> map = new HashMap<>(24);
        elements.forEach(e -> {
            List<Integer> list = new ArrayList<>();
            List<Element> temp = e.elements(), children = new ArrayList<>();

            temp.forEach(t -> {
                children.addAll(t.elements());
            });

            String name = e.attribute("name").getValue();
            List<GlyphRenderer.Point> points = new ArrayList<>(children.size());
            map.put(name, points);
            Element child = null;
            for (int i = 0; i < children.size(); i++) {
                child = children.get(i);
                int o = Integer.parseInt(child.attribute(on).getValue());
                if (o != 0) {
                    int x = Integer.parseInt(child.attribute(xName).getValue());
                    int y = Integer.parseInt(child.attribute(yName).getValue());
                    points.add(new GlyphRenderer.Point(x, y));
                }
            }
        });
        return map;
    }
}
