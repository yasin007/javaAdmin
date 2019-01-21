package com.yangyi.resume.core.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.*;

/**
 * 实现SpringSecurity内的UserDetails数据模型
 */
@Getter
@AllArgsConstructor
public class JwtUser implements UserDetails {

    @JsonIgnore
    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final String avatar;

    private final String email;

    // 返回分配给用户的角色列表
    @JsonIgnore
    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean enabled;

    private Timestamp createTime;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    // 账户是否过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 获取密码
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }
    // 账户是否激活
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 在我们保存权限的时候加上了前缀ROLE_，因此在这里需要处理下数据
     *
     * @return
     */
    public Collection getRoles() {
        Set<String> roles = new LinkedHashSet<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority().substring(5));
        }
        return roles;
    }
}