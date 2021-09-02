package com.pzt.web;

import com.pzt.pojo.Type;
import com.pzt.service.BlogService;
import com.pzt.service.TypeService;
import com.pzt.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 蒲正庭 on 2021/8/27
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 3, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog,
                        @PathVariable Long id, Model model) {
        List<Type> types = typeService.listTypeTop(10000);
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
