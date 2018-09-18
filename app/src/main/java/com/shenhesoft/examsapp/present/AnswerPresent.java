package com.shenhesoft.examsapp.present;

import android.text.TextUtils;
import android.util.Log;

import com.shenhesoft.examsapp.AppConstant;
import com.shenhesoft.examsapp.data.AnswerDataSource;
import com.shenhesoft.examsapp.data.remote.AnswerRemoteDataSource;
import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;
import com.shenhesoft.examsapp.network.model.StartAnswerModel;
import com.shenhesoft.examsapp.util.DateUtil;
import com.shenhesoft.examsapp.view.AnswerView;

import cn.droidlover.xdroidmvp.base.ActivityCollector;
import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.kit.IToast;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/4/18
 * @desc TODO
 */
public class AnswerPresent extends XPresent<AnswerView> {
    private AnswerDataSource answerDataSource;

    public AnswerPresent() {
        answerDataSource = new AnswerRemoteDataSource();
    }

    public void loadData(final boolean isFirst, final int start, int length, String productId, int answerType, Integer wrongOrAll, String answerPolicyName) {
        if (answerType == AppConstant.DoHomeWork) {
            answerDataSource.loadHomeWorkData(start, length, answerType, productId, answerPolicyName, new AnswerDataSource.LoadHomeWorkCallBack() {
                @Override
                public void onSuccess(ListALLResults<AnswerModel> allResults) {
                    if (allResults.getRows().isEmpty()) {
                        IToast.showShort("没有题目或已做完");
                        if (isFirst) {
                            getV().finishActivity();
                        }
                        return;
                    }
                    getV().setTotalAnswerNum(allResults.getExtra());
                    if (start == 0) {
                        //开始做题前调用
                        startAnswer();
                        getV().updateData(allResults.getRows());
                    } else {
                        getV().updateAddData(allResults.getRows());
                        getV().nextAnswer();
                    }
                }

                @Override
                public void onFail(String msg) {
                    IToast.showShort(msg);
                }
            });

        } else if (answerType == AppConstant.HomeAnalysis) {
            answerDataSource.loadHomeAnalysisData(start, length, answerType, productId, answerPolicyName, wrongOrAll, new AnswerDataSource.LoadHomeAnalysisCallBack() {
                @Override
                public void onSuccess(ListALLResults<AnswerModel> allResults) {
                    if (allResults.getRows().isEmpty()) {
                        IToast.showShort("没有题目或已做完");
                        if (isFirst) {
                            getV().finishActivity();
                        }
                        return;
                    }
                    getV().setTotalAnswerNum(allResults.getExtra());
                    if (start == 0) {
                        getV().updateData(allResults.getRows());
                    } else {
                        getV().updateAddData(allResults.getRows());
                        getV().nextAnswer();
                    }
                }

                @Override
                public void onFail(String msg) {
                    IToast.showShort(msg);
                }
            });
        }
    }

    public void nextSubject(final boolean isback, String answerId, String questionId, int answerErrored, int finished) {
        if (TextUtils.isEmpty(answerId)) {
            IToast.showShort("异常，请重新做题");
            Log.e(TAG, "nextSubject: 获取不到answerId");
            return;
        }
        if (getV().getCurrentPage() < getV().getDoneSize() && !AppConstant.AnswerPolicy4.equals(getV().getAnswerPolicyName())) {
            replyNextSubject(isback, answerId, questionId, answerErrored, finished);
        } else {
            firstNestSubject(isback, answerId, questionId, answerErrored, finished);
        }
    }

    private void firstNestSubject(final boolean isback, String id, String questionId, int answerErrored, int finished) {
        answerDataSource.nextSubject(id, questionId, answerErrored, finished, getV().getAnswerPolicyName(), new AnswerDataSource.NextSubjectCallBack() {
            @Override
            public void onSuccess() {
                if (isback) {
                    getV().nextSuccess();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void replyNextSubject(boolean isback, String id, String questionId, int answerErrored, int finished) {
        answerDataSource.replyNextSubject(id, questionId, answerErrored, finished, getV().getAnswerPolicyName(), new AnswerDataSource.ReplyNextSubjectCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void startAnswer() {
        answerDataSource.startAnswer(getAnswerPolicy(), new AnswerDataSource.StartAnswerCallBack() {
            @Override
            public void onSuccess(StartAnswerModel answerModel) {
                getV().setAnswerId(answerModel.getId());
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort("异常，请重新做题");
                getV().finishActivity();
            }
        });
    }

    public String getAnswerPolicy() {
        return SharedPref.getInstance(ActivityCollector.getTopActivity()).getString(AppConstant.AnswerPolicy, "");
    }

    public void finishAnswer( long startTime) {
        long stopTime = System.currentTimeMillis();
        int total = getV().getTotalAnswerNum();
        int done = getV().getDoneSize() + 1 + getV().getStartAnswerNum();
        String answerSchedule = done + "/" + total;
        answerDataSource.finish(getV().getAnswerId(), answerSchedule, String.valueOf(getV().getDoneSize() + 1), DateUtil.MillisToDate(startTime)
                , DateUtil.MillisToDate(stopTime), DateUtil.TimeDifference(startTime, stopTime), getV().getAnswerPolicyName()
                , new AnswerDataSource.FinishAnswerCallBack() {
                    @Override
                    public void onSuccess(FinishAnswerModel model) {
                        getV().toStatisticsActivity(model);
                    }

                    @Override
                    public void onFail(String msg) {
                        IToast.showLong(msg);
                    }
                });
    }

    public void collectAnswer(String id) {
        answerDataSource.collectAnswer(id, new AnswerDataSource.CollectAnswerCallBack() {
            @Override
            public void onSuccess() {
                IToast.showShort("收藏成功");
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void cancelCollect(String id) {
        answerDataSource.cancelCollect(id, new AnswerDataSource.CancelCollectCallBack() {
            @Override
            public void onSuccess() {
                IToast.showShort("取消收藏成功");
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }

    public void removeQuestion(String id) {
        answerDataSource.removeQuestion(id, new AnswerDataSource.RemoveCallBack() {
            @Override
            public void onSuccess() {
                IToast.showShort("移除错题成功");
            }

            @Override
            public void onFail(String msg) {
                IToast.showShort(msg);
            }
        });
    }
}
