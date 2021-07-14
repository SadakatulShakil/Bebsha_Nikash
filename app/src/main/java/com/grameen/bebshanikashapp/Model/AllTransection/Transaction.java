
package com.grameen.bebshanikashapp.Model.AllTransection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Transaction implements Serializable {

    @SerializedName("debit")
    @Expose
    private String debit;
    @SerializedName("credit")
    @Expose
    private String credit;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("txn_type")
    @Expose
    private String txnType;
    @SerializedName("txn_date")
    @Expose
    private String txnDate;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("app_user_id")
    @Expose
    private Integer appUserId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "debit='" + debit + '\'' +
                ", credit='" + credit + '\'' +
                ", productId='" + productId + '\'' +
                ", txnType='" + txnType + '\'' +
                ", txnDate='" + txnDate + '\'' +
                ", comment='" + comment + '\'' +
                ", appUserId=" + appUserId +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id=" + id +
                '}';
    }
}
