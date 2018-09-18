package com.shenhesoft.examsapp.network;

import com.shenhesoft.examsapp.network.model.LessonsIntroModel;
import com.shenhesoft.examsapp.network.model.LessonsModel;
import com.shenhesoft.examsapp.network.model.ProductModel;

import java.util.Map;

import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author mashanshui
 * @date 2018/6/4
 * @desc TODO
 */
public interface LessonService {
    /**
     * @param params
     * @return 课件pdf资料
     */
    @GET("api/pdfByInterface")
    Observable<RequestResults<ListALLResults<LessonsModel>>> getLessonsPdf(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 单个课件pdf资料
     */
    @GET("api/pdfInterface/{id}")
    Observable<RequestResults<ListALLResults<LessonsModel>>> getSingleLessonsPdf(@Path("id") String productId);

    /**
     * @param params
     * @return 已购买课件
     */
    @GET("api/productOrder/getProducts")
    Observable<RequestResults<ListALLResults<ProductModel>>> getAlreadyBuyCourse(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 听课视频列表
     */
    @GET("api/videoByInterface")
    Observable<RequestResults<ListALLResults<LessonsModel>>> getLessons(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param productId
     * @return 单个听课视频列表
     */
    @GET("api/videoInterface/{id}")
    Observable<RequestResults<ListALLResults<LessonsModel>>> getSingleLessons(@Path("id") String productId);

    /**
     * @return 通过id获取下载url的信息
     */
    @GET("api/download/url/{id}")
    Observable<RequestResults> getUrlById(@Path("id") String id);

    /**
     * @return 获取课件介绍
     */
    @GET("api/getCourseDetailById/{id}")
    Observable<RequestResults<LessonsIntroModel>> getLessonsIntro(@Path("id") String id);

    /**
     * @return 用户视频统计数据
     */
    @PUT("api/getUserVideoStatics")
    Observable<RequestResults> videoStatics(@Body Map<String, Object> params);

}
