package fashionshop.jasmine.fashionshopproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItem implements Serializable {
    @SerializedName("ProductId")
    private int productId;
    @SerializedName("Quantity")
    private int quantity;
    @SerializedName("Size")
    private String size;
    @SerializedName("Color")
    private String color;

    public OrderItem() {
    }

    public OrderItem(int productId, int quantity, String size, String color) {
        this.productId = productId;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
