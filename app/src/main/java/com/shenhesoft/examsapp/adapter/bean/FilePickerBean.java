package com.shenhesoft.examsapp.adapter.bean;

/**
 * @author mashanshui
 * @date 2018-07-01
 * @desc TODO
 */
public class FilePickerBean {
    protected String name;
    protected String path;
    private String mimeType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
