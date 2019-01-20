package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fashionshop.jasmine.fashionshopproject.model.Category;
import fashionshop.jasmine.fashionshopproject.model.CategoryItem;
import fashionshop.jasmine.fashionshopproject.model.Order;
import fashionshop.jasmine.fashionshopproject.model.OrderItem;
import fashionshop.jasmine.fashionshopproject.model.OrderResult;
import fashionshop.jasmine.fashionshopproject.model.ProductAndCategory;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.model.ResponseResult;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.utils.CallBackData;
import fashionshop.jasmine.fashionshopproject.utils.ClientApi;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fashionRepositoryImpl implements fashionRepository {
    @Override
    public void getListProduct(final Context context, final CallBackData<List<CategoryItem>> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.fashionService().getProduct();
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response != null && response.body() != null) {
                    if (response.code() == 200) {
                        try {
                            String result = response.body().string();
                            Type type = new TypeToken<ResponseResult<ProductAndCategory>>() {

                            }.getType();
                            ResponseResult<ProductAndCategory> responseResult = new Gson().fromJson(result, type);
                            if (responseResult.getData() == null) {
                                callBackData.onFail(response.message());
                            }
                            ProductAndCategory productAndCategory = responseResult.getData().getData();
                            List<CategoryItem> categoryItemsList = new ArrayList<>();
                            for (Category category : productAndCategory.getCategoryList()) {
                                List<Products> productsList = new ArrayList<>();
                                for (Products products : productAndCategory.getProductList()) {
                                    if (products.getCategoryId() == category.getCateId()) {
                                        productsList.add((Products) products.clone());
                                    }
                                }
                                if (productsList.size() > 0) {
                                    CategoryItem categoryItem = new CategoryItem();
                                    categoryItem.setCategory(category);
                                    categoryItem.setProductList(productsList);
                                    categoryItemsList.add(categoryItem);
                                }
                            }
                            callBackData.onSuccess(categoryItemsList);

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //if response is fail => return message fail
                        callBackData.onFail(response.message());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void getProductDetail(Context context, String productId, CallBackData<Products> callBackData) {

    }

    @Override
    public void loginFacebook(Context context, String fbAccessToken, String avatarUrl, final CallBackData<Customer> callBackData) {
        ClientApi clientApi = new ClientApi();
        Call<ResponseBody> serviceCall = clientApi.fashionService().LoginByFacebook(fbAccessToken, avatarUrl);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        Type type = new TypeToken<ResponseResult<Customer>>() {

                        }.getType();
                        ResponseResult<Customer> responseResult = new Gson().fromJson(result, type);
                        if (responseResult.getData() == null) {
                            callBackData.onFail(response.message());
                        }
                        Customer customer = responseResult.getData().getData();
                        callBackData.onSuccess(customer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    callBackData.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callBackData.onFail(t.getMessage());
            }
        });
    }

    @Override
    public void SetOrder(Context context, Order order, String accessToken, final CallBackData<OrderResult> callBackData) {
        ClientApi clientApi = new ClientApi();
        JSONObject orderJsonObject = new JSONObject();
        try {
            JSONArray orderDetailJsonArray = new JSONArray();
            for (OrderItem orderItem : order.getOrderDetails()) {
                JSONObject orderItemJsonObject = new JSONObject();
                orderItemJsonObject.put("ProductId", orderItem.getProductId());
                orderItemJsonObject.put("Quantity", orderItem.getQuantity());
                orderItemJsonObject.put("Size", orderItem.getSize());
                orderItemJsonObject.put("Color", orderItem.getColor());
                orderDetailJsonArray.put(orderItemJsonObject);
            }
            orderJsonObject.put("OrderDetails", orderDetailJsonArray);
            orderJsonObject.put("DeliveryAddress", order.getDeliveryAddress());
            orderJsonObject.put("ReceiverName", order.getReceiverName());
            orderJsonObject.put("ReceiverPhone", order.getReceiverPhone());
            orderJsonObject.put("accessToken", accessToken);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), orderJsonObject.toString());
        Call<ResponseBody> serviceCall = clientApi.fashionService().SetOrder(body);
        serviceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200 && response.body() != null) {
                    try {
                        String result = response.body().string();
                        Type type = new TypeToken<ResponseResult<OrderResult>>() {

                        }.getType();
                        ResponseResult<OrderResult> responseResult = new Gson().fromJson(result, type);
                        if (responseResult.getData() == null) {
                            callBackData.onFail(response.message());
                        }
                        OrderResult orderResult = responseResult.getData().getData();
                        callBackData.onSuccess(orderResult);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                } else {
                    callBackData.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
