package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fashionshop.jasmine.fashionshopproject.R;

public class CartMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_main);
    }
    public static void intentToCartActivity(Activity activity) {
        Intent intent = new Intent(activity, CartMainActivity.class);
        activity.startActivity(intent);
    }
}
