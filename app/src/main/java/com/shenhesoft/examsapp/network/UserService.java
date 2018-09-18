package com.shenhesoft.examsapp.network;

import com.shenhesoft.examsapp.network.model.AchieveModel;
import com.shenhesoft.examsapp.network.model.AddressModel;
import com.shenhesoft.examsapp.network.model.AdviceModel;
import com.shenhesoft.examsapp.network.model.EvaluateModel;
import com.shenhesoft.examsapp.network.model.ExpenseCalendarModel;
import com.shenhesoft.examsapp.network.model.LoginModel;
import com.shenhesoft.examsapp.network.model.PayModel;
import com.shenhesoft.examsapp.network.model.PayPswModel;
import com.shenhesoft.examsapp.network.model.PayResultModel;
import com.shenhesoft.examsapp.network.model.PersonMessageModel;
import com.shenhesoft.examsapp.network.model.ProductModel;
import com.shenhesoft.examsapp.network.model.PushMessageModel;
import com.shenhesoft.examsapp.network.model.RechargeWayModel;
import com.shenhesoft.examsapp.network.model.SecretKeyModel;
import com.shenhesoft.examsapp.network.model.StatisticsModel;
import com.shenhesoft.examsapp.network.model.VerifyCodeModel;
import com.shenhesoft.examsapp.network.model.WalletMessageModel;

import java.util.Map;

import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResultsList;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface UserService {

    /**
     * @return 获取密钥
     */
    @GET("sessionKey")
    Observable<RequestResults<SecretKeyModel>> getSecretKey();

    /**
     * @param params
     * @return 登录
     */
    @POST("doLogin/cellphone/abc")
    Observable<RequestResults<LoginModel>> login(@Body Map<String, Object> params);

    /**
     * @return 登出
     */
    @POST("logout/cellphone")
    Observable<RequestResults> logout();

    /**
     * @param params
     * @return 注册
     */
    @POST("api/regedit/student")
    Observable<RequestResults> register(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 忘记密码
     */
    @POST("api/resetPasswd")
    Observable<RequestResults> forgetPsw(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 获取验证码
     */
    @POST("api/sendCHkCode")
    Observable<RequestResults<VerifyCodeModel>> getVerifyCode(@Body Map<String, Object> params);

    /**
     * @return 获取学习统计记录
     */
    @GET("api/reportByStudent/{userId}/{type}")
    Observable<RequestResultsList<StatisticsModel>> studyStatistics(@Path("userId") String userId, @Path("type") String type);

    /**
     * @param params
     * @return 获取赠送金币区间
     */
    @GET("api/goldDiscountPolicys")
    Observable<RequestResults<ListALLResults<RechargeWayModel>>> getGoldSection(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 充值
     */
    @POST("api/goldDetailStudentOrder")
    Observable<RequestResults<PayModel>> recharge(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 投诉建议管理列表
     */
    @GET("api/userComplianOpinions")
    Observable<RequestResults<ListALLResults<AdviceModel>>> getAdviceList(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 新增建议管理
     */
    @POST("api/complainOpion")
    Observable<RequestResults> addAdvice(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 准备微信支付
     */
    @POST("weixin/addOrder")
    Observable<RequestResults<PayResultModel>> prepareWechatPay(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 我的成就
     */
    @GET("api/medals/getMedalsByUserId")
    Observable<RequestResults<AchieveModel>> getMyAchieveList(@QueryMap Map<String, Object> params);

    /**
     * @param params
     * @return 查看评价
     */
    @GET("api/scores")
    Observable<RequestResults<ListALLResults<EvaluateModel>>> lookEvaluate(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 是否已经评价
     */
    @GET("api/scoresOnlyOne")
    Observable<RequestResults<ListALLResults<EvaluateModel>>> isWritingEvaluate(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 填写评价
     */
    @POST("api/score")
    Observable<RequestResults> writeEvaluate(@Body Map<String, Object> params);

    /**
     * @param params
     * @return 钱包消费记录
     */
    @GET("api/goldDetailStudents")
    Observable<RequestResults<ListALLResults<ExpenseCalendarModel>>> expenseCalendar(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 钱包金币余额等信息
     */
    @GET("api/students")
    Observable<RequestResults<ListALLResults<WalletMessageModel>>> myWalletMessage(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @param params
     * @return 购买商品
     */
    @GET("api/productOrder/buy")
    Observable<RequestResults> buy(@QueryMap Map<String, Object> params);

    /**
     * @param params
     * @return 设置或修改支付密码
     */
    @POST("api/goldPaySafe")
    Observable<RequestResults> changePayPsw(@Body Map<String, Object> params);

    /**
     * @return 是否修改了支付密码
     */
    @GET("api/goldPaySafes/noPage")
    Observable<RequestResultsList<PayPswModel>> isHavePayPsw(@Query("userId") String userId);

    /**
     * @param params
     * @return 修改登录密码
     */
    @POST("api/alterPasswd/phone")
    Observable<RequestResults> changePsw(@Body Map<String, Object> params);

    /**
     * @return 查看个人资料
     */
    @GET("api/student/{id}")
    Observable<RequestResults<PersonMessageModel>> lookPersonData(@Path("id") String userId);

    /**
     * @return 修改个人资料
     */
    @PUT("api/student")
    Observable<RequestResults> modifyMessage(@Body Map<String, Object> params);

    /**
     * @return 地址管理查看地址
     */
    @GET("api/contactWays")
    Observable<RequestResults<ListALLResults<AddressModel>>> lookAddress(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 地址管理查看地址
     */
    @POST("api/contactWay")
    Observable<RequestResults> addAddress(@Body Map<String, Object> params);

    /**
     * @return 修改或删除地址
     */
    @PUT("api/contactWay")
    Observable<RequestResults> modifyOrDeleteAddress(@Body Map<String, Object> params);

    /**
     * @return 获取消息推送历史列表
     */
    @GET("api/pushMessages")
    Observable<RequestResults<ListALLResults<PushMessageModel>>> getPushMessage(@QueryMap(encoded = true) Map<String, Object> params);

    /**
     * @return 获取产品详情（所有产品,在点开商品详情后使用）
     */
    @GET("api/products/getProductByMap")
    Observable<RequestResults<ProductModel>> getProductDetail(@QueryMap() Map<String, Object> params);
}
