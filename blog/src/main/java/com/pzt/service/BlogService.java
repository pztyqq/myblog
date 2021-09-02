package com.pzt.service;

import com.pzt.pojo.Blog;
import com.pzt.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Blog getAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);//分页的查询；listBlog；查询一组数据

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query,Pageable pageable);

    Blog saveBlog(Blog blog);

    Map<String,List<Blog>> archiveBlog();

    Long countBlog();

    List<Blog> listRecommendBlogTop(Integer size);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);
}
