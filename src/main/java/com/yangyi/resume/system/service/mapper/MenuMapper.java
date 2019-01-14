package com.yangyi.resume.system.service.mapper;

import com.yangyi.resume.common.mapper.EntityMapper;
import com.yangyi.resume.system.domain.Menu;
import com.yangyi.resume.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jie
 * @date 2018-12-17
 */
@Mapper(componentModel = "spring", uses = {RoleMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}
