package com.yangyi.resume.core.rest;


import com.yangyi.resume.core.security.AuthenticationToken;
import com.yangyi.resume.core.security.AuthorizationUser;
import com.yangyi.resume.core.security.JwtUser;
import com.yangyi.resume.core.utils.EncryptUtils;
import com.yangyi.resume.core.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

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
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity<?> authenticationLogin(@RequestBody AuthorizationUser authorizationUser) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authorizationUser.getUsername());
       String string = EncryptUtils.encryptPassword(authorizationUser.getPassword());
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword()))) {
            throw new AccountExpiredException("密码错误");
        }
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
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(jwtTokenUtil.getUserName(request));
        return ResponseEntity.ok(jwtUser);
    }

}

