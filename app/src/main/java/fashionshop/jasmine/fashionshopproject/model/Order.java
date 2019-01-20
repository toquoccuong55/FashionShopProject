package fashionshop.jasmine.fashionshopproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    @SerializedName("OrderDetails")
    private ArrayList<OrderItem> OrderDetails;
    @SerializedName("DeliveryAddress")
    private String DeliveryAddress;
    @SerializedName("ReceiverName")
    private String ReceiverName;
    @SerializedName("ReceiverPhone")
    private String ReceiverPhone;

    public Order() {
    }

    public Order(ArrayList<OrderItem> orderDetails, String deliveryAddress, String receiverName, String receiverPhone) {
        OrderDetails = orderDetails;
        DeliveryAddress = deliveryAddress;
        ReceiverName = receiverName;
        ReceiverPhone = receiverPhone;
    }

    public ArrayList<OrderItem> getOrderDetails() {
        return OrderDetails;
    }

    public void setOrderDetails(ArrayList<OrderItem> orderDetails) {
        OrderDetails = orderDetails;
    }

    public String getDeliveryAddress() {
        return DeliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        DeliveryAddress = deliveryAddress;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    public String getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        ReceiverPhone = receiverPhone;
    }
}
