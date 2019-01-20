package fashionshop.jasmine.fashionshopproject.room.entities;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "customers")
public class Customer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int customerId;
    @SerializedName("accessToken")
    @ColumnInfo(name = "accessToken")
    private String accessToken;
    @ColumnInfo(name = "fullName")
    private String fullName;
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    public Customer() {
    }

    public Customer(int customerId, String accessToken, String fullName, String imageUrl) {
        this.customerId = customerId;
        this.accessToken = accessToken;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
