package com.yangyi.resume.tools.service.query;


import com.yangyi.resume.common.utils.PageUtil;
import com.yangyi.resume.tools.domain.QiniuContent;
import com.yangyi.resume.tools.repository.QiniuContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class QiNiuQueryService {

    @Autowired
    private QiniuContentRepository qiniuContentRepository;

    public Object queryAll(QiniuContent qiniuContent, Pageable pageable) {
        return PageUtil.toPage(qiniuContentRepository.findAll(new Spec(qiniuContent), pageable));
    }

    class Spec implements Specification<QiniuContent> {

        private QiniuContent qiniuContent;

        public Spec(QiniuContent qiniuContent) {
            this.qiniuContent = qiniuContent;
        }

        @Override
        public Predicate toPredicate(Root<QiniuContent> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(qiniuContent.getKey())) {
                /**
                 * 模糊
                 */
                list.add(cb.like(root.get("key").as(String.class), "%" + qiniuContent.getKey() + "%"));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
