package com.yangyi.resume.core.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * 返回token
 *
 * @author yangyi
 */
@Getter
@AllArgsConstructor
public class AuthenticationToken implements Serializable {

    private final String token;
}
