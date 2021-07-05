
package com.grameen.bebshanikashapp.Model.LogIn;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LogIn implements Serializable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isActive")
    @Expose
    private String isActive;
    @SerializedName("token")
    @Expose
    private String token;

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "LogIn{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", isActive='" + isActive + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
