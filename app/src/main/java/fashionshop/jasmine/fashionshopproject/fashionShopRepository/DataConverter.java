package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;

import fashionshop.jasmine.fashionshopproject.model.Products;

public class DataConverter implements Serializable{

    @TypeConverter
    public String fromProductList(Products products){
        if(products == null){
            return (null);
        }
        Gson gson = new Gson();
        Type type =  new TypeToken<Products>(){

        }.getType();
        String json = gson.toJson(products, type);

        return json;
    }
    @TypeConverter // note this annotation
    public Products toExtraList(String productsString) {
        if (productsString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Products>() {
        }.getType();
        Products products = gson.fromJson(productsString, type);
        return products;
    }

}
