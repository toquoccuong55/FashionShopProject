package fashionshop.jasmine.fashionshopproject.activity;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.OrderItemManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.UserManagement;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepository;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.fashionRepositoryImpl;
import fashionshop.jasmine.fashionshopproject.fragment.ProductFragment;
import fashionshop.jasmine.fashionshopproject.model.Category;
import fashionshop.jasmine.fashionshopproject.model.CategoryItem;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.room.entities.Customer;
import fashionshop.jasmine.fashionshopproject.room.entities.OrderItemEntity;
import fashionshop.jasmine.fashionshopproject.utils.CallBackData;
import fashionshop.jasmine.fashionshopproject.utils.CurrencyManagement;
import fashionshop.jasmine.fashionshopproject.utils.FacebookHelper;

public class OrderActivity extends BaseActivity implements ProductFragment.OnFragmentInteractionListener, View.OnClickListener {
    private Toolbar mToolbar;
    public List<OrderItemEntity> mShoppingCart;
    private TextView mTxtTotalAndItems;
    private LinearLayout mLnlCheckout;
    private FragmentPagerItemAdapter mAdapter;
    private OrderItemManagement mOrderItemManagement;
    private fashionRepository mRepository;
    private static final String CATEGORY_ITEM = "CATEGORY_ITEM";
    private static final String CURRENCY = "đ";
    private UserManagement mUserManagement;
    private TextView mTxtCustomerName;
    private CircleImageView mImgAvatar;
    private ImageView mImgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initialView();
        initialData();
    }

    public void initialView() {
//        mToolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Danh sách sản phẩm");
        mLnlCheckout = findViewById(R.id.linear_checkout_cart);
        mTxtTotalAndItems = findViewById(R.id.text_view_total_and_items);
        mLnlCheckout.setOnClickListener(this);
        mTxtCustomerName = findViewById(R.id.text_view_customer_name);
        mImgAvatar = findViewById(R.id.image_view_avatar_profile);
        mImgLogout = findViewById(R.id.image_view_logout);
        mImgLogout.setOnClickListener(this);
    }

    private void initialSmartTabLayout() {

        mRepository = new fashionRepositoryImpl();
        mRepository.getListProduct(this, new CallBackData<List<CategoryItem>>() {
            @Override
            public void onSuccess(List<CategoryItem> categoryItemsList) {
                FragmentPagerItems.Creator creator = FragmentPagerItems.with(getApplicationContext());
                for (CategoryItem categoryItem : categoryItemsList) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CATEGORY_ITEM, categoryItem);
                    creator.add(categoryItem.getCategory().getCateName(), ProductFragment.class, bundle);

                }
                mAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(mAdapter);
                viewPager.setOffscreenPageLimit(categoryItemsList.size());
                SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.view_pager_tab);
                viewPagerTab.setViewPager(viewPager);
            }

            @Override
            public void onFail(String message) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linear_checkout_cart:
                //CartMainActivity.intentToCartActivity(OrderActivity.this);
                CartActivity.intentToCartActivity(OrderActivity.this);
                break;
            case R.id.image_view_logout:
                showDialogLogout();
                break;
        }
    }

    private void showDialogLogout() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_confirm_logout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOk = dialog.findViewById(R.id.button_logout_ok);
        Button btnCancel = dialog.findViewById(R.id.button_logout_cancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                dialog.cancel();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void logOut() {
        FacebookHelper.logOutFacebook(this);
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getCustomer().observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                if (customer != null) {
                    mUserManagement.deleteCustomer(customer);
                    SplashActivity.intentToSplashActivity(OrderActivity.this);
                }
            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static void intentToOrderActivity(Activity activity) {
        Intent intent = new Intent(activity, OrderActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void showCheckOutCartButton() {
        //show the checkout button

        if (mShoppingCart.isEmpty()) {
            mLnlCheckout.setVisibility(View.GONE);
        } else {
            mLnlCheckout.setVisibility(View.VISIBLE);
            mTxtTotalAndItems.setText(CurrencyManagement.getPrice(getCartTotalAmount(), CURRENCY)
                    + "  •  " + getCartItemQuantity());
        }
    }

    private int getCartItemQuantity() {
        int count = 0;
        if (mShoppingCart != null) {
            for (int i = 0; i < mShoppingCart.size(); i++) {
                count += mShoppingCart.get(i).getProducts().getProductQuantity();
            }
        }
        return count;
    }

    private double getCartTotalAmount() {
        double totalAmount = 0;
        if (mShoppingCart != null) {
            for (int i = 0; i < mShoppingCart.size(); i++) {
                Products products = mShoppingCart.get(i).getProducts();
                totalAmount += products.getProductPrice() * products.getProductQuantity();
            }
        }
        return totalAmount;
    }

    private void initialData() {
        mUserManagement = new UserManagement(getApplication());
        mUserManagement.getCustomer().observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                if (customer != null) {
                    if (customer.getImageUrl() != null) {
                        Picasso.get().load(customer.getImageUrl()).error(R.mipmap.ic_hanger).placeholder(R.mipmap.ic_hanger).into(mImgAvatar);
                    }
                    mTxtCustomerName.setText(customer.getFullName());
                }
            }
        });
        if (mShoppingCart == null) {
            mShoppingCart = new ArrayList<>();
            getOrderItemsFromRoom();

        }
    }

    private void getOrderItemsFromRoom() {
        if (mOrderItemManagement == null) {
            mOrderItemManagement = new OrderItemManagement(getApplication());
        }
        mOrderItemManagement.getAllOrderitem().observe(this, new Observer<List<OrderItemEntity>>() {
            @Override
            public void onChanged(@Nullable List<OrderItemEntity> orderItemEntities) {
                if (orderItemEntities != null) {
                    mShoppingCart = orderItemEntities;
                }

            }
        });
        initialSmartTabLayout();
    }

}
