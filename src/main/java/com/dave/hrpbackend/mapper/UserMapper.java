package com.dave.hrpbackend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dave.hrpbackend.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}