package com.dave.hrpbackend.entity.dto;

import com.dave.hrpbackend.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRespDTO {
    private String id;
    private String username;
    private String nickname;
    private Integer age;
    private String email;
    private String job;
    private String authorities;

    public static UserRespDTO of(User user) {
        UserRespDTO userRespDTO = new UserRespDTO();
        userRespDTO.id = user.getId();
        userRespDTO.username = user.getUsername();
        userRespDTO.nickname = user.getNickname();
        userRespDTO.age = user.getAge();
        userRespDTO.email = user.getEmail();
        userRespDTO.job = user.getJob();
        userRespDTO.authorities = user.getAuthorities();
        return userRespDTO;
    }
}
