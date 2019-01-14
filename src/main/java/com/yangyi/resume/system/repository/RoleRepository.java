package com.yangyi.resume.system.repository;

import com.yangyi.resume.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jie
 * @date 2018-12-03
 */
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     *
     * @param name
     * @return
     */
    Role findByName(String name);
}
