package com.yangyi.resume.tools.service;

import com.yangyi.resume.tools.domain.QiniuConfig;
import com.yangyi.resume.tools.domain.QiniuContent;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.multipart.MultipartFile;

public interface QiNiuService {

    /**
     * 查配置
     *
     * @return
     */
    QiniuConfig find();

    /**
     * 修改配置
     *
     * @param qiniuConfig
     * @return
     */
    QiniuConfig update(QiniuConfig qiniuConfig);

    /**
     * 上传图片
     *
     * @param file
     * @param qiniuConfig
     * @return
     */
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     *
     * @param id
     * @return
     */
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     *
     * @param content
     * @param config
     * @return
     */
    String download(QiniuContent content, QiniuConfig config);


    /**
     * 删除文件
     *
     * @param content
     * @param config
     * @return
     */
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     *
     * @param config
     */
    void synchronize(QiniuConfig config);

}
