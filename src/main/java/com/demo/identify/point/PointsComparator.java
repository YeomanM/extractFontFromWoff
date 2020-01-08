package com.demo.identify.point;

import org.apache.fontbox.ttf.GlyphRenderer;

import java.util.List;

/**
 * @Description :
 * ---------------------------------
 * @Author : Yeoman
 * @Date : Create in 2020/1/7
 */
public interface PointsComparator {

    /**
     * 获取两个数组列表相似性，数值越大，相似性越高
     * @param points1 坐标列表1
     * @param points2 坐标列表2
     * @return 两个数组列表相似性
     */
    int getSimilarity(List<GlyphRenderer.Point> points1, List<GlyphRenderer.Point> points2);

}
