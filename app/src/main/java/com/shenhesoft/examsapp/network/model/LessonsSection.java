package com.shenhesoft.examsapp.network.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;
import com.shenhesoft.examsapp.adapter.LessonsListAdapter;

import java.util.List;

/**
 * @author mashanshui
 * @date 2018/6/7
 * @desc TODO
 */
public class LessonsSection extends AbstractExpandableItem<LessonsVideo> implements MultiItemEntity {

    /**
     * createUserId : null
     * productId : 9999
     * updateUserId : null
     * remark : null
     * updateTime : null
     * classVideo : []
     * product_title : 课件2
     * courseName : 课时2
     * createTime : null
     * courseNo : 9
     * videoNums : 3
     * id : 4
     * productNo : 9999
     * status : 1
     */

    private Object createUserId;
    private String productId;
    private Object updateUserId;
    private Object remark;
    private Object updateTime;
    private String product_title;
    private String courseName;
    private Object createTime;
    private int courseNo;
    private int videoNums;
    private String id;
    private String productNo;
    private int status;

    @SerializedName(value = "classVideo", alternate = "classPdfs")
    private List<LessonsVideo> classVideo;

    public Object getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Object createUserId) {
        this.createUserId = createUserId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Object getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Object updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public int getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(int courseNo) {
        this.courseNo = courseNo;
    }

    public int getVideoNums() {
        return videoNums;
    }

    public void setVideoNums(int videoNums) {
        this.videoNums = videoNums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LessonsVideo> getClassVideo() {
        return classVideo;
    }

    public void setClassVideo(List<LessonsVideo> classVideo) {
        this.classVideo = classVideo;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return LessonsListAdapter.LESSONS_LIST_2;
    }
}
