package com.bob.ezil.web;

import com.bob.ezil.entity.AccountBalance;
import com.bob.ezil.entity.CoinMining;
import com.bob.ezil.entity.CoinPrice;
import com.bob.ezil.entity.Reported;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public interface IEzilApi {

    @GET("/current_stats/{eth_address}.{zil_address}/reported")
    Observable<Reported> getReported(@Path("eth_address") String ethAddress,
                                     @Path("zil_address") String zilAddress);

    @GET("/forecasts_with_hashrate/{eth_address}.{zil_address}")
    Observable<CoinMining> getCoinMining(@Path("eth_address") String ethAddress,
                                         @Path("zil_address") String zilAddress);

    @GET("/balances/{eth_address}.{zil_address}")
    Observable<AccountBalance> getAccountBalance(@Path("eth_address") String ethAddress,
                                                 @Path("zil_address") String zilAddress);

    @GET("/rates")
    Observable<CoinPrice> getCoinPrice();

}
