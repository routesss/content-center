package com.sola.contentcenter.domain.dto.user;


import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Id
     */
    private Integer id;

    /**
     * 微信id
     */
    private String wxId;

    /**
     * 微信昵称
     */
    private String wxNickname;

    /**
     * 角色
     */
    private String roles;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 积分
     */
    private Integer bonus;

}
