package com.shenhesoft.examsapp.data;

import com.shenhesoft.examsapp.network.model.AnswerModel;
import com.shenhesoft.examsapp.network.model.FinishAnswerModel;
import com.shenhesoft.examsapp.network.model.StartAnswerModel;

import cn.droidlover.xdroidmvp.net.entity.ListALLResults;

/**
 * @author mashanshui
 * @date 2018/5/24
 * @desc TODO
 */
public interface AnswerDataSource {
    interface LoadHomeAnalysisCallBack {
        void onSuccess(ListALLResults<AnswerModel> allResults);

        void onFail(String msg);
    }

    /**
     * 获取题目分析的题目
     */
    void loadHomeAnalysisData(int start, int length,int answerType, String answerId,String answerPolicyName, Integer wrongOrAll, LoadHomeAnalysisCallBack callBack);

    interface LoadHomeWorkCallBack {
        void onSuccess(ListALLResults<AnswerModel> allResults);

        void onFail(String msg);
    }

    /**
     * 获取题目
     */
    void loadHomeWorkData(int start, int length,int answerType, String testPaperId, String answerPolicyName, LoadHomeWorkCallBack callBack);

    interface NextSubjectCallBack {
        void onSuccess();

        void onFail(String msg);
    }

    /**
     * 下一题
     */
    void nextSubject(String answerId, String questionId, int answerErrored, int finished,String answerPolicyName, NextSubjectCallBack callBack);

    interface StartAnswerCallBack {
        void onSuccess(StartAnswerModel answerModel);

        void onFail(String msg);
    }

    interface ReplyNextSubjectCallBack {
        void onSuccess();

        void onFail(String msg);
    }
    /**
     * 二次提交，下一题
     */
    void replyNextSubject(String answerId, String questionId, int answerErrored, int finished,String answerPolicyName, ReplyNextSubjectCallBack callBack);

    /**
     * 开始答题
     *
     * @param answerPolicy
     * @param callBack
     */
    void startAnswer(String answerPolicy, StartAnswerCallBack callBack);

    interface FinishAnswerCallBack {
        void onSuccess(FinishAnswerModel model);

        void onFail(String msg);
    }

    /**
     * 答题结束
     *
     * @param answerSchedule
     * @param answerQuestionNum
     * @param answerTimeStart
     * @param answerTimeEnd
     * @param usedTime
     * @param callBack
     */
    void finish(String answerId, String answerSchedule, String answerQuestionNum, String answerTimeStart, String answerTimeEnd
            , String usedTime,String answerPolicyName, FinishAnswerCallBack callBack);

    interface CollectAnswerCallBack {
        void onSuccess();

        void onFail(String msg);
    }

    /**
     * 收藏题目
     * @param id
     * @param callBack
     */
    void collectAnswer(String id, CollectAnswerCallBack callBack);

    interface CancelCollectCallBack {
        void onSuccess();

        void onFail(String msg);
    }

    /**
     * 取消收藏
     * @param id
     * @param callBack
     */
    void cancelCollect(String id, CancelCollectCallBack callBack);

    interface RemoveCallBack {
        void onSuccess();

        void onFail(String msg);
    }

    /**
     * 从错题中移除
     * @param id
     * @param callBack
     */
    void removeQuestion(String id, RemoveCallBack callBack);
}
