package com.lingfenglong.spzx.service.pay.controller;

import com.lingfenglong.spzx.model.entity.pay.PaymentInfo;
import com.lingfenglong.spzx.model.vo.common.CommonResultCode;
import com.lingfenglong.spzx.model.vo.common.Result;
import com.lingfenglong.spzx.service.pay.service.impl.AlipayServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Tag(name = "支付接口")
@Controller
@RequestMapping("/api/order/alipay")
public class AliPayController {

    private final AlipayServiceImpl alipayServiceImpl;
    private final SpringTemplateEngine templateEngine;

    public AliPayController(AlipayServiceImpl alipayServiceImpl, SpringTemplateEngine templateEngine) {
        this.alipayServiceImpl = alipayServiceImpl;
        this.templateEngine = templateEngine;
    }

    @ResponseBody
    @GetMapping("/submitAlipay/{orderNo}")
    public Result<String> submitAlipay(@PathVariable("orderNo") String orderNo) {
        String form = alipayServiceImpl.submitAlipay(orderNo);

        // PaymentInfo paymentInfo = alipayServiceImpl.submitMyAlipay(orderNo);
        // Context context = new Context();
        // context.setVariable("paymentInfo", paymentInfo);
        // String form = templateEngine.process("payment", context);

        return Result.build(form, CommonResultCode.SUCCESS);
    }

    @ResponseBody
    @RequestMapping("/callback/notify")
    public String alipayNotify() {
        alipayServiceImpl.updatePaymentStatus();
        return "success";
    }
}