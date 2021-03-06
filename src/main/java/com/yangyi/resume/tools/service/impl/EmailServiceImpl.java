package com.yangyi.resume.tools.service.impl;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.yangyi.resume.common.exception.BadRequestException;
import com.yangyi.resume.common.utils.ElAdminConstant;
import com.yangyi.resume.core.utils.EncryptUtils;
import com.yangyi.resume.tools.domain.EmailConfig;
import com.yangyi.resume.tools.domain.vo.EmailVo;
import com.yangyi.resume.tools.repository.EmailRepository;
import com.yangyi.resume.tools.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author jie
 * @date 2018-12-26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old) {
        try {
            if (!emailConfig.getPass().equals(old.getPass())) {
                // 对称加密
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        emailRepository.saveAndFlush(emailConfig);
        return emailConfig;
    }

    @Override
    public EmailConfig find() {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        if (emailConfig.isPresent()) {
            return emailConfig.get();
        } else {
            return new EmailConfig();
        }
    }

    public void send(EmailVo emailVo, EmailConfig emailConfig) {
        if (emailConfig == null) {
            throw new BadRequestException("请先配置，再操作");
        }
        /**
         * 封装
         */
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try {
            // 对称解密
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass()));
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        //ssl方式发送
        account.setStartttlsEnable(true);
        String content = emailVo.getContent() + ElAdminConstant.EMAIL_CONTENT;
        /**
         * 发送
         */
        try {
            MailUtil.send(account,
                    emailVo.getTos(),
                    emailVo.getSubject(),
                    content,
                    true);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

}
