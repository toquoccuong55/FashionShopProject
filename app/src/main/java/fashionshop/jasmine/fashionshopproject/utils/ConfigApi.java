package fashionshop.jasmine.fashionshopproject.utils;

public class ConfigApi {
    public static final String BASE_URL = "http://103.79.142.115:42000/Mobile/";
    public static final String PAGE_LIMIT = "25";
    public interface Api {
        String GET_STORE = "GetStoreList";
        String GET_PRODUCT = "getListProduct";
        String LOGIN_FACEBOOK = "LoginByFacebook";
        String SET_ORDER = "SetOrder";
    }
}
