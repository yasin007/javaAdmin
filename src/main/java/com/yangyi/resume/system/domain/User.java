package com.yangyi.resume.system.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * 用户信息表
 *
 * @author yangyi
 */
@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements Serializable {

    @Id// 标识该属性为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 主键的生成策略
    private Long id;

    @NotBlank
    private String username;

    private String avatar;

    @NotBlank
    @Pattern(regexp = "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}", message = "格式错误")
    private String email;

    @NotNull
    private Boolean enabled;

    private String password;

    @CreationTimestamp
    private Timestamp createTime;

    private Date lastPasswordResetTime;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", lastPasswordResetTime=" + lastPasswordResetTime +
                '}';
    }
}