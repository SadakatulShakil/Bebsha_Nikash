
package com.grameen.bebshanikashapp.Model.Divisions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Division implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bn_name")
    @Expose
    private String bnName;
    @SerializedName("url")
    @Expose
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBnName() {
        return bnName;
    }

    public void setBnName(String bnName) {
        this.bnName = bnName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Datum{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bnName='" + bnName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
