package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.login.LoginManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.UserManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepository;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2000;
    private fashionRepository mPassioRepository;
    private UserManagement mUserManagement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        delaySplashScreen();
    }

    private void printKeyHash() {
        try {
            PackageInfo info = getPackageManager()
                    .getPackageInfo("fashionshop.jasmine.fashionshopproject",
                            PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KEYHASH", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    private void delaySplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mUserManagement = new UserManagement(getApplication());
                mUserManagement.getCustomer().observe(SplashActivity.this, new Observer<Customer>() {
                    @Override
                    public void onChanged(@Nullable Customer customer) {
                        if (customer == null) {
                            LoginActivity.intentToLoginActivity(SplashActivity.this);
                            Log.i("SLASH", "đã vô login");
                        } else {
                            OrderActivity.intentToOrderActivity(SplashActivity.this);
                            LoginManager.getInstance().logOut();
                            Log.i("SLASH", "đã vô home");
                        }
                    }
                });

            }
        }, SPLASH_TIME_OUT);
    }
    public static void intentToSplashActivity(Activity activity) {
        Intent intent = new Intent(activity, SplashActivity.class);
        activity.startActivity(intent);
    }

}
