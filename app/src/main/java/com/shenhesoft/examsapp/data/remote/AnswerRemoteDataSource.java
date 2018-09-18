package com.shenhesoft.examsapp.data.remote;

import android.text.TextUtils;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.data.AnswerDataSource;
import com.shenhesoft.examsapp.network.WorkRetrofit;
import com.shenhesoft.examsapp.network.WorkService;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;
import com.shenhesoft.examsapp.network.model.StartAnswerModel;

import cn.droidlover.xdroidmvp.net.HttpObserver;
import cn.droidlover.xdroidmvp.net.HttpRequestUtil;
import cn.droidlover.xdroidmvp.net.RetrofitConfig;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;
import cn.droidlover.xdroidmvp.net.entity.RequestResults;
import io.reactivex.Observable;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc TODO
 */
public class AnswerRemoteDataSource implements AnswerDataSource {
    private WorkService workService;

    public AnswerRemoteDataSource() {
        workService = HttpRequestUtil.getRetrofitClient(WorkService.class.getName());
    }

    @Override
    public void loadHomeWorkData(int start, int length, int answerType, String testPaperId, String answerPolicyName, final LoadHomeWorkCallBack callBack) {
        Observable<RequestResults<ListALLResults<AnswerModel>>> observable;
        if (TextUtils.equals(answerPolicyName, AppConstant.AnswerPolicy2)) {
            observable = workService.getBankByKnowledge(WorkRetrofit.getInstance().getBankByKnowledge(start, length, testPaperId));
        } else {
            observable = workService.getHomeWorkQuestions(WorkRetrofit.getInstance()
                    .getHomeWorkQuestions(start, length, answerType, testPaperId, answerPolicyName, 1));
        }
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AnswerModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AnswerModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AnswerModel>> data) {
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
    public void loadHomeAnalysisData(int start, int length, int answerType, String answerId, String answerPolicyName, Integer wrongOrAll, final LoadHomeAnalysisCallBack callBack) {
        Observable<RequestResults<ListALLResults<AnswerModel>>> observable;
        if (wrongOrAll == 0) {
            observable = workService.getHomeWorkQuestions(WorkRetrofit.getInstance()
                    .getHomeWorkQuestions(start, length, answerType, answerId, answerPolicyName, 1));
        } else {
            observable = workService.getAnalysisQuestions(WorkRetrofit.getInstance().getAnalysisQuestions(start, length, answerId, wrongOrAll, 1));
        }
        HttpObserver httpObserver = new HttpObserver<RequestResults<ListALLResults<AnswerModel>>>(new HttpObserver.OnNextListener<RequestResults<ListALLResults<AnswerModel>>>() {
            @Override
            public void onNext(RequestResults<ListALLResults<AnswerModel>> data) {
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
    public void nextSubject(String answerId, String questionId, int answerErrored, int finished, String answerPolicyName, final NextSubjectCallBack callBack) {
        Observable<RequestResults> observable = workService
                .nextSubject(WorkRetrofit.getInstance().nextSubject(answerId, questionId, answerErrored, finished, answerPolicyName));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void replyNextSubject(String answerId, String questionId, int answerErrored, int finished, String answerPolicyName, final ReplyNextSubjectCallBack callBack) {
        Observable<RequestResults> observable = workService
                .replyNextSubject(WorkRetrofit.getInstance().replyNextSubject(answerId, questionId, answerErrored, finished));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void startAnswer(String answerPolicy, final StartAnswerCallBack callBack) {
        Observable<RequestResults<StartAnswerModel>> observable = workService
                .startAnswer(WorkRetrofit.getInstance().startAnswer(answerPolicy));
        HttpObserver httpObserver = new HttpObserver<RequestResults<StartAnswerModel>>(new HttpObserver.OnNextListener<RequestResults<StartAnswerModel>>() {
            @Override
            public void onNext(RequestResults<StartAnswerModel> data) {
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
    public void finish(String answerId, String answerSchedule, String answerQuestionNum, String answerTimeStart, String answerTimeEnd
            , String usedTime, String answerPolicyName, final FinishAnswerCallBack callBack) {
        Observable<RequestResults<FinishAnswerModel>> observable = workService
                .answerFinish(WorkRetrofit.getInstance().finishAnswer(answerId, answerSchedule, answerQuestionNum, answerTimeStart, answerTimeEnd, usedTime, answerPolicyName));
        HttpObserver httpObserver = new HttpObserver<RequestResults<FinishAnswerModel>>(new HttpObserver.OnNextListener<RequestResults<FinishAnswerModel>>() {
            @Override
            public void onNext(RequestResults<FinishAnswerModel> data) {
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
    public void collectAnswer(String id, final CollectAnswerCallBack callBack) {
        Observable<RequestResults> observable = workService
                .collectAnswer(WorkRetrofit.getInstance().collectAnswer(id));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 201) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void cancelCollect(String id, final CancelCollectCallBack callBack) {
        Observable<RequestResults> observable = workService
                .cancelCollect(WorkRetrofit.getInstance().cancelCollect(id));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

    @Override
    public void removeQuestion(String id, final RemoveCallBack callBack) {
        Observable<RequestResults> observable = workService
                .removeQuestion(WorkRetrofit.getInstance().removeQuestion(id));
        HttpObserver httpObserver = new HttpObserver<RequestResults>(new HttpObserver.OnNextListener<RequestResults>() {
            @Override
            public void onNext(RequestResults data) {
                if (data.getState() != 200) {
                    callBack.onFail(data.getMsg());
                } else {
                    callBack.onSuccess();
                }
            }
        });
        RetrofitConfig.getInstance().statrPostTask(observable, httpObserver);
    }

}
