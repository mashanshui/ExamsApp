package com.shenhesoft.examsapp.adapter.bean;

/**
 * @author mashanshui
 * @date 2018/5/26
 * @desc TODO
 */
public class UMengMessageBean {

    /**
     * display_type : notification
     * extra : {"test":"helloworld"}
     * msg_id : umhnx9d152730134952101
     * body : {"after_open":"go_url","ticker":"Android broadcast ticker","text":"Android broadcast text","title":"中文的title","url":"https://www.baidu.com"}
     * random_min : 0
     */

    private String display_type;
    private ExtraBean extra;
    private String msg_id;
    private BodyBean body;
    private int random_min;

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public ExtraBean getExtra() {
        return extra;
    }

    public void setExtra(ExtraBean extra) {
        this.extra = extra;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public int getRandom_min() {
        return random_min;
    }

    public void setRandom_min(int random_min) {
        this.random_min = random_min;
    }

    public static class ExtraBean {
        /**
         * test : helloworld
         */

        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class BodyBean {
        /**
         * after_open : go_url
         * ticker : Android broadcast ticker
         * text : Android broadcast text
         * title : 中文的title
         * url : https://www.baidu.com
         */

        private String after_open;
        private String ticker;
        private String text;
        private String title;
        private String url;
        private String custom;

        public String getCustom() {
            return custom;
        }

        public void setCustom(String custom) {
            this.custom = custom;
        }

        public String getAfter_open() {
            return after_open;
        }

        public void setAfter_open(String after_open) {
            this.after_open = after_open;
        }

        public String getTicker() {
            return ticker;
        }

        public void setTicker(String ticker) {
            this.ticker = ticker;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
