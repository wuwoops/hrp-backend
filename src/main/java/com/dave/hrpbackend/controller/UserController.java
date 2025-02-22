package com.dave.hrpbackend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dave.hrpbackend.entity.User;
import com.dave.hrpbackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/employeeList")
    public IPage<User> listUsers(@RequestParam(defaultValue = "1") int currentPage,
                                 @RequestParam(defaultValue = "10") int pageSize,
                                    @RequestParam(required = false) String email
    ) {
        Page<User> page = new Page<>(currentPage, pageSize);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }

        // 返回帶條件的分頁結果
        return userService.page(page, queryWrapper);
    }

    @GetMapping("/employee")
    public ResponseEntity<List<User>> showAllUserList() {
        List<User> listUsers = userService.list();
        return ResponseEntity.ok().body(listUsers);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<User> showUserList(@PathVariable String id) {
        User user = userService.getById(id);
        log.info("Employee with id {} found", id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/employee")
    public ResponseEntity addEmployee(@RequestBody User user) {
        user.setId(UUID.randomUUID().toString());
        boolean isSuccess = userService.save(user);
        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/employee")
    public ResponseEntity updateEmployee(@RequestBody User user) {
        boolean isSuccess = userService.updateById(user);
        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id) {
        boolean isSuccess = userService.removeById(id);
        if (!isSuccess) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}


