package com.gzzhsl.pcms.util;

public class PageCalculator {
    public static int calcPageIndex2RowIndex(int pageIndex, int pageSize){
        return pageIndex > 0 ? (pageIndex-1) * pageSize : 0;
    }
}
