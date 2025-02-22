package com.dave.hrpbackend.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dave.hrpbackend.entity.User;
import com.dave.hrpbackend.mapper.UserMapper;
import com.dave.hrpbackend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
