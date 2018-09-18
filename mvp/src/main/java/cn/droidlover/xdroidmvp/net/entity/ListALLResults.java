package cn.droidlover.xdroidmvp.net.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 张明星
 * @date 2017/12/24
 * @description 网络请求返回类型类List
 */
public class ListALLResults<T> {
    @SerializedName(value = "data", alternate = "list")
    private List<T> rows;

    @SerializedName("recordsTotal")
    private int recordsTotal;

    @SerializedName("recordsFiltered")
    private int recordsFiltered;

    @SerializedName(value = "count")
    private int extra;

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
