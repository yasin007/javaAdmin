package com.yangyi.resume.tools.repository;

import com.yangyi.resume.tools.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jie
 * @date 2018-12-31
 */
public interface AlipayRepository extends JpaRepository<AlipayConfig,Long> {
}
