package com.shenhesoft.examsapp;

public class AppConstant {
    public static final String UserName = "username";
    public static final String UserPassword = "userpassword";
    public static final String ThemeMode = "thememode";
    public static final String VerifyCode = "verifyCode";
    public static final String JsessionId = "jsessionid";
    public static final String UserId = "id";
    public static final String AnswerPolicy = "answerPolicy";
    public static final String UMENG_PUSH_ALIAS_TYPE = "userId";
    public static final String WeChatAppId = "wxd3f214c324a79a39";
    public static final String WeChatAppSecret = "666e396a255d73156c457e7f925c661f";
    public static final int PageLength = 30;
    /**
     * 加密登录密码的公钥和私钥
     */
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMiQboJ5QZC3CqdbDzJj//zbdQpg6oEK3zbP4t5ANkufBBCHc13uoQcM2rBC/b0eYVYa8IWTdROM/Wyvw8jIiiFDYmSWNxHbz3Z2WvNybDnPmtWsmzIRt3s/6J4FcR99hlyGNZR6fNA+K4Y2fdQLGkgApx6yx2E87yhf+78foP9QIDAQAB";
    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIyJBugnlBkLcKp1sPMmP//Nt1CmDqgQrfNs/i3kA2S58EEIdzXe6hBwzasEL9vR5hVhrwhZN1E4z9bK/DyMiKIUNiZJY3EdvPdnZa83JsOc+a1aybMhG3ez/ongVxH32GXIY1lHp80D4rhjZ91AsaSACnHrLHYTzvKF/7vx+g/1AgMBAAECgYAGPF5OFtN/eP560dYUM/JLC3nC3ltwIa1WS9PLfX6ZDZcXi6Seg1GngRnfKMRm4J1w/XCi0jKeLAMs6LzgguvdFA8b4Z6ltI7Zggme0i9Edckhtk7q7N9MtfJolp2m8bkjvYksi9PwMw6vetgxZddtFI3uohMez9AEFcnPx4jZUQJBAOkuKtrshYkibqlYZjRMTRrHngAnbb7toF7piYvB/irCGvElPnEOvE4blG2A+Mplddm2fFPce9JaXnONSnZnJ8cCQQCaSdYKlqvqH3VMCbAz988nNJGxM/HyW3qcpHKu4g6S+lhWokd4iTeIc3Y5ikKnhgbcSoX28B6ehX1gdBDhSOJjAkEAyu08qm0wARoQCBxARb8pUubpn+SwY3pVGIRRCWl4Ein+XGI3JxjV03u4/ltBKJKHr0JajHjS4Ut360PIy6YWPQJABqj7SVe5shCLkq20YnnnAk09oMWaQxj50Ps0TCJL+MI/v/WfWi+qKwS47MDwIaUidK3u3Hr/B17By8EW3XK0+wJAbeTKBsQ9/eCSa5ww66Kpz5q1Ej4MLi8cxP36txFTdk8pd91YZOZB56OTyEyaqnqjo5nynZ8+iMuuR7Ja423ILg==";

    //答题策略
    public static final String AnswerPolicy1 = "试卷年份";
    public static final String AnswerPolicy2 = "知识点";
    public static final String AnswerPolicy3 = "收藏";
    public static final String AnswerPolicy4 = "错题";

    //学术领域
    /**
     * 数学
     */
    public static final String MATH = "数学";
    /**
     * 逻辑
     */
    public static final String LOGIC = "逻辑";
    /**
     * 专业课
     */
    public static final String MAJOR = "专业课";
    /**
     * 英语
     */
    public static final String ENGLISH = "英语";
    /**
     * 写作
     */
    public static final String WRITING = "写作";
    /**
     * 其他
     */
    public static final String OTHER = "其他";

    //商品类型
    /**
     * 题库
     */
    public static final int QuestionBank = 1;
    /**
     * 课件
     */
    public static final int CourseWare = 2;
    /**
     * 作文批改
     */
    public static final int WritingCheck = 3;

    //题库的购买状态
    /**
     * 未购
     */
    public static final int NotBuy = 1;
    /**
     * 已购
     */
    public static final int Buy = 2;
    /**
     * 免费
     */
    public static final int Free = 3;


    //做题的类型
    public static final String AnswerType = "answerType";
    /**
     * 做题
     */
    public static final int DoHomeWork = 1;
    /**
     * 题目分析
     */
    public static final int HomeAnalysis = 2;

    //题目选择状态
    /**
     * 选中
     */
    public static final int Select = 1;
    /**
     * 未选中
     */
    public static final int NotSelect = 2;

    //全部题目分析还是错题分析
    public static final int OnlyWrongQuestion = 1;
    public static final int AllQuestion = 2;

    //选择文件的位置
    public static final int QQ = 0;
    public static final int WeChat = 1;
}
