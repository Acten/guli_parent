package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author heqi
 * @since 2022-03-09
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师id",required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current",value = "当前页",required = true)@PathVariable long current,
                             @ApiParam(name = "limit",value = "每页记录数",required = true)@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        eduTeacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "条件查询讲师带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@ApiParam(name = "current",value = "当前页",required = true)@PathVariable Long current,
                                  @ApiParam(name = "limit",value = "每页记录数",required = true)@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        eduTeacherService.page(pageTeacher,wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }
}

