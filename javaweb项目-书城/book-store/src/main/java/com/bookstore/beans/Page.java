package com.bookstore.beans;

import java.util.List;

public class Page<T> {
    public static final Integer PAGE_SIZE = 4;

    // 当前页码
    private Integer pageNo;
    // 总页码
    private Integer pageTotal;
    // 每页显示数量
    private Integer pageSize = PAGE_SIZE;

    // 页面数量
    private Integer pageTotalCount;
    // 当前页面的数据
    private List<T> items;
    // 为了将分页条模块抽取出来，不同模块在引入分页条的时候需要设置自己独有的uri地址
    private String uriPart;

    public Page() {
    }

    public Page(Integer pageNo, Integer pageTotal, Integer pageSize, Integer pageTotalCount, List<T> items) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.items = items;
    }

    public Page(Integer pageNo, Integer pageTotal, Integer pageSize, Integer pageTotalCount
            , List<T> items, String uriPart) {
        this.pageNo = pageNo;
        this.pageTotal = pageTotal;
        this.pageSize = pageSize;
        this.pageTotalCount = pageTotalCount;
        this.items = items;
        this.uriPart = uriPart;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageTotalCount() {
        return pageTotalCount;
    }

    public void setPageTotalCount(Integer pageTotalCount) {
        this.pageTotalCount = pageTotalCount;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public String getUriPart() {
        return uriPart;
    }

    public void setUriPart(String uriPart) {
        this.uriPart = uriPart;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNo=" + pageNo +
                ", pageTotal=" + pageTotal +
                ", pageSize=" + pageSize +
                ", pageTotalCount=" + pageTotalCount +
                ", items=" + items +
                ", uriPart='" + uriPart + '\'' +
                '}';
    }
}
