package com.shenhesoft.examsapp.network;

import com.shenhesoft.examsapp.network.model.ChatMsgModel;
import com.shenhesoft.examsapp.network.model.InteractiveModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.SearchDateModel;
import com.shenhesoft.examsapp.network.model.SearchTypeModel;

import java.util.Map;

import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author mashanshui
 * @date 2018/5/30
 * @desc TODO
 */
public interface ModifyService {

    /**
     * @param params
     * @return 获取已购作文批改列表
     */
    @GET("api/articleOrders")
    Observable<RequestResults<ListALLResults<ProductModel>>> getAlreadyBuyProduct(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 获取推荐作文批改列表
     */
    @GET("api/products")
    Observable<RequestResults<ListALLResults<ProductModel>>> getRecommendProduct(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 老师详情
     */
    @GET("api/products")
    Observable<RequestResults<ListALLResults<ProductModel>>> getTeacherDetail(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 获取师生交互详情
     */
    @GET("api/articleAndroids")
    Observable<RequestResults<ListALLResults<InteractiveModel>>> getInteractiveDetail(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 获取师生问答详情
     */
    @GET("api/chatDetails")
    Observable<RequestResults<ListALLResults<ChatMsgModel>>> getAskDetail(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 师生问答发送消息
     */
    @POST("api/chatDetail")
    Observable<RequestResults<ListALLResults<ChatMsgModel>>> sendMessage(@Body Map<String, Object> params);

    /**
     * @return 上传作文
     */
    @Multipart
    @POST("api/article")
    Observable<RequestResults> uploadWriting(@QueryMap() Map<String, Object> params, @Part MultipartBody.Part file);

    /**
     * @return 搜索作文批改获取类型或题源
     */
    @GET("api/basicdata/dims/{pid}")
    Observable<RequestResultsList<SearchTypeModel>> getSearchType(@Path("pid") String pid);

    /**
     * @return 搜索作文批改获取年份
     */
    @GET("api/articleCorrectionConfs/noPage")
    Observable<RequestResults<SearchDateModel>> getSearchDate();

}
