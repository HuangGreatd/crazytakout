package com.pidan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pidan.common.Ressult;
import com.pidan.exception.BusinessException;
import com.pidan.model.entity.Category;
import com.pidan.model.entity.Employee;
import com.pidan.model.enums.ErrorCode;
import com.pidan.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 黄大头
 * @date 2023年04月26日 20:42
 */
@Slf4j
@RequestMapping("/category")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 新增分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public Ressult<String> save(@RequestBody Category category) {
        if (category == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        categoryService.save(category);
        return Ressult.success("新增分类成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Ressult<Page> page(int page, int pageSize, String name) {
        //分页构造器
        Page categoryPage = new Page(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> employeeQueryWrapper = new LambdaQueryWrapper<>();
        //添加一个排序
        employeeQueryWrapper.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryService.page(categoryPage, employeeQueryWrapper);
        return Ressult.success(page1);
    }

    /**
     * 根据id删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public Ressult<String> delete(Long id) {
        boolean b = categoryService.removeById(id);
        return Ressult.success("删除成功");
    }

    @PutMapping
    public Ressult<String> update(@RequestBody Category category) {
        if (category == null) {
            throw new BusinessException(ErrorCode.NOT_DATA);
        }
        boolean b = categoryService.updateById(category);
        if (!b){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"修改失败");
        }
        return Ressult.success("修改成功");
    }

}
