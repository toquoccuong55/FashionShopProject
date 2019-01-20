package fashionshop.jasmine.fashionshopproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShippingAddress implements Serializable {
    @SerializedName("DeliveryAddress")
    private String deliveryAddress;
    @SerializedName("ReceiverName")
    private String receiverName;
    @SerializedName("ReceiverPhone")
    private String receiverPhone;

    public ShippingAddress(String deliveryAddress, String receiverName, String receiverPhone) {
        this.deliveryAddress = deliveryAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }

    public ShippingAddress() {
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
}
