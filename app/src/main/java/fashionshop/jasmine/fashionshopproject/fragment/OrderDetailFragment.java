package fashionshop.jasmine.fashionshopproject.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import fashionshop.jasmine.fashionshopproject.R;
import fashionshop.jasmine.fashionshopproject.activity.OrderActivity;
import fashionshop.jasmine.fashionshopproject.adapter.ColorAdapter;
import fashionshop.jasmine.fashionshopproject.adapter.MainSliderAdapter;
import fashionshop.jasmine.fashionshopproject.adapter.SizeAdapter;
import fashionshop.jasmine.fashionshopproject.fashionShopRepository.PicassoImageLoadingService;
import fashionshop.jasmine.fashionshopproject.model.Color;
import fashionshop.jasmine.fashionshopproject.model.ProductImage;
import fashionshop.jasmine.fashionshopproject.model.Products;
import fashionshop.jasmine.fashionshopproject.model.Size;
import fashionshop.jasmine.fashionshopproject.utils.CurrencyManagement;
import ss.com.bannerslider.ImageLoadingService;
import ss.com.bannerslider.Slider;


public class OrderDetailFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String BUNDLE_PRODUCT_OBJECT = "PRODUCT_OBJECT";
    private static final String BUNDLE_ORDER_ITEM = "ORDER_ITEM";
    private Products mProducts;
    private OnClickListener mListen;
    private View mView;
    private Button mBtnClose;
    private Button mBtnMinus, mBtnPlus;
    private LinearLayout mLnlAddToCart;
    private TextView mTxtQuantity;
    private Products mOrderItem;
    private RecyclerView mRecyclerViewSize;
    private RecyclerView mRecyclerViewColor;
    private TextView mTxtProductName, mTxtPrice;
    private Slider mSliderImg;
    private SizeAdapter mSizeAdapter;
    private ColorAdapter mColorAdapter;
    private ArrayList<String> mSizeList;
    private ArrayList<String> mColorList;

    public void setOnClickCart(OnClickListener listener) {
        this.mListen = listener;
    }

    public static OrderDetailFragment newInstance() {
        return new OrderDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_order_detail, container, false);
        initialView();
        initialData();
        Slider.init(new PicassoImageLoadingService());
        return mView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_dialog_close:
                dismiss();
                break;
            case R.id.button_minus_product:
                int quantityMinus = Integer.parseInt((String) mTxtQuantity.getText());
                if (quantityMinus >= 2) {
                    quantityMinus -= 1;
                }
                mTxtQuantity.setText(String.valueOf(quantityMinus));
                break;
            case R.id.button_plus_product:
                int quantityPlus = Integer.parseInt((String) mTxtQuantity.getText());
                quantityPlus += 1;
                mTxtQuantity.setText(String.valueOf(quantityPlus));
                break;
            case R.id.linear_layout_add_to_cart:
                clickAddToCart();
                break;
        }
    }

    public interface OnClickListener {
        void onClick();
    }

    public void initialView() {
        mBtnClose = mView.findViewById(R.id.button_dialog_close);
        mBtnClose.setOnClickListener(this);
        mBtnMinus = mView.findViewById(R.id.button_minus_product);
        mBtnMinus.setOnClickListener(this);
        mBtnPlus = mView.findViewById(R.id.button_plus_product);
        mBtnPlus.setOnClickListener(this);
        mTxtQuantity = mView.findViewById(R.id.text_view_product_quantity);
        mLnlAddToCart = mView.findViewById(R.id.linear_layout_add_to_cart);
        mLnlAddToCart.setOnClickListener(this);
        mRecyclerViewSize = mView.findViewById(R.id.recycle_view_size);
        mRecyclerViewSize.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerSize = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewSize.setLayoutManager(linearLayoutManagerSize);
        mRecyclerViewColor = mView.findViewById(R.id.recycle_view_color);
        mRecyclerViewColor.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerColor = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewColor.setLayoutManager(linearLayoutManagerColor);
        mTxtProductName = mView.findViewById(R.id.text_view_product_name);
        mTxtPrice = mView.findViewById(R.id.text_view_product_price);
        mSliderImg = mView.findViewById(R.id.banner_slider_product);

    }

    public void initialData() {
        Bundle bundle = getArguments();
        mProducts = (Products) bundle.getSerializable(BUNDLE_PRODUCT_OBJECT);
        mTxtProductName.setText(mProducts.getProductName());
        mTxtPrice.setText(CurrencyManagement.getPrice(mProducts.getProductPrice(), " đ"));
        mSliderImg.setAdapter(new MainSliderAdapter(mProducts.getProductImagesList()));
        if (mProducts.getSize() != null) {
            mSizeAdapter = new SizeAdapter(mProducts.getSizeItem(), getActivity());
            mRecyclerViewSize.setAdapter(mSizeAdapter);
        }
        if (mProducts.getColor() != null) {
            mColorAdapter = new ColorAdapter(mProducts.getColorItem(), getActivity());
            mRecyclerViewColor.setAdapter(mColorAdapter);

        }
    }

    private OrderActivity getOrderActivity() {
        return ((OrderActivity) getOrderActivity());
    }

    public void clickAddToCart() {
        Bundle bundle = new Bundle();
        mOrderItem = mProducts;
        for (Size size : mSizeAdapter.getmSizeList()) {
            if (size.isChecked() == true) {
                mOrderItem.setSize(size.getSizeName());
                break;
            }
        }
        for (Color color : mColorAdapter.getmColorList()){
            if(color.isChecked() == true){
                mOrderItem.setColor(color.getColorName());
                break;
            }
        }
        mOrderItem.setProductPrice(getFinalPrice());
        mOrderItem.setProductQuantity(Integer.parseInt(String.valueOf(mTxtQuantity.getText())));
        bundle.putSerializable(BUNDLE_ORDER_ITEM, mOrderItem);
        setArguments(bundle);
        if(mListen != null){
            mListen.onClick();
            dismiss();
        }
    }
    private double getFinalPrice(){
        double finalPrice = mProducts.getProductPrice();
        return  finalPrice;
    }
}
