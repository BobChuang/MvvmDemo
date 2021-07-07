package com.bob.ezil.view.fragment.home;

import android.content.Context;
import android.databinding.ObservableField;
import android.util.Log;

import com.bob.common.base.viewmodel.ViewModel;
import com.bob.common.messenger.Messenger;
import com.bob.ezil.config.MessageToken;
import com.bob.ezil.entity.AccountBalance;
import com.bob.ezil.entity.CoinMining;
import com.bob.ezil.entity.CoinPrice;
import com.bob.ezil.entity.EstimatedReward;
import com.bob.ezil.entity.Reported;
import com.google.gson.Gson;

/**
 * Created by BobCheung on 6/30/21 00:46
 */
public class HomeViewModel extends ViewModel {

    private Context context;

    public ObservableField<Reported> reported = new ObservableField<>();
    public ObservableField<CoinMining> coinMining = new ObservableField<>();
    public ObservableField<AccountBalance> accountBalance = new ObservableField<>();
    public ObservableField<CoinPrice> coinPrice = new ObservableField<>();
    public ObservableField<EstimatedReward> estimatedReward = new ObservableField<>();

    public HomeViewModel(Context context) {
        this.context = context;
        getRewards();
        getCoinMining();
        getCoinPrice();
        getAccountBalance();
        initMessenger();
    }

    private void initMessenger() {
        //刷新数据
        Messenger.getDefault().register(this, MessageToken.REFRESH_MAIN, ()-> {
            getRewards();
            getCoinMining();
            getCoinPrice();
            getAccountBalance();
        });
    }

    /**
     * 算力
     */
    private void getRewards() {
        new HomeModel().getReward(context, data -> {
            Log.e("getReported", new Gson().toJson(data));
            reported.set(data);
        });
    }

    /**
     * 预估挖币
     */
    private void getCoinMining() {
        new HomeModel().getCoinMining(context, data -> {
            Log.e("getCoinMining", new Gson().toJson(data));
            coinMining.set(data);
            calculateEarnings();
        });
    }

    /**
     * 获取币价
     */
    private void getCoinPrice() {
        new HomeModel().getCoinPrice(context, data -> {
            Log.e("getCoinMining", new Gson().toJson(data));
            coinPrice.set(data);
            calculateEarnings();
        });
    }

    /**
     * 计算收益
     */
    private void calculateEarnings() {
        if (coinPrice.get() != null && coinPrice.get().getETH() != null && coinMining.get() != null && coinMining.get().getEth() != null && accountBalance.get() != null && accountBalance.get().getCreated_at() != null ) {
            EstimatedReward reward = new EstimatedReward();
            reward.setEthCoinPrice(coinPrice.get().getETH().get("USD"));
            reward.setEthDayReward(coinPrice.get().getETH().get("USD") * coinMining.get().getEth().getDay());
            reward.setEthWeekReward(coinPrice.get().getETH().get("USD") * coinMining.get().getEth().getWeek());
            reward.setEthMonthReward(coinPrice.get().getETH().get("USD") * coinMining.get().getEth().getThirty_days());
            reward.setEthTotalRevenue(coinPrice.get().getETH().get("USD") * accountBalance.get().getEth());
            reward.setEthRemainingTime((accountBalance.get().getEth_min_payout() - accountBalance.get().getEth())/coinMining.get().getEth().getDay());

            reward.setZilCoinPrice(coinPrice.get().getZIL().get("USD"));
            reward.setZilDayReward(coinPrice.get().getZIL().get("USD") * coinMining.get().getZil_eth().getDay());
            reward.setZilWeekReward(coinPrice.get().getZIL().get("USD") * coinMining.get().getZil_eth().getWeek());
            reward.setZilMonthReward(coinPrice.get().getZIL().get("USD") * coinMining.get().getZil_eth().getThirty_days());
            reward.setZilTotalRevenue(coinPrice.get().getZIL().get("USD") * accountBalance.get().getZil());
            reward.setZilRemainingTime((accountBalance.get().getZil_min_payout() - accountBalance.get().getZil())/coinMining.get().getZil_eth().getDay());
            estimatedReward.set(reward);
        }
    }

    /**
     * 账号价值
     */
    private void getAccountBalance() {
        new HomeModel().getAccountBalance(context, data -> {
            Log.e("getAccountBalance", new Gson().toJson(data));
            accountBalance.set(data);
            calculateEarnings();
        });
    }
}
