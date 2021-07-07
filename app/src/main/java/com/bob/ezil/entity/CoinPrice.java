package com.bob.ezil.entity;

import java.util.Map;

/**
 * Created by BobCheung on 7/1/21 01:18
 */
public class CoinPrice {
    /**
     * {
     *     "ETH":{
     *         "BTC":0.06174,
     *         "USD":2118.3
     *     },
     *     "ETC":{
     *         "BTC":0.001574,
     *         "USD":54.14
     *     },
     *     "ZIL":{
     *         "BTC":0.00000237,
     *         "USD":0.0809
     *     }
     * }
     */
    private Map<String, Double> ETH;
    private Map<String, Double> ETC;
    private Map<String, Double> ZIL;

    public Map<String, Double> getETH() {
        return ETH;
    }

    public void setETH(Map<String, Double> ETH) {
        this.ETH = ETH;
    }

    public Map<String, Double> getETC() {
        return ETC;
    }

    public void setETC(Map<String, Double> ETC) {
        this.ETC = ETC;
    }

    public Map<String, Double> getZIL() {
        return ZIL;
    }

    public void setZIL(Map<String, Double> ZIL) {
        this.ZIL = ZIL;
    }
}
