package com.example.kylinarm.photorequesttest;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/7.
 */

public interface PhotoService {
    @Multipart
    @POST("site/post")
    Observable<ResponseBody> upload(@PartMap HashMap<String, RequestBody> params);
}
