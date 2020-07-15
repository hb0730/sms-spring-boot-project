package com.github.jackieonway.sms.service;

import com.github.jackieonway.sms.entity.BaseRequest;
import com.github.jackieonway.sms.entity.SmsProperties;
import com.github.jackieonway.sms.entity.SubMailRequest;
import com.github.jackieonway.sms.exception.SmsException;
import com.github.jackieonway.sms.submail.client.SubMailClient;
import com.github.jackieonway.sms.submail.model.MultiParams;
import com.github.jackieonway.sms.submail.model.SubMailParams;
import com.github.jackieonway.sms.submail.model.SubMailProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 赛邮短信服务
 *
 * @author bing_huang
 * @see <a href="https://www.mysubmail.com/chs/documents/developer/index"></a>
 * @since V1.0 2020/07/09 8:12
 */
public class SubMailSmsServiceImpl implements SmsService {
    private final SubMailClient client;

    public SubMailSmsServiceImpl(SmsProperties smsProperties) {
        SubMailProperties properties = new SubMailProperties(smsProperties.getAppid(), smsProperties.getSecurityKey());
        client = new SubMailClient(properties);
    }

    @Override
    public Object sendSms(BaseRequest params) throws SmsException {
        Assert.notNull(params, "params can not be null");
        if (params instanceof SubMailRequest) {

            SubMailRequest smsParams = (SubMailRequest) params;
            String phoneNumber = smsParams.getPhoneNumber();
            Assert.hasText(phoneNumber, "手机号码不为空");
            String content = smsParams.getContent();
            Assert.hasText(content, "正文不为空");
            try {
                SubMailParams subMailParams = new SubMailParams();
                subMailParams.setTag(smsParams.getTag());
                subMailParams.setSignType(smsParams.getSignType().getValue());
                return client.sendSms(phoneNumber, content, subMailParams);
            } catch (Exception e) {
                throw new SmsException(e);
            }
        }
        return null;
    }

    @Override
    public Object sendTemplateSms(@NonNull String templateId, BaseRequest params) throws SmsException {
        Assert.hasText(templateId, "templateId must not null");
        Assert.notNull(params, "params can not be null");
        if (params instanceof SubMailRequest) {

            SubMailRequest smsParams = (SubMailRequest) params;
            String phoneNumber = smsParams.getPhoneNumber();
            Assert.hasText(phoneNumber, "手机号码不为空");
            try {
                SubMailParams subMailParams = new SubMailParams();
                subMailParams.setTag(smsParams.getTag());
                subMailParams.setSignType(smsParams.getSignType().getValue());
                return client.sendTemplateSms(phoneNumber, templateId, smsParams.getContent(), subMailParams);
            } catch (Exception e) {
                throw new SmsException(e);
            }
        }
        return null;
    }

    @Override
    public Object sendBatchSms(@NonNull BaseRequest params) throws SmsException {
        Assert.notNull(params, "params can not be null");
        if (params instanceof SubMailRequest) {

            SubMailRequest smsParams = (SubMailRequest) params;
            String content = smsParams.getContent();
            List<SubMailRequest.Multi> multi = smsParams.getMulti();
            Assert.hasText(content, "正文不为空");
            Assert.notEmpty(multi, "批量信息不为空");
            try {

                List<MultiParams> multiParams = new ArrayList<>();
                multi.forEach(e -> {
                    MultiParams p = new MultiParams();
                    BeanUtils.copyProperties(e, p);
                    multiParams.add(p);
                });
                SubMailParams subMailParams = new SubMailParams();
                subMailParams.setTag(smsParams.getTag());
                subMailParams.setSignType(smsParams.getSignType().getValue());
                return client.sendBatchSms(content, multiParams, subMailParams);
            } catch (Exception e) {
                throw new SmsException(e);

            }
        }
        return null;
    }

    @Override
    public Object sendBatchTemplateSms(@NonNull String templateId, BaseRequest params) throws SmsException {
        Assert.hasText(templateId, "templateId must not null");
        Assert.notNull(params, "params can not be null");
        if (params instanceof SubMailRequest) {
            SubMailRequest smsParams = (SubMailRequest) params;
            List<SubMailRequest.Multi> multi = smsParams.getMulti();
            Assert.notEmpty(multi, "批量信息不为空");
            try {

                List<MultiParams> multiParams = new ArrayList<>();
                multi.forEach(e -> {
                    MultiParams p = new MultiParams();
                    BeanUtils.copyProperties(e, p);
                    multiParams.add(p);
                });
                SubMailParams subMailParams = new SubMailParams();
                subMailParams.setTag(smsParams.getTag());
                subMailParams.setSignType(smsParams.getSignType().getValue());
                return client.sendBatchTemplateSms(templateId, multiParams, subMailParams);
            } catch (Exception e) {
                throw new SmsException(e);
            }

        }
        return null;
    }
}
