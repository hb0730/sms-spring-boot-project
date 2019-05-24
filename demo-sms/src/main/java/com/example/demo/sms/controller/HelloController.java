package com.example.demo.sms.controller;

import com.aliyuncs.exceptions.ClientException;
import com.github.qcloudsms.httpclient.HTTPException;
import com.pengzu.sms.entity.TencentSmsRequest;
import com.pengzu.sms.service.PengzuSmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author Jackie
 * @version 1.0
 * @className HelloController
 * @description TODO
 * @date 2018/11/8 10:17
 **/
@RestController
public class HelloController {

    @Autowired
    private PengzuSmsService pengzuSmsService;

    @GetMapping("/sayHello")
    public Object sayHello() throws ClientException, HTTPException, IOException {
        // your template params
        String[] paramst = {"5678","5"};
        TencentSmsRequest tencentSmsRequest = new TencentSmsRequest();
        tencentSmsRequest.setPhoneNumber(new String[]{"your cellphone"});
        tencentSmsRequest.setParams(paramst);
        return pengzuSmsService.sendTemplateSms("328921", tencentSmsRequest);
    }
}
