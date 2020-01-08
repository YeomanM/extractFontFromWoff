package com.demo.identify.point.number;

import com.demo.identify.point.PointsComparator;
import org.apache.fontbox.ttf.GlyphRenderer;

import java.util.List;

/**
 * @author 冯宇明
 * @version 1.0
 * @date 2020/1/7
 * @desc
 */
public final class DefaultNumberPointsComparator implements PointsComparator {
    @Override
    public int getSimilarity(List<GlyphRenderer.Point> points1, List<GlyphRenderer.Point> points2) {
        if (points1.size() > points2.size()) {
            return this.getSimilarityByOrder(points2, points1);
        }
        return this.getSimilarityByOrder(points1, points2);
    }

    private int getSimilarityByOrder(List<GlyphRenderer.Point> points, List<GlyphRenderer.Point> longPoint) {
        int x = 0,lx = 0,len = points.size(),count = 0,llen = longPoint.size(),temp = 0,normal = 0;
        for (x = 0;x < len && lx < llen; x++,lx++) {
            if (points.get(x).like(longPoint.get(lx), 20)){
                count++;
                normal = 0;
            } else {
                normal = normal == 0 ? 1 : normal;
                if (normal == 1) {
                    x--;
                    temp++;
                    if (temp == 5) {
                        temp = 0;
                        lx -= 5;
                        normal = 2;
                    }
                } else if(normal == 2){
                    lx--;
                    temp++;
                    if (temp == 5) {
                        temp = 0;
                        x -= 5;
                        normal = 3;
                    }
                } else {
                    normal = 0;
                }
            }
        }
        return count;
    }
}
