package com.vblog.service;

import com.vblog.entity.Blog;
import com.vblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BlogService {

    Blog getBlog(Long id);

    Page<Blog> ListBlog(Pageable pageable, BlogQuery blog);

    Blog saveBlog(Blog blog);

    Page<Blog> listAll(Pageable pageable);

    Page<Blog> listNewBlog(Integer size);

    Blog updateBlog(Blog blog);

    void deleteBlog(Long id);
}
