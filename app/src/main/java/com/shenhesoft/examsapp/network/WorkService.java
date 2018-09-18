package com.shenhesoft.examsapp.network;

import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.network.model.AnswerPolicysModel;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;
import com.shenhesoft.examsapp.network.model.KnowledgeModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.ScienceDomainsModel;
import com.shenhesoft.examsapp.network.model.StartAnswerModel;
import com.shenhesoft.examsapp.network.model.TestPagerListModel;

import java.util.Map;

import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;

/**
 * @author mashanshui
 * @date 2018/5/22
 * @desc TODO
 */
public interface WorkService {

    /**
     * @param params
     * @return 已购题库
     */
    @GET("api/productOrder/getProducts")
    Observable<RequestResults<ListALLResults<ProductModel>>> getAlreadyBuyProducts(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 商品列表
     */
    @GET("api/products")
    Observable<RequestResults<ListALLResults<ProductModel>>> getProductsList(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 试卷列表
     */
    @GET("api/testPaper/getTestPapersByProductId")
    Observable<RequestResults<ListALLResults<TestPagerListModel>>> getTestPapersByProductId(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 做题预览
     */
    @GET("api/answerPolicys")
    Observable<RequestResults<ListALLResults<AnswerPolicysModel>>> getAnswerPolicys(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 获取题目（题目分析）
     */
    @GET("api/appQuestions")
    Observable<RequestResults<ListALLResults<AnswerModel>>> getAnalysisQuestions(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 获取题目（做题）
     */
    @GET("api/questionsByInterface")
    Observable<RequestResults<ListALLResults<AnswerModel>>> getHomeWorkQuestions(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 下一题
     */
    @POST("api/questionAnswerDetail")
    Observable<RequestResults> nextSubject(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 二次提交,下一题
     */
    @PUT("api/questionAnswerDetail")
    Observable<RequestResults> replyNextSubject(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 开始做题
     */
    @POST("api/questionAnswer")
    Observable<RequestResults<StartAnswerModel>> startAnswer(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 答题结束，上传信息
     */
    @PUT("api/questionAnswer")
    Observable<RequestResults<FinishAnswerModel>> answerFinish(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 知识点
     */
    @GET("api/knowledgePoints")
    Observable<RequestResults<ListALLResults<KnowledgeModel>>> getKnowledge(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 学术领域
     */
    @GET("api/scienceDomains")
    Observable<RequestResults<ListALLResults<ScienceDomainsModel>>> getScienceDomains(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 按知识点做题
     */
    @GET("api/getKnowledgeQuestions")
    Observable<RequestResults<ListALLResults<AnswerModel>>> getBankByKnowledge(@QueryMap(encoded = true) Map<String, Object> params);


    /**
     * @return 题库、课件、作文搜索
     */
    @GET("api/product/getProductsBySearch")
    Observable<RequestResults<ListALLResults<ProductModel>>> searchQuestionBank(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 题目搜索
     */
    @GET("api/questions/getQuestionsBySearch")
    Observable<RequestResults<ListALLResults<AnswerModel>>> searchSubjectBank(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 收藏题目
     */
    @POST("api/loveCollect")
    Observable<RequestResults> collectAnswer(@Body Map<String, Object> params);

    /**
     * @return 取消收藏题目
     */
    @PUT("api/loveCollect")
    Observable<RequestResults> cancelCollect(@Body Map<String, Object> params);

    /**
     * @return 移除错题
     */
    @PUT("api/deleteWrongQuesiton")
    Observable<RequestResults> removeQuestion(@Body Map<String, Object> params);
}
