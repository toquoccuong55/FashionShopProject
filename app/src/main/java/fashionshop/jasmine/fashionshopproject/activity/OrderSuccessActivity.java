package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fashionshop.jasmine.fashionshopproject.R;

public class OrderSuccessActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnOrderMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        initialView();
    }
    public static void intentToOrderActivity(Activity activity) {
        Intent intent = new Intent(activity, OrderSuccessActivity.class);
        activity.startActivity(intent);
        activity.finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_order_more:
                OrderActivity.intentToOrderActivity(OrderSuccessActivity.this);
                break;
        }
    }
    public void initialView(){
        mBtnOrderMore = findViewById(R.id.button_order_more);
        mBtnOrderMore.setOnClickListener(this);
    }
}
