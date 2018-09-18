package com.shenhesoft.examsapp.network.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.shenhesoft.examsapp.adapter.LessonsListAdapter;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public class LessonsVideo implements MultiItemEntity {

    /**
     * readTnum : 0
     * courseType : 0
     * videoId : 1003909520566517760
     * remark : null
     * uploadTime : 2018-06-05 16:00:56
     * courseName : 课时2
     * videoName : WeChat_20180601230852
     * courseNo : 9
     * id : 1003909520696541184
     * downTnum : 0
     * courseId : 9
     * fileType : mp4
     * status : 1
     */

    private int readTnum;
    private int courseType;
    private String videoId;
    private Object remark;
    private String uploadTime;
    private String courseName;
    private String videoName;
    private int courseNo;
    private String id;
    private int downTnum;
    private String courseId;
    private String fileType;
    private int status;

    public int getReadTnum() {
        return readTnum;
    }

    public void setReadTnum(int readTnum) {
        this.readTnum = readTnum;
    }

    public int getCourseType() {
        return courseType;
    }

    public void setCourseType(int courseType) {
        this.courseType = courseType;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDownTnum() {
        return downTnum;
    }

    public void setDownTnum(int downTnum) {
        this.downTnum = downTnum;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int getItemType() {
        return LessonsListAdapter.LESSONS_LIST_3;
    }
}
