package fashionshop.jasmine.fashionshopproject.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "shippingAddress")
public class ShippingAddressEntity implements Serializable, Cloneable {

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    @NonNull
    private String shippingAddressId;
    @ColumnInfo(name = "shippingAddress")
    private String shippingAddress;
    @ColumnInfo(name = "receiverName")
    private String receiverName;
    @ColumnInfo(name = "receiverPhone")
    private String receiverPhone;

    public ShippingAddressEntity(String shippingAddressId, String shippingAddress, String receiverName, String receiverPhone) {
        this.shippingAddressId = shippingAddressId;
        this.shippingAddress = shippingAddress;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
    }

    public ShippingAddressEntity() {
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
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
