package com.yangyi.resume.tools.rest;

import com.yangyi.resume.tools.domain.AlipayConfig;
import com.yangyi.resume.tools.domain.vo.TradeVo;
import com.yangyi.resume.tools.service.AlipayService;
//import io.swagger.annotations.ApiOperation;
import com.yangyi.resume.tools.utils.AlipayUtils;
import lombok.extern.slf4j.Slf4j;
import com.yangyi.resume.common.aop.log.Log;
//import me.zhengjie.tools.domain.vo.TradeVo;
//import me.zhengjie.tools.service.AlipayService;
//import me.zhengjie.tools.util.AliPayStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author jie
 * @date 2018-12-31
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class AliPayController {

    @Autowired
    AlipayUtils alipayUtils;

    @Autowired
    private AlipayService alipayService;

    @GetMapping(value = "/aliPay")
    public ResponseEntity get() {
        return new ResponseEntity(alipayService.find(), HttpStatus.OK);
    }

    @Log(description = "配置支付宝")
    @PutMapping(value = "/aliPay")
    public ResponseEntity payConfig(@Validated @RequestBody AlipayConfig alipayConfig) {
        alipayConfig.setId(1L);
        alipayService.update(alipayConfig);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log(description = "支付宝PC网页支付")
//    @ApiOperation(value = "PC网页支付")
    @PostMapping(value = "/aliPay/toPayAsPC")
    public ResponseEntity toPayAsPC(@Validated @RequestBody TradeVo trade) throws Exception {
        log.warn("REST request to toPayAsPC Trade : {}" + trade);
        AlipayConfig alipay = alipayService.find();
        trade.setOutTradeNo(alipayUtils.getOrderCode());
        String payUrl = alipayService.toPayAsPC(alipay, trade);
        return ResponseEntity.ok(payUrl);
    }

}
