package fashionshop.jasmine.fashionshopproject.room.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import fashionshop.jasmine.fashionshopproject.model.ShippingAddress;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.room.entities.ShippingAddressEntity;
@Dao
public interface ShippingAddressDao {
    @Insert
    void insertShippingAddress(ShippingAddressEntity... shippingAddresses);
    @Update
    void updateShippingAddress(ShippingAddressEntity... shippingAddresses);
    @Delete
    void deleteShippingAddress(ShippingAddressEntity... shippingAddress);

    @Query("select * from shippingAddress")
    LiveData<ShippingAddressEntity> getShippingAddress();
}
