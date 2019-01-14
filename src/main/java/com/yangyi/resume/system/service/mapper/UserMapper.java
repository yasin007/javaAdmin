package com.yangyi.resume.system.service.mapper;

import com.yangyi.resume.common.mapper.EntityMapper;
import com.yangyi.resume.system.domain.User;
import com.yangyi.resume.system.service.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
