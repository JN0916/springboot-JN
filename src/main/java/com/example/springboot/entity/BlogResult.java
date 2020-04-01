package com.example.springboot.entity;

import java.util.List;

public class BlogResult extends Result<List<Blog>> {
    private int total;
    private int page;
    private int totalPage;

    public static BlogResult newBlogs(List<Blog> data, int total, int page, int totalPage) {
        return new BlogResult(ResultStatus.OK,"获取成功",data, total, page, totalPage);
    }

    public static BlogResult failure(String msg){
        return new BlogResult(ResultStatus.FAIL,msg,null,0,0,0);
    }

    private BlogResult(ResultStatus status,String msg,List<Blog> data, int total, int page, int totalPage) {
        super(status, msg, data);

        this.total = total;
        this.page = page;
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getTotalPage() {
        return totalPage;
    }
}
