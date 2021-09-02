package com.pzt.web.admin;

import com.pzt.pojo.Tag;
import com.pzt.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 标签控制器
 *
 * @author 蒲正庭 on 2021/8/2
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //默认分页根据id，倒序
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        //前端模型page放入查询分页的数据
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    //新增方法
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    //修改
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }


    //提交  @Valid校验  BindResult校验结果
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }

        //校验不通过
        if (result.hasErrors()) {
            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);
        if (t == null) {
            //操作失败
            attributes.addFlashAttribute("message", "新增失败！");
        } else {
            //操作成功
            attributes.addFlashAttribute("message", "新增成功！");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }

        //校验不通过
        if (result.hasErrors()) {
            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            //操作失败
            attributes.addFlashAttribute("message", "更新失败！");
        } else {
            //操作成功
            attributes.addFlashAttribute("message", "更新成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/tags";
    }

}
