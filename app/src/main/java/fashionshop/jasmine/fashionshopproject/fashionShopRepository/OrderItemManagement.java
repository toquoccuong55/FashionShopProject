package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import fashionshop.jasmine.fashionshopproject.room.daos.ProductDao;
import fashionshop.jasmine.fashionshopproject.room.databases.FashionDatabase;
import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;

public class OrderItemManagement {

    private ProductDao mProductDao;
    private Application application;

    public OrderItemManagement(Application application) {
        this.application = application;
        FashionDatabase fashionDatabase = FashionDatabase.getDatabase(application);
        mProductDao = fashionDatabase.mProductDao();
    }

    public LiveData<List<OrderItemEntity>> getAllOrderitem() {
        return mProductDao.getAllOrderItem();
    }

    public void addOrderItem(OrderItemEntity orderItem) {
        AddOrderItemAsyn addOrderItemAsyn = new AddOrderItemAsyn(mProductDao);
        addOrderItemAsyn.execute(orderItem);

    }

    public void updateOrderItem(OrderItemEntity orderItem) {
        UpdateOrderItemAsyn updateOrderItemAsyn = new UpdateOrderItemAsyn(mProductDao);
        updateOrderItemAsyn.execute(orderItem);
    }
    public void deleteOrderItem(OrderItemEntity orderItem) {
       DeleteOrderItemAsyn deleteOrderItemAsyn = new DeleteOrderItemAsyn(mProductDao);
       deleteOrderItemAsyn.execute(orderItem);
    }

    private class AddOrderItemAsyn extends AsyncTask<OrderItemEntity, Void, Void> {
        private ProductDao mProductDao;

        public AddOrderItemAsyn(ProductDao mProductDao) {
            this.mProductDao = mProductDao;
        }

        @Override
        protected Void doInBackground(OrderItemEntity... orderItemEntities) {
            mProductDao.insertOrderItem(orderItemEntities);
            return null;
        }
    }

    private class UpdateOrderItemAsyn extends AsyncTask<OrderItemEntity, Void, Void> {
        private ProductDao mProductDao;

        public UpdateOrderItemAsyn(ProductDao mProductDao) {
            this.mProductDao = mProductDao;
        }

        @Override
        protected Void doInBackground(OrderItemEntity... orderItemEntities) {
            mProductDao.updateOrderItem(orderItemEntities);
            return null;
        }
    }

    private class DeleteOrderItemAsyn extends AsyncTask<OrderItemEntity, Void, Void> {
        private ProductDao mProductDao;

        public DeleteOrderItemAsyn(ProductDao mProductDao) {
            this.mProductDao = mProductDao;
        }

        @Override
        protected Void doInBackground(OrderItemEntity... orderItemEntities) {
            mProductDao.deleteOrderItem(orderItemEntities);
            return null;
        }
    }
}
