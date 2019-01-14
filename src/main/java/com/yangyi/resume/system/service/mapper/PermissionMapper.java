package com.yangyi.resume.system.service.mapper;

import com.yangyi.resume.common.mapper.EntityMapper;
import com.yangyi.resume.system.domain.Permission;
import com.yangyi.resume.system.service.dto.PermissionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-11-23
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper extends EntityMapper<PermissionDTO, Permission> {

}
