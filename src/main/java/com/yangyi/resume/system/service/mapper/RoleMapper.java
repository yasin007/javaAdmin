package com.yangyi.resume.system.service.mapper;

import  com.yangyi.resume.common.mapper.EntityMapper;
import  com.yangyi.resume.system.domain.Role;
import  com.yangyi.resume.system.service.dto.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", uses = {PermissionMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<RoleDTO, Role> {

}
