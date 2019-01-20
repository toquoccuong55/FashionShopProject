package fashionshop.jasmine.fashionshopproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImage implements Serializable{
    @SerializedName("ID")
    private int id;
    @SerializedName("ProductId")
    private int productId;
    @SerializedName("PicUrl")
    private String picUrl;
    @SerializedName("DisplayOrder")
    private  int displayOrder;

    public ProductImage() {
    }

    public ProductImage(int id, int productId, String picUrl, int displayOrder) {
        this.id = id;
        this.productId = productId;
        this.picUrl = picUrl;
        this.displayOrder = displayOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
