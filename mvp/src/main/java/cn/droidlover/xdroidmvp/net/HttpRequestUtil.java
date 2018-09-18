package cn.droidlover.xdroidmvp.net;

import retrofit2.Retrofit;

/**
 * @author 马山水
 * @date 2018/3/22
 * @desc 通过接口名获取不同的实例
 */

/**
 * 请求总类
 */
public class HttpRequestUtil {

    public static <T> T getRetrofitClient(String interfaceName) {
        Retrofit requestClient = RetrofitConfig.getInstance().getRetrofit();
        T t = null;
        try {
            t = (T) requestClient.create(Class.forName(interfaceName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("没有找到这个类文件,请检查类名是否正确", e);
        } finally {
            return t;
        }
    }
}





