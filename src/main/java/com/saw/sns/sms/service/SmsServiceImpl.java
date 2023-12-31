package com.saw.sns.sms.service;

import com.saw.sns.common.SnsResponse;
import com.saw.sns.exception.OperationFailedException;
import com.saw.sns.exception.ValidationErrorException;
import com.saw.sns.sms.infc.SmsService;
import com.saw.sns.sms.model.vm.SmsVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SmsServiceImpl implements SmsService {
    @Value("${SNS_AWS_REGION}")
    private String snsAwsRegion;
    Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    @Override
    public SnsResponse SendMessage(SmsVm smsVm) throws ValidationErrorException, OperationFailedException {
        SnsClient client = SnsClient.builder().region(Region.of(snsAwsRegion)).build();
        for (String number : smsVm.getTo()) {
            try
            {

                PublishRequest request = PublishRequest.builder()
                        .message(smsVm.getMessage())
                        .phoneNumber(number)
                        .build();

                PublishResponse response = client.publish(request);
                logger.debug("======SMS RESPONSE " + response);
            }
            catch (Exception e) {
                client.close();
                logger.error("======SMS ERROR " + e.getMessage());
                throw new OperationFailedException("Sending SMS failed\n " + e.getMessage());
            }
        }
        client.close();

        return SnsResponse.builder()
                .message("Message sent")
                .build();
    }
}
