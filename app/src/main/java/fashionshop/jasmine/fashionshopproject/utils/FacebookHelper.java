package fashionshop.jasmine.fashionshopproject.utils;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

public class FacebookHelper {


    public static void handleFacebookLogin(final AppCompatActivity context, CallbackManager callbackManager, final FacebookCallbackData callbackData) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                if (loginResult.getAccessToken() != null) {
                    String accessToken = loginResult.getAccessToken().getToken();
                    Log.e("AccessToken", accessToken);
                    if (isFbLoggedIn()) {
                        callbackData.onSuccess(true);
                    }
                }
            }

            @Override
            public void onCancel() {
                Log.i("", "LoginManager FacebookCallback onCancel");
                if (isFbLoggedIn()) {
                    LoginManager.getInstance().logOut();
                    callbackData.onFail("");
                }
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("", "LoginManager FacebookCallback onError");
            }
        });
    }

    public static boolean isFbLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public static String getAccessToken() {
        String accessToken = AccessToken.getCurrentAccessToken().getToken();
        return accessToken;
    }

    public static void loginFacebook(AppCompatActivity context) {
        LoginManager.getInstance().logInWithReadPermissions((Activity) context, Arrays.asList("public_profile, email"));
    }

    public static void logOutFacebook(AppCompatActivity context) {
        LoginManager.getInstance().logOut();
    }

}
