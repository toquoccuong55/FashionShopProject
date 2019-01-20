package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;
import java.util.regex.Pattern;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.ShippingAddressManagement;
import fashionshop.jasmine.fashionshopproject.model.ShippingAddress;
import fashionshop.jasmine.fashionshopproject.room.entities.ShippingAddressEntity;

public class SelectShippingAddressActivity extends BaseActivity implements View.OnClickListener {
    private static final String BUNDLE_SHIPPING_ADDRESS = "STORE_ADDRESS";
    private static final String BUNDLE = "BUNDLE";
    private Toolbar mToolBar;
    private Button mBtnSaveAddress;
    private ShippingAddress shippingAddress;
    private EditText mEdtAddress, mEdtPhone, mEdtName;
    private ShippingAddressEntity mShippingEntity;
    private static final Pattern PATTERN
            = Pattern.compile("^[0-9]{10}");
    private ShippingAddressManagement mShippingAddressManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_shipping_address);
        initialView();
        initialData();
    }

    public void initialView() {
        mToolBar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin giao hàng");
        mBtnSaveAddress = findViewById(R.id.button_save_address);
        mBtnSaveAddress.setOnClickListener(this);
        mEdtAddress = findViewById(R.id.edit_text_address);
        mEdtPhone = findViewById(R.id.edit_text_phone);
        mEdtName = findViewById(R.id.edit_text_customer_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void intentToShippingActivity(Activity activity) {
        Intent intent = new Intent(activity, SelectShippingAddressActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_save_address:
                String shippingId = UUID.randomUUID().toString();
                String name = mEdtName.getText().toString();
                String phone = mEdtPhone.getText().toString();
                String address = mEdtAddress.getText().toString();
                if(mShippingAddressManagement == null){
                    mShippingAddressManagement = new ShippingAddressManagement(getApplication());
                }

                if (name.equals("") || phone.equals("") || address.equals("")) {
                    Toast.makeText(SelectShippingAddressActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();

                } else if (!isValid()) {
                    Toast.makeText(SelectShippingAddressActivity.this, "Vui lòng nhập số điện thoại chính xác", Toast.LENGTH_SHORT).show();

                } else if (mShippingEntity == null) {
                    mShippingEntity = new ShippingAddressEntity(shippingId, address, name, phone);
                    mShippingAddressManagement.addShippingAddress(mShippingEntity);
                    CartActivity.intentToCartActivity(SelectShippingAddressActivity.this);
                }else if (mShippingEntity != null){
                    mShippingEntity = new ShippingAddressEntity(mShippingEntity.getShippingAddressId(), address, name, phone);
                    mShippingAddressManagement.updateShippingAddress(mShippingEntity);
                    CartActivity.intentToCartActivity(SelectShippingAddressActivity.this);
                }

                break;
        }
    }

    private boolean isValid() {
        return PATTERN.matcher(mEdtPhone.getText().toString().trim()).matches();
    }
    private void initialData(){
        mShippingAddressManagement = new ShippingAddressManagement(getApplication());
        mShippingAddressManagement.getShipping().observe(this, new Observer<ShippingAddressEntity>() {
            @Override
            public void onChanged(@Nullable ShippingAddressEntity shippingAddressEntity) {
                if (shippingAddressEntity != null) {
                    mShippingEntity = shippingAddressEntity;
                    mEdtAddress.setText(shippingAddressEntity.getShippingAddress());
                    mEdtName.setText(shippingAddressEntity.getReceiverName());
                    mEdtPhone.setText(shippingAddressEntity.getReceiverPhone());
                }
            }
        });
    }
}
