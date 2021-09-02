package com.pzt.web.admin;

import com.pzt.pojo.Type;
import com.pzt.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
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
 * 分类控制器
 *
 * @author 蒲正庭 on 2021/8/2
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //默认分页根据id，倒序
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        //前端模型page放入查询分页的数据
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    //新增方法
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //修改
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }


    //提交  @Valid校验  BindResult校验结果
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }

        //校验不通过
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);
        if (t == null) {
            //操作失败
            attributes.addFlashAttribute("message", "新增失败！");
        } else {
            //操作成功
            attributes.addFlashAttribute("message", "新增成功！");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,
                           @PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }

        //校验不通过
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        Type t = typeService.updateType(id, type);
        if (t == null) {
            //操作失败
            attributes.addFlashAttribute("message", "更新失败！");
        } else {
            //操作成功
            attributes.addFlashAttribute("message", "更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/types";
    }
}
