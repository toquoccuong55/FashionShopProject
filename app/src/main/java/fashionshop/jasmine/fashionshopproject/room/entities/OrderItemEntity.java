package fashionshop.jasmine.fashionshopproject.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.io.Serializable;

import fashionshop.jasmine.fashionshopproject.fashionShopRepository.DataConverter;
import fashionshop.jasmine.fashionshopproject.model.Products;

@Entity(tableName = "OrderItemEntities")
public class OrderItemEntity implements Serializable, Cloneable{

    @PrimaryKey
    @ColumnInfo(name = "uuid")
    @NonNull
    private String orderItemId;

    @TypeConverters(DataConverter.class)
    @ColumnInfo(name = "products")
    private Products products;

    public OrderItemEntity() {
    }

    public OrderItemEntity(@NonNull String orderItemId, Products products) {
        this.orderItemId = orderItemId;
        this.products = products;
    }

    @NonNull
    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(@NonNull String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
    public Object clone() throws CloneNotSupportedException{
        OrderItemEntity orderItem = new OrderItemEntity();
        orderItem.setOrderItemId(orderItemId);
        orderItem.setProducts(products);
        return orderItem;
    }


}
