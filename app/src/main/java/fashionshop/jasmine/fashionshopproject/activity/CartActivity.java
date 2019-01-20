package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.adapter.OrderAdapter;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.OrderItemManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.ShippingAddressManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.UserManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepository;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepositoryImpl;
import fashionshop.jasmine.fashionshopproject.model.Order;
import fashionshop.jasmine.fashionshopproject.model.OrderItem;
import fashionshop.jasmine.fashionshopproject.model.OrderResult;
import fashionshop.jasmine.fashionshopproject.model.ShippingAddress;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;
import fashionshop.jasmine.fashionshopproject.room.entities.ShippingAddressEntity;
import fashionshop.jasmine.fashionshopproject.utils.CallBackData;
import fashionshop.jasmine.fashionshopproject.utils.CurrencyManagement;

public class CartActivity extends BaseActivity implements View.OnClickListener {
    private static final String BUNDLE_SHIPPING_ADDRESS = "STORE_ADDRESS";
    private static final String BUNDLE = "BUNDLE";
    private static final String CURRENCY = "đ";
    private RecyclerView mRecylerViewOrder;
    private OrderAdapter mOrderAdapter;
    private Toolbar mToolbar;
    private OrderItemManagement mOrderItemManagement;
    private ArrayList<OrderItemEntity> mShoppingCart;
    private TextView mTotalCartPrice;
    private EditText mEdtShipingAddress;
    private ShippingAddress mShippingAddress;
    private ShippingAddressManagement mShippingAddressManagement;
    private UserManagement mUserManagerment;
    private LinearLayout mLnlAddtoCart;
    private ShippingAddressEntity mShippingEntity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initialView();
        initialData();
    }

    public void initialView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");
        mRecylerViewOrder = (RecyclerView) findViewById(R.id.recycle_view_cart);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecylerViewOrder.setLayoutManager(linearLayoutManager);
        mRecylerViewOrder.setHasFixedSize(true);
        mTotalCartPrice = findViewById(R.id.text_view_total);
        mEdtShipingAddress = findViewById(R.id.edit_text_select_shipping_address);
        mEdtShipingAddress.setOnClickListener(this);
        mLnlAddtoCart = findViewById(R.id.linear_layout_add_to_cart);
        mLnlAddtoCart.setOnClickListener(this);

    }

    public static void intentToCartActivity(Activity activity) {
        Intent intent = new Intent(activity, CartActivity.class);
        activity.startActivity(intent);

    }

    private void initialData() {
        if (mOrderItemManagement == null) {
            mOrderItemManagement = new OrderItemManagement(getApplication());
        }
        mOrderItemManagement.getAllOrderitem().observe(this, new Observer<List<OrderItemEntity>>() {
            @Override
            public void onChanged(@Nullable List<OrderItemEntity> orderItemEntities) {
                if (orderItemEntities != null) {
                    mShoppingCart = (ArrayList<OrderItemEntity>) orderItemEntities;
                    if (mShoppingCart != null) {
                        mOrderAdapter = new OrderAdapter(mShoppingCart, CartActivity.this);
                        mRecylerViewOrder.setAdapter(mOrderAdapter);
                    }
                    mTotalCartPrice.setText(CurrencyManagement.getPrice(getTotalPrice(), CURRENCY));

//                    Intent intent = getIntent();
//                    Bundle bundle = intent.getBundleExtra(BUNDLE);
//                    if (bundle != null) {
//                        mShippingAddress = (ShippingAddress) bundle.getSerializable(BUNDLE_SHIPPING_ADDRESS);
//                        mEdtShipingAddress.setText("Địa chỉ: " + mShippingAddress.getDeliveryAddress() + "\n" + "Tên người nhận: " + mShippingAddress.getReceiverName()
//                                + "\n" + "Số điện thoại: " + mShippingAddress.getReceiverPhone());
//                    }
                    mShippingAddressManagement = new ShippingAddressManagement(getApplication());
                    mShippingAddressManagement.getShipping().observe(CartActivity.this, new Observer<ShippingAddressEntity>() {
                        @Override
                        public void onChanged(@Nullable ShippingAddressEntity shippingAddressEntity) {
                            mShippingEntity = shippingAddressEntity;
                            if(shippingAddressEntity != null){
                                mEdtShipingAddress.setText("Địa chỉ: " + shippingAddressEntity.getShippingAddress() + "\n" + "Tên người nhận: " + shippingAddressEntity.getReceiverName()
                                        + "\n" + "Số điện thoại: " + shippingAddressEntity.getReceiverPhone());
                            }
                        }
                    });
                }
            }
        });
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for (OrderItemEntity orderItem : mShoppingCart) {
            totalPrice += (orderItem.getProducts().getProductPrice() * orderItem.getProducts().getProductQuantity());
        }
        return totalPrice;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                OrderActivity.intentToOrderActivity(CartActivity.this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_text_select_shipping_address:
                SelectShippingAddressActivity.intentToShippingActivity(CartActivity.this);
                break;
            case R.id.linear_layout_add_to_cart:
                clickToLnlOrder();
                break;
        }
    }

    private void clickToLnlOrder() {
        if (!mShoppingCart.isEmpty() || mShippingEntity != null) {
            Order order = new Order();
            ArrayList<OrderItem> orderDetail = new ArrayList<>();
            if(mShoppingCart.isEmpty()){
                Toast.makeText(this,"Giỏ hàng hiện đang rỗng", Toast.LENGTH_SHORT).show();
            }
            for (OrderItemEntity orderItemInCart : mShoppingCart) {
                OrderItem orderItemDB = new OrderItem();
                orderItemDB.setProductId(orderItemInCart.getProducts().getProductId());
                orderItemDB.setQuantity(orderItemInCart.getProducts().getProductQuantity());
                orderItemDB.setColor(orderItemInCart.getProducts().getColor());
                orderItemDB.setSize(orderItemInCart.getProducts().getSize());
                orderDetail.add(orderItemDB);
            }
            order.setOrderDetails(orderDetail);
            if(mShippingEntity != null){
                order.setDeliveryAddress(mShippingEntity.getShippingAddress());
                order.setReceiverName(mShippingEntity.getReceiverName());
                order.setReceiverPhone(mShippingEntity.getReceiverPhone());
                sendOrderToServer(order);
            }
            else{
                Toast.makeText(this,"Vui lòng nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            }
        }else{

        }
    }

    private void sendOrderToServer(final Order order) {
        mUserManagerment = new UserManagement(getApplication());
        mUserManagerment.getCustomer().observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                fashionRepository  fashionRepository = new fashionRepositoryImpl();
                String accessToken = customer.getAccessToken();
                fashionRepository.SetOrder(CartActivity.this, order, accessToken, new CallBackData<OrderResult>() {
                    @Override
                    public void onSuccess(OrderResult orderResult) {
                        if (orderResult.getInvoice_id() != null) {
                            if(mOrderItemManagement == null){
                                mOrderItemManagement = new OrderItemManagement(getApplication());
                            }
                            for (OrderItemEntity orderItem : mShoppingCart){
                                mOrderItemManagement.deleteOrderItem(orderItem);
                            }
                            mShoppingCart.clear();
                            OrderSuccessActivity.intentToOrderActivity(CartActivity.this);
                        }
                    }

                    @Override
                    public void onFail(String message) {

                    }
                });
            }
        });
    }
}
