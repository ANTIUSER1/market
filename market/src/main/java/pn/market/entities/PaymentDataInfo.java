package pn.market.entities;

/**
 * not need to store in db
 */
public class PaymentDataInfo {

    private long paymentID;
    private long oderId;

    public PaymentDataInfo(long paymentID, long oderId) {
        this.paymentID = paymentID;
        this.oderId = oderId;
    }

    public long getPaymentID() {
        return paymentID;
    }

    public long getOderId() {
        return oderId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PaymentDataInfo{");
        sb.append("paymentID=").append(paymentID);
        sb.append(", oderId=").append(oderId);
        sb.append('}');
        return sb.toString();
    }
}
