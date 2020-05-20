package com.sendi.netrequest.rxquest;

/**
 * Created by Administrator on 2020/5/19.
 */
public class MapInfo {
    String search;
    String pageNum;
    String pageSize;

 /*   public MapInfo(String search, String pageNum, String pageSize) {
        this.search = search;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }*/

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
