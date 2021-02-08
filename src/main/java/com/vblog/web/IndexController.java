package com.vblog.web;

import com.vblog.service.BlogService;
import com.vblog.service.TypeService;
import com.vblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "/")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("blogs",blogService.listAll(pageable));
        model.addAttribute("newBlogs",blogService.listNewBlog(6));
        return "index";
    }

    @RequestMapping(value = "/types")
    public String types(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model){
        model.addAttribute("types",typeService.ListType());
        model.addAttribute("blogs",blogService.listAll(pageable));
        return "types";
    }

    @RequestMapping(value = "/archives")
    public String archives(){
        return "archives";
    }
}
