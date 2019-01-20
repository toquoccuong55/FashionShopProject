package fashionshop.jasmine.fashionshopproject.fashionShopRepository;

import java.util.ArrayList;

import fashionshop.jasmine.fashionshopproject.model.OrderDetail;
import fashionshop.jasmine.fashionshopproject.utils.ConfigApi;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface fashionService {

    @POST(ConfigApi.Api.GET_PRODUCT)
    Call<ResponseBody> getProduct();

    @FormUrlEncoded
    @POST(ConfigApi.Api.LOGIN_FACEBOOK)
    Call<ResponseBody> LoginByFacebook(@Field("fbAccessToken") String fbAccessToken,
                                       @Field("avatarUrl") String avatarUrl
    );

    //    @FormUrlEncoded
//    @POST(ConfigApi.Api.SET_ORDER)
//    Call<ResponseBody> SetOrder(
//                                @Field("DeliveryAddress") String DeliveryAddress,
//                                @Field("ReceiverName") String ReceiverName,
//                                @Field("ReceiverPhone") String ReceiverPhone,
//                                @Field("OrderDetails")ArrayList<OrderDetail> OrderDetails,
//                                @Field("accessToken") String accessToken
//                                );
    @POST(ConfigApi.Api.SET_ORDER)
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseBody> SetOrder(@Body RequestBody orderJsonObject);
}
