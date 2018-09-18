package cn.droidlover.xdroidmvp.net;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * token重新获取(可用于token过期后重新获取A，也可以用于设置刷新token）
 */
public class ATokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) {
//        Request originalRequest = chain.request();
//        Request tokenRequest = originalRequest.newBuilder()
//                .header("userToken", HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_TOKEN,""))
//                .build();//添加本地token请求
//        Logger.i("userToken-->"+ HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_TOKEN,""));
//        Response response = chain.proceed(tokenRequest);//请求得到token是否过期的数据
//        byte[] respByte = response.body().bytes();
//        String responseStr = new String(respByte);
//        Logger.i(responseStr);
//        try {
//            JSONObject object = new JSONObject(responseStr);
//            int statusCode = object.getInt("StatusCode");
//            Logger.i("statusCode--->"+statusCode);
//            if (statusCode < 0){
//           //说明token过期重新请求
//                String token = resetNewToken();
//                if (null != token && token.length()>0){
//                    Request newRequest = chain.request()
//                            .newBuilder()
//                            .header("userToken",token)
//                            .build();
//                    return chain.proceed(newRequest);//得到新的就会保存到本地
//                }
//            }
//            return response.newBuilder()
//                    .body(ResponseBody.create(null, respByte))
//                    .build();//在前面获取bytes的时候response的stream已经被关闭了，要重新生成response
//        } catch (JSONException e) {
//            e.printStackTrace();
//            Logger.i("responseStr-->"+responseStr);
////            throw new HttpResultException("解析错误");
//            return response.newBuilder()
//                    .body(ResponseBody.create(null, respByte))
//                    .build();//在前面获取bytes的时候response的stream已经被关闭了，要重新生成response
//        }
        return null;
    }

    private String resetNewToken() {
//        Call<NewLoginData> newLoginDataCall = HttpUtils.getHouseService().newCallLogin("login", HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_PHONE, ""), HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_PSD, ""));
//        try {
//            Logger.i("获取token");
//            retrofit2.Response<NewLoginData> execute = newLoginDataCall.execute();
//            HouseApplication.getMainPreferences().edit().putString(SharePreferenceConstant.USER_TOKEN,execute.body().getToken()).apply();
//
//            Logger.i("userToken-->"+ HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_TOKEN,""));
//            return execute.body().getToken();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "";
    }

//    private void getNewToken() {
//
//        HttpUtils.getHouseService().newLogin("", HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_PHONE,null),HouseApplication.getMainPreferences().getString(SharePreferenceConstant.USER_PSD,null))
//                .observeOn(Schedulers.io())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new HttpResultObserver<NewLoginData>() {
//                    @Override
//                    public void onSuccess(NewLoginData newLoginData) {
//                        HouseApplication.getMainPreferences().edit().putString(SharePreferenceConstant.USER_TOKEN,newLoginData.getToken())
//                                .putString(SharePreferenceConstant.USER_CODE,newLoginData.getUserCode()).apply();
//                    }
//
//                    @Override
//                    public void _onError(Throwable e) {
//
//                    }
//                });
//    }
//
//    private boolean isTokenExpired(String response) throws IOException {
//        try {
//            JSONObject jsonObject = new JSONObject(response);
//            int statusCode = jsonObject.getInt("StatusCode");
//            if (statusCode < 0){
//                return true;
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }
}
