package com.vblog.web.admin;

import com.vblog.entity.Blog;
import com.vblog.entity.Type;
import com.vblog.entity.User;
import com.vblog.service.BlogService;
import com.vblog.service.TypeService;
import com.vblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;


    @RequestMapping(value = "/blogs")
    public String blog(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                               Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("page",blogService.ListBlog(pageable, blog));
        return "/admin/blogs";
    }

    @PostMapping(value = "/blogs/search")
    public String search(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                               Pageable pageable, BlogQuery blog, Model model){
        Page<Blog> blogs = null;
         blogs = blogService.ListBlog(pageable, blog);
        model.addAttribute("page",blogs);
        return "/admin/blogs :: blogList ";
    }

    @GetMapping(value = "/blogs/add")
    public String addHtml(Model model){
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("blogs",new Blog());
        return "/admin/blogs-input";
    }

    @RequestMapping(value = "/blogs/{id}/input")
    public String editBlogsInput(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.ListType());
        Blog blog = blogService.getBlog(id);
        model.addAttribute("blogs",blog);
        return "/admin/blogs-input";
    }

    @PostMapping(value = "/blogs/addsubmit")
    public String addsubmit(@Valid Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        Blog blogid = blogService.getBlog(blog.getId());
        Blog b = null;
        if (blogid == null){
            blog.setType(typeService.getType(blog.getType().getId()));
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog);
        }
        if (b == null){
            redirectAttributes.addFlashAttribute("message","操作失败！");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功！");
        }
        return "redirect:/admin/blogs";
    }

    @RequestMapping(value = "/blogs/{id}/delete")
    public String deleteBlogs(@PathVariable Long id,RedirectAttributes redirectAttributes){
        Blog blog = blogService.getBlog(id);
        if (blog == null){
            redirectAttributes.addFlashAttribute("message","操作失败!");
        }else {
            blogService.deleteBlog(id);
            redirectAttributes.addFlashAttribute("message","操作成功!");
        }
        return "redirect:/admin/blogs";
    }
}
