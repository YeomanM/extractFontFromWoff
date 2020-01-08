package com.demo.identify.point;

import com.demo.identify.point.number.DefaultNumberConvert;
import com.demo.identify.point.number.DefaultNumberPointsComparator;
import org.apache.fontbox.ttf.GlyphData;
import org.apache.fontbox.ttf.GlyphRenderer;
import org.apache.fontbox.ttf.TTFParser;
import org.apache.fontbox.ttf.TrueTypeFont;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description :
 * ---------------------------------
 * @Author : Yeoman
 * @Date : Create in 2020/1/7
 */
public abstract class Convert {

    protected InputStream templateIs;
    public Convert(){}

    public Convert(InputStream templateIs){
        this.templateIs = templateIs;
    }

    Map<String,List<GlyphRenderer.Point>> getUniMap2Points(InputStream ttfIs) throws IOException {
        TTFParser parser = new TTFParser(true);
        TrueTypeFont ttf = parser.parse(ttfIs);
        Map<String,List<GlyphRenderer.Point>> result = new HashMap<>(16);
        if (ttf.getGlyph() != null && ttf.getGlyph().getGlyphs().length > 0) {
            GlyphData[] data = ttf.getGlyph().getGlyphs();
            GlyphRenderer renderer = new GlyphRenderer(null);
            String[] unicodes = ttf.getPostScript().getGlyphNames();
            for (int i = 0; i < data.length; i++) {
                if (data[i] == null || data[i].getDescription() == null) {
                    continue;
                }
                result.put(unicodes[i], renderer.describeWithoutNotDraw(data[i].getDescription()));
            }
        }
        return result;
    }

    public abstract Map<String,List<GlyphRenderer.Point>> getTemplateUniMap2Point();

    public abstract Map<String,String> getTemplateUniMap2Value();

    public static class ConvertHelper{
        private Convert convert;
        private InputStream ttfIs;
        private PointsComparator comparator;
        private String ttfPath;

        public ConvertHelper(InputStream templateIs, InputStream ttfIs) {
            convert = new DefaultNumberConvert(templateIs);
            comparator = new DefaultNumberPointsComparator();
            this.ttfIs = ttfIs;
        }

        public ConvertHelper(String templatePath, InputStream ttfIs) {
            convert = new DefaultNumberConvert(templatePath);
            comparator = new DefaultNumberPointsComparator();
            this.ttfIs = ttfIs;
        }

        public ConvertHelper(InputStream templateIs, String ttfPath) {
            convert = new DefaultNumberConvert(templateIs);
            comparator = new DefaultNumberPointsComparator();
            this.ttfPath = ttfPath;
        }

        public ConvertHelper(String templatePath, String ttfPath) {
            convert = new DefaultNumberConvert(templatePath);
            comparator = new DefaultNumberPointsComparator();
            this.ttfPath = ttfPath;
        }

        public ConvertHelper ttfIs(InputStream ttfIs) {
            this.ttfIs = ttfIs;
            return this;
        }

        public ConvertHelper convert(Convert convert) {
            this.convert = convert;
            return this;
        }

        public ConvertHelper comparator(PointsComparator comparator) {
            this.comparator = comparator;
            return this;
        }

        public Map<String,String> get() throws Exception{
            if (ttfIs == null) {
                ttfIs = new FileInputStream(ttfPath);
            }
            Map<String,List<GlyphRenderer.Point>> map = this.convert.getUniMap2Points(ttfIs);
            Map<String,String> result = new HashMap<>(map.size());
            Map<String,String> unicodeMap2Value = this.convert.getTemplateUniMap2Value();
            Map<String,List<GlyphRenderer.Point>> unicodeMap2Point = this.convert.getTemplateUniMap2Point();

            map.forEach((k,v) -> {
                List<GlyphRenderer.Point> points = null;
                int max = 0;
                String maxUni = "";
                GlyphRenderer.Point p1,p2;
                for (Map.Entry<String, List<GlyphRenderer.Point>> entry : unicodeMap2Point.entrySet()) {
                    int count = comparator.getSimilarity(v,entry.getValue());
                    if (count > max) {
                        max = count;
                        maxUni = entry.getKey();
                    }
                }
                result.put(k, unicodeMap2Value.get(maxUni));
            });
            return result;
        }

    }

}
