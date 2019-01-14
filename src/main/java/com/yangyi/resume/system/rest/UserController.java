package com.yangyi.resume.system.rest;

//import me.zhengjie.common.aop.log.Log;

import com.yangyi.resume.common.exception.BadRequestException;
//import com.yangyi.resume.common.utils.ElAdminConstant;
//import com.yangyi.resume.common.utils.RequestHolder;
import com.yangyi.resume.core.security.JwtUser;
import com.yangyi.resume.core.utils.EncryptUtils;
import com.yangyi.resume.core.utils.JwtTokenUtil;
import com.yangyi.resume.system.domain.User;
import com.yangyi.resume.system.domain.VerificationCode;
import com.yangyi.resume.system.service.UserService;
import com.yangyi.resume.system.service.VerificationCodeService;
import com.yangyi.resume.system.service.dto.UserDTO;
import com.yangyi.resume.system.service.query.UserQueryService;
//import com.yangyi.resume.tools.domain.Picture;
//import com.yangyi.resume.tools.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jie
 * @date 2018-11-23
 */
@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserQueryService userQueryService;


    private static final String ENTITY_NAME = "user";

    @GetMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUser(@PathVariable Long id) {
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserDTO userDTO, Pageable pageable) {
        return new ResponseEntity(userQueryService.queryAll(userDTO, pageable), HttpStatus.OK);
    }

    //    @Log(description = "新增用户")
    @PostMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody User resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(userService.create(resources), HttpStatus.CREATED);
    }

    //    @Log(description = "修改用户")
    @PutMapping(value = "/users")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody User resources) {
        if (resources.getId() == null) {
            throw new BadRequestException(ENTITY_NAME + " ID Can not be empty");
        }
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    //    @Log(description = "删除用户")
    @DeleteMapping(value = "/users/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
