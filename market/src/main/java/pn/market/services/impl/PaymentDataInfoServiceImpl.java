package pn.market.services.impl;

import org.springframework.stereotype.Service;
import pn.market.entities.PaymentDataInfo;
import pn.market.services.PaymentDataInfoService;
@Service
public class PaymentDataInfoServiceImpl implements PaymentDataInfoService {
    @Override
    public PaymentDataInfo create(long paymentId, long orderId) {
        return new PaymentDataInfo(paymentId, orderId);
    }
}
