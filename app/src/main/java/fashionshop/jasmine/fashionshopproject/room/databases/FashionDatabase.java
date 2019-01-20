package fashionshop.jasmine.fashionshopproject.room.databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import fashionshop.jasmine.fashionshopproject.room.daos.CustomerDao;
import fashionshop.jasmine.fashionshopproject.room.daos.ProductDao;
import fashionshop.jasmine.fashionshopproject.room.daos.ShippingAddressDao;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;
import fashionshop.jasmine.fashionshopproject.room.entities.ShippingAddressEntity;

import static fashionshop.jasmine.fashionshopproject.room.databases.FashionDatabase.DATABASE_VERSION;


@Database(entities = {Customer.class, OrderItemEntity.class, ShippingAddressEntity.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class FashionDatabase extends RoomDatabase {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "fashion-database";
    private static FashionDatabase INSTANCE;
    public abstract CustomerDao mCustomerDao();
    public abstract ProductDao mProductDao();
    public abstract ShippingAddressDao mShippingAddressDaO();
    public static FashionDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (FashionDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FashionDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addMigrations(MIGRATION_1_0)
                            .build();
                }
            }

        }
        return INSTANCE;
    }
    static final Migration MIGRATION_1_0 = new Migration(1, 0) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("create table customers(" +
                    "idCustomer int primary key AUTOINCREMENT," +
                    "fullName nvarchar(255)" +
                    ",accessToken  nvarchar(255)" +
                    ",imageUrl nvarchar(255)" +
                    ",phone nvarchar(255)" +
                    ",point int)");
        }
    };
}