
package com.unlimited.matajer.common.Moudules.Authresponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Authresponse {

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("status")
    @Expose
    private Boolean status=null;

    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
