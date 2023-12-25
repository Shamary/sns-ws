package com.saw.sns.sms.infc;

import com.saw.sns.exception.OperationFailedException;
import com.saw.sns.exception.ValidationErrorException;
import com.saw.sns.sms.model.vm.SmsVm;
import org.springframework.web.client.HttpClientErrorException;

public interface SmsService {
    public String SendMessage(SmsVm smsVm) throws ValidationErrorException, OperationFailedException;
}
