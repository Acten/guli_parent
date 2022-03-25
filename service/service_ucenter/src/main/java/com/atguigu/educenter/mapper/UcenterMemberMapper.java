package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    //查询某一天注册人数
    Integer countRegisterDay(String day);
}
