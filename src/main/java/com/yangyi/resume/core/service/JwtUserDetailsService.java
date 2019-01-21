package com.yangyi.resume.core.service;

import com.yangyi.resume.common.exception.EntityNotFoundException;
import com.yangyi.resume.common.utils.ValidationUtil;
import com.yangyi.resume.core.security.JwtUser;
import com.yangyi.resume.system.domain.Permission;
import com.yangyi.resume.system.domain.Role;
import com.yangyi.resume.system.domain.User;
import com.yangyi.resume.system.repository.PermissionRepository;
import com.yangyi.resume.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * token实现类
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * 重写实现SpringSecurity内的UserDetailsService接口来完成自定义查询用户的逻辑
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = null;
        if (ValidationUtil.isEmail(username)) {
            user = userRepository.findByEmail(username);
        } else {
            user = userRepository.findByUsername(username);
        }

        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", username);
        } else {
            return create(user);
        }
    }

    /**
     * 创建SpringSecurity user模型
     *
     * @param user
     * @return
     */
    public UserDetails create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getAvatar(),
                user.getEmail(),
                mapToGrantedAuthorities(user.getRoles(), permissionRepository),
                user.getEnabled(),
                user.getCreateTime(),
                user.getLastPasswordResetTime()
        );
    }

    /**
     * 创建roles
     *
     * @param roles
     * @param permissionRepository
     * @return
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles, PermissionRepository permissionRepository) {

        Set<Permission> permissions = new HashSet<>();
        for (Role role : roles) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            permissions.addAll(permissionRepository.findByRoles(roleSet));
        }

        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority("ROLE_" + permission.getName()))
                .collect(Collectors.toList());
    }
}