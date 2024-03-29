package com.bob.ezil.web;

import com.bob.ezil.entity.AppConfig;
import com.bob.ezil.entity.Friend;
import com.bob.ezil.entity.ItemData;
import com.bob.common.base.web.HttpResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Jimmy on 2017/9/27 0027.
 */
public interface OlsApi {

    @GET("/api/v1/config/app-config")
    Observable<HttpResponse<AppConfig>> getAppConfig();

    @GET("/api/v1/user/{userId}/friends/new-request-count")
    Observable<HttpResponse<List<Friend>>> getFriends(@Path("userId") Long userId,
                                                      @Query("lastTime") Long lastTime,
                                                      @Header("Access-Token") String accessToken);

    @GET("/api/v1/config/app-config")
    Observable<HttpResponse<List<ItemData>>> getItemData();

}
