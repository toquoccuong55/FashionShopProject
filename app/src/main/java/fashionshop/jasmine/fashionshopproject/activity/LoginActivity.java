package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.UserManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepository;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepositoryImpl;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.utils.CallBackData;
import fashionshop.jasmine.fashionshopproject.utils.FacebookCallbackData;
import fashionshop.jasmine.fashionshopproject.utils.FacebookHelper;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private fashionRepository mFashionRepository;
    private UserManagement mUserManagement;
    private Button mBtnLoginFaceBook;
    private CallbackManager mCallbackManager;
    private static Profile mProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void initialView() {
        mBtnLoginFaceBook = findViewById(R.id.btn_login_facebook);
        mBtnLoginFaceBook.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_facebook:
                loginFacebook();
                break;
        }

    }

    private void loginFacebook() {
        FacebookHelper.loginFacebook(this);
        mCallbackManager = CallbackManager.Factory.create();
        FacebookHelper.handleFacebookLogin(this, mCallbackManager, new FacebookCallbackData() {
            @Override
            public void onSuccess(boolean isLogged) {
                if (isLogged) {
                    final String[] profilePicUrl = {""};
                    final String[] profileName = {""};

                    String accessToken =  FacebookHelper.getAccessToken();
                    Bundle params = new Bundle();
                    params.putString("fields", "id,email,gender,cover,picture.type(large)");
                    new GraphRequest(AccessToken.getCurrentAccessToken(), "me", params, HttpMethod.GET,
                            new GraphRequest.Callback() {
                                @Override
                                public void onCompleted(GraphResponse response) {
                                    if (response != null) {
                                        try {
                                            JSONObject data = response.getJSONObject();
                                            if(data.has("email")){
                                                profileName[0] = data.get("email").toString();
                                            }
                                            if (data.has("picture")) {
                                                profilePicUrl[0] = data.getJSONObject("picture").getJSONObject("data").getString("url");

                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).executeAsync();
                    mFashionRepository = new fashionRepositoryImpl();
                    mFashionRepository.loginFacebook(LoginActivity.this, accessToken, profilePicUrl[0], new CallBackData<Customer>() {
                        @Override
                        public void onSuccess(Customer customer) {
                            if(customer != null){
                                mUserManagement = new UserManagement(getApplication());
                                customer.setImageUrl(profilePicUrl[0]);
                                Profile profile = getProfile();
                                customer.setFullName(profile.getName());
                                mUserManagement.addCustomer(customer);
                            }
                            OrderActivity.intentToOrderActivity(LoginActivity.this);
                        }

                        @Override
                        public void onFail(String message) {
                            Log.e("message", message);
                        }
                    });

                }

            }

            @Override
            public void onFail(String message) {
                Log.e("message", message);
            }
        });
    }

    public static void intentToLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public static Profile getProfile(){
        Profile profile = Profile.getCurrentProfile();
        mProfile = profile;
        return  mProfile;
    }
}
