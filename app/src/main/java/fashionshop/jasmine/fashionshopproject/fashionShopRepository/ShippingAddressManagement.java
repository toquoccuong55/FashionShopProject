package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import fashionshop.jasmine.fashionshopproject.room.daos.ShippingAddressDao;
import fashionshop.jasmine.fashionshopproject.room.databases.FashionDatabase;
import fashionshop.jasmine.fashionshopproject.room.entities.ShippingAddressEntity;


public class ShippingAddressManagement {
    private ShippingAddressDao mShippingAddressDao;
    private Application application;

    public ShippingAddressManagement(Application application) {

        this.application = application;
        FashionDatabase libraryDatabase = FashionDatabase.getDatabase(application);
        this.mShippingAddressDao = libraryDatabase.mShippingAddressDaO();
    }

    public LiveData<ShippingAddressEntity> getShipping() {
        return mShippingAddressDao.getShippingAddress();
    }
    public void updateShippingAddress(ShippingAddressEntity shipping) {
        UpdateShippingAsync updateShippingAsyn = new UpdateShippingAsync(mShippingAddressDao);
        updateShippingAsyn.execute(shipping);
    }
    public void addShippingAddress(ShippingAddressEntity shipping) {
        AddShippingAsync addShippingAsyn = new AddShippingAsync(mShippingAddressDao);
        addShippingAsyn.execute(shipping);
    }
    private class AddShippingAsync extends AsyncTask<ShippingAddressEntity, Void, Void> {
        private ShippingAddressDao mShippingAddressDao;

        public AddShippingAsync(ShippingAddressDao mShippingAddressDao) {
            this.mShippingAddressDao = mShippingAddressDao;
        }

        @Override
        protected Void doInBackground(ShippingAddressEntity... shippingAddressEntities) {
            mShippingAddressDao.insertShippingAddress(shippingAddressEntities);
            return null;
        }
    }
    private class UpdateShippingAsync extends AsyncTask<ShippingAddressEntity, Void, Void> {
        private ShippingAddressDao mShippingAddressDao;

        public UpdateShippingAsync(ShippingAddressDao mShippingAddressDao) {
            this.mShippingAddressDao = mShippingAddressDao;
        }
        @Override
        protected Void doInBackground(ShippingAddressEntity... shippingAddressEntities) {
            mShippingAddressDao.updateShippingAddress(shippingAddressEntities);
            return null;
        }
    }
}
