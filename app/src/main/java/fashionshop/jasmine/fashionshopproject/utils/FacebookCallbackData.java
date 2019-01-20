package fashionshop.jasmine.fashionshopproject.utils;

public interface FacebookCallbackData {
    void onSuccess(boolean isLogged);

    void onFail(String message);
}
