package com.manage.common.dto;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class UserDto implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

    private Integer status;

    private Long postId;

    private String description;

    private LocalDateTime created;

    private String phone;

    private Long deptId;

    private String sexy;
}
