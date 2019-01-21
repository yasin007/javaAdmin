package com.yangyi.resume.tools.repository;

import com.yangyi.resume.tools.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author jie
 * @date 2018-12-27
 */
public interface PictureRepository extends JpaRepository<Picture, Long>, JpaSpecificationExecutor {
}
