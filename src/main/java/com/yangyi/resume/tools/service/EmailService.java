package com.yangyi.resume.tools.service;


import com.yangyi.resume.tools.domain.EmailConfig;
import com.yangyi.resume.tools.domain.vo.EmailVo;
import org.springframework.scheduling.annotation.Async;


public interface EmailService {

    /**
     * 更新邮件配置
     *
     * @param emailConfig
     * @param old
     * @return
     */
    EmailConfig update(EmailConfig emailConfig, EmailConfig old);

    /**
     * 查询配置
     *
     * @return
     */
    EmailConfig find();

    /**
     * 发送邮件
     *
     * @param emailVo
     * @param emailConfig
     * @throws Exception
     */
    @Async
    void send(EmailVo emailVo, EmailConfig emailConfig) throws Exception;

}
