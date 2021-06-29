package com.sandboxol.common.config;

/**
 * Description:
 * 用于那些跨模块使用的埋点统计
 *
 * @author GZDong
 * @date 2019/12/6
 */

public interface CommonEventConstant {

    String CLICK_MAIN_ENTRANCE = "click_main_entrance"; //点击活动入口
    String CLICK_MAIN_CARD_FREE = "click_main_card.free"; //点击活动入口卡片，免费
    String CLICK_MAIN_CARD_PAY = "click_main_card.pay"; //点击活动入口卡片，付费
    String CLICK_LIST_CARD_FREE = "click_list_card_free"; //点击奖励卡片列表获取奖励，免费
    String CLICK_LIST_CARD_PAY = "click_list_card_pay"; //点击奖励卡片列表获取奖励，付费
    String CLICK_BUY_PASS = "click_buy_pass"; //点击购买通行证
    String CLICK_BUY_LEVEL = "click_buy_level"; //点击购买等级
    String CLICK_DETAIL = "click_detail"; //点击详情
    String CLICK_BRIEF = "click_brief"; //点击简介
    String CLICK_RECEIVE = "click_receive"; //点击全部领取
    String LEVEL_UP = "level_up"; //用户提升等级
    String BUY_PASS = "buy_pass"; //成功购买通行证
    String BUY_PASS_LEVEL = "buy_pass_level"; //成功购买通行证
    String BUY_LEVEL_COUNT = "buy_level_count"; //成功购买等级
    String BUY_LEVEL = "buy_level";//购买时的等级
    String CLICK_RECOMMEND_SHOP = "click_recommend_shop"; //通过推荐页进入商店模块
    String CLICK_CART_ENTRANCE = "click_cart_entrance"; //结算购物车时点击活动入口
    String CLICK_CARD_ENTRANCE = "click_card_entrance"; //点击装扮卡片下活动入口
}
