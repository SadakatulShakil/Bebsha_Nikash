
package com.grameen.bebshanikashapp.Model.AllProducts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("buying_price")
    @Expose
    private String buyingPrice;
    @SerializedName("selling_price")
    @Expose
    private String sellingPrice;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("photo1")
    @Expose
    private String photo1;
    @SerializedName("photo2")
    @Expose
    private String photo2;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(String buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", userId=" + userId +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", buyingPrice='" + buyingPrice + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", sku='" + sku + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", category='" + category + '\'' +
                ", photo1='" + photo1 + '\'' +
                ", photo2='" + photo2 + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
