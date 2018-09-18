package com.shenhesoft.examsapp.data.remote;

import com.shenhesoft.examsapp.data.AskDataSource;
import com.shenhesoft.examsapp.network.ModifyRetrofit;
import com.shenhesoft.examsapp.network.ModifyService;
import com.shenhesoft.examsapp.network.model.ChatMsgModel;

import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/5/31
 * @desc TODO
 */
public class AskRemoteDataaSource implements AskDataSource {
    private ModifyService modifyService;

    public AskRemoteDataaSource() {
        modifyService = HttpRequestUtil.getRetrofitClient(ModifyService.class.getName());
    }

    @Override
    public void loadData(int start, int length, String chatId, final LoadDataCallBack callBack) {
        Observable<RequestResults<ListALLResults<ChatMsgModel>>> observable = modifyService.getAskDetail(ModifyRetrofit.getInstance().getAskDetail(start, length, chatId));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ChatMsgModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ChatMsgModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ChatMsgModel>> data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess(data.getObj());
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void sendMessage(String chatId, String message, final SendMessageCallBack callBack) {
        Observable<RequestResults<ListALLResults<ChatMsgModel>>> observable = modifyService.sendMessage(ModifyRetrofit.getInstance().sendMessage(chatId, message));
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<ChatMsgModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<ChatMsgModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<ChatMsgModel>> data) {
                if (data.getState() != 201) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }
}
