package com.shenhesoft.examsapp.util.event;

/**
 * @author mashanshui
 * @date 2018/6/12
 * @desc TODO
 */
public class ChatEvent {
    private String chatId;

    public ChatEvent(String chatId) {
        this.chatId = chatId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
