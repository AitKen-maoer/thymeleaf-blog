package com.vblog.service.impl;

import com.vblog.dao.BlogRepository;
import com.vblog.entity.Blog;
import com.vblog.entity.Type;
import com.vblog.handler.NotFoundException;
import com.vblog.service.BlogService;
import com.vblog.util.MyBeanUtils;
import com.vblog.vo.BlogQuery;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Blog getBlog(Long id) {
        if(id == null){
            return null;
        }
        return blogRepository.getOne(id);
    }

    @Override
    public Page<Blog> ListBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null && !"".equals(blog.getTypeId())) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        Date date = new Date();
        blog.setCreateTime(date);
        blog.setUpdateTime(date);
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listAll(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listNewBlog(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"views");
        Pageable pageRequest = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageRequest);
    }

    @Transactional
    @Override
    public Blog updateBlog(Blog blog) {
        Blog b = blogRepository.getOne(blog.getId());
        if(b == null){
            throw new NotFoundException("该博客不存在!");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
