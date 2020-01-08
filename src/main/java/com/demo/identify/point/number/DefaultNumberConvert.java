package com.demo.identify.point.number;

import com.demo.identify.point.Convert;
import org.apache.fontbox.ttf.GlyphRenderer;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author 冯宇明
 * @version 1.0
 * @date 2020/1/7
 * @desc
 */
public final class DefaultNumberConvert extends Convert {
    
    private NumberGlyphXMLHandler handler; 
    
    public DefaultNumberConvert(InputStream templateIs){
        super();
        handler = new NumberGlyphXMLHandler(templateIs);
    }

    public DefaultNumberConvert(String templatePath){
        super();
        handler = new NumberGlyphXMLHandler(templatePath);
    }
    

    @Override
    public Map<String, List<GlyphRenderer.Point>> getTemplateUniMap2Point() {
        return handler.getMap(XPath.TTGlyph.xpath, XPath.pt.key, XPath.pt.value, "on");
    }

    @Override
    public Map<String, String> getTemplateUniMap2Value() {
        Map<String,String> map = handler.getMap(XPath.UnicodeMap2Value.xpath, XPath.UnicodeMap2Value.key, XPath.UnicodeMap2Value.value);
        if(map.isEmpty()) {
            Map<String,String> glyph = handler.getMap(XPath.GlyphIdMap2Value.xpath, XPath.GlyphIdMap2Value.key, XPath.GlyphIdMap2Value.value);
            Map<String,String> unicode = handler.getMap(XPath.IndexMap2Value.xpath, XPath.IndexMap2Value.key, XPath.IndexMap2Value.value);
            unicode.forEach((key, value) -> {
                map.put(value, glyph.get(key));
            });
        }
        return map;
    }

    enum XPath {
        UnicodeMap2Value("/ttFont/UnicodeMap2Value/Map", "unicode", "value"),
        GlyphIdMap2Value("/ttFont/GlyphOrder/GlyphID", "id", "value"),
        IndexMap2Value("/ttFont/IndexMap2Value/Map", "index", "value"),
        pt("/contour/pt", "x", "y"),
        TTGlyph("/ttFont/glyf/TTGlyph", "x", "y"),
        ;

        private String xpath;
        private String key;
        private String value;

        XPath(String xpath,String key,String value) {
            this.xpath = xpath;
            this.key = key;
            this.value = value;
        }
    }

}
