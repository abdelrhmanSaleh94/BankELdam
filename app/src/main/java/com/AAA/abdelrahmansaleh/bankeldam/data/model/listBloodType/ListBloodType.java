
package com.AAA.abdelrahmansaleh.bankeldam.data.model.listBloodType;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListBloodType {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private List<ListBloodTypeData> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ListBloodTypeData> getData() {
        return data;
    }

    public void setData(List<ListBloodTypeData> data) {
        this.data = data;
    }

}
