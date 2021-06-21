
package com.grameen.bebshanikashapp.Model.SignUp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SignUp implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("sms_code")
    @Expose
    private Integer smsCode;
    @SerializedName("phone")
    @Expose
    private String phone;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(Integer smsCode) {
        this.smsCode = smsCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SignUp{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", smsCode=" + smsCode +
                ", phone='" + phone + '\'' +
                '}';
    }
}
