package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import android.content.Context;

import java.util.List;

import fashionshop.jasmine.fashionshopproject.model.CategoryItem;
import fashionshop.jasmine.fashionshopproject.model.Order;
import fashionshop.jasmine.fashionshopproject.model.OrderResult;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.utils.CallBackData;

public interface fashionRepository {
    void getListProduct(Context context, CallBackData<List<CategoryItem>> callBackData);
    void getProductDetail(Context context, String productId, CallBackData<Products> callBackData);
    void loginFacebook (Context context, String fbAccessToken, String avatarUrl, CallBackData<Customer> callBackData);
    void SetOrder(Context context, Order order, String accessToken, CallBackData<OrderResult> callBackData);
}
