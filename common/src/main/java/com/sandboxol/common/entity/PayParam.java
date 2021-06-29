package com.sandboxol.common.entity;

/**
 * Created by Bob on 2017/12/25
 */
public class PayParam {

    private String signature;
    private String purchaseData;
    private String mOrderId;
    private String mSku;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPurchaseData() {
        return purchaseData;
    }

    public void setPurchaseData(String purchaseData) {
        this.purchaseData = purchaseData;
    }

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getmSku() {
        return mSku;
    }

    public void setmSku(String mSku) {
        this.mSku = mSku;
    }
}
