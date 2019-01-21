package com.yangyi.resume.tools.rest;


import com.yangyi.resume.tools.domain.EmailConfig;
import com.yangyi.resume.tools.domain.vo.EmailVo;
import com.yangyi.resume.tools.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import com.yangyi.resume.common.aop.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping(value = "/email")
    public ResponseEntity get() {
        return new ResponseEntity(emailService.find(), HttpStatus.OK);
    }

    @Log(description = "配置邮件")
    @PutMapping(value = "/email")
    public ResponseEntity emailConfig(@Validated @RequestBody EmailConfig emailConfig) {
        emailService.update(emailConfig, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log(description = "发送邮件")
    @PostMapping(value = "/email")
    public ResponseEntity send(@Validated @RequestBody EmailVo emailVo) throws Exception {
        log.warn("REST request to send Email : {}" + emailVo);
        emailService.send(emailVo, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
}
