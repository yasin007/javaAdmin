package com.yangyi.resume.system.repository;

import com.yangyi.resume.system.domain.Menu;
import com.yangyi.resume.system.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

/**
 * @author jie
 * @date 2018-12-17
 */
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor {

    /**
     * findByName
     *
     * @param name
     * @return
     */
    Menu findByName(String name);

    /**
     * findByRoles
     *
     * @param roleSet
     * @return
     */
    Set<Menu> findByRolesOrderBySort(Set<Role> roleSet);

    /**
     * findByPid
     *
     * @param pid
     * @return
     */
    List<Menu> findByPid(long pid);
}