package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import fashionshop.jasmine.fashionshopproject.room.daos.CustomerDao;
import fashionshop.jasmine.fashionshopproject.room.databases.FashionDatabase;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;

public class UserManagement {
    private CustomerDao mCustomerDao;
    private Application mApplication;

    public UserManagement(Application application) {
        this.mApplication = application;
        FashionDatabase libraryDatabase = FashionDatabase.getDatabase(application);
        this.mCustomerDao = libraryDatabase.mCustomerDao();
    }

    public LiveData<Customer> getCustomer() {
        return mCustomerDao.getCustomer();
    }

    public void deleteCustomer(Customer customer) {
        DeleteCustomerAsync deleteAsync = new DeleteCustomerAsync(mCustomerDao);
        deleteAsync.execute(customer);
    }

    public void addCustomer(Customer customer) {
        AddCustomerAsync addCustomerAsync = new AddCustomerAsync(mCustomerDao);
        addCustomerAsync.execute(customer);
    }

    private class AddCustomerAsync extends AsyncTask<Customer, Void, Void> {
        private CustomerDao mDaoAsync;

        public AddCustomerAsync(CustomerDao mDaoAsync) {
            this.mDaoAsync = mDaoAsync;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            try {
                mDaoAsync.insertCustomer(customers);
            } catch (SQLiteConstraintException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    private class DeleteCustomerAsync extends AsyncTask<Customer, Void, Void> {
        private CustomerDao mDaoAsync;

        public DeleteCustomerAsync(CustomerDao mDaoAsync) {
            this.mDaoAsync = mDaoAsync;
        }

        @Override
        protected Void doInBackground(Customer... customers) {
            try {
                mDaoAsync.deleteCustomer(customers);
            } catch (SQLiteConstraintException e) {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
