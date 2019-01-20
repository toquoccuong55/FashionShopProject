package fashionshop.jasmine.fashionshopproject.room.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;
@Dao
public interface ProductDao {

    @Insert
    public void insertOrderItem(OrderItemEntity... orderItem);

    @Delete
    public void deleteOrderItem(OrderItemEntity... orderItem);

    @Update
    public void updateOrderItem(OrderItemEntity... orderItem);

    @Query("select * from OrderItemEntities")
    LiveData<List<OrderItemEntity>> getAllOrderItem();
}
