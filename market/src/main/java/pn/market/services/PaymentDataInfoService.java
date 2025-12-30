package pn.market.services;

import pn.market.entities.PaymentDataInfo;

@FunctionalInterface
public interface PaymentDataInfoService {
    PaymentDataInfo create(long paymentId, long orderId);
}
