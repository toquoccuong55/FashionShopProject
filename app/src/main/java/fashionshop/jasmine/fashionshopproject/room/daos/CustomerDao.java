package fashionshop.jasmine.fashionshopproject.room.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import fashionshop.jasmine.fashionshopproject.room.entities.Customer;

@Dao
public interface CustomerDao {
    //insert customer
    @Insert
    void insertCustomer(Customer... customers);
    //delete customer
    @Delete
    void deleteCustomer(Customer... customers);
    //get customer information
    @Query("select customerId, accessToken, fullName, imageUrl from customers")
    LiveData<Customer> getCustomer();
    //get access token of customer
    @Query("select accessToken from customers")
    String getAccessToken();
}
