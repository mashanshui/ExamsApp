package cn.droidlover.xdroidmvp.net;




import cn.droidlover.xdroidmvp.kit.IToast;
import io.reactivex.functions.Function;


/**
 * 作者：Tornado
 * 创作日期：2017/8/24.
 * 描述：对请求结果进行判断，成功则将业务数据与请求状态数据分离
 */

public class HttpRequestFunc<T> implements Function<HttpResult<T>, T> {


    @Override
    public T apply(HttpResult<T> result) throws Exception {
        //code不为1 则没有返回有效数据
        if (!result.getState()) {
            IToast.showShort("=====result====="+result.getMessage());
        }
        //后台非要在data上再套一层[摊手]
        return result.getData();
    }
}
