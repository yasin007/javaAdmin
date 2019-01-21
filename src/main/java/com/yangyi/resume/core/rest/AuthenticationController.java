package com.yangyi.resume.core.rest;

import lombok.extern.slf4j.Slf4j;
import com.yangyi.resume.common.aop.log.Log;
import com.yangyi.resume.core.security.AuthenticationToken;
import com.yangyi.resume.core.security.AuthorizationUser;
import com.yangyi.resume.core.utils.JwtTokenUtil;
import com.yangyi.resume.core.security.JwtUser;
import com.yangyi.resume.core.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 授权、根据token获取用户详细信息
 *
 * @author yangyi
 */
@Slf4j
@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * 登录授权
     *
     * @param authorizationUser
     * @return
     */
    @Log(description = "用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity<?> authenticationLogin(@Valid @RequestBody AuthorizationUser authorizationUser) {

        // 根据用户名获取用户信息
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authorizationUser.getUsername());

        // 查询密码是否正确
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))) {
            throw new AccountExpiredException("密码错误");
        }

        // 查询账号是否停用
        if (!userDetails.isEnabled()) {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }

        // 生成令牌
        final String token = jwtTokenUtil.generateToken(userDetails);

        // 返回 token
        return ResponseEntity.ok(new AuthenticationToken(token));
    }

    /**
     * 获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo(HttpServletRequest request) {
        // 根据用户名获取用户信息
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(jwtTokenUtil.getUserName(request));
        return ResponseEntity.ok(jwtUser);
    }
}

